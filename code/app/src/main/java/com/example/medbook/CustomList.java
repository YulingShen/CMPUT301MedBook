package com.example.medbook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

//following lab 3 for creating custom list
public class CustomList extends ArrayAdapter<Medicine> {
    private ArrayList<Medicine> medicines;
    private Context context;

    public CustomList( Context context, ArrayList<Medicine> medicines) {
        super(context, 0, medicines);
        this.medicines = medicines;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //return super.getView(position, convertView, parent);
        View view = convertView;
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.content, parent,false);
        }
        Medicine medicine = medicines.get(position);
        TextView medicineName = view.findViewById(R.id.medicine_text);
        TextView doseAmount = view.findViewById(R.id.dose_amount_text);
        TextView doseType = view.findViewById(R.id.dose_type_text);
        TextView doseFrequency = view.findViewById(R.id.dose_frequency_text);
        medicineName.setText(medicine.getName());
        doseAmount.setText(Double.toString(medicine.getDose_amount()));
        doseType.setText(medicine.getDose_type());
        doseFrequency.setText(Integer.toString(medicine.getDose_frequency())+" time(s) a day");
        return view;
    }
}
