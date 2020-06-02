package core.nodes;

import core.API;
import core.models.Item;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.bank.BankMode;
import org.dreambot.api.script.TaskNode;

import java.util.Objects;

public class Bank extends TaskNode {

    @Override
    public boolean accept() {
        log("BANK: " + canBank());
        return canBank();
    }

    @Override
    public int execute() {
        if (API.currentRequest.getBankArea().contains(getLocalPlayer())) {
            if (getBank().isOpen()) {
                if (getBank().getWithdrawMode().equals(BankMode.NOTE)) {
                    for (Item item : API.currentRequest.getItems()) {
                        if (!getInventory().contains(item.getName())) {
                            getBank().withdraw(item.getName(), item.getAmount());
                            sleepUntil(() -> getInventory().contains(item.getName()), (int)Calculations.nextGaussianRandom(5000, 1500));
                        }
                    }
                    if (getBank().close())
                        sleepUntil(() -> !getBank().isOpen(), (int) Calculations.nextGaussianRandom(3000, 2000));
                } else {
                    if (getBank().setWithdrawMode(BankMode.NOTE))
                        sleepUntil(() -> getBank().getWithdrawMode().equals(BankMode.NOTE), (int) Calculations.nextGaussianRandom(4000, 2000));
                }
            } else {
                if (getWalking().shouldWalk(Calculations.random(2, 4))) {
                    getBank().openClosest();
                }
            }
        } else {
            if (getBank().isOpen()) {
                if (getBank().close())
                    sleepUntil(() -> !getBank().isOpen(), (int)Calculations.nextGaussianRandom(4000, 2000));
                //6 allfrey99@hotmail.com:fish5648||138.128.63.175
                //heart-kolakis@hotmail.com:peepeepoO32||Megera11618:L117Bpxd0||138.128.63.147
            }

            if (getWalking().shouldWalk(Calculations.random(2, 7))) {
                getWalking().walk(API.currentRequest.getBankArea().getCenter().getRandomizedTile(3));
            }
        }
        return API.sleep();
    }

    private boolean canBank() {
        return API.canMule && API.hasSupplies && !API.isCheckingBank && Objects.nonNull(API.currentRequest)
                && !API.currentRequest.getItems().isEmpty() && !API.withdrewAllSupplies();
    }
}
