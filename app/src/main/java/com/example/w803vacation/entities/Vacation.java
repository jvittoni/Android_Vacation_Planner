package com.example.w803vacation.entities;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "vacations")
public class Vacation {

    @PrimaryKey(autoGenerate = true)
    private int vacationID;
    private String vacationName;
    private String hotelName;

    private String vacationStartDate;

    private String vacationEndDate;

    public Vacation(int vacationID, String vacationName, String hotelName, String vacationStartDate, String vacationEndDate) {
        this.vacationID = vacationID;
        this.vacationName = vacationName;
        this.hotelName = hotelName;
        this.vacationStartDate = vacationStartDate;
        this.vacationEndDate = vacationEndDate;
    }

    public int getVacationID() {
        return vacationID;
    }

    public void setVacationID(int vacationID) {
        this.vacationID = vacationID;
    }

    public String getVacationName() {
        return vacationName;
    }

    public void setVacationName(String vacationName) {
        this.vacationName = vacationName;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getVacationStartDate() {
        return vacationStartDate;
    }

    public void setVacationStartDate(String vacationStartDate) {
        this.vacationStartDate = vacationStartDate;
    }

    public String getVacationEndDate() {
        return vacationEndDate;
    }

    public void setVacationEndDate(String vacationEndDate) {
        this.vacationEndDate = vacationEndDate;
    }
}