package com.job.softclick_mobile.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.job.softclick_mobile.R;
import com.job.softclick_mobile.databinding.ActivityAddProjectBinding;
import com.job.softclick_mobile.databinding.ActivityDetailProjetctivityBinding;

public class DetailProjetctivity extends AppCompatActivity {

    private ActivityDetailProjetctivityBinding binding;
    TextView project_name ,domain, date_debut ,date_fin , description , name_chef ,clients,equips,revenue,depense;
    LinearProgressIndicator etat_avancement;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailProjetctivityBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        project_name = binding.projectName;
        domain = binding.domainProject;
        date_debut = binding.dateDebut;
        date_fin = binding.dateFin;
        description = binding.descriptionProjet;
        name_chef = binding.nameChef;
        clients = binding.clientsText;
        equips = binding.equipesText;
        revenue = binding.revenuTotal;
        depense = binding.depenseTotal;
        etat_avancement = binding.etatAvancement;
        fetchDate();
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

    public void fetchDate(){
        project_name.setText("Systém de détéction de violance");
        domain.setText("Intelligance Artificial & Big Data");
        date_debut.setText("18/12/2021");
        date_fin.setText("18/12/2022");
        description.setText("C'est un systém intelligent qui detect les acts violents sur caméra de surveillance");
        name_chef.setText("Mr Youssef Gahi");
        clients.setText("- oukacha prison \n - école sanabil \n - Army American");
        equips.setText("- équipe frontend N° 1 \n équipe fullstack N° 5");
        revenue.setText("5000000 DH");
        depense.setText("200000 DH");
        etat_avancement.setProgress(25);
    }
}