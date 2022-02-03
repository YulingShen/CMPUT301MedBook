package com.example.medbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements AddMedFragment.OnFragmentInteractionListener{

    ListView medList;
    ArrayAdapter<Medicine> medAdapter;
    ArrayList<Medicine> medDataList;
    int selected = -1;

    public static final String EXTRA_MESSAGE = "com.example.meds.MESSAGE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        medList = findViewById(R.id.med_list);

        MedData medData = MedData.getInstance(this, R.layout.content);

        medList.setAdapter(medData.medAdapter);
        medList.setOnItemClickListener(new ListView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3){
                viewDetails(arg2);
            };
        });

        final FloatingActionButton addCityButton = findViewById(R.id.add_med_button);
        addCityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected = -1;
                new AddMedFragment().show(getSupportFragmentManager(), "ADD_MED");
            }
        });

        TextView totalDose = findViewById(R.id.total_dose_text);
        totalDose.setText(Integer.toString(medData.getTotalFrequency()) + " dose(s) in frequency a day");
    }

    //method called when an element in the list selected, brings up a new activity
    public void viewDetails(int inputIndex){
        selected = inputIndex;
        Intent intent = new Intent(this, DisplayDetailActivity.class);
        intent.putExtra(EXTRA_MESSAGE, selected);
        startActivity(intent);
    }

    @Override
    public void onOkPressed(Medicine newMed) {
        MedData.getInstance().medAdapter.add(newMed);
        TextView totalDose = findViewById(R.id.total_dose_text);
        totalDose.setText(Integer.toString(MedData.getInstance().getTotalFrequency()) + " dose(s) in frequency a day");
    }

    //implemented for updating the total amount of dosed after deleting or editing an element
    @Override
    protected void onResume() {
        super.onResume();
        TextView totalDose = findViewById(R.id.total_dose_text);
        totalDose.setText(Integer.toString(MedData.getInstance().getTotalFrequency()) + " dose(s) in frequency a day");
    }
}