package com.example.w803vacation.UI;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.w803vacation.R;
import com.example.w803vacation.database.Repository;
import com.example.w803vacation.entities.Excursion;
import com.example.w803vacation.entities.Vacation;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class VacationDetails extends AppCompatActivity {

    String name;
    String hotel;
    int vacationID;
    EditText editName;
    EditText editHotel;


    String startVacationDate;
    String endVacationDate;

    TextView editStartVacaDate;
    TextView editEndVacaDate;

    DatePickerDialog.OnDateSetListener startVacaDate;
    DatePickerDialog.OnDateSetListener endVacaDate;
    final Calendar myCalendarStart = Calendar.getInstance();
    final Calendar myCalendarEnd = Calendar.getInstance();

    Vacation currentVacation;
    int numExcursions;

    Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_vacation_details);

        editName = findViewById(R.id.titletext);
        editHotel = findViewById(R.id.hoteltext);
        vacationID = getIntent().getIntExtra("id", -1);
        name = getIntent().getStringExtra("name");
        hotel = getIntent().getStringExtra("hotel");
        editName.setText(name);
        editHotel.setText(hotel);

        //
        editStartVacaDate = findViewById(R.id.selectStart);
        editEndVacaDate = findViewById(R.id.selectEnd);
        startVacationDate = getIntent().getStringExtra("startVacationDate");
        endVacationDate = getIntent().getStringExtra("endVacationDate");
        editStartVacaDate.setText(startVacationDate);
        editEndVacaDate.setText(endVacationDate);


        FloatingActionButton fab = findViewById(R.id.floatingActionButton2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VacationDetails.this, ExcursionDetails.class);
                startActivity(intent);
            }
        });

//        RecyclerView recyclerView = findViewById(R.id.excursionrecyclerview);
//        repository = new Repository(getApplication());
//        final ExcursionAdapter excursionAdapter = new ExcursionAdapter(this);
//        recyclerView.setAdapter(excursionAdapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        excursionAdapter.setExcursions(repository.getxAllExcursions());

        RecyclerView recyclerView = findViewById(R.id.excursionrecyclerview);
        repository = new Repository(getApplication());
        final ExcursionAdapter excursionAdapter = new ExcursionAdapter(this);
        recyclerView.setAdapter(excursionAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Excursion> filteredExcursions = new ArrayList<>();
        for (Excursion p : repository.getxAllExcursions()) {
            if (p.getVacationID() == vacationID) filteredExcursions.add(p);
        }
        excursionAdapter.setExcursions(filteredExcursions);

        editStartVacaDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String info = editStartVacaDate.getText().toString();

                // If the field is empty, set a default value
                if(info.equals("")) {
                    info = "02/10/24"; // Default value, could also use current date
                }

                // Instead of parsing, directly set the calendar to the default date or current date
                myCalendarStart.setTime(new Date());
                new DatePickerDialog(VacationDetails.this, startVacaDate, myCalendarStart
                        .get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH),
                        myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        editEndVacaDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String info = editEndVacaDate.getText().toString();

                // If the field is empty, set a default value
                if(info.equals("")) {
                    info = "02/10/24"; // Default value, could also use current date
                }

                // Instead of parsing, directly set the calendar to the default date or current date
                myCalendarEnd.setTime(new Date());
                new DatePickerDialog(VacationDetails.this, endVacaDate, myCalendarEnd
                        .get(Calendar.YEAR), myCalendarEnd.get(Calendar.MONTH),
                        myCalendarEnd.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

        startVacaDate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub

                myCalendarStart.set(Calendar.YEAR, year);
                myCalendarStart.set(Calendar.MONTH, monthOfYear);
                myCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);


                updateLabelStart();
            }

        };

        endVacaDate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub

                myCalendarEnd.set(Calendar.YEAR, year);
                myCalendarEnd.set(Calendar.MONTH, monthOfYear);
                myCalendarEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);


                updateLabelEnd();
            }

        };

    }

    private void updateLabelStart() {
        // Directly set the text without any date format validation
        editStartVacaDate.setText(myCalendarStart.getTime().toString());
    }

    private void updateLabelEnd() {
        // Directly set the text without any date format validation
        editEndVacaDate.setText(myCalendarEnd.getTime().toString());
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
                vacation = new Vacation(vacationID, editName.getText().toString(), editHotel.getText().toString(), editStartVacaDate.getText().toString(), editEndVacaDate.getText().toString());
                repository.insert(vacation);
                this.finish();
            } else {
                vacation = new Vacation(vacationID, editName.getText().toString(), editHotel.getText().toString(), editStartVacaDate.getText().toString(), editEndVacaDate.getText().toString());
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
        return super.onOptionsItemSelected(item);
    }

}