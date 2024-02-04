package com.wenkrang.nian_beast.Entity;

import com.wenkrang.nian_beast.Nian_beast;
import com.wenkrang.nian_beast.command.nb;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

import java.util.*;

import static com.wenkrang.nian_beast.Nian_beast.isShutdown;

public class EntityTarget implements Listener {
    public static void EvokerFangsDamage(Mob mob) {
        Location mobEyeLocation = mob.getLocation();
        Location location2 = mobEyeLocation.clone();

        location2.add(location2.getDirection().clone().multiply(2.5));
        for (int i = 0; i < 30; i++) {
            location2.add(location2.getDirection().clone().multiply(0.8));
            location2.setPitch(0);
            mob.getWorld().spawn(location2, EvokerFangs.class);
            World world = mob.getWorld();
            Collection<Entity> nearbyEntities = world.getNearbyEntities(location2, 1, 1, 1);
            for (Entity entity : nearbyEntities) {
                if (entity instanceof Damageable) {
                    Damageable damageable = (Damageable) entity;
                    damageable.damage(19);
                }
            }
        }

    }


    public static double[] getLookDirection(Location from, Location to) {
        Vector direction = to.toVector().subtract(from.toVector()).normalize();
        double yaw = Math.atan2(direction.getY(), direction.getX()) * 180 / Math.PI - 90;
        double pitch = -Math.atan2(direction.getZ(), Math.sqrt(direction.getX() * direction.getX() + direction.getY() * direction.getY())) * 180 / Math.PI;
        return new double[]{yaw, pitch};
    }

    public static void Damage(Player player, Mob mob) {

        double distance = mob.getLocation().distance(player.getLocation());

        if (distance < 30) {
            EvokerFangsDamage(mob);
        }

    }


    @EventHandler
    public static void onEntityTarget(EntityTargetLivingEntityEvent event) {

        if (event.getTarget() != null) {
            if (event.getTarget() instanceof Player) {
                Player player = (Player) event.getTarget();
                if (event.getEntity().getScoreboardTags().contains("nian_beastthree")) {
                    Mob mob = (Mob) event.getEntity();
                    new BukkitRunnable() {

                        @Override
                        public void run() {
                            if (!isShutdown && mob.getTarget() != null && !mob.isDead()) {
                                Random random = new Random();

                                    new BukkitRunnable() {
                                        @Override
                                        public void run() {
                                            Damage(player, mob);
                                        }
                                    }.runTaskLater(Nian_beast.getPlugin(Nian_beast.class), 0);

                            } else {
                                cancel();
                            }
                        }
                    }.runTaskTimerAsynchronously(Nian_beast.getPlugin(Nian_beast.class), 0, 60);
                    new BukkitRunnable() {

                        @Override
                        public void run() {
                            if (!isShutdown && mob.getTarget() != null && !mob.isDead()) {
                                Random random = new Random();

                                new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        mob.teleport(player.getLocation());
                                    }
                                }.runTaskLater(Nian_beast.getPlugin(Nian_beast.class), 0);

                            } else {
                                cancel();
                            }
                        }
                    }.runTaskTimerAsynchronously(Nian_beast.getPlugin(Nian_beast.class), 0, 300);
                }
            }
        }




    }
}
