package com.datechnologies.androidtest.login;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import com.datechnologies.androidtest.MainActivity;
import com.datechnologies.androidtest.R;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import okhttp3.*;

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
        // done
        // TODO: The AlertDialog should also display how long the API call took in milliseconds.
        // done
        // TODO: When a login is successful, tapping 'OK' on the AlertDialog should bring us back to the MainActivity
        // done

        // TODO: The only valid login credentials are:
        // TODO: email: info@rapptrlabs.com
        // TODO: password: Test123
        // TODO: so please use those to test the login.
        // done
    }

    public void LoginCheck(View v) {

        String emailText = emailInput.getText().toString().trim();
        String passwordText = passwordInput.getText().toString().trim();
        ValidateLogin(emailText, passwordText);
    }

    protected void ValidateLogin(String emailText, String passwordText) {

        String URL = "http://dev.rapptrlabs.com/Tests/scripts/login.php";
        // using OkHttp
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");

        RequestBody body = RequestBody.create(mediaType, "email="+emailText+"&password="+passwordText);

        Request request = new Request.Builder()
                .url(URL)
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
                assert response.body() != null;
                String myResponse = response.body().string();

                long tx = response.sentRequestAtMillis();
                long rx = response.receivedResponseAtMillis();
                String responseTime = "Response Time : " + (rx - tx) + " ms";

                JSONObject Jobject;
                String alertCode = "";
                String alertMessage = "";

                try {
                    Jobject = new JSONObject(myResponse);
                    alertCode = Jobject.getString("code");
                    alertMessage = Jobject.getString("message");
                } catch (JSONException ignored) {}

                String finalAlertCode = alertCode;
                String finalAlertMessage = alertMessage;

                LoginActivity.this.runOnUiThread(() -> alertDialog(finalAlertCode, finalAlertMessage, responseTime));
            }
        });
    }

    private void alertDialog(String alertCode, String alertMessage, String responseTime) {

        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Code: " + alertCode)
                .setMessage("Message: " + alertMessage + "\n" + responseTime)
                .setPositiveButton("Ok?", (dialog, which) -> onBackPressed());
        alert.create().show();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }
}