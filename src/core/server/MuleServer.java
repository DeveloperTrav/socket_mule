package core.server;

import core.API;
import core.Utils;
import core.models.Item;
import core.models.Request;
import core.models.Response;
import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.randoms.RandomEvent;
import org.dreambot.api.randoms.RandomManager;
import org.dreambot.api.script.AbstractScript;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.util.Objects;

public class MuleServer extends WebSocketServer {
    private MethodContext context;
    private RandomManager randomManager;

    public MuleServer(InetSocketAddress address, MethodContext context) {
        super(address);
        this.context = context;
    }

    @Override
    public void onOpen(WebSocket webSocket, ClientHandshake clientHandshake) {
        API.client = webSocket;
        System.out.println("New connection from: " + webSocket.getRemoteSocketAddress());
    }

    @Override
    public void onClose(WebSocket webSocket, int i, String s, boolean b) {
        System.out.println("Closed socket: " + webSocket.getRemoteSocketAddress() + " exit code: " + i + " reason: " + s);
    }

    @Override
    public void onMessage(WebSocket webSocket, String json) {
        Request request = Utils.getGson().fromJson(json, Request.class);
        API.requests.add(request);
    }

    @Override
    public void onError(WebSocket webSocket, Exception e) {
        if (Objects.nonNull(webSocket))
            System.out.println("Error on: " + webSocket.getRemoteSocketAddress());

        e.printStackTrace();
    }

    @Override
    public void onStart() {
        System.out.println("Server started successfully");
    }
}
