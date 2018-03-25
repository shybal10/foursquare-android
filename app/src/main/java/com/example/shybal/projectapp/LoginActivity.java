package com.example.shybal.projectapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shybal.projectapp.Adapters.ReviewRecycleViewAdapter;
import com.example.shybal.projectapp.database.LoginDataBaseAdapter;
import com.example.shybal.projectapp.database.SaveSharedPreference;
import com.example.shybal.projectapp.gps.GpsService;
import com.example.shybal.projectapp.model.DataHolder;
import com.example.shybal.projectapp.model.VenueList;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import static android.widget.Toast.makeText;
import static com.example.shybal.projectapp.database.SaveSharedPreference.getUserName;
import static com.example.shybal.projectapp.model.DataHolder.isLoggedIn;

public class LoginActivity extends AppCompatActivity {
    ReviewRecycleViewAdapter mAdapter;


    EditText userEmailEditText;
    EditText userPasswordEditText;
    Button loginButton;
    CallbackManager callbackManager;
    Button skipButton;
    TextView id;
    public static final String EXTRA_ANSWER = "EXTRA_ANSWER";
    public static final String USER_NAME ="USER_NAME";
    LoginDataBaseAdapter loginDataBaseAdapter;
    VenueList venues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!(getUserName(getApplicationContext()).length() == 0))

        {
            loginDataBaseAdapter = new LoginDataBaseAdapter(getApplicationContext());
            venues = new  VenueList();
            String user = SaveSharedPreference.getUserName(getApplicationContext());
            DataHolder.userName = user;
            DataHolder.isLoggedIn = true;
            DataHolder.getFavouritesJsonString(LoginActivity.this);

            Intent homeActivityIntent = new Intent(LoginActivity.this,HomeActivity.class);
            homeActivityIntent.putExtra(USER_NAME,user);
            LoginActivity.this.finish();
            startActivity(homeActivityIntent);
        }


        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);
        //makes the status bar transparent
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);



        id = (TextView) findViewById(R.id.user_id);

        //get user permissions
        runTimePermissions();

        //initialize app database
        loginDataBaseAdapter = new LoginDataBaseAdapter(this);
        loginDataBaseAdapter = loginDataBaseAdapter.open();

        skipButton = (Button) findViewById(R.id.skip_button_id);
        userEmailEditText = (EditText) findViewById(R.id.user_email_login);
        userPasswordEditText = (EditText) findViewById(R.id.user_password_login);

        //facebook login


        loginButton = (Button) findViewById(R.id.fb_login_id);
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(final LoginResult loginResult) {
                        String userId = loginResult.getAccessToken().getUserId();
                        AccessToken accessToken = loginResult.getAccessToken();
                        SaveSharedPreference.setUserName(getApplicationContext(),userId);
                        //  AccessToken accessToken = null;
                        GraphRequest request = GraphRequest.newMeRequest(
                                accessToken,
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject object, GraphResponse response) {

                                        JSONObject dataObject = response.getJSONObject();
                                        try {
                                            String name = dataObject.getString("name");
                                            id.setText(name);
                                            String id = dataObject.getString("id");
                                            JSONObject picture = dataObject.getJSONObject("picture");
                                            JSONObject data = picture.getJSONObject("data");
                                            String profileUrl = data.getString("url");
                                            SaveSharedPreference.setUserName(getApplicationContext(),name);
                                            SaveSharedPreference.setPhotoUrl(getApplication(),profileUrl);
                                            if (!loginDataBaseAdapter.checkUser(name)){
                                                loginDataBaseAdapter.insertEntry(name, "", "");
                                            }

                                            DataHolder.userName = name;
                                            DataHolder.isLoggedIn = true;
                                            DataHolder.getFavouritesJsonString(LoginActivity.this);
                                            loginDataBaseAdapter.close();

                                            Intent homeActivityIntent = new Intent(LoginActivity.this, HomeActivity.class);
                                            LoginActivity.this.finish();

                                            homeActivityIntent.putExtra("id",id);
                                            homeActivityIntent.putExtra("profileUrl",profileUrl);

                                            startActivity(homeActivityIntent);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });


                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,picture");
                        request.setParameters(parameters);
                        request.executeAsync();
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_LONG).show();
                    }
                });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void loginClick(View view) throws JSONException {
        String userEmail = userEmailEditText.getText().toString();
        String userPassword = userPasswordEditText.getText().toString();
        if (userEmail.equals("") || userPassword.equals("")) {
            makeText(this, "Please enter user email and password", Toast.LENGTH_LONG).show();
        } else {
            // fetch the Password form database for respective user name
            String storedPassword = loginDataBaseAdapter.getSingleEntry(userEmail);

            // check if the Stored password matches with  Password entered by user
            if (userPassword.equals(storedPassword)) {
                //makeText(LoginActivity.this, "Congrats: Login Successful", Toast.LENGTH_LONG).show();
                SaveSharedPreference.setUserName(getApplicationContext(),userEmail);
                Intent homeActivityIntent = new Intent(getApplicationContext(), HomeActivity.class);
                homeActivityIntent.putExtra(USER_NAME,userEmail);

                DataHolder.userName = userEmail;
                DataHolder.isLoggedIn = true;
                DataHolder.getFavouritesJsonString(LoginActivity.this);

                LoginActivity.this.finish();
                startActivity(homeActivityIntent);
                Intent startService = new Intent(getApplicationContext(), GpsService.class);
                startService(startService);

            } else {
                makeText(LoginActivity.this, "invalid username and password", Toast.LENGTH_LONG).show();
            }
        }


    }



    public void ShowCreateAccountPage(View view) {
        Intent registerActivityIntent = new Intent(getApplicationContext(), RegisterActivity.class);
        LoginActivity.this.finish();
        startActivity(registerActivityIntent);
    }

    public void ShowOtpPage(View view) {
        Intent otpActivityIntent = new Intent(getApplicationContext(), OtpActivity.class);
        startActivity(otpActivityIntent);
    }



    public void setSkipButton(View view){
        isLoggedIn = false;
        DataHolder.userName = "";
        Intent homeActivityIntent = new Intent(getApplicationContext(), HomeActivity.class);
        homeActivityIntent.putExtra(EXTRA_ANSWER, DataHolder.isLoggedIn);
        LoginActivity.this.finish();
        startActivity(homeActivityIntent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginDataBaseAdapter.close();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 10) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED &&grantResults[2] == PackageManager.PERMISSION_GRANTED ) {

            } else {
                runTimePermissions();
            }
        }
    }

    public boolean runTimePermissions() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
        ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)  {
            ActivityCompat.requestPermissions(this, new String[]{
                    android.Manifest.permission.ACCESS_COARSE_LOCATION,
                    android.Manifest.permission.ACCESS_FINE_LOCATION ,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE }, 10);
            return true;
        }

        return true;
    }
}
