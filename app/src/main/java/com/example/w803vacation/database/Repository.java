package com.example.w803vacation.database;

import android.app.Application;

import com.example.w803vacation.dao.ExcursionDAO;
import com.example.w803vacation.dao.VacationDAO;
import com.example.w803vacation.entities.Excursion;
import com.example.w803vacation.entities.Vacation;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {
    private ExcursionDAO xExcursionDAO;
    private VacationDAO xVacationDAO;

    private List<Vacation> xAllVacations;

    private List<Excursion> xAllExcursions;

    private static int NUMBER_OF_THREADS=4;
    static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public Repository(Application application) {
        PlannerDatabaseBuilder db=PlannerDatabaseBuilder.getDatabase(application);
        xExcursionDAO = db.excursionDAO();
        xVacationDAO = db.vacationDAO();
    }

    public List<Vacation>getxAllVacations(){
        databaseExecutor.execute(()->{
            xAllVacations = xVacationDAO.getAllVacations();
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return xAllVacations;
    }

    public void insert(Vacation vacation){
        databaseExecutor.execute(()->{
            xVacationDAO.insert(vacation);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void update(Vacation vacation){
        databaseExecutor.execute(()->{
            xVacationDAO.update(vacation);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete(Vacation vacation){
        databaseExecutor.execute(()->{
            xVacationDAO.delete(vacation);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public List<Excursion>getxAllExcursions(){
        databaseExecutor.execute(()->{
            xAllExcursions = xExcursionDAO.getAllExcursions();
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return xAllExcursions;
    }

    public List<Excursion>getxAssociatedExcursions(int vacationID){
        databaseExecutor.execute(()->{
            xAllExcursions = xExcursionDAO.getAssociatedExcursions(vacationID);
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return xAllExcursions;
    }


    public void insert(Excursion excursion){
        databaseExecutor.execute(()->{
            xExcursionDAO.insert(excursion);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void update(Excursion excursion){
        databaseExecutor.execute(()->{
            xExcursionDAO.update(excursion);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete(Excursion excursion){
        databaseExecutor.execute(()->{
            xExcursionDAO.delete(excursion);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
