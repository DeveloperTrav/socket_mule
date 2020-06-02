package core.nodes;

import core.API;
import core.models.Item;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.script.TaskNode;

public class Trade extends TaskNode {

    @Override
    public boolean accept() {
        System.out.println(canTrade());
        return canTrade();
    }

    @Override
    public int execute() {
        if (getTrade().isOpen()) {
            if (getTrade().isOpen(1)) {
                if (API.allItemsInTrade()) {
                    if (getTrade().contains(false, "Red spiders' eggs")) {
                        if (getTrade().acceptTrade(1))
                            sleepUntil(() -> getTrade().isOpen(2), 10000);
                    }
                } else {
                    for (Item item : API.currentRequest.getItems()) {
                        if (!getTrade().contains(true, item.getAmount(), item.getName())) {
                            if (getTrade().addItem(item.getName(), item.getAmount()))
                                sleepUntil(() -> getTrade().contains(true, item.getAmount(), item.getName()), (int) Calculations.nextGaussianRandom(5000, 2000));
                        }
                    }
                }
            } else {
                if (getTrade().contains(false, "Red spiders' eggs")) {
                    if (getTrade().acceptTrade(2))
                        sleepUntil(() -> !getTrade().isOpen(2), (int) Calculations.random(7000, 2000));
                }
            }
        } else {
            if (getTrade().tradeWithPlayer(API.currentRequest.getName()))
                sleepUntil(() -> getTrade().isOpen(), 30000);
        }
        return API.sleep();
    }

    private boolean canTrade() {
        return API.canMule && API.hasSupplies && API.withdrewAllSupplies();
    }
}
