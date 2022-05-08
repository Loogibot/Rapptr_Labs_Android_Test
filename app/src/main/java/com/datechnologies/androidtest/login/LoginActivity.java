

package com.datechnologies.androidtest.login;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.datechnologies.androidtest.LoginData;
import com.datechnologies.androidtest.MainActivity;
import com.datechnologies.androidtest.R;

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
    public EditText emailInput;
    public EditText passwordInput;

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

        findViewById(R.id.log_in).setOnClickListener(View -> {
            sendLoginData(emailText, passwordText);
        });

        // TODO: When you receive a response from the login endpoint, display an AlertDialog.
        // TODO: The AlertDialog should display the 'code' and 'message' that was returned by the endpoint.
        // TODO: The AlertDialog should also display how long the API call took in milliseconds.
        // TODO: When a login is successful, tapping 'OK' on the AlertDialog should bring us back to the MainActivity

        // TODO: The only valid login credentials are:
        // TODO: email: info@rapptrlabs.com
        // TODO: password: Test123
        // TODO: so please use those to test the login.
    }


    public void LoginCheck(View v) {

        String emailText = emailInput.getText().toString().trim();
        String passwordText = passwordInput.getText().toString().trim();

        if (emailText.equals("info@rapptrlabs.com") && passwordText.equals("Test123")) {
            ValidLogin();
            Toast.makeText(LoginActivity.this, "Correct login!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(LoginActivity.this, "Try again!", Toast.LENGTH_SHORT).show();
        }
    }

    public void ValidLogin() {
        AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this);
        alert.setTitle("Login Confirmed!");
        alert.setMessage("You are logged in");
        alert.setPositiveButton("Continue?", (dialog, which) -> Toast.makeText(LoginActivity.this, "Lets go", Toast.LENGTH_SHORT).show());
        alert.setNegativeButton("Go Back?", (dialog, which) -> Toast.makeText(LoginActivity.this, "No worries", Toast.LENGTH_SHORT).show());

    }

    public void sendLoginData(String email, String password) {

        final Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://dev.rapptrlabs.com/Tests/scripts/login.php")
                .addConverterFactory(GsonConverterFactory.create());

        final Retrofit retroFit = builder.build();

        LoginData loginData = retroFit.create(LoginData.class);

        Call<ResponseBody> call = loginData.sendLoginData(
                email, password
        );

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(LoginActivity.this, "success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
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