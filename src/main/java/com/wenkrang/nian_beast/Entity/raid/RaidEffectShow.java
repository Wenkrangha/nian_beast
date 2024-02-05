package com.wenkrang.nian_beast.Entity.raid;

import com.wenkrang.nian_beast.Nian_beast;
import com.wenkrang.nian_beast.lib.SpigotConsoleColors;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.generator.structure.Structure;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.StructureSearchResult;

import javax.xml.crypto.dsig.spec.ExcC14NParameterSpec;
import java.util.ArrayList;

public class RaidEffectShow implements Listener {
    public static void AddEffect(Player player) {

    }
    public static void showeffect(Player player) {
        player.sendMessage(Nian_beast.RaidEffect.toString());
        //如果玩家有这个效果，就显示效果
        if (Nian_beast.RaidEffect.contains(player.getPlayerProfile().getUniqueId().toString())) {
            //效果的显示
            BossBar bossBar = Bukkit.createBossBar(SpigotConsoleColors.DARK_YELLOW + SpigotConsoleColors.BOLD +
                    "年兽" + SpigotConsoleColors.RESET + SpigotConsoleColors.DARK_YELLOW + "袭击", BarColor.RED, BarStyle.SOLID);
            bossBar.setVisible(true);
            bossBar.addPlayer(player);
            bossBar.setProgress(0);
            //持续检测玩家的位置，以便更新效果显示
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (player.isOnline()) {
                        new BukkitRunnable() {

                            @Override
                            public void run() {
                                ArrayList<Location> locations = new ArrayList<>();
                                ArrayList<StructureSearchResult> results = new ArrayList<>();
                                results.add(player.getWorld().locateNearestStructure(player.getLocation(), Structure.VILLAGE_DESERT, 200, false));
                                results.add(player.getWorld().locateNearestStructure(player.getLocation(), Structure.VILLAGE_SNOWY, 200, false));
                                results.add(player.getWorld().locateNearestStructure(player.getLocation(), Structure.VILLAGE_PLAINS, 200, false));
                                results.add(player.getWorld().locateNearestStructure(player.getLocation(), Structure.VILLAGE_SAVANNA, 200, false));
                                results.add(player.getWorld().locateNearestStructure(player.getLocation(), Structure.VILLAGE_TAIGA, 200, false));
                                for (StructureSearchResult result : results) {
                                    if (result != null) {
                                        locations.add(result.getLocation());
                                    }
                                }
                                if (!locations.isEmpty()) {
                                    //从Location获取与玩家的距离
                                    ArrayList<Double> distances = new ArrayList<Double>();
                                    for (Location location : locations) {
                                        Location location1 = location.clone();
                                        location1.setY(player.getLocation().getBlockY());
                                        distances.add(location1.distance(player.getLocation()));
                                    }
                                    //从distances选出最小值
                                    double min = distances.get(0);
                                    for (int i = 1; i < distances.size(); i++) {
                                        if (distances.get(i) < min) {
                                            min = distances.get(i);
                                        }
                                    }
                                    min = min - 40;
                                    min = 160 - min;
                                    if (min > 0) {
                                        if (min > 160) {
                                            bossBar.setStyle(BarStyle.SOLID);
                                            bossBar.setProgress(1);
                                            bossBar.setTitle(SpigotConsoleColors.DARK_YELLOW + SpigotConsoleColors.BOLD +
                                                    "年兽" + SpigotConsoleColors.RESET + SpigotConsoleColors.DARK_YELLOW + "袭击 - 即将来临");

                                        } else {
                                            bossBar.setStyle(BarStyle.SEGMENTED_12);
                                            bossBar.setTitle(SpigotConsoleColors.DARK_YELLOW + SpigotConsoleColors.BOLD +
                                                    "年兽" + SpigotConsoleColors.RESET + SpigotConsoleColors.DARK_YELLOW + "袭击");
                                            double progress = min / 160;
                                            try {
                                                bossBar.setProgress(progress);
                                            }catch (Exception e) {}
                                        }


                                        player.sendMessage(String.valueOf(min));
                                    } else {
                                        bossBar.setStyle(BarStyle.SOLID);
                                        bossBar.setProgress(1);
                                    }
                                }else {
                                    bossBar.setProgress(1);
                                }


                            }
                        }.runTaskLater(Nian_beast.getPlugin(Nian_beast.class), 0);







                    } else {
                        cancel();
                    }
                }
            }.runTaskTimerAsynchronously(Nian_beast.getPlugin(Nian_beast.class), 0, 2);
        }
    }
    @EventHandler
    public static void OnRaidEffectShow(PlayerJoinEvent event) {
        showeffect(event.getPlayer());

    }
}
