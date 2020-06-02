package core;

import core.models.Item;
import core.models.Request;
import core.server.MuleServer;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodContext;
import org.java_websocket.WebSocket;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class API {
    private static MethodContext context;
    public static String status = "Starting script...";
    public static int eggs = 0;
    public static boolean isCheckingBank = true;
    public  static boolean canMule = true;
    public static boolean hasSupplies = true;
    public static List<Request> requests;
    public static Request currentRequest;
    public static MuleServer server;
    public static WebSocket client;

    public API(MethodContext context) {
        API.context = context;
        API.requests = new ArrayList<>();
    }

    public static int sleep() {
        return (int) Calculations.nextGaussianRandom(400, 150);
    }

    public static boolean withdrewAllSupplies() {
        if (Objects.nonNull(API.currentRequest)) {
            if (Objects.nonNull(API.currentRequest.getItems())) {
                for (Item item : currentRequest.getItems()) {
                    if (!context.getInventory().contains(item.getName()))
                        return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    public static boolean allItemsInTrade() {
        if (Objects.nonNull(API.currentRequest)) {
            if (Objects.nonNull(API.currentRequest.getItems())) {
                for (Item item : API.currentRequest.getItems()) {
                    if (!context.getTrade().contains(true, item.getName()))
                        return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }
}
