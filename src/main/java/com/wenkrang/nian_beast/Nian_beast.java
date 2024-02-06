package com.wenkrang.nian_beast;

import com.wenkrang.nian_beast.Entity.*;
import com.wenkrang.nian_beast.Entity.raid.RaidEffectShow;
import com.wenkrang.nian_beast.command.nb;
import com.wenkrang.nian_beast.lib.SpigotConsoleColors;
import org.bukkit.Location;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;


public final class Nian_beast extends JavaPlugin {
    public static boolean isShutdown = false;
    public static ArrayList<String> Keys = new ArrayList<>();
    public static ArrayList<String> RaidEffect = new ArrayList<>();
    @Override
    public void onEnable() {
        // Plugin startup logic
        ConsoleCommandSender consoleSender = getServer().getConsoleSender();
        consoleSender.sendMessage(SpigotConsoleColors.BLUE + "[*] " + SpigotConsoleColors.RESET + "正在加载Nian_beast, 版本 -=> 24a01a");
        consoleSender.sendMessage("    _   ___                  __                    __ ");
        consoleSender.sendMessage("   / | / (_)___ _____       / /_  ___  ____ ______/ /_");
        consoleSender.sendMessage("  /  |/ / / __ `/ __ \\     / __ \\/ _ \\/ __ `/ ___/ __/");
        consoleSender.sendMessage(" / /|  / / /_/ / / / /    / /_/ /  __/ /_/ (__  ) /_  ");
        consoleSender.sendMessage("/_/ |_/_/\\__,_/_/ /_/____/_.___/\\___/\\__,_/____/\\__/  ");
        consoleSender.sendMessage("                   /_____/                            ");
        consoleSender.sendMessage(SpigotConsoleColors.BLUE + "[*] " + SpigotConsoleColors.RESET + "正在注册命令");

        //Load Command
        getServer().getPluginCommand("nb").setExecutor(new nb());
        consoleSender.sendMessage(SpigotConsoleColors.BLUE + "[*] " + SpigotConsoleColors.RESET + "正在注册监听器");
        getServer().getPluginManager().registerEvents(new Spawner(), this);
        getServer().getPluginManager().registerEvents(new EntityDamageByBlock(), this);
        getServer().getPluginManager().registerEvents(new EntityDamageByEntity(), this);
        getServer().getPluginManager().registerEvents(new EntityDeath(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
        getServer().getPluginManager().registerEvents(new SpawnerTwo(), this);
        getServer().getPluginManager().registerEvents(new safdasfs(), this); //没啥事别开
        getServer().getPluginManager().registerEvents(new EntityTarget(), this);
        getServer().getPluginManager().registerEvents(new RaidEffectShow(), this);
        getServer().getPluginManager().registerEvents(new DamageEvent(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        ConsoleCommandSender consoleSender = getServer().getConsoleSender();
        consoleSender.sendMessage(SpigotConsoleColors.BLUE + "[*] " + SpigotConsoleColors.RESET + "Nian_beast 正在关闭");
        isShutdown = true;
    }
}
