package com.wenkrang.nian_beast.Entity.raid;

import com.wenkrang.nian_beast.Nian_beast;
import com.wenkrang.nian_beast.lib.SpigotConsoleColors;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.generator.structure.Structure;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.StructureSearchResult;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.Random;

public class RaidEvent {
    public static Structure getnearvillage (Player player) {
        ArrayList<Location> locations = new ArrayList<>();
        ArrayList<StructureSearchResult> results = new ArrayList<>();
        Structure structure = null;
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
            for (int i = 0; i < locations.size(); i++) {
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
                    structure = results.get(i).getStructure();
                }
            }
        }
        return structure;
    }

    public static void RaidMain(BossBar bossBar, Location location, Player player, Location Realocation) {
        //袭击号角

        Collection<Entity> nearbyEntities = location.getWorld().getNearbyEntities(location, 150, 150, 150);
        for (Entity entity : nearbyEntities) {
            if (entity instanceof Player) {
                Player player1 = (Player) entity;
                player1.playSound(player1.getLocation(), Sound.EVENT_RAID_HORN, 7F, 1);

            }
        }

        location.getWorld().playSound(location, Sound.EVENT_RAID_HORN, 7F, 1);

        //袭击开始
        new BukkitRunnable(){

            @Override
            public void run() {
                bossBar.setVisible(false);
                bossBar.removeAll();
                Nian_beast.Keys.remove(String.valueOf(location.getBlockX())+String.valueOf(location.getBlockY())+String.valueOf(location.getBlockZ()));
            }
        }.runTaskLater(Nian_beast.getPlugin(Nian_beast.class), 60);

    }





    /**
     * 加载年兽BossBar并开始加载进度
     *
     * @param bossBar 年兽BossBar对象
     * @param location 事件位置
     * @param player 玩家对象
     */
    public static void LoadRaid(BossBar bossBar, Location location, Player player) {

        if (!Nian_beast.Keys.contains(String.valueOf(location.getBlockX())+String.valueOf(location.getBlockY())+String.valueOf(location.getBlockZ()))) {
            Nian_beast.Keys.add(String.valueOf(location.getBlockX())+String.valueOf(location.getBlockY())+String.valueOf(location.getBlockZ()));
            new BukkitRunnable() {
                @Override
                public void run() {


                    //跑条子
                    if (bossBar.getProgress() + 0.01 >= 1) {
                        new BukkitRunnable() {

                            @Override
                            public void run() {
                                bossBar.setTitle(SpigotConsoleColors.DARK_YELLOW + SpigotConsoleColors.BOLD +
                                        "年兽" + SpigotConsoleColors.RESET + SpigotConsoleColors.DARK_YELLOW + "袭击");

                                RaidMain(bossBar, location, player, location);
                            }
                        }.runTaskLater(Nian_beast.getPlugin(Nian_beast.class), 0);


                        cancel();

                    } else {
                        new BukkitRunnable() {

                            @Override
                            public void run() {
                                bossBar.setProgress(bossBar.getProgress() + 0.01);
                                //寻找袭击位置

                                Random random = new Random();
                                if (random.nextInt(100) < 5) {
                                    Objects.requireNonNull(location.getWorld()).playSound(location, Sound.BLOCK_BELL_USE, 7F, 1);
                                }
                            }
                        }.runTaskLater(Nian_beast.getPlugin(Nian_beast.class), 0);
                    }

                }
            }.runTaskTimerAsynchronously(Nian_beast.getPlugin(Nian_beast.class), 0, 5);
            new BukkitRunnable() {

                @Override
                public void run() {
                    if (Nian_beast.isShutdown) {
                        cancel();
                    }


                    Collection<Entity> nearbyEntities = Objects.requireNonNull(location.getWorld()).getNearbyEntities(location, 150, 150, 150);
                    for (Entity entity : nearbyEntities) {
                        if (entity instanceof Player) {
                            Player player1 = (Player) entity;
                            bossBar.addPlayer(player1);
                        }
                    }


                }
            }.runTaskTimer(Nian_beast.getPlugin(Nian_beast.class), 0, 20);
        }




    }
}
