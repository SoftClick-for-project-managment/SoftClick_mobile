package com.job.softclick_mobile.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.job.softclick_mobile.R;

public class DetailProjetctivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_projetctivity);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.edit_delete_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.edit:
                return true;

            case R.id.delete:
                showDialogDeleteConfirmation();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
    public void showDialogDeleteConfirmation(){
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(DetailProjetctivity.this);
        builder.setTitle("Delete Confirmation");
        builder.setMessage("Are you sure you want to delete this project ?");
        builder.setPositiveButton("Yes I'm Sure", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //to do delete project
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Dismiss deletion
            }
        });
        builder.show();
    }
}