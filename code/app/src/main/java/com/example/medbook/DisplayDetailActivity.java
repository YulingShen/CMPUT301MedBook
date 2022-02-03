package com.example.medbook;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

//an activity used to display detail
public class DisplayDetailActivity extends AppCompatActivity implements AddMedFragment.OnFragmentInteractionListener {
    MedData medData;
    int index;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);

        Intent intent = getIntent();
        this.index = intent.getIntExtra(MainActivity.EXTRA_MESSAGE, -1);

        if (index == -1){
            finish();
        }

        medData = MedData.getInstance();
        Medicine medicine = medData.getMed(index);


        setTexts(medicine);
    }

    //bring up a fragment for editing
    public void editMed(View view){
        new AddMedFragment(medData.getMed(index)).show(getSupportFragmentManager(), "EDIT_MED");
    }

    public void deleteMed(View view){
        medData.deleteMed(index);
        finish();
    }

    //setting the text shown in the activity
    private void setTexts(Medicine medicine){
        TextView medicineName = findViewById(R.id.medicine_text_detail);
        TextView doseAmount = findViewById(R.id.dose_amount_text_detail);
        TextView doseType = findViewById(R.id.dose_type_text_detail);
        TextView doseFrequency = findViewById(R.id.dose_frequency_text_detail);
        TextView medDate = findViewById(R.id.dose_date_text_detail);
        medicineName.setText(medicine.getName());
        doseAmount.setText(Double.toString(medicine.getDose_amount()));
        doseType.setText(medicine.getDose_type());
        doseFrequency.setText(Integer.toString(medicine.getDose_frequency())+" time(s) a day");
        medDate.setText(medicine.getDate());
    }

    //need to update when edited
    @Override
    public void onOkPressed(Medicine newMed) {
        medData.editMed(index,newMed);
        setTexts(newMed);
    }
}
