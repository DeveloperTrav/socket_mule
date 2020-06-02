package core.nodes;

import core.API;
import core.Utils;
import core.models.Item;
import core.models.Response;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.script.TaskNode;

import java.util.Objects;

public class CheckBank extends TaskNode {

    @Override
    public boolean accept() {
        log("CHECK BANK: " + canCheckBank());
        return canCheckBank();
    }

    @Override
    public int execute() {
        if (getBank().isOpen()) {
            for (Item item : API.currentRequest.getItems()) {
                if (!getBank().contains(item.getName()) || getBank().count(item.getName()) < item.getAmount()) {
                    API.hasSupplies = false;
                    API.canMule = false;
                    return API.sleep();
                }
            }
            if (API.hasSupplies) {
                API.isCheckingBank = false;
                API.client.send(Utils.getGson().toJson(new Response(getLocalPlayer().getName(), API.hasSupplies)));
            }
        } else {
            if (getWalking().shouldWalk(Calculations.random(2, 5)))
                getBank().openClosest();
        }
        return API.sleep();
    }

    private boolean canCheckBank() {
        return getClient().isLoggedIn() && API.isCheckingBank && Objects.nonNull(API.currentRequest);
    }
}
