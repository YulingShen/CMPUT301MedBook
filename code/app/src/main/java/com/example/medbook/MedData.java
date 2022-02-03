package com.example.medbook;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

//implemented for sharing the reference between activities
public class MedData {

    private static MedData instance;

    public ArrayAdapter<Medicine> medAdapter;
    public ArrayList<Medicine> medDataList;

    private MedData(Context activity, int layout){
        medDataList = new ArrayList<>();
        medAdapter = new CustomList(activity, medDataList);
    }

    public static MedData getInstance() throws NullPointerException{
        return instance;
    }

    public static MedData getInstance(Context activity, int layout){
        if (instance == null){
            instance = new MedData(activity, layout);
        }
        return instance;
    }

    public void addMed(String date, String name, double dose_amount, int dose_frequency, String dose_type){
        medAdapter.add(new Medicine(date,name,dose_frequency,dose_frequency,dose_type));
        medAdapter.notifyDataSetChanged();
    }

    public void deleteMed(int index){
        medAdapter.remove(medAdapter.getItem(index));
        medAdapter.notifyDataSetChanged();
    }

    public void editMed(int index, Medicine medicine){
        medAdapter.insert(medicine,index);
        medAdapter.remove(medAdapter.getItem(index+1));
        medAdapter.notifyDataSetChanged();
    }

    public Medicine getMed(int index){
        return medAdapter.getItem(index);
    }

    //used by main activity for getting the total amount of doses
    public int getTotalFrequency(){
        int sum=0;
        for (int i=0; i<medAdapter.getCount();i++){
            sum+=medAdapter.getItem(i).getDose_frequency();
        }
        return sum;
    }
}
