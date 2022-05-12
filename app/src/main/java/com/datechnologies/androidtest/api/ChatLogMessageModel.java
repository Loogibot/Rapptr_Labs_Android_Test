package com.datechnologies.androidtest.api;

/**
 * A data model that represents a chat log message fetched from the D & A Technologies Web Server.
 */

public class ChatLogMessageModel
{
    public int userId;
    public String avatarUrl;
    public String username;
    public String message;

    public int getUserId() {
        return userId;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getUsername() {
        return username;
    }

    public String getMessage() { return message; }
}
