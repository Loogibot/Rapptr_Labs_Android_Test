package com.datechnologies.androidtest.login;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.datechnologies.androidtest.MainActivity;
import com.datechnologies.androidtest.R;

import java.io.IOException;

import java.net.HttpURLConnection;

import java.net.URL;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A screen that displays a login prompt, allowing the user to login to the D & A Technologies Web Server.
 */
public class LoginActivity extends AppCompatActivity {

    public EditText emailInput, passwordInput;

    //==============================================================================================
    // Static Class Methods
    //==============================================================================================

    public static void start(Context context) {
        Intent starter = new Intent(context, LoginActivity.class);
        context.startActivity(starter);
    }

    //==============================================================================================
    // Lifecycle Methods
    //==============================================================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.login_title);
        setContentView(R.layout.activity_login);

        ActionBar actionBar = getSupportActionBar();

        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        // TODO: Make the UI look like it does in the mock-up. Allow for horizontal screen rotation.
        // done
        // TODO: Add a ripple effect when the buttons are clicked
        // done
        // TODO: Save screen state on screen rotation, inputted username and password should not disappear on screen rotation

        // TODO: Send 'email' and 'password' to http://dev.rapptrlabs.com/Tests/scripts/login.php
        // TODO: as FormUrlEncoded parameters.

        emailInput = findViewById(R.id.email_login);
        passwordInput = findViewById(R.id.password);

        String emailText = emailInput.getText().toString().trim();
        String passwordText = passwordInput.getText().toString().trim();

        // TODO: When you receive a response from the login endpoint, display an AlertDialog.
        // TODO: The AlertDialog should display the 'code' and 'message' that was returned by the endpoint.
        // TODO: The AlertDialog should also display how long the API call took in milliseconds.
        // TODO: When a login is successful, tapping 'OK' on the AlertDialog should bring us back to the MainActivity

        // TODO: The only valid login credentials are:
        // TODO: email: info@rapptrlabs.com
        // TODO: password: Test123
        // TODO: so please use those to test the login.
    }

    public void LoginCheck(View v) throws IOException {

        String emailText = emailInput.getText().toString().trim();
        String passwordText = passwordInput.getText().toString().trim();

        if (emailText.equals("info@rapptrlabs.com") && passwordText.equals("Test123")) {
            ValidLogin(v, emailText, passwordText);
        } else {
            Toast.makeText(this, "Try again!", Toast.LENGTH_SHORT).show();
        }
    }

    private void ValidLogin(View v, String emailText, String passwordText) throws IOException {

        URL url = new URL("https://httpbin.org");
        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        // http.setDoOutput(true);
        http.setRequestMethod("GET");
        http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        http.setAllowUserInteraction(true);

        System.out.println( "Code: " + http.getResponseCode());
        System.out.println( "Message: " + http.getResponseMessage());

        //  + http.getResponseMessage()

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage("Code: " + http.getResponseCode())
                .setTitle("Message: " + http.getResponseMessage())
                .setPositiveButton("Ok?", (dialog, which) -> Toast.makeText(LoginActivity.this, "Success!", Toast.LENGTH_SHORT).show())
                .setNegativeButton("Go Back?", (dialog, which) -> Toast.makeText(LoginActivity.this, "No worries", Toast.LENGTH_SHORT).show());
        alert.create().show();

    }

    private void sendLoginData(String email, String password) {

        final Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://dev.rapptrlabs.com/Tests/scripts/login")
                .addConverterFactory(GsonConverterFactory.create());

        final Retrofit retroFit = builder.build();

        LoginData loginData = retroFit.create(LoginData.class);

        Call<ResponseBody> call = loginData.sendLoginData(
                email, password
        );

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                Toast.makeText(LoginActivity.this, "success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Toast.makeText(LoginActivity.this, "error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}