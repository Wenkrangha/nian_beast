package com.wenkrang.nian_beast.Entity;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.util.Random;

public class Spawner implements Listener {
    /**
     * 当生物生成时触发的事件
     */
    @EventHandler
    public void onSpawn(CreatureSpawnEvent event) {
        if (event.getEntity().getWorld().getName().equalsIgnoreCase("world")) {
            if (event.getSpawnReason() == CreatureSpawnEvent.SpawnReason.NATURAL) { //这里是检测生成的方法，NA
                // TURAL也就是自然生成
                if (event.getEntity().getType().equals(EntityType.SPIDER)) {

                    Random random = new Random();
                    if (random.nextInt(100) <= 10) {
                        entity.getEntityone(event.getLocation());
                        event.setCancelled(true);
                    }
                }
                if (event.getEntity().getType().equals(EntityType.SKELETON)) {

                    Random random = new Random();
                    if (random.nextInt(100) <= 5) {
                        entity.getEntityone(event.getLocation());
                        event.setCancelled(true);
                    }
                }
                if (event.getEntity().getType().equals(EntityType.PHANTOM)) {

                    Random random = new Random();

                    entity.getEntitytwo(event.getLocation());
                    event.setCancelled(true);

                }
            }
        }

    }
}
