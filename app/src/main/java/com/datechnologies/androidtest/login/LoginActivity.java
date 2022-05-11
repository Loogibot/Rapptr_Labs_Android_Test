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
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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
        // done

        // TODO: Send 'email' and 'password' to http://dev.rapptrlabs.com/Tests/scripts/login.php
        // TODO: as FormUrlEncoded parameters.
        // DONE

        emailInput = findViewById(R.id.email_login);
        passwordInput = findViewById(R.id.password);

        // TODO: When you receive a response from the login endpoint, display an AlertDialog.
        // done
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
        ValidLogin(v, emailText, passwordText);
    }

    protected void ValidLogin(View v, String emailText, String passwordText) throws IOException {

        // using OkHttp
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");

        RequestBody body = RequestBody.create(mediaType, "email="+emailText+"&password="+passwordText);

        Request request = new Request.Builder()
                .url("http://dev.rapptrlabs.com/Tests/scripts/login.php")
                .method("POST", body)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    String myResponse = response.body().string();

                    LoginActivity.this.runOnUiThread(() -> {

                        alertDialog(myResponse);
                    });

                }

            }
        });
    }

    private void alertDialog(String myResponse) {

        // Toast.makeText(LoginActivity.this, myResponse, Toast.LENGTH_SHORT).show();

        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setMessage("Code: " + myResponse)
                .setTitle("Message: " + myResponse)
                .setPositiveButton("Ok?", (dialog, which) -> Toast.makeText(LoginActivity.this, "Success!", Toast.LENGTH_SHORT).show())
                .setNegativeButton("Go Back?", (dialog, which) -> Toast.makeText(LoginActivity.this, "No worries", Toast.LENGTH_SHORT).show());
        alert.create().show();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}