package fr.spectr2155e.housepurchase.commands;

import org.bukkit.entity.Player;

public class Test extends SubCommand {

    @Override
    public String getName() {
        return "test";
    }

    @Override
    public String getPermission() {
        return "permission";
    }

    @Override
    public String getDescription() {
        return "akakaja";
    }

    @Override
    public void perform(Player player, String[] args) {
        player.sendMessage("test");
    }
}
