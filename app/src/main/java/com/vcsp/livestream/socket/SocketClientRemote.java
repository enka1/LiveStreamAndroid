package com.vcsp.livestream.socket;

import android.util.Log;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.engineio.client.Transport;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Manager;
import com.github.nkzawa.socketio.client.Socket;
import com.vcsp.livestream.utils.Url;

import java.net.URISyntaxException;

public class SocketClientRemote {
    private static Socket socket;

    public static Socket getSocket() {
        if (socket != null) {
            return socket;
        }
        try {
            socket = IO.socket(Url.API_SERVER_URL + "/");
            socket.connect();
            socket.io().on(Manager.EVENT_TRANSPORT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    Transport transport = (Transport) args[0];
                    transport.on(Transport.EVENT_ERROR, new Emitter.Listener() {
                        @Override
                        public void call(Object... args) {
                            Exception e = (Exception) args[0];
                            Log.i("ugo", "Transport error " + e);
                            e.printStackTrace();
                            e.getCause().printStackTrace();
                        }
                    });
                }
            });
            return socket;
        } catch (URISyntaxException e) {
            return null;
        }
    }
}
