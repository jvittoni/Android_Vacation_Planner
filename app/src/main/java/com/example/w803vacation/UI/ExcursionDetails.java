package com.example.w803vacation.UI;

import android.app.DatePickerDialog;
import android.content.Intent;
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
    TextView editDate;
    String excursionDate;

    Excursion currentExcursion;
    Repository repository;
    Date startStartDate = null;
    Date endEndDate = null;
    DatePickerDialog.OnDateSetListener startDate;
    final Calendar myCalendarStart = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_excursion_details);

        repository = new Repository(getApplication());
        title = getIntent().getStringExtra("name");
        excursionDate = getIntent().getStringExtra("excursionDate");
        editName = findViewById(R.id.excursionTitle);
        editName.setText(title);
        excursionID = getIntent().getIntExtra("id", -1);
        vacaID = getIntent().getIntExtra("vacationID", -1);
        vacationStartDate = getIntent().getStringExtra("startVacationDate");
        endVacationDate = getIntent().getStringExtra("endVacationDate");
        editDate = findViewById(R.id.excursiondate);
        editDate.setText(excursionDate);


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

        editDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Date date;

                String info = editDate.getText().toString();
                if(info.equals("")) {
                    info="01/01/24";
                }

                myCalendarStart.setTime(new Date());
                new DatePickerDialog(
                        ExcursionDetails.this, startDate, myCalendarStart
                        .get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH),
                        myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        startDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendarStart.set(Calendar.YEAR, year);
                myCalendarStart.set(Calendar.MONTH, monthOfYear);
                myCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelStart();
            }

        };

    }

    private void updateLabelStart() {
        editDate.setText(myCalendarStart.getTime().toString());
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
                excursion = new Excursion(excursionID, editName.getText().toString(), vacaID, editDate.getText().toString());
                repository.insert(excursion);
                this.finish();
            } else {
                excursion = new Excursion(excursionID, editName.getText().toString(), vacaID, editDate.getText().toString());
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
}