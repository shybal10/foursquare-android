package com.example.shybal.projectapp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shybal.projectapp.database.LoginDataBaseAdapter;
import com.example.shybal.projectapp.database.SaveSharedPreference;
import com.example.shybal.projectapp.model.DataHolder;
import com.example.shybal.projectapp.model.VenueList;
import com.google.gson.Gson;

public class RegisterActivity extends AppCompatActivity {

    LoginDataBaseAdapter loginDataBaseAdapter;
    EditText userEmailEditText;
    EditText userNumberEditText;
    EditText userPasswordEditText;
    EditText userConfirmPasswordEditText;


    private Button registerLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);


        userEmailEditText = (EditText) findViewById(R.id.user_email);
        userNumberEditText = (EditText) findViewById(R.id.user_number);
        userConfirmPasswordEditText = (EditText) findViewById(R.id.user_confirm_password);
        userPasswordEditText = (EditText) findViewById(R.id.user_password);
        registerLoginButton = (Button) findViewById(R.id.login_register);
        loginDataBaseAdapter = new LoginDataBaseAdapter(this);
        loginDataBaseAdapter = loginDataBaseAdapter.open();



        registerLoginButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                String userEmail = userEmailEditText.getText().toString();
                String userNumber = userNumberEditText.getText().toString();
                String userPassword = userPasswordEditText.getText().toString();
                String userConfirmPassword = userConfirmPasswordEditText.getText().toString();

                // check if any of the fields are vaccant
                if(userEmail.equals("")||userPassword.equals("")||userConfirmPassword.equals("")||userNumber.equals(" "))
                {
                    Toast.makeText(RegisterActivity.this,"Please fill details", Toast.LENGTH_LONG).show();
                    return;
                }
                if(!(isValidEmail(userEmail))) {
                    Toast.makeText(RegisterActivity.this,"Please fill a valid email", Toast.LENGTH_LONG).show();
                    return;
                }


                // check if both password matches
                if(!userPassword.equals(userConfirmPassword))
                {
                    Toast.makeText(RegisterActivity.this,"Password does not match", Toast.LENGTH_LONG).show();
                    return;
                }
                else
                {
                    boolean userExists = loginDataBaseAdapter.checkUser(userEmail);
                    if(!userExists) {
                        // Save the Data in Database
                        loginDataBaseAdapter.insertEntry(userEmail, userPassword, "");
                        DataHolder.userName = userEmail;
                        DataHolder.isLoggedIn = true;
                        loginDataBaseAdapter.close();
                        DataHolder.getFavouritesJsonString(RegisterActivity.this);
                        SaveSharedPreference.setUserName(RegisterActivity.this, userEmail);
                        Intent homeActivityIntent = new Intent(RegisterActivity.this,HomeActivity.class);
                        RegisterActivity.this.finish();
                        startActivity(homeActivityIntent);
                        Toast.makeText(RegisterActivity.this, "Account Successfully Created ", Toast.LENGTH_LONG).show();
                    }else
                    {
                        Toast.makeText(RegisterActivity.this, "username already taken", Toast.LENGTH_LONG).show();
                    }

                }

            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginDataBaseAdapter.close();
    }
    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

}
