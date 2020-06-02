package core.nodes;

import core.API;
import org.dreambot.api.randoms.RandomManager;
import org.dreambot.api.script.TaskNode;

public class Logout extends TaskNode {
    private RandomManager randomManager;

    public Logout(RandomManager randomManager) {
        this.randomManager = randomManager;
    }

    @Override
    public boolean accept() {
        log("LOGOUT: " + canLogout());
        return canLogout();
    }

    @Override
    public int execute() {
        //if no supplies then send message otherwise just logout

        return API.sleep();
    }

    private boolean canLogout() {
        return !API.canMule;
    }
}
