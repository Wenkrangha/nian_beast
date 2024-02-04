package com.wenkrang.nian_beast.Entity;

import com.wenkrang.nian_beast.lib.SpigotConsoleColors;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

import static com.wenkrang.nian_beast.Entity.PlayerJoin.findAirBlock;

public class EntityDamageByEntity implements Listener {
    /**
     * 当实体受到实体伤害时触发的事件
     *
     * @param event 实体受到实体伤害的事件
     */
    @EventHandler
    public static void OnEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
        if (event.getEntity().getScoreboardTags().contains("nian_beastone") || event.getEntity().getScoreboardTags().contains("nian_beasttwo") || event.getEntity().getScoreboardTags().contains("nian_beastthree")) {
            if (event.getDamager() instanceof Player) {
                Random random = new Random();
                Player player = (Player) event.getDamager();
                if (event.getEntity().getScoreboardTags().contains("nian_beastone")) {
                    if (random.nextInt(100) < 10) {
                        player.sendMessage(SpigotConsoleColors.DARK_RED + "Nian_Beast " + SpigotConsoleColors.RESET + SpigotConsoleColors.WHITE + ">> " + SpigotConsoleColors.RESET + SpigotConsoleColors.DARK_YELLOW + "普通年兽 : 纳米上皮组织，小子! ( 你只能使用烟花来攻击 )");
                    }
                }
            }

            if (event.getDamager().getType().equals(EntityType.FIREWORK)) {
                Firework firework = (Firework) event.getDamager();
                FireworkMeta meta = firework.getFireworkMeta();
                int effectCount = meta.getPower();

                if (effectCount >= 0) {
                    event.setDamage(15);
                }
                if (effectCount >= 1) {
                    event.setDamage(20);
                }
                if (effectCount >= 2) {
                    event.setDamage(40);
                }
                //firework.
                //在Minecraft中，烟花（Firework Rocket）并没有明确的“阶数”概念。然而，可以通过检查烟花火箭的FireworkMeta来获取其详细信息，包括爆炸效果、颜色和飞行时间等。

            } else {
                event.setDamage(1);
            }
        }
        if (event.getEntity() instanceof Player) {
            if (event.getDamager().getScoreboardTags().contains("nian_beastone") || event.getDamager().getScoreboardTags().contains("nian_beasttwo")) {
                Player player = (Player) event.getEntity();
                Random random = new Random();

                if (random.nextInt(100) < 30) {
                    PotionEffect potionEffect = new PotionEffect(PotionEffectType.BLINDNESS, 10 * 20, 1);
                    ((Player) event.getEntity()).addPotionEffect(potionEffect);
                }
                if (random.nextInt(100) < 30) {
                    PotionEffect potionEffect = new PotionEffect(PotionEffectType.HUNGER, 10 * 20, 1);
                    ((Player) event.getEntity()).addPotionEffect(potionEffect);
                }
                if (random.nextInt(100) < 30) {
                    PotionEffect potionEffect = new PotionEffect(PotionEffectType.WEAKNESS, 10 * 20, 1);
                    ((Player) event.getEntity()).addPotionEffect(potionEffect);
                }
                if (random.nextInt(100) < 30) {
                    PotionEffect potionEffect = new PotionEffect(PotionEffectType.POISON, 10 * 20, 1);
                    ((Player) event.getEntity()).addPotionEffect(potionEffect);
                }
            }
            if (event.getDamager().getScoreboardTags().contains("nian_beasttwo")) {
                event.getEntity().setFireTicks(160);
                event.getEntity().setVelocity(event.getDamager().getLocation().getDirection().multiply(3));
            }
            if (event.getDamager().getScoreboardTags().contains("nian_beastthree")) {
                Player player = (Player) event.getEntity();
                player.setFireTicks(160);
                Random random = new Random();

                if (random.nextInt(100) < 30) {
                    PotionEffect potionEffect = new PotionEffect(PotionEffectType.POISON, 30 * 20, 1);
                    ((Player) event.getEntity()).addPotionEffect(potionEffect);
                }
                if (random.nextInt(100) < 60) {
                    PotionEffect potionEffect = new PotionEffect(PotionEffectType.BLINDNESS, 10 * 20, 1);
                    ((Player) event.getEntity()).addPotionEffect(potionEffect);
                }
                if (random.nextInt(100) < 60) {
                    PotionEffect potionEffect = new PotionEffect(PotionEffectType.HUNGER, 10 * 20, 1);
                    ((Player) event.getEntity()).addPotionEffect(potionEffect);
                }
                if (random.nextInt(100) < 60) {
                    PotionEffect potionEffect = new PotionEffect(PotionEffectType.WEAKNESS, 10 * 20, 1);
                    ((Player) event.getEntity()).addPotionEffect(potionEffect);
                }
                if (random.nextInt(100) < 60) {
                    PotionEffect potionEffect = new PotionEffect(PotionEffectType.SLOW, 10 * 20, 1);
                    ((Player) event.getEntity()).addPotionEffect(potionEffect);
                }
                if (random.nextInt(100) <= 10) {
                    int randomnubmer = random.nextInt(100);
                    if (randomnubmer < 60) {
                        entity.getEntityone(player.getLocation());
                    }else {
                        entity.getEntitytwo(findAirBlock(player.getEyeLocation()));
                    }
                }

            }
        }
    }

}
