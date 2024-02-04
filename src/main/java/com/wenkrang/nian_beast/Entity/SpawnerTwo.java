package com.wenkrang.nian_beast.Entity;

import com.wenkrang.nian_beast.Nian_beast;
import org.bukkit.entity.Phantom;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

import static com.wenkrang.nian_beast.Entity.PlayerJoin.findAirBlock;
import static com.wenkrang.nian_beast.Nian_beast.isShutdown;

public class SpawnerTwo implements Listener {
    @EventHandler
    public static void SpawnTwo(PlayerJoinEvent event) {
        new BukkitRunnable() {
            /**
             * 重写的run方法
             */
            @Override
            public void run() {
                // 如果已经关闭，则取消任务
                if (isShutdown) {
                    cancel();
                }
                new BukkitRunnable() {

                    @Override
                    public void run() {
                        // 如果玩家在线
                        if (event.getPlayer().isOnline()) {
                            // 如果玩家所在的维度是"end"
                            if (event.getPlayer().getWorld().getName().equalsIgnoreCase("world_the_end")) {
                                // 如果玩家眼睛所处的方块高度小于320
                                if (event.getPlayer().getEyeLocation().getBlockY() < 320) {
                                    Random random = new Random();
                                    // 随机数小于30时
                                    if (random.nextInt(100) < 30) {

                                        // 在玩家眼睛所处的方块周围寻找空气方块
                                        for (int i = 0; i < 3; i++) {
                                            Phantom entitytwo = entity.getEntitytwo(findAirBlock(event.getPlayer().getEyeLocation()));
                                            entitytwo.setTarget(event.getPlayer());
                                        }

                                    }
                                }
                            }
                        } else {
                            // 如果玩家不在线，取消任务
                            cancel();
                        }
                    }
                }.runTaskLater(Nian_beast.getPlugin(Nian_beast.class), 0);

            }
        }.runTaskTimerAsynchronously(Nian_beast.getPlugin(Nian_beast.class), 0, 12000);
    }
}
