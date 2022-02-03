package com.example.medbook;


import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

//this is the class representing medicine with all information stored and with getter and setter.
public class Medicine implements Validate{
    private String date;
    private String name;
    private double dose_amount;
    private int dose_frequency;
    private String dose_type;

    public Medicine(String date, String name, double dose_amount, int dose_frequency, String dose_type) {
        this.date = date;
        this.name = name;
        this.dose_amount = dose_amount;
        this.dose_frequency = dose_frequency;
        this.dose_type = dose_type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getDose_amount() {
        return dose_amount;
    }

    public void setDose_amount(double dose_amount) {
        this.dose_amount = dose_amount;
    }

    public int getDose_frequency() {
        return dose_frequency;
    }

    public void setDose_frequency(int dose_frequency) {
        this.dose_frequency = dose_frequency;
    }

    public String getDose_type() {
        return dose_type;
    }

    public void setDose_type(String dose_type) {
        this.dose_type = dose_type;
    }

    //the method from interface
    @Override
    public boolean checkValid() {
        if (name.length()<41 && name.length()>0 && dose_amount>0.0 && dose_frequency>0){
            try {
                Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(date);
            }
            catch(ParseException e){
                return false;
            }
            return true;
        }
        else{return false;}
    }
}
