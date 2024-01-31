package com.wenkrang.nian_beast.Entity;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByBlockEvent;

public class EntityDamageByBlock implements Listener {
    @EventHandler
    public static void OnEntityDamageByBlockEvent (EntityDamageByBlockEvent event) {
        if (event.getEntity().getScoreboardTags().contains("nian_beastone")) {
            event.setDamage(1);
        }
    }
}
