package com.wenkrang.nian_beast.Entity;

import com.wenkrang.nian_beast.Nian_beast;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import static com.wenkrang.nian_beast.Nian_beast.isShutdown;




public class PlayerJoin implements Listener {
    /**
     * 自动设置实体的目标
     *
     * @param range    仇视范围
     * @param mobtaget 要设置目标的实体的标签
     * @param player   检查并运行方法的对象
     * @return 是否锁定目标
     */                                         
    public static boolean AutoSetTarget(int range, String mobtaget, Player player) {
        boolean IsFind = false;

        // 获取玩家附近的实体列表
        List<Entity> nearbyEntities = player.getNearbyEntities(range, range, range);

        // 遍历附近的实体列表
        for (int i = 0; i < nearbyEntities.size(); i++) {
            // 如果附近的实体的Scoreboard标签中包含mobtaget标签
            if (nearbyEntities.get(i).getScoreboardTags().contains(mobtaget)) {
                // 获取附近的实体的mob对象
                Mob mob = (Mob) nearbyEntities.get(i);
                // 获取mob对象附近的实体列表
                List<Entity> nearbyEntities1 = mob.getNearbyEntities(range, range, range);
                // 遍历mob对象附近的实体列表
                for (int j = 0; j < nearbyEntities1.size(); j++) {
                    Entity entity = nearbyEntities1.get(j);
                    // 如果实体的类型是Player
                    if (entity.getType().equals(EntityType.PLAYER)) {
                        // 如果实体的游戏模式是SURVIVAL
                        if (((Player) entity).getGameMode().equals(GameMode.SURVIVAL)) {
                            // 设置PolarBear对象的目标为实体
                            mob.setTarget((LivingEntity) entity);
                            IsFind = true;
                            // 跳出循环
                            break;
                        }
                    }
                }
            }
        }
        return IsFind;
    }

    /**
     * 查找上方有空气方块的位置
     *
     * @param location1 起始位置
     * @return 上方有空气方块的位置，如果没有则返回null
     */
    public static Location findAirBlock(Location location1) {
        Location result = null;
        Location location = location1.clone();
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = location.getBlockY() + 1; i < location.getBlockY() + 45; i++) {
            Location x = location.clone();
            x.setY(i);
            if (x.getBlock().getBlockData().getMaterial().equals(Material.AIR)) {
                list.add(x.getBlockY());
            }
        }
        if (!list.isEmpty()) {
            int maxValue = list.get(0);
            for (int num : list) {
                if (num > maxValue) {
                    maxValue = num;
                }
            }
            result = location1.clone();
            result.setY(maxValue);
        }
        return result;
    }
    @EventHandler
    public static void OnJoin(PlayerJoinEvent event) {
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
                    // 如果玩家所在的服务器是"world"世界
                    if (event.getPlayer().getWorld().getName().equalsIgnoreCase("world") || event.getPlayer().getWorld().getName().equalsIgnoreCase("world_the_end")) {
                        // 创建一个BukkitRunnable任务
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                AutoSetTarget(25, "nian_beastone", event.getPlayer());
                                AutoSetTarget(50, "nian_beasttwo", event.getPlayer());
                            }
                        }.runTaskLater(Nian_beast.getPlugin(Nian_beast.class), 0);
                    }
                } else {
                    // 如果玩家不在线，则取消任务
                    cancel();
                }
            }
        }.runTaskTimerAsynchronously(Nian_beast.getPlugin(Nian_beast.class), 0, 40);

    }
}
