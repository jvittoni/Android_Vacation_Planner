package com.example.w803vacation.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.w803vacation.dao.ExcursionDAO;
import com.example.w803vacation.dao.VacationDAO;
import com.example.w803vacation.entities.Excursion;
import com.example.w803vacation.entities.Vacation;

@Database(entities = {Vacation.class, Excursion.class}, version = 5, exportSchema = false)
public abstract class PlannerDatabaseBuilder extends RoomDatabase {
    public abstract VacationDAO vacationDAO();
    public abstract ExcursionDAO excursionDAO();
    private static volatile  PlannerDatabaseBuilder INSTANCE;

    static PlannerDatabaseBuilder getDatabase(final Context context) {
        if (INSTANCE==null) {
            synchronized (PlannerDatabaseBuilder.class) {
                if (INSTANCE==null) {
                    INSTANCE= Room.databaseBuilder(context.getApplicationContext(), PlannerDatabaseBuilder.class, "MyPlannerDatabase.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
