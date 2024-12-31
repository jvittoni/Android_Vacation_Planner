package com.example.w803vacation.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

import java.util.List;

public class VacationList extends AppCompatActivity {

    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_vacation_list);

        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VacationList.this, VacationDetails.class);
                startActivity(intent);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.vacationrecyclerview);
        repository = new Repository(getApplication());
        List<Vacation> allVacations = repository.getxAllVacations();
        final VacationAdapter vacationAdapter = new VacationAdapter(this);
        recyclerView.setAdapter(vacationAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        vacationAdapter.setVacations(allVacations);

//        System.out.println(getIntent().getStringExtra("test"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_vacation_list, menu);
        return true;
    }

    protected void onResume() {
        super.onResume();
        List<Vacation> allVacations = repository.getxAllVacations();
        RecyclerView recyclerView = findViewById(R.id.vacationrecyclerview);
        final VacationAdapter vacationAdapter= new VacationAdapter(this);
        recyclerView.setAdapter(vacationAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        vacationAdapter.setVacations(allVacations);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.sample) {
            repository = new Repository(getApplication());
//            Toast.makeText(VacationList.this,"put in sample data", Toast.LENGTH_LONG).show();

            Vacation vacation = new Vacation(0, "Italy", "hotel1", "12/01/24", "12/30/24");
            repository.insert(vacation);

            vacation = new Vacation(0, "France", "hotel2", "12/01/24", "12/30/24");
            repository.insert(vacation);

            Excursion excursion = new Excursion(0, "Hiking", 1,  "12/17/24");
            repository.insert(excursion);

            excursion = new Excursion(0, "Snorkeling", 1,  "12/29/24");
            repository.insert(excursion);

            return true;
        }
        if(item.getItemId()==android.R.id.home) {
            this.finish();
            return true;
        }
        return true;
    }

}