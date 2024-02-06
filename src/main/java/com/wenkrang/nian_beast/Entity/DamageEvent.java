package com.wenkrang.nian_beast.Entity;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;



public class DamageEvent implements Listener {
    @EventHandler
    public static void OnDamageEvent(EntityDamageEvent event) {
        if (event.getCause().equals(EntityDamageEvent.DamageCause.FIRE_TICK)) {
            if (event.getEntity().getScoreboardTags().contains("nian_beasttwo")) {
                event.getEntity().setFireTicks(0);
                event.setCancelled(true);
            }
        }
    }
}
