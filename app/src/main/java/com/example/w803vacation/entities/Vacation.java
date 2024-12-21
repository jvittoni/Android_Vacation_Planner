package com.example.w803vacation.entities;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "vacations")
public class Vacation {

    @PrimaryKey(autoGenerate = true)
    private int vacationID;
    private String vacationName;
    private String hotelName;

    private String startVacationDate;

    private String endVacationDate;

    public Vacation(int vacationID, String vacationName, String hotelName, String startVacationDate, String endVacationDate) {
        this.vacationID = vacationID;
        this.vacationName = vacationName;
        this.hotelName = hotelName;
        this.startVacationDate = startVacationDate;
        this.endVacationDate = endVacationDate;
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

    public String getStartVacationDate() {
        return startVacationDate;
    }

    public void setStartVacationDate(String startVacationDate) {
        this.startVacationDate = startVacationDate;
    }

    public String getEndVacationDate() {
        return endVacationDate;
    }

    public void setEndVacationDate(String endVacationDate) {
        this.endVacationDate = endVacationDate;
    }
}