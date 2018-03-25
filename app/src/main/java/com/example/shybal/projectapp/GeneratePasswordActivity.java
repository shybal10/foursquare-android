package com.example.shybal.projectapp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class GeneratePasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_password);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);


    }

    public void showLoginPage(View view){
        Intent loginActivityIntent = new Intent(GeneratePasswordActivity.this,LoginActivity.class);
        GeneratePasswordActivity.this.finish();
        startActivity(loginActivityIntent);
    }
}
