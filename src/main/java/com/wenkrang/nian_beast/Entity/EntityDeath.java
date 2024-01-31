package com.wenkrang.nian_beast.Entity;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class EntityDeath implements Listener {
    /**
     * 当实体死亡事件发生时的事件处理器。
     *
     * @param event 实体死亡事件对象
     */
    @EventHandler
    public static void OnEntityDeathEvent(org.bukkit.event.entity.EntityDeathEvent event) {
        // 检查实体的得分板标签是否包含"nian_beastone"
        if (event.getEntity().getScoreboardTags().contains("nian_beastone")) {
            Random random = new Random();

            // 随机生成一个0到100的整数，小于5时，掉落数量为1的钻石
            if (random.nextInt(100) < 5) {
                ItemStack itemStack = new ItemStack(Material.DIAMOND);
                event.getEntity().getWorld().dropItem(event.getEntity().getLocation(), itemStack);
            }

            // 随机生成一个0到100的整数，小于5时，掉落数量为1的金块
            if (random.nextInt(100) < 5) {
                ItemStack itemStack = new ItemStack(Material.GOLD_BLOCK);
                event.getEntity().getWorld().dropItem(event.getEntity().getLocation(), itemStack);
            }

            // 始终执行，掉落数量为6到10的火药
            if (true) {
                ItemStack itemStack = new ItemStack(Material.GUNPOWDER, 6 + random.nextInt(4));
                event.getEntity().getWorld().dropItem(event.getEntity().getLocation(), itemStack);
            }

            // 随机生成一个0到100的整数，小于70时，掉落数量为8的曲奇
            if (random.nextInt(100) < 70) {
                ItemStack itemStack = new ItemStack(Material.COOKIE, 8);
                event.getEntity().getWorld().dropItem(event.getEntity().getLocation(), itemStack);
            }

            // 随机生成一个0到100的整数，小于50时，掉落数量为1到2的绿宝石
            if (random.nextInt(100) < 50) {
                ItemStack itemStack = new ItemStack(Material.EMERALD, 1 + random.nextInt(2));
                event.getEntity().getWorld().dropItem(event.getEntity().getLocation(), itemStack);
            }
        }
    }
}
