package com.job.softclick_mobile.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;


import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.navigation.NavigationView;
import com.job.softclick_mobile.R;
import com.job.softclick_mobile.databinding.ActivityLoginBinding;
import com.job.softclick_mobile.databinding.FooterBinding;

public class FooterActivity  extends AppCompatActivity {
    private FooterBinding binding;
    private BottomAppBar mBottomAppBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FooterBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        mBottomAppBar= findViewById(R.id.bottomAppBar);
        setSupportActionBar(mBottomAppBar);
        mBottomAppBar.setFabAlignmentMode(BottomAppBar.FAB_ALIGNMENT_MODE_CENTER);
        mBottomAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.footer_menu,menu);
        return true;
    }
}
