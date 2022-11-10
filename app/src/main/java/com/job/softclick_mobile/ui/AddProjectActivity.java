package com.job.softclick_mobile.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.job.softclick_mobile.R;
import com.job.softclick_mobile.databinding.ActivityAddProjectBinding;
import com.job.softclick_mobile.databinding.ActivityLoginBinding;

public class AddProjectActivity extends AppCompatActivity {

    private ActivityAddProjectBinding binding;
    EditText date_picker_debut;
    EditText date_picker_fin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddProjectBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        date_picker_debut =  binding.dateDebut;
        date_picker_fin = binding.datePickerFin;

        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("Select a Date");
        final MaterialDatePicker materialDatePicker_debut = builder.build();
        final MaterialDatePicker materialDatePicker_fin = builder.build();

        date_picker_debut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDatePicker_debut.show(getSupportFragmentManager(),"DATE_PICKER");
            }
        });

        date_picker_fin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDatePicker_fin.show(getSupportFragmentManager(),"DATE_PICKER");
            }
        });

        materialDatePicker_debut.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                date_picker_debut.setText(materialDatePicker_debut.getHeaderText());
            }
        });

        materialDatePicker_fin.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                date_picker_fin.setText(materialDatePicker_fin.getHeaderText());
            }
        });

    }

}