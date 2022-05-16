package com.datechnologies.androidtest.chat;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.datechnologies.androidtest.MainActivity;
import com.datechnologies.androidtest.R;
import com.datechnologies.androidtest.api.ChatLogMessageModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Screen that displays a list of chats from a chat log.
 */
public class ChatActivity extends AppCompatActivity {

    //==============================================================================================
    // Class Properties
    //==============================================================================================

    private ChatAdapter chatAdapter;

    //==============================================================================================
    // Static Class Methods
    //==============================================================================================

    public static void start(Context context) {
        Intent starter = new Intent(context, ChatActivity.class);
        context.startActivity(starter);
    }

    //==============================================================================================
    // Lifecycle Methods
    //==============================================================================================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.chat_title);
        setContentView(R.layout.activity_chat);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        ActionBar actionBar = getSupportActionBar();

        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        chatAdapter = new ChatAdapter();

        recyclerView.setAdapter(chatAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(
                getApplicationContext(), 1));

        List<ChatLogMessageModel> tempList = new ArrayList<>();



        // TODO: Make the UI look like it does in the mock-up. Allow for horizontal screen rotation.
        // done

        // TODO: Retrieve the chat data from http://dev.rapptrlabs.com/Tests/scripts/chat_log.php
        // done
        // TODO: Parse this chat data from JSON into ChatLogMessageModel and display it.
        viewChatData(tempList);

    }

    public void viewChatData(List<ChatLogMessageModel> tempList) {
        String URL = "http://dev.rapptrlabs.com/Tests/scripts/chat_log.php";
        // using OkHttp
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url(URL)
                .get() // or use .method("GET", null) or .method("POST", body)
                .build();

        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                assert response.body() != null;
                String myResponse = response.body().string();

                JSONObject jObject;
                JSONObject jData;
                JSONArray jArray = new JSONArray();
                try {
                    jObject = new JSONObject(myResponse);
                    jArray = jObject.getJSONArray("data");
                } catch (JSONException ignored) {
                }
                int n = jArray.length();

                for (int i = 0; i < n; i++) {
                    jData = jArray.optJSONObject(i);
                    //create a new chat from the model for each iteration and adds it to tempList
                    ChatLogMessageModel chatLogMessageModel = new ChatLogMessageModel();
                    chatLogMessageModel.userId = jData.optInt("user_id");
                    chatLogMessageModel.username = jData.optString("name");
                    chatLogMessageModel.message = jData.optString("message");
                    chatLogMessageModel.avatarUrl = jData.optString("avatar_url");
                    tempList.add(chatLogMessageModel);
                }
                // renders chat with tempList using chatAdapter
                runOnUiThread(() -> chatAdapter.setChatLogMessageModelList(tempList));
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}