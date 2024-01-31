package com.wenkrang.nian_beast.Entity;

import com.wenkrang.nian_beast.Nian_beast;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.wenkrang.nian_beast.Nian_beast.isShutdown;

public class PlayerJoin implements Listener {

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
        for (int i = location.getBlockY() + 1; i < 319; i++) {
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
                    if (event.getPlayer().getWorld().getName().equalsIgnoreCase("world")) {
                        // 创建一个BukkitRunnable任务
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                // 获取玩家附近的实体列表
                                List<Entity> nearbyEntities = event.getPlayer().getNearbyEntities(25, 25, 25);

                                // 遍历附近的实体列表
                                for (int i = 0; i < nearbyEntities.size(); i++) {
                                    // 如果附近的实体的Scoreboard标签中包含"nian_beastone"
                                    if (nearbyEntities.get(i).getScoreboardTags().contains("nian_beastone")) {
                                        // 获取附近的实体的PolarBear对象
                                        PolarBear polarBear = (PolarBear) nearbyEntities.get(i);
                                        // 获取PolarBear对象附近的实体列表
                                        List<Entity> nearbyEntities1 = polarBear.getNearbyEntities(25, 25, 25);
                                        // 遍历PolarBear对象附近的实体列表
                                        for (int j = 0; j < nearbyEntities1.size(); j++) {
                                            Entity entity = nearbyEntities1.get(j);
                                            // 如果实体的类型是Player
                                            if (entity.getType().equals(EntityType.PLAYER)) {
                                                // 如果实体的游戏模式是SURVIVAL
                                                if (((Player) entity).getGameMode().equals(GameMode.SURVIVAL)) {
                                                    // 设置PolarBear对象的目标为实体
                                                    polarBear.setTarget((LivingEntity) entity);
                                                    // 跳出循环
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }.runTaskLater(Nian_beast.getPlugin(Nian_beast.class), 0);
                    }
                } else {
                    // 如果玩家不在线，则取消任务
                    cancel();
                }
            }
        }.runTaskTimerAsynchronously(Nian_beast.getPlugin(Nian_beast.class), 0, 40);
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
                // 如果玩家在线
                if (event.getPlayer().isOnline()) {
                    // 如果玩家所在的维度是"end"
                    if (event.getPlayer().getWorld().getName().equalsIgnoreCase("end")) {
                        // 如果玩家眼睛所处的方块高度小于320
                        if (event.getPlayer().getEyeLocation().getBlockY() < 320) {
                            Random random = new Random();
                            // 随机数小于30时
                            if (random.nextInt(100) < 30) {
                                boolean hasblock = false;
                                // 遍历玩家眼睛所处的方块以上1到319的方块
                                for (int i = event.getPlayer().getEyeLocation().getBlockY() + 1; i < 319; i++) {
                                    Location clone = event.getPlayer().getEyeLocation().clone();
                                    clone.setY(i);
                                    // 如果方块不是空气
                                    if (!clone.getBlock().getBlockData().getMaterial().equals(Material.AIR)) {
                                        hasblock = true;
                                    }
                                }
                                // 如果玩家眼睛所处的方块以上没有方块
                                if (!hasblock) {
                                    // 在玩家眼睛所处的方块周围寻找空气方块
                                    for (int i = 0; i < 3; i++) {
                                        entity.getEntitytwo(findAirBlock(event.getPlayer().getEyeLocation()));
                                    }
                                }
                            }
                        }
                    }
                } else {
                    // 如果玩家不在线，取消任务
                    cancel();
                }
            }
        }.runTaskTimerAsynchronously(Nian_beast.getPlugin(Nian_beast.class), 0, 24000);
    }
}
