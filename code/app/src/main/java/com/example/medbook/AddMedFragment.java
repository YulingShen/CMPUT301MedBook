package com.example.medbook;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

//following the lab3 method of creating a fragment
public class AddMedFragment extends DialogFragment {

    private OnFragmentInteractionListener listener;
    private EditText medName;
    private EditText medDoseAmount;
    private EditText medDoseFrequency;
    private TextView medDate;
    private Spinner medType;
    DatePickerDialog picker;
    Medicine currentMed;

    public AddMedFragment(Medicine medicine){
        currentMed = medicine;
    }

    public AddMedFragment(){
        currentMed = new Medicine("","",0,0,"mg");
    }

    public interface OnFragmentInteractionListener {
        void onOkPressed(Medicine newMed);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            listener = (OnFragmentInteractionListener) context;
        }
        else{
            throw new RuntimeException(context.toString()
                    + " must impelment OnFragmentInteractionListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle sacedInstanceState){
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.add_med_fragment, null);
        medName = view.findViewById(R.id.med_name_editText);
        medDoseAmount = view.findViewById(R.id.med_dose_amount);
        medDoseFrequency = view.findViewById(R.id.med_dose_frequency);
        medDate = view.findViewById(R.id.med_date);
        medType = view.findViewById(R.id.med_type_spinner);
        //implement an adapter for the spinner
        String []typeChoice = {"mg", "mcg", "drop"};
        ArrayList<String> medTypes = new ArrayList<String>();
        medTypes.addAll(Arrays.asList(typeChoice));
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,medTypes);
        medType.setAdapter(spinnerAdapter);

        medDate.setInputType(InputType.TYPE_NULL);
        //using example 2 from https://www.tutlane.com/tutorial/android/android-datepicker-with-examples
        medDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                medDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                            }
                        }, year, month, day);
                picker.show();
            }
        });

        medName.setText(currentMed.getName());
        medDate.setText(currentMed.getDate());
        medType.setSelection(medTypes.indexOf(currentMed.getDose_type()));
        if (currentMed.getDose_amount() > 0) {
            medDoseAmount.setText(Double.toString(currentMed.getDose_amount()));
        }
        if (currentMed.getDose_frequency() > 0) {
            medDoseFrequency.setText(Integer.toString(currentMed.getDose_frequency()));
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("Add/Edit Medicine")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String name = medName.getText().toString();
                        String date = medDate.getText().toString();
                        String type = medType.getSelectedItem().toString();
                        if (medName.getText().toString().length() == 0 ||
                                medDate.getText().toString().length() == 0 ||
                                medDoseAmount.getText().toString().length() == 0 ||
                                medDoseFrequency.getText().toString().length() == 0 ){
                            Toast.makeText(getContext(), "An input field is empty", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Double amount = Double.valueOf(medDoseAmount.getText().toString());
                        int frequency = Integer.valueOf(medDoseFrequency.getText().toString());
                        Medicine tempMed = new Medicine(date,name,amount,frequency,type);
                        if (!tempMed.checkValid()){
                            Toast.makeText(getContext(), "Medicine input is not valid", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        else {
                            listener.onOkPressed(tempMed);
                        }
                    }
                })
                .create();
    }
}
