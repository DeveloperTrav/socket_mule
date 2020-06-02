package core.nodes;

import core.API;
import org.dreambot.api.data.GameState;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.randoms.RandomEvent;
import org.dreambot.api.randoms.RandomManager;
import org.dreambot.api.script.TaskNode;

import java.util.Objects;

public class Login extends TaskNode {
    private final RandomManager randomManager;

    public Login(RandomManager randomManager) {
        this.randomManager = randomManager;
    }

    @Override
    public boolean accept() {
        log("LOGIN: " + canLogin());
        return canLogin();
    }

    @Override
    public int execute() {
        API.currentRequest = API.requests.remove(0);
        getWorldHopper().quickHop(API.currentRequest.getWorld());
        sleepUntil(() -> getClient().getCurrentWorld() == API.currentRequest.getWorld(), (int) Calculations.nextGaussianRandom(3000, 1500));
        randomManager.enableSolver(RandomEvent.LOGIN);
        sleepUntil(() -> getClient().getGameState() != GameState.LOADING && getClient().getGameState() != GameState.INITIAL_SPLASH_SCREEN
                && getClient().isLoggedIn(), 7000);
        return API.sleep();
    }

    private boolean canLogin() {
        return !API.requests.isEmpty() && !getClient().isLoggedIn() && Objects.isNull(API.currentRequest);
    }
}
