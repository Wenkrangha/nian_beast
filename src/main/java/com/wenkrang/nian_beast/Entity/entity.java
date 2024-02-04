package com.wenkrang.nian_beast.Entity;

import com.wenkrang.nian_beast.Nian_beast;
import com.wenkrang.nian_beast.lib.SpigotConsoleColors;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Phantom;
import org.bukkit.entity.PolarBear;
import org.bukkit.entity.Ravager;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class entity {
    /**
     * 我们这里新建一个方法
     * @param location 一个Location对象，表示要生成年兽的位置
     */
    public static void getEntityone(Location location) {
        //特效
        location.getWorld().spawnParticle(Particle.SONIC_BOOM,location, 1);
        new BukkitRunnable() {
            @Override
            public void run() {
                location.getWorld().spawnParticle(Particle.CAMPFIRE_COSY_SMOKE,location, 20);
                // 在指定位置生成一个年兽
                PolarBear polarBear = (PolarBear) location.getWorld().spawnEntity(location, EntityType.POLAR_BEAR);
                // 设置年兽的自定义名称
                polarBear.setCustomName(SpigotConsoleColors.DARK_YELLOW + SpigotConsoleColors.BOLD
                        + "年兽" + SpigotConsoleColors.RESET + SpigotConsoleColors.DARK_YELLOW + "-普通");
                polarBear.setCustomNameVisible(true);
                // 将年兽添加到"nian_beastone"的分数板上
                polarBear.addScoreboardTag("nian_beastone");
                // 设置年兽的最大生命值为60滴
                double newMaxHealth = 60.0;
                // 生成一个随机的UUID
                UUID uuid = UUID.randomUUID();
                // 获取年兽的"最大生命值"属性实例
                AttributeInstance maxHealthAttribute = polarBear.getAttribute(Attribute.GENERIC_MAX_HEALTH);
                // 在原有最大生命值的基础上增加30滴
                AttributeModifier modifier = new AttributeModifier("new MaxHealth", 30, AttributeModifier.Operation.ADD_NUMBER);
                maxHealthAttribute.addModifier(modifier);
                polarBear.setHealth(60);

                // 获取年兽的"攻击伤害"属性实例
                maxHealthAttribute = polarBear.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE);
                // 在原有攻击伤害的基础上增加11点
                modifier = new AttributeModifier("new DAMAGE", 11, AttributeModifier.Operation.ADD_NUMBER);
                maxHealthAttribute.addModifier(modifier);

                // 速度I
                PotionEffect speedEffect = new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1, true, false);
                speedEffect.apply(polarBear);

                // 闪烁效果
                PotionEffect GlowingEffect = new PotionEffect(PotionEffectType.GLOWING, Integer.MAX_VALUE, 1, true, false);
                GlowingEffect.apply(polarBear);
            }
        }.runTaskLater(Nian_beast.getPlugin(Nian_beast.class), 35);
    }
    public static Phantom getEntitytwo(Location location) {
        Phantom phantom = (Phantom) location.getWorld().spawnEntity(location, EntityType.PHANTOM);
        phantom.setCustomName(SpigotConsoleColors.DARK_YELLOW + SpigotConsoleColors.BOLD
                + "年兽" + SpigotConsoleColors.RESET + SpigotConsoleColors.DARK_YELLOW + "-飞行");
        phantom.setCustomNameVisible(true);
        phantom.addScoreboardTag("nian_beasttwo");
        UUID uuid = UUID.randomUUID();
        AttributeInstance maxHealthAttribute = phantom.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        //这是在北极熊原有的30滴血上增加30滴
        AttributeModifier modifier = new AttributeModifier("new MaxHealth", 30, AttributeModifier.Operation.ADD_NUMBER);
        maxHealthAttribute.addModifier(modifier);
        phantom.setHealth(50);

        maxHealthAttribute = phantom.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE);
        modifier = new AttributeModifier("new DAMAGE", 11, AttributeModifier.Operation.ADD_NUMBER);
        maxHealthAttribute.addModifier(modifier);

        // 速度II

        PotionEffect speedEffect = new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 2, true, false);
        speedEffect.apply(phantom);

        PotionEffect GlowingEffect = new PotionEffect(PotionEffectType.GLOWING, Integer.MAX_VALUE, 1, true, false);
        GlowingEffect.apply(phantom);
        return phantom;
    }
    public static Ravager getEntitythree(Location location) {
        Ravager ravager = (Ravager) location.getWorld().spawnEntity(location, EntityType.RAVAGER);
        ravager.setCustomName(SpigotConsoleColors.DARK_YELLOW + SpigotConsoleColors.BOLD
                + "年兽" + SpigotConsoleColors.RESET + SpigotConsoleColors.DARK_YELLOW + "王");
        ravager.setCustomNameVisible(true);
        ravager.addScoreboardTag("nian_beastthree");
        UUID uuid = UUID.randomUUID();
        AttributeInstance maxHealthAttribute = ravager.getAttribute(Attribute.GENERIC_MAX_HEALTH);

        AttributeModifier modifier = new AttributeModifier("new MaxHealth", 500, AttributeModifier.Operation.ADD_NUMBER);
        maxHealthAttribute.addModifier(modifier);
        ravager.setHealth(600);

        maxHealthAttribute = ravager.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE);
        modifier = new AttributeModifier("new DAMAGE", 12, AttributeModifier.Operation.ADD_NUMBER);
        maxHealthAttribute.addModifier(modifier);


        PotionEffect GlowingEffect = new PotionEffect(PotionEffectType.GLOWING, Integer.MAX_VALUE, 1, true, false);
        GlowingEffect.apply(ravager);
        return ravager;
    }
}
