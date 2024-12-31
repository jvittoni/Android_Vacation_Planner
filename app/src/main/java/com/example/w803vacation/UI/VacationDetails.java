package com.example.w803vacation.UI;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.w803vacation.R;
import com.example.w803vacation.database.Repository;
import com.example.w803vacation.entities.Excursion;
import com.example.w803vacation.entities.Vacation;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class VacationDetails extends AppCompatActivity {

    String name;
    String hotel;
    int vacationID;
    EditText editName;
    EditText editHotel;


    String vacationStartDate;
    String vacationEndDate;

    TextView editVacaStartDate;
    TextView editVacaEndDate;

    DatePickerDialog.OnDateSetListener vacaStartDate;
    DatePickerDialog.OnDateSetListener vacaEndDate;
    final Calendar myCalendarStart = Calendar.getInstance();
    final Calendar myCalendarEnd = Calendar.getInstance();

    Vacation currentVacation;
    int numExcursions;

    // Notification Alert
    Random rand = new Random();
    int numAlert = rand.nextInt(99999);

    List<Excursion> filteredExcursions = new ArrayList<>();


    Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_vacation_details);

        repository = new Repository(getApplication());

        editName = findViewById(R.id.titletext);
        editHotel = findViewById(R.id.hoteltext);
        vacationID = getIntent().getIntExtra("id", -1);
        name = getIntent().getStringExtra("name");
        hotel = getIntent().getStringExtra("hotel");
        editName.setText(name);
        editHotel.setText(hotel);

        // Vacation Start Date & End Date
        editVacaStartDate = findViewById(R.id.selectStart);
        editVacaEndDate = findViewById(R.id.selectEnd);
        vacationStartDate = getIntent().getStringExtra("vacationStartDate");
        vacationEndDate = getIntent().getStringExtra("vacationEndDate");
        editVacaStartDate.setText(vacationStartDate);
        editVacaEndDate.setText(vacationEndDate);

        // Format Validation
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        numAlert = rand.nextInt(99999);


        FloatingActionButton fab = findViewById(R.id.floatingActionButton2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(VacationDetails.this, ExcursionDetails.class);
                intent.putExtra("vacationID", vacationID);
                intent.putExtra("vacationStartDate", vacationStartDate);
                intent.putExtra("vacationEndDate", vacationEndDate);
                startActivity(intent);
            }
        });



//        RecyclerView recyclerView = findViewById(R.id.excursionrecyclerview);
//        repository = new Repository(getApplication());
//        final ExcursionAdapter excursionAdapter = new ExcursionAdapter(this);
//        recyclerView.setAdapter(excursionAdapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        excursionAdapter.setExcursions(repository.getxAllExcursions());

//        RecyclerView recyclerView = findViewById(R.id.excursionrecyclerview);
//        repository = new Repository(getApplication());
//        final ExcursionAdapter excursionAdapter = new ExcursionAdapter(this);
//        recyclerView.setAdapter(excursionAdapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        List<Excursion> filteredExcursions = new ArrayList<>();
//        for (Excursion e : repository.getxAllExcursions()) {
//            if (e.getVacationID() == vacationID) filteredExcursions.add(e);
//        }
//        excursionAdapter.setExcursions(filteredExcursions);


        RecyclerView recyclerView = findViewById(R.id.excursionrecyclerview);
        repository = new Repository(getApplication());
        final ExcursionAdapter excursionAdapter = new ExcursionAdapter(this);
        recyclerView.setAdapter(excursionAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        for (Excursion e: repository.getxAllExcursions()) {
            if (e.getVacationID() == vacationID) filteredExcursions.add(e);
        }
        excursionAdapter.setExcursions(filteredExcursions);

        editVacaStartDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Date date;
                String info = editVacaStartDate.getText().toString();

                if(info.equals(""))info="12/01/24";
                try{
                    myCalendarStart.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                new DatePickerDialog(VacationDetails.this, vacaStartDate, myCalendarStart
                        .get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH),
                        myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        editVacaEndDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Date date;
                String info = editVacaEndDate.getText().toString();

                if(info.equals(""))info="12/01/24";
                try{
                    myCalendarEnd.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                new DatePickerDialog(VacationDetails.this, vacaEndDate, myCalendarEnd
                        .get(Calendar.YEAR), myCalendarEnd.get(Calendar.MONTH),
                        myCalendarEnd.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

        vacaStartDate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                myCalendarStart.set(Calendar.YEAR, year);
                myCalendarStart.set(Calendar.MONTH, monthOfYear);
                myCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateLabelStart();
            }

        };

        vacaEndDate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                myCalendarEnd.set(Calendar.YEAR, year);
                myCalendarEnd.set(Calendar.MONTH, monthOfYear);
                myCalendarEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                // Validate that vacation end date is after vacation start date
                if (myCalendarEnd.before(myCalendarStart)) {
                    Toast.makeText(VacationDetails.this, "Vacation end date must be after vacation start date.", Toast.LENGTH_LONG).show();
                    return;
                }

                updateLabelEnd();
            }

        };

    }

    private void updateLabelStart() {

        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editVacaStartDate.setText(sdf.format(myCalendarStart.getTime()));
    }

    private void updateLabelEnd() {

        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editVacaEndDate.setText(sdf.format(myCalendarEnd.getTime()));
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_vacationdetails, menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        if (item.getItemId() == R.id.vacationsave) {
            Vacation vacation;
            if (vacationID == -1) {
                if (repository.getxAllVacations().size() == 0) vacationID = 1;
                else
                    vacationID = repository.getxAllVacations().get(repository.getxAllVacations().size() - 1).getVacationID() + 1;
                vacation = new Vacation(vacationID, editName.getText().toString(), editHotel.getText().toString(), editVacaStartDate.getText().toString(), editVacaEndDate.getText().toString());
                repository.insert(vacation);
                this.finish();
            } else {
                vacation = new Vacation(vacationID, editName.getText().toString(), editHotel.getText().toString(), editVacaStartDate.getText().toString(), editVacaEndDate.getText().toString());
                repository.update(vacation);
                this.finish();
            }
        }
        if (item.getItemId() == R.id.vacationdelete) {
            for (Vacation vaca : repository.getxAllVacations()) {
                if (vaca.getVacationID() == vacationID) currentVacation = vaca;
            }

            numExcursions = 0;
            for (Excursion excursion : repository.getxAllExcursions()) {
                if (excursion.getVacationID() == vacationID) ++numExcursions;
            }

            if (numExcursions == 0) {
                repository.delete(currentVacation);
                Toast.makeText(VacationDetails.this, currentVacation.getVacationName() + " was deleted", Toast.LENGTH_LONG).show();
                VacationDetails.this.finish();
            } else {
                Toast.makeText(VacationDetails.this, "Can't delete a Vacation with excursions", Toast.LENGTH_LONG).show();
            }
            return true;
        }

        // Set Vacation Start Date Alert
        if (item.getItemId() == R.id.alertstartvaca) {
            String dateFromScreen = editVacaStartDate.getText().toString();
            String alert = "Vacation Starting Today: " + name;
            alertPicker(dateFromScreen, alert);

            return true;
        }

        // Set Vacation End Date Alert
        if (item.getItemId() == R.id.alertendvaca) {
            String dateFromScreen = editVacaEndDate.getText().toString();
            String alert = "Vacation Ending Today: " + name;
            alertPicker(dateFromScreen, alert);

            return true;
        }


        // Set Vacation Start Date and End Date Alert
        if (item.getItemId() == R.id.alertbothvaca) {
            String dateFromScreen = editVacaStartDate.getText().toString();
            String alert = "Vacation Starting Today: " + name;
            alertPicker(dateFromScreen, alert);
            dateFromScreen = editVacaEndDate.getText().toString();
            alert = "Vacation Ending Today: " + name;
            alertPicker(dateFromScreen, alert);

            return true;
        }

        // Sharing Features
        if (item.getItemId() == R.id.sharevaca) {
            Intent sentIntent = new Intent();
            sentIntent.setAction(Intent.ACTION_SEND);
            sentIntent.putExtra(Intent.EXTRA_TITLE, "Sharing Vacation Details");
            StringBuilder shareData = new StringBuilder();
            shareData.append("Vacation Title: " + editName.getText().toString() + "\n");
            shareData.append("Hotel Name: " + editHotel.getText().toString() + "\n");
            shareData.append("Start Date: " + editVacaStartDate.getText().toString() + "\n");
            shareData.append("End Date: " + editVacaEndDate.getText().toString() + "\n");
            for (int i = 0; i < filteredExcursions.size(); i++) {
                shareData.append((i + 1) + ") " + "Excursion Title: " + filteredExcursions.get(i).getExcursionName() + "\n");
                shareData.append((i + 1) + ") " + "Excursion Date: " + filteredExcursions.get(i).getExcursionDate() + "\n");
            }
            sentIntent.putExtra(Intent.EXTRA_TEXT, shareData.toString());
            sentIntent.setType("text/plain");
            Intent shareIntent = Intent.createChooser(sentIntent, null);
            startActivity(shareIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Used for Vacation Start Date Alert, Vacation End Date Alert, & Both Vacation Date Alert
    public void alertPicker(String dateFromScreen, String alert) {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        Date myDate = null;
        try {
            myDate = sdf.parse(dateFromScreen);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Long trigger = myDate.getTime();
        Intent intent = new Intent(VacationDetails.this, MyReceiver.class);
        intent.putExtra("key", alert);
//        PendingIntent sender = PendingIntent.getBroadcast(VacationDetails.this, ++MainActivity.numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
        PendingIntent sender = PendingIntent.getBroadcast(VacationDetails.this, numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
        numAlert = rand.nextInt(99999);
        System.out.println("numAlert Vacation = " + numAlert);
    }

    @Override
    protected void onResume() {
        super.onResume();

        //finds the excursions associated with the vacation and populates the RecyclerView list with it
        RecyclerView recyclerView = findViewById(R.id.excursionrecyclerview);
        repository = new Repository(getApplication());
        final ExcursionAdapter excursionAdapter = new ExcursionAdapter(this);
        recyclerView.setAdapter(excursionAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Excursion> filteredExcursions = new ArrayList<>();
        for (Excursion e: repository.getxAllExcursions()) {
            if (e.getVacationID() == vacationID) filteredExcursions.add(e);
        }
        excursionAdapter.setExcursions(filteredExcursions);

        updateLabelStart();
        updateLabelEnd();
    }



}