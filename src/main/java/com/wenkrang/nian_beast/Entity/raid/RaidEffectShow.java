package com.wenkrang.nian_beast.Entity.raid;

import com.wenkrang.nian_beast.Nian_beast;
import com.wenkrang.nian_beast.lib.SpigotConsoleColors;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.generator.structure.Structure;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.StructureSearchResult;

import javax.xml.crypto.dsig.spec.ExcC14NParameterSpec;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class RaidEffectShow implements Listener {
    /**
     * 移除玩家的特效
     * @param player 玩家对象
     */
    public static void RemoveEffect(Player player) {
        // 如果玩家的UUID在Nian_beast.RaidEffect中存在
        if (Nian_beast.RaidEffect.contains(Objects.requireNonNull(player.getPlayerProfile().getUniqueId()).toString())) {
            // 从Nian_beast.RaidEffect中移除玩家的UUID
            player.removeScoreboardTag("Raid");
            Nian_beast.RaidEffect.remove(player.getPlayerProfile().getUniqueId().toString());
        }
    }

    /**
     * 给玩家添加效果
     * @param player 玩家对象
     */
    public static void AddEffect(Player player) throws IOException, InvalidConfigurationException {
        // 如果玩家的唯一ID不在Nian_beast.RaidEffect集合中
        if (!Nian_beast.RaidEffect.contains(Objects.requireNonNull(player.getPlayerProfile().getUniqueId()).toString())) {
            // 将玩家的唯一ID添加到Nian_beast.RaidEffect集合中
            Nian_beast.RaidEffect.add(player.getPlayerProfile().getUniqueId().toString());
            // 显示效果给玩家
            RaidEffectShow.showeffect(player);
            player.addScoreboardTag("Raid");
        }
    }

    /**
     * 显示效果
     * @param player 玩家对象
     */
    public static void showeffect(Player player) throws IOException, InvalidConfigurationException {
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
                    if (player.isOnline() && Nian_beast.RaidEffect.contains(player.getPlayerProfile().getUniqueId().toString())) {
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
                                ArrayList<Structure> structures = new ArrayList<>();
                                for (StructureSearchResult result : results) {
                                    if (result != null) {
                                        locations.add(result.getLocation());
                                        structures.add(result.getStructure());
                                    }
                                }
                                if (!locations.isEmpty()) {
                                    //从Location获取与玩家的距离
                                    for (int i = 0;i < locations.size(); i++) {
                                        Location location = locations.get(i).clone();
                                        location.setY(player.getLocation().getBlockY());
                                        locations.set(i, location);
                                    }
                                    Location location = null;

                                    //从distances选出最小值
                                    double min = locations.get(0).distance(player.getLocation());
                                    for (int i = 1; i < locations.size(); i++) {
                                        if (locations.get(i).distance(player.getLocation()) < min) {
                                            min = locations.get(i).distance(player.getLocation());
                                            location = locations.get(i);

                                        }
                                    }
                                    min = min - 40;
                                    min = 160 - min;
                                    if (min > 0) {
                                        if (min > 160) {
                                            //触发事件
                                            bossBar.setStyle(BarStyle.SOLID);
                                            bossBar.setProgress(0);
                                            bossBar.setTitle(SpigotConsoleColors.DARK_YELLOW + SpigotConsoleColors.BOLD +
                                                    "年兽" + SpigotConsoleColors.RESET + SpigotConsoleColors.DARK_YELLOW + "袭击 - 即将来临");
                                            RemoveEffect(player);

                                            RaidEvent.LoadRaid(bossBar, location, player);

                                        } else {
                                            bossBar.setStyle(BarStyle.SEGMENTED_12);
                                            bossBar.setTitle(SpigotConsoleColors.DARK_YELLOW + SpigotConsoleColors.BOLD +
                                                    "年兽" + SpigotConsoleColors.RESET + SpigotConsoleColors.DARK_YELLOW + "袭击");
                                            double progress = min / 160;
                                            try {
                                                bossBar.setProgress(progress);
                                            } catch (Exception e) {}
                                        }
                                    } else {
                                        bossBar.setStyle(BarStyle.SOLID);
                                        bossBar.setProgress(1);
                                    }
                                } else {
                                    bossBar.setProgress(1);
                                }
                            }
                        }.runTaskLater(Nian_beast.getPlugin(Nian_beast.class), 0);

                    } else {
                        cancel();
                    }
                }
            }.runTaskTimerAsynchronously(Nian_beast.getPlugin(Nian_beast.class), 0, 2);
        }else {
            if (player.getScoreboardTags().contains("Raid")) {
                AddEffect(player);
                showeffect(player);
            }
        }
    }

    @EventHandler
    public static void OnRaidEffectShow(PlayerJoinEvent event) throws IOException, InvalidConfigurationException {
        showeffect(event.getPlayer());
    }
}
