package com.example.w803vacation.UI;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.w803vacation.R;
import com.example.w803vacation.database.Repository;
import com.example.w803vacation.entities.Excursion;
import com.example.w803vacation.entities.Vacation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ExcursionDetails extends AppCompatActivity {

    String title;
    int excursionID;
    int vacaID;
    String vacationStartDate;
    String endVacationDate;
    EditText editName;
    TextView editExcursionDate;
    String setExcursionDate;

    Excursion currentExcursion;
    Repository repository;
    Date startStartDate = null;
    Date endEndDate = null;
    DatePickerDialog.OnDateSetListener excursionStartDate;
    final Calendar myCalendarExcursionDate = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_excursion_details);

        repository = new Repository(getApplication());
        title = getIntent().getStringExtra("name");
        setExcursionDate = getIntent().getStringExtra("excursionDate");
        editName = findViewById(R.id.excursionTitle);
        editName.setText(title);
        excursionID = getIntent().getIntExtra("id", -1);
        vacaID = getIntent().getIntExtra("vacationID", -1);
        vacationStartDate = getIntent().getStringExtra("startVacationDate");
        endVacationDate = getIntent().getStringExtra("endVacationDate");
        editExcursionDate = findViewById(R.id.excursiondate);
        editExcursionDate.setText(setExcursionDate);

        // Date Format Validation
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);


        ArrayList<Vacation> vacationArrayList = new ArrayList<>();
        vacationArrayList.addAll(repository.getxAllVacations());
        ArrayList<Integer> vacationIdList = new ArrayList<>();
        for (Vacation vacation : vacationArrayList) {
            vacationIdList.add(vacation.getVacationID());
        }

        // Spinner for vacation id
        ArrayAdapter<Integer> vacationIdAdapter= new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item,vacationIdList);
        Spinner spinner=findViewById(R.id.spinner);
        spinner.setAdapter(vacationIdAdapter);
        spinner.setSelection(vacaID - 1);

        Log.d("DebugTag", "vacationIdList size: " + vacationIdList.size());
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("DebugTag", "Selected position: " + position + ", List size: " + vacationIdList.size());
                if (position >= 0 && position < vacationIdList.size()) {
                    vacaID = vacationIdList.get(position);
                } else {
                    Log.e("DebugTag", "Invalid position: " + position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        if (setExcursionDate != null) {
            try {
                Date excursionDate = sdf.parse(setExcursionDate);
                myCalendarExcursionDate.setTime(excursionDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        editExcursionDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Date date;

                String info = editExcursionDate.getText().toString();
                if(info.equals("")) info = setExcursionDate;
                try {
                    myCalendarExcursionDate.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

//                myCalendarExcursionStart.setTime(new Date());
                new DatePickerDialog(
                        ExcursionDetails.this, excursionStartDate, myCalendarExcursionDate
                        .get(Calendar.YEAR), myCalendarExcursionDate.get(Calendar.MONTH),
                        myCalendarExcursionDate.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        excursionStartDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendarExcursionDate.set(Calendar.YEAR, year);
                myCalendarExcursionDate.set(Calendar.MONTH, monthOfYear);
                myCalendarExcursionDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelExcursionStart();
            }

        };

    }

    private void updateLabelExcursionStart() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        editExcursionDate.setText(sdf.format(myCalendarExcursionDate.getTime()));
//        editExcursionDate.setText(myCalendarExcursionStart.getTime().toString());
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_excursiondetails, menu);
        return true;
    }



    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId()== android.R.id.home){
            this.finish();
            return true;
        }

        if (item.getItemId()== R.id.excursionsave){
            Excursion excursion;
            if (excursionID == -1) {
                if (repository.getxAllExcursions().size() == 0)
                    excursionID = 1;
                else
                    excursionID = repository.getxAllExcursions().get(repository.getxAllExcursions().size() - 1).getExcursionID() + 1;
                excursion = new Excursion(excursionID, editName.getText().toString(), vacaID, editExcursionDate.getText().toString());
                repository.insert(excursion);
                this.finish();
            } else {
                excursion = new Excursion(excursionID, editName.getText().toString(), vacaID, editExcursionDate.getText().toString());
                repository.update(excursion);
                this.finish();
            }
            return true;
        }

        if (item.getItemId() == R.id.excursiondelete) {
            for (Excursion excursion : repository.getxAllExcursions()) {
                if (excursion.getExcursionID() == excursionID) currentExcursion = excursion;
            }
            repository.delete(currentExcursion);
            Toast.makeText(ExcursionDetails.this, currentExcursion.getExcursionName() + " has been deleted", Toast.LENGTH_LONG).show();
            ExcursionDetails.this.finish();
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        updateLabelExcursionStart();
    }
}