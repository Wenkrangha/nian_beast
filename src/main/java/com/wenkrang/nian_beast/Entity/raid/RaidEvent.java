package com.wenkrang.nian_beast.Entity.raid;

import com.wenkrang.nian_beast.Nian_beast;
import com.wenkrang.nian_beast.lib.SpigotConsoleColors;
import org.bukkit.*;
import org.bukkit.advancement.Advancement;
import org.bukkit.advancement.AdvancementProgress;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.*;
import org.bukkit.generator.structure.Structure;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.StructureSearchResult;

import java.util.*;

import static org.bukkit.Bukkit.getServer;

public class RaidEvent {
    public static Structure getnearvillage (Player player) {
        ArrayList<Location> locations = new ArrayList<>();
        ArrayList<StructureSearchResult> results = new ArrayList<>();
        Structure structure = null;
        results.add(player.getWorld().locateNearestStructure(player.getLocation(), Structure.VILLAGE_DESERT, 200, false));
        results.add(player.getWorld().locateNearestStructure(player.getLocation(), Structure.VILLAGE_SNOWY, 200, false));
        results.add(player.getWorld().locateNearestStructure(player.getLocation(), Structure.VILLAGE_PLAINS, 200, false));
        results.add(player.getWorld().locateNearestStructure(player.getLocation(), Structure.VILLAGE_SAVANNA, 200, false));
        results.add(player.getWorld().locateNearestStructure(player.getLocation(), Structure.VILLAGE_TAIGA, 200, false));
        ArrayList<Structure> structures = new ArrayList<>();
        for (StructureSearchResult result : results) {
            if (result != null) {
                locations.add(result.getLocation());
                structures.add(result.getStructure());
            }
        }
        if (!locations.isEmpty()) {
            //从Location获取与玩家的距离
            for (int i = 0; i < locations.size(); i++) {
                Location location = locations.get(i).clone();
                location.setY(player.getLocation().getBlockY());
                locations.set(i, location);
            }
            Location location = null;

            //从distances选出最小值
            double min = locations.get(0).distance(player.getLocation());
            for (int i = 1; i < locations.size(); i++) {
                if (locations.get(i).distance(player.getLocation()) < min) {
                    min = locations.get(i).distance(player.getLocation());
                    location = locations.get(i);
                    structure = results.get(i).getStructure();
                }
            }
        }
        return structure;
    }
    public static void RaidThree(BossBar bossBar, Location location, Player player, Location Realocation) {
        bossBar.setStyle(BarStyle.SOLID);
        bossBar.setProgress(0);
        new BukkitRunnable() {

            @Override
            public void run() {
                if (Nian_beast.isShutdown) {
                    cancel();
                }
                if (bossBar.getProgress() + 0.01 > 1) {
                    new BukkitRunnable(){

                        @Override
                        public void run() {
                            // 袭击号角
                            Collection<Entity> nearbyEntities = Objects.requireNonNull(location.getWorld()).getNearbyEntities(location, 150, 150, 150);
                            for (Entity entity : nearbyEntities) {
                                if (entity instanceof Player) {
                                    Player player1 = (Player) entity;
                                    player1.playSound(player1.getLocation(), Sound.EVENT_RAID_HORN, 100, 1);
                                }
                            }
                            location.getWorld().playSound(location, Sound.EVENT_RAID_HORN, 100, 1);
                        }
                    }.runTaskLater(Nian_beast.getPlugin(Nian_beast.class), 0);


                    // 袭击开始
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            ArrayList<Entity> entities = new ArrayList<>();
                            for (int i = 0; i < 20; i++) {
                                Location location1 = location.clone();
                                location1.add(0, 20, 0);
                                Random random = new Random();
                                if (random.nextInt(100) > 50) {
                                    location1.add(random.nextInt(30), 0, 0);
                                } else {
                                    location1.add(-random.nextInt(30), 0, 0);
                                }
                                if (random.nextInt(100) > 50) {
                                    location1.add(0, 0, random.nextInt(30));
                                } else {
                                    location1.add(0, 0, -random.nextInt(30));
                                }
                                player.sendMessage(location1.toString());
                                location1.getWorld().spawnParticle(Particle.SONIC_BOOM, location1, 1);

                                new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        location1.getWorld().spawnParticle(Particle.CAMPFIRE_COSY_SMOKE, location1, 50);

                                        // 在指定位置生成一个年兽
                                        PolarBear polarBear = (PolarBear) location1.getWorld().spawnEntity(location1, EntityType.POLAR_BEAR);
                                        // 设置年兽的自定义名称
                                        polarBear.setCustomName(SpigotConsoleColors.DARK_YELLOW + SpigotConsoleColors.BOLD
                                                + "年兽" + SpigotConsoleColors.RESET + SpigotConsoleColors.DARK_YELLOW + "-普通");
                                        polarBear.setCustomNameVisible(true);
                                        // 将年兽添加到"nian_beastone"的分数板上
                                        polarBear.addScoreboardTag("nian_beastone");
                                        polarBear.addScoreboardTag("NORaid");
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
                                        entities.add(polarBear);

                                        PotionEffect SlowEffect = new PotionEffect(PotionEffectType.SLOW_FALLING, Integer.MAX_VALUE, 1, true, false);
                                        SlowEffect.apply(polarBear);


                                    }
                                }.runTaskLater(Nian_beast.getPlugin(Nian_beast.class), 30);


                            }
                            for (int i = 0; i < 15; i++) {
                                Location location1 = location.clone();
                                location1.add(0, 20, 0);
                                Random random = new Random();
                                if (random.nextInt(100) > 50) {
                                    location1.add(random.nextInt(30), 0, 0);
                                } else {
                                    location1.add(-random.nextInt(30), 0, 0);
                                }
                                if (random.nextInt(100) > 50) {
                                    location1.add(0, 0, random.nextInt(30));
                                } else {
                                    location1.add(0, 0, -random.nextInt(30));
                                }
                                location1.getWorld().spawnParticle(Particle.SONIC_BOOM, location1, 1);
                                player.sendMessage(location1.toString());
                                new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        location1.getWorld().spawnParticle(Particle.CAMPFIRE_COSY_SMOKE, location1, 50);
                                        Phantom phantom = (Phantom) location1.getWorld().spawnEntity(location1, EntityType.PHANTOM);
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
                                        entities.add(phantom);

                                    }
                                }.runTaskLater(Nian_beast.getPlugin(Nian_beast.class), 30);


                            }
                            if (true) {
                                Location location1 = location.clone();
                                location1.add(0, 20, 0);
                                Random random = new Random();
                                if (random.nextInt(100) > 50) {
                                    location1.add(random.nextInt(30), 0, 0);
                                } else {
                                    location1.add(-random.nextInt(30), 0, 0);
                                }
                                if (random.nextInt(100) > 50) {
                                    location1.add(0, 0, random.nextInt(30));
                                } else {
                                    location1.add(0, 0, -random.nextInt(30));
                                }
                                location1.getWorld().spawnParticle(Particle.SONIC_BOOM, location1, 1);
                                player.sendMessage(location1.toString());
                                new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        Ravager ravager = (Ravager) location1.getWorld().spawnEntity(location1, EntityType.RAVAGER);
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
                                        entities.add(ravager);
                                    }
                                }.runTaskLater(Nian_beast.getPlugin(Nian_beast.class), 30);
                            }
                            bossBar.setStyle(BarStyle.SEGMENTED_12);
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    if (Nian_beast.isShutdown) {
                                        cancel();
                                    }

                                    int LIFE = 0;
                                    for (Entity entity : entities) {
                                        if (!entity.isDead()) {
                                            LIFE += 1;
                                        }
                                    }
                                    int finalLIFE = LIFE;
                                    new BukkitRunnable() {

                                        @Override
                                        public void run() {
                                            player.sendMessage(entities.toString());
                                            bossBar.setProgress((double) finalLIFE / 36);
                                        }
                                    }.runTaskLater(Nian_beast.getPlugin(Nian_beast.class), 0);

                                    if (bossBar.getProgress() == 0) {
                                        new BukkitRunnable(){

                                            @Override
                                            public void run() {
                                                bossBar.setProgress(1);
                                                bossBar.setTitle(SpigotConsoleColors.DARK_YELLOW + SpigotConsoleColors.BOLD +
                                                        "年兽" + SpigotConsoleColors.RESET + SpigotConsoleColors.DARK_YELLOW + "袭击 - 胜利");
                                                Collection<Entity> nearbyEntities = Objects.requireNonNull(location.getWorld()).getNearbyEntities(location, 150, 150, 150);
                                                for (Entity entity : nearbyEntities) {
                                                    if (entity instanceof Player) {
                                                        Player player1 = (Player) entity;
                                                        PotionEffect potionEffect = new PotionEffect(PotionEffectType.HERO_OF_THE_VILLAGE, 114514*20, 1, true, false);
                                                        potionEffect.apply(player1);
                                                        player1.playSound(player1.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 100, 1);
                                                    }
                                                }

                                            }
                                        }.runTaskLater(Nian_beast.getPlugin(Nian_beast.class), 0);
                                        new BukkitRunnable() {

                                            @Override
                                            public void run() {
                                                bossBar.setVisible(false);
                                                bossBar.removeAll();
                                                Nian_beast.Keys.remove(String.valueOf(location.getBlockX()) + String.valueOf(location.getBlockY()) + String.valueOf(location.getBlockZ()));
                                            }
                                        }.runTaskLater(Nian_beast.getPlugin(Nian_beast.class), 300);

                                        cancel();
                                    }
                                }
                            }.runTaskTimerAsynchronously(Nian_beast.getPlugin(Nian_beast.class), 40, 20);
                        }
                    }.runTaskLater(Nian_beast.getPlugin(Nian_beast.class), 60);
                    cancel();
                }else {
                    new BukkitRunnable(){

                        @Override
                        public void run() {
                            bossBar.setProgress(bossBar.getProgress() + 0.01);
                        }
                    }.runTaskLater(Nian_beast.getPlugin(Nian_beast.class), 0);

                }
            }
        }.runTaskTimerAsynchronously(Nian_beast.getPlugin(Nian_beast.class), 0, 5);

    }
    public static void RaidTwo(BossBar bossBar, Location location, Player player, Location Realocation) {
        bossBar.setStyle(BarStyle.SOLID);
        bossBar.setProgress(0);
        new BukkitRunnable() {

            @Override
            public void run() {
                if (Nian_beast.isShutdown) {
                    cancel();
                }

                if (bossBar.getProgress() + 0.01 > 1) {
                    // 袭击号角
                    new BukkitRunnable() {

                        @Override
                        public void run() {
                            Collection<Entity> nearbyEntities = Objects.requireNonNull(location.getWorld()).getNearbyEntities(location, 150, 150, 150);
                            for (Entity entity : nearbyEntities) {
                                if (entity instanceof Player) {
                                    Player player1 = (Player) entity;
                                    player1.playSound(player1.getLocation(), Sound.EVENT_RAID_HORN, 100, 1);
                                }
                            }
                            location.getWorld().playSound(location, Sound.EVENT_RAID_HORN, 100, 1);
                        }
                    }.runTaskLater(Nian_beast.getPlugin(Nian_beast.class), 0);


                    // 袭击开始
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            ArrayList<Entity> entities = new ArrayList<>();
                            for (int i = 0; i < 15; i++) {
                                Location location1 = location.clone();
                                location1.add(0, 20, 0);
                                Random random = new Random();
                                if (random.nextInt(100) > 50) {
                                    location1.add(random.nextInt(30), 0, 0);
                                } else {
                                    location1.add(-random.nextInt(30), 0, 0);
                                }
                                if (random.nextInt(100) > 50) {
                                    location1.add(0, 0, random.nextInt(30));
                                } else {
                                    location1.add(0, 0, -random.nextInt(30));
                                }
                                location1.getWorld().spawnParticle(Particle.SONIC_BOOM, location1, 1);

                                new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        location1.getWorld().spawnParticle(Particle.CAMPFIRE_COSY_SMOKE, location1, 50);

                                        // 在指定位置生成一个年兽
                                        PolarBear polarBear = (PolarBear) location1.getWorld().spawnEntity(location1, EntityType.POLAR_BEAR);
                                        // 设置年兽的自定义名称
                                        polarBear.setCustomName(SpigotConsoleColors.DARK_YELLOW + SpigotConsoleColors.BOLD
                                                + "年兽" + SpigotConsoleColors.RESET + SpigotConsoleColors.DARK_YELLOW + "-普通");
                                        polarBear.setCustomNameVisible(true);
                                        // 将年兽添加到"nian_beastone"的分数板上
                                        polarBear.addScoreboardTag("nian_beastone");
                                        polarBear.addScoreboardTag("NORaid");
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
                                        entities.add(polarBear);

                                        PotionEffect SlowEffect = new PotionEffect(PotionEffectType.SLOW_FALLING, Integer.MAX_VALUE, 1, true, false);
                                        SlowEffect.apply(polarBear);


                                    }
                                }.runTaskLater(Nian_beast.getPlugin(Nian_beast.class), 30);


                            }
                            for (int i = 0; i < 10; i++) {
                                Location location1 = location.clone();
                                location1.add(0, 10, 0);
                                Random random = new Random();
                                if (random.nextInt(100) > 50) {
                                    location1.add(random.nextInt(30), 0, 0);
                                } else {
                                    location1.add(-random.nextInt(30), 0, 0);
                                }
                                if (random.nextInt(100) > 50) {
                                    location1.add(0, 0, random.nextInt(30));
                                } else {
                                    location1.add(0, 0, -random.nextInt(30));
                                }
                                location1.getWorld().spawnParticle(Particle.SONIC_BOOM, location1, 1);

                                new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        location1.getWorld().spawnParticle(Particle.CAMPFIRE_COSY_SMOKE, location1, 50);
                                        Phantom phantom = (Phantom) location1.getWorld().spawnEntity(location1, EntityType.PHANTOM);
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
                                        entities.add(phantom);

                                    }
                                }.runTaskLater(Nian_beast.getPlugin(Nian_beast.class), 30);


                            }
                            bossBar.setStyle(BarStyle.SEGMENTED_12);
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    if (Nian_beast.isShutdown) {
                                        cancel();
                                    }

                                    int LIFE = 0;
                                    for (Entity entity : entities) {
                                        if (!entity.isDead()) {
                                            LIFE += 1;
                                        }
                                    }
                                    int finalLIFE = LIFE;
                                    new BukkitRunnable() {

                                        @Override
                                        public void run() {
                                            bossBar.setProgress((double) finalLIFE / 25);

                                        }
                                    }.runTaskLater(Nian_beast.getPlugin(Nian_beast.class), 0);

                                    if (bossBar.getProgress() == 0) {
                                        new BukkitRunnable() {

                                            @Override
                                            public void run() {
                                                RaidThree(bossBar, location, player, location);
                                            }
                                        }.runTaskLater(Nian_beast.getPlugin(Nian_beast.class), 0);

                                        cancel();
                                    }
                                }
                            }.runTaskTimerAsynchronously(Nian_beast.getPlugin(Nian_beast.class), 40, 20);
                        }
                    }.runTaskLater(Nian_beast.getPlugin(Nian_beast.class), 60);
                    cancel();
                }else {
                    new BukkitRunnable(){

                        @Override
                        public void run() {
                            if (!(bossBar.getProgress() + 0.01 > 1)) {
                                bossBar.setProgress(bossBar.getProgress() + 0.01);
                            }

                        }
                    }.runTaskLater(Nian_beast.getPlugin(Nian_beast.class), 0);
                }
            }
        }.runTaskTimerAsynchronously(Nian_beast.getPlugin(Nian_beast.class), 0, 5);


    }

    /**
     * 袭击开始
     * @param bossBar 袭击进度条
     * @param location 袭击位置
     * @param player 玩家
     * @param Realocation 重新定位
     */
    public static void RaidMain(BossBar bossBar, Location location, Player player, Location Realocation) {
        // 袭击号角
        Collection<Entity> nearbyEntities = location.getWorld().getNearbyEntities(location, 150, 150, 150);
        for (Entity entity : nearbyEntities) {
            if (entity instanceof Player) {
                Player player1 = (Player) entity;
                player1.playSound(player1.getLocation(), Sound.EVENT_RAID_HORN, 100, 1);
            }
        }
        location.getWorld().playSound(location, Sound.EVENT_RAID_HORN, 100, 1);

        // 袭击开始
        new BukkitRunnable() {
            @Override
            public void run() {
                ArrayList<Entity> entities = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    Location location1 = location.clone();
                    location1.add(0, 20, 0);
                    Random random = new Random();
                    if (random.nextInt(100) > 50) {
                        location1.add(random.nextInt(30), 0, 0);
                    } else {
                        location1.add(-random.nextInt(30), 0, 0);
                    }
                    if (random.nextInt(100) > 50) {
                        location1.add(0, 0, random.nextInt(30));
                    } else {
                        location1.add(0, 0, -random.nextInt(30));
                    }
                    location1.getWorld().spawnParticle(Particle.SONIC_BOOM, location1, 1);

                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            location1.getWorld().spawnParticle(Particle.CAMPFIRE_COSY_SMOKE, location1, 50);

                            // 在指定位置生成一个年兽
                            PolarBear polarBear = (PolarBear) location1.getWorld().spawnEntity(location1, EntityType.POLAR_BEAR);
                            // 设置年兽的自定义名称
                            polarBear.setCustomName(SpigotConsoleColors.DARK_YELLOW + SpigotConsoleColors.BOLD
                                    + "年兽" + SpigotConsoleColors.RESET + SpigotConsoleColors.DARK_YELLOW + "-普通");
                            polarBear.setCustomNameVisible(true);
                            // 将年兽添加到"nian_beastone"的分数板上
                            polarBear.addScoreboardTag("nian_beastone");
                            polarBear.addScoreboardTag("NORaid");
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
                            entities.add(polarBear);

                            PotionEffect SlowEffect = new PotionEffect(PotionEffectType.SLOW_FALLING, Integer.MAX_VALUE, 1, true, false);
                            SlowEffect.apply(polarBear);


                        }
                    }.runTaskLater(Nian_beast.getPlugin(Nian_beast.class), 30);


                }
                bossBar.setStyle(BarStyle.SEGMENTED_12);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        if (Nian_beast.isShutdown) {
                            cancel();
                        }

                        int LIFE = 0;
                        for (Entity entity : entities) {
                            if (!entity.isDead()) {
                                LIFE += 1;
                            }
                        }
                        int finalLIFE = LIFE;
                        new BukkitRunnable() {

                            @Override
                            public void run() {
                                bossBar.setProgress((double) finalLIFE / 10);
                            }
                        }.runTaskLater(Nian_beast.getPlugin(Nian_beast.class), 0);
                        double tmp = (double) finalLIFE / 10;
                        if (tmp == 0) {
                            new BukkitRunnable(){

                                @Override
                                public void run() {
                                    new BukkitRunnable() {

                                        @Override
                                        public void run() {
                                            RaidTwo(bossBar, location, player, location);
                                        }
                                    }.runTaskLater(Nian_beast.getPlugin(Nian_beast.class), 0);
                                }
                            }.runTaskLaterAsynchronously(Nian_beast.getPlugin(Nian_beast.class), 0);
                            cancel();
                        }
                    }
                }.runTaskTimerAsynchronously(Nian_beast.getPlugin(Nian_beast.class), 40, 20);
            }
        }.runTaskLater(Nian_beast.getPlugin(Nian_beast.class), 60);
    }






    /**
     * 加载年兽BossBar并开始加载进度
     *
     * @param bossBar 年兽BossBar对象
     * @param location 事件位置
     * @param player 玩家对象
     */
    public static void LoadRaid(BossBar bossBar, Location location, Player player) {

        if (!Nian_beast.Keys.contains(String.valueOf(location.getBlockX()) + String.valueOf(location.getBlockY()) + String.valueOf(location.getBlockZ()))) {
            Nian_beast.Keys.add(String.valueOf(location.getBlockX()) + String.valueOf(location.getBlockY()) + String.valueOf(location.getBlockZ()));
            new BukkitRunnable() {
                @Override
                public void run() {


                    //跑条子
                    if (bossBar.getProgress() + 0.01 >= 1) {
                        new BukkitRunnable() {

                            @Override
                            public void run() {
                                bossBar.setTitle(SpigotConsoleColors.DARK_YELLOW + SpigotConsoleColors.BOLD +
                                        "年兽" + SpigotConsoleColors.RESET + SpigotConsoleColors.DARK_YELLOW + "袭击");

                                RaidMain(bossBar, location, player, location);
                            }
                        }.runTaskLater(Nian_beast.getPlugin(Nian_beast.class), 0);


                        cancel();

                    } else {
                        new BukkitRunnable() {

                            @Override
                            public void run() {
                                bossBar.setProgress(bossBar.getProgress() + 0.01);
                                //寻找袭击位置

                                Random random = new Random();
                                if (random.nextInt(100) < 5) {
                                    Objects.requireNonNull(location.getWorld()).playSound(location, Sound.BLOCK_BELL_USE, 7F, 1);
                                }
                            }
                        }.runTaskLater(Nian_beast.getPlugin(Nian_beast.class), 0);
                    }

                }
            }.runTaskTimerAsynchronously(Nian_beast.getPlugin(Nian_beast.class), 0, 5);
            new BukkitRunnable() {

                @Override
                public void run() {
                    if (Nian_beast.isShutdown) {
                        cancel();
                    }


                    Collection<Entity> nearbyEntities = Objects.requireNonNull(location.getWorld()).getNearbyEntities(location, 150, 150, 150);
                    for (Entity entity : nearbyEntities) {
                        if (entity instanceof Player) {
                            Player player1 = (Player) entity;
                            bossBar.addPlayer(player1);
                        }
                    }


                }
            }.runTaskTimer(Nian_beast.getPlugin(Nian_beast.class), 0, 20);
        }else{
            bossBar.removeAll();
            bossBar.setVisible(false);
        }


    }
}
