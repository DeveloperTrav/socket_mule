import com.google.gson.Gson;
import core.API;
import core.Utils;
import core.nodes.Bank;
import core.nodes.CheckBank;
import core.nodes.Login;
import core.nodes.Trade;
import core.server.MuleServer;
import org.dreambot.api.randoms.RandomEvent;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.script.impl.TaskScript;

import java.awt.*;
import java.io.IOException;
import java.net.InetSocketAddress;

@ScriptManifest(category = Category.MONEYMAKING, name = "socket_mule", author = "Notjohn", version = 1.0)
public class Main extends TaskScript {
    API api = new API(this);
    Utils utils = new Utils(new Gson());

    @Override
    public void onStart() {
        API.server = new MuleServer(new InetSocketAddress("localhost", 4457), this);
        new Thread() {
            @Override
            public void run() {
                super.run();
                API.server.run();
            }
        }.start();
        getRandomManager().disableSolver(RandomEvent.LOGIN);

        addNodes(new Login(getRandomManager()));
        addNodes(new CheckBank());
        addNodes(new Bank());
//        addNodes(new Logout(getRandomManager()));
        addNodes(new Trade());
    }

    @Override
    public void onExit() {
        try {
            API.server.stop();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPaint(Graphics2D g) {
        g.drawString("Status: " + API.status, 10, 30);
        g.drawString("Eggs muled: " + API.eggs, 10, 45);
    }
}
