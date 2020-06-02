package core;

import com.google.gson.Gson;

public class Utils {
    private static Gson gson;

    public Utils(Gson gson) {
        Utils.gson = gson;
    }

    public static Gson getGson() {
        return gson;
    }
}
