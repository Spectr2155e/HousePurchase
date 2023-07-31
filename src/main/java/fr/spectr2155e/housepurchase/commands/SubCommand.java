package fr.spectr2155e.housepurchase.commands;

import org.bukkit.entity.Player;

public abstract class SubCommand {

    public abstract String getName();

    public abstract String getPermission();

    public abstract String getDescription();

    public abstract void perform(Player player, String[] args);
}
