package fr.spectr2155e.housepurchase.managers;

import fr.spectr2155e.housepurchase.HousePurchase;
import fr.spectr2155e.housepurchase.classes.Houses;
import fr.spectr2155e.housepurchase.objects.managers.DiscordWebhook;
import fr.spectr2155e.housepurchase.objects.managers.Utils;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.awt.*;
import java.io.IOException;

public class WebhookHouseManager {
    public static void createWebHook(String option, Player player, int id) throws IOException {
        DiscordWebhook webhook = new DiscordWebhook("https://discord.com/api/webhooks/1210529395438256168/HROECWZDy8ZhcJHJJAtL2fsVLo3kOH2clhoa_4Hx1d3temX-mz88I2myCJ6WzVntkQ0X");
        Location loc = Utils.getJSONToLocation(Houses.houses.get(id).getLocation());
        Houses house = Houses.houses.get(id);
        switch (option) {
            case "createHouse":
                webhook.addEmbed(new DiscordWebhook.EmbedObject()
                        .setTitle("Création de maison - " + HousePurchase.utils.getTime())
                        .addField("", "- ID: "+house.getId(), true)
                        .addField("", "- Prix d'achat: "+house.getPriceOfBuy(), true)
                        .addField("", "- Prix de location par jour: "+house.getPriceOfLease(), true)
                        .addField("", "- Position: x:"+ loc.getX() + ", y: "+loc.getY()+", z:"+loc.getZ()+", yaw:"+loc.getYaw() +", pitch: "+loc.getPitch(), true)

                        .setColor(Color.GREEN)
                        .setAuthor(player.getName(), "https://fr.namemc.com/search?q=" + player.getName(), "https://cravatar.eu/avatar/" + player.getName()));
                webhook.execute();
                break;
            case "removeHouse":
                if(house.isLease()){
                    webhook.addEmbed(new DiscordWebhook.EmbedObject()
                            .setTitle("Suppresion de maison - " + HousePurchase.utils.getTime())
                            .addField("", "- ID: "+house.getId(), true)
                            .addField("", "- Ancien prix d'achat: "+house.getPriceOfBuy(), true)
                            .addField("", "- Ancien prix de location: "+house.getPriceOfLease(), true)
                            .addField("", "- Ancienne Position: x:"+ loc.getX() + ", y: "+loc.getY()+", z:"+loc.getZ()+", yaw:"+loc.getYaw() +", pitch: "+loc.getPitch(), true)
                            .addField("", "- Ancien(ne) locataire: "+house.getOwner(), true)
                            .addField("", "- Ancienne fin de location: "+house.getLeaseDate(), true)
                            .setColor(Color.GREEN)
                            .setAuthor(player.getName(), "https://fr.namemc.com/search?q=" + player.getName(), "https://cravatar.eu/avatar/" + player.getName()));
                    webhook.execute();
                    return;
                }
                if(house.isOwned()){
                    webhook.addEmbed(new DiscordWebhook.EmbedObject()
                            .setTitle("Suppresion de maison - " + HousePurchase.utils.getTime())
                            .addField("", "- ID: "+house.getId(), true)
                            .addField("", "- Ancien prix d'achat: "+house.getPriceOfBuy(), true)
                            .addField("", "- Ancien prix de location: "+house.getPriceOfLease(), true)
                            .addField("", "- Ancienne Position: x:"+ loc.getX() + ", y: "+loc.getY()+", z:"+loc.getZ()+", yaw:"+loc.getYaw() +", pitch: "+loc.getPitch(), true)
                            .addField("", "- Ancien(ne) propriétaire: "+house.getOwner(), true)
                            .addField("", "- Ancienne date d'achat: "+house.getDateOfBuy(), true)
                            .setColor(Color.GREEN)
                            .setAuthor(player.getName(), "https://fr.namemc.com/search?q=" + player.getName(), "https://cravatar.eu/avatar/" + player.getName()));
                    webhook.execute();
                    return;
                }
                webhook.addEmbed(new DiscordWebhook.EmbedObject()
                        .setTitle("Suppresion de maison - " + HousePurchase.utils.getTime())
                        .addField("", "- ID: "+house.getId(), true)
                        .addField("", "- Ancien prix d'achat: "+house.getPriceOfBuy(), true)
                        .addField("", "- Ancien prix de location: "+house.getPriceOfLease(), true)
                        .addField("", "- Ancienne Location: x:"+ loc.getX() + ", y: "+loc.getY()+", z:"+loc.getZ()+", yaw:"+loc.getYaw() +", pitch: "+loc.getPitch(), true)
                        .setColor(Color.GREEN)
                        .setAuthor(player.getName(), "https://fr.namemc.com/search?q=" + player.getName(), "https://cravatar.eu/avatar/" + player.getName()));
                webhook.execute();
                break;
        }
    }
}
