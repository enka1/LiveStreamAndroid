package com.vcsp.livestream.utils;

import com.github.nkzawa.socketio.client.Socket;
import com.vcsp.livestream.room.entities.User;
import com.vcsp.livestream.socket.SocketClientRemote;
import org.json.JSONException;
import org.json.JSONObject;

public class SocketUtil {

    public static final String CREATE_NEW_BROADCASTING_SUCCESS = "CREATE_NEW_BROADCASTING_SUCCESS";
    public static final String CREATE_NEW_BROADCASTING_FAILED = "CREATE_NEW_BROADCASTING_FAILED";
    public static final String STOP_BROADCASTING_SUCCESS = "STOP_BROADCASTING_SUCCESS";
    public static final String STOP_BROADCASTING_FAILED = "STOP_BROADCASTING_FAILED";
    private static final String CREATE_NEW_BROADCASTING = "CREATE_NEW_BROADCASTING";
    private static final String STOP_BROADCASTING = "STOP_BROADCASTING";
    public static Socket socket = SocketClientRemote.getSocket();

    public static void joinChatRoom(String roomName, User user) {
        JSONObject sendData = new JSONObject();
        try {
            sendData.put("roomName", roomName);
            sendData.put("userId", user.getUserId());
            sendData.put("userName", user.getUsername());
            sendData.put("displayName", user.getDisplayName());
            socket.emit("userJoinRoom", sendData);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void chatToRoom(String roomName, User user, String content) {
        JSONObject sendData = new JSONObject();
        try {
            sendData.put("roomName", roomName);
            sendData.put("userId", user.getUserId());
            sendData.put("username", user.getUsername());
            sendData.put("displayName", user.getDisplayName());
            sendData.put("avatarUrl", user.getAvatarUrl());
            sendData.put("content", content);
            socket.emit("userChat", sendData);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void createNewBroadcasting(
            String token,
            String title,
            String description,
            String categoryId,
            String thumbnailUrl
    ) throws JSONException {
        JSONObject requestData = new JSONObject();
        requestData.put("token", token);
        requestData.put("title", title);
        requestData.put("description", description);
        requestData.put("categoryId", categoryId);
        requestData.put("thumbnailUrl", thumbnailUrl);
        socket.emit(CREATE_NEW_BROADCASTING, requestData);
    }

    public static void stopBroadcasting(String token, String broadcastingId) throws JSONException {
        JSONObject requestData = new JSONObject();
        requestData.put("token", token);
        requestData.put("broadcastingId", broadcastingId);
        socket.emit(STOP_BROADCASTING, requestData);
    }
}
