package com.wenkrang.nian_beast.Entity;

import com.wenkrang.nian_beast.Nian_beast;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.wenkrang.nian_beast.Entity.PlayerJoin.AutoSetTarget;
import static com.wenkrang.nian_beast.Nian_beast.isShutdown;

public class BossDamage implements Listener {
    @EventHandler
    public static void OnBossDamage (PlayerJoinEvent event) {
        new BukkitRunnable() {
            /**
             * 运行方法
             */
            @Override
            public void run() {
                // 如果已经关闭，则取消任务
                if (isShutdown) {
                    cancel();
                }
                // 如果玩家在线
                if (event.getPlayer().isOnline()) {
                    // 创建一个BukkitRunnable任务
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            AutoSetTarget(40, "nian_beastthree", event.getPlayer());
                        }
                    }.runTaskLater(Nian_beast.getPlugin(Nian_beast.class), 0);

                } else {
                    // 如果玩家不在线，则取消任务
                    cancel();
                }
            }
        }.runTaskTimerAsynchronously(Nian_beast.getPlugin(Nian_beast.class), 0, 40);

    }
}
