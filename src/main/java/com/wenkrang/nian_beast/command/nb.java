package com.wenkrang.nian_beast.command;

import com.wenkrang.nian_beast.Entity.entity;
import com.wenkrang.nian_beast.Entity.raid.RaidEffectShow;
import com.wenkrang.nian_beast.Entity.raid.RaidEvent;
import com.wenkrang.nian_beast.Nian_beast;
import com.wenkrang.nian_beast.lib.SpigotConsoleColors;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.*;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;
import sun.security.ec.point.ExtendedHomogeneousPoint;
import sun.security.util.math.IntegerModuloP;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import static com.wenkrang.nian_beast.Entity.PlayerJoin.findAirBlock;

public class nb implements CommandExecutor {
    /**
     * 向传入的CommandSender对象发送帮助信息
     *
     * @param sender CommandSender对象，用于发送消息
     */
    public static void gethelp(CommandSender sender) {
        sender.sendMessage("§7[!]  §4年兽 - nian_beast §7正在运行");
        sender.sendMessage(" §4| §7help  帮助列表");
        sender.sendMessage(" §4| §7spawnLO 生成普通年兽");
        sender.sendMessage(" §4| §7spawnLT 生成飞行年兽");
        sender.sendMessage(" §4| §7testwo  测试生成第二只年兽的机制");
        sender.sendMessage(" §4| §7spawnLThree 生成年兽王");
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length == 0) {
            gethelp(commandSender);
        } else {
            if (commandSender.isOp()) {
                if (strings[0].equalsIgnoreCase("help")) {
                    gethelp(commandSender);
                }
                if (strings[0].equalsIgnoreCase("spawnLO")) {
                    if (commandSender.isOp()) {
                        if (commandSender instanceof Player) {
                            Player player = (Player) commandSender;
                            entity.getEntityone(player.getLocation());
                            commandSender.sendMessage(SpigotConsoleColors.BLUE + "[-] " + SpigotConsoleColors.RESET + "普通年兽已生成");
                        } else {
                            commandSender.sendMessage(SpigotConsoleColors.DARK_RED + "[-] " + SpigotConsoleColors.RESET + "你必须在游戏内才可以使用此命令");
                        }
                    } else {
                        commandSender.sendMessage(SpigotConsoleColors.DARK_RED + "[-] " + SpigotConsoleColors.RESET + "阿巴阿巴");
                    }

                }
                if (strings[0].equalsIgnoreCase("spawnLT")) {
                    if (commandSender.isOp()) {
                        if (commandSender instanceof Player) {
                            Player player = (Player) commandSender;
                            entity.getEntitytwo(findAirBlock(player.getEyeLocation()));
                            commandSender.sendMessage(SpigotConsoleColors.BLUE + "[-] " + SpigotConsoleColors.RESET + "飞行年兽已生成");
                        } else {
                            commandSender.sendMessage(SpigotConsoleColors.DARK_RED + "[-] " + SpigotConsoleColors.RESET + "你必须在游戏内才可以使用此命令");
                        }
                    } else {
                        commandSender.sendMessage(SpigotConsoleColors.DARK_RED + "[-] " + SpigotConsoleColors.RESET + "阿巴阿巴");
                    }

                }
                if (strings[0].equalsIgnoreCase("spawnLThree")) {
                    if (commandSender.isOp()) {
                        if (commandSender instanceof Player) {
                            Player player = (Player) commandSender;
                            entity.getEntitythree(player.getLocation());
                            commandSender.sendMessage(SpigotConsoleColors.BLUE + "[-] " + SpigotConsoleColors.RESET + "年兽王已生成");
                        } else {
                            commandSender.sendMessage(SpigotConsoleColors.DARK_RED + "[-] " + SpigotConsoleColors.RESET + "你必须在游戏内才可以使用此命令");
                        }
                    } else {
                        commandSender.sendMessage(SpigotConsoleColors.DARK_RED + "[-] " + SpigotConsoleColors.RESET + "阿巴阿巴");
                    }

                }
                if (strings[0].equalsIgnoreCase("testwo")) {
                    if (commandSender.isOp()) {
                        if (commandSender instanceof Player) {
                            Player player = (Player) commandSender;
                            if (player.getPlayer().getWorld().getName().equalsIgnoreCase("world_the_end")) {
                                // 如果玩家眼睛所处的方块高度小于320
                                if (player.getPlayer().getEyeLocation().getBlockY() < 320) {
                                    Random random = new Random();
                                    // 随机数小于30时
                                    if (random.nextInt(100) < 30) {
                                        // 在玩家眼睛所处的方块周围寻找空气方块
                                        for (int i = 0; i < 3; i++) {
                                            entity.getEntitytwo(findAirBlock(player.getPlayer().getEyeLocation()));
                                        }
                                    }
                                }
                            }
                            commandSender.sendMessage(SpigotConsoleColors.BLUE + "[-] " + SpigotConsoleColors.RESET + "飞行年兽测试");
                        } else {
                            commandSender.sendMessage(SpigotConsoleColors.DARK_RED + "[-] " + SpigotConsoleColors.RESET + "你必须在游戏内才可以使用此命令");
                        }
                    } else {
                        commandSender.sendMessage(SpigotConsoleColors.DARK_RED + "[-] " + SpigotConsoleColors.RESET + "阿巴阿巴");
                    }

                }

                if (strings[0].equalsIgnoreCase("test")) {
                    Player player = (Player) commandSender;
                    try {
                        RaidEffectShow.AddEffect(player);
                    } catch (IOException | InvalidConfigurationException e) {
                        throw new RuntimeException(e);
                    }
                }
                if (strings[0].equalsIgnoreCase("test2")) {
                    Player player = (Player) commandSender;
                    player.sendMessage(Nian_beast.Keys.toString());
                    player.sendMessage(Nian_beast.RaidEffect.toString());
                    player.sendMessage(player.getScoreboardTags().toString());
                }


            } else {

                commandSender.sendMessage(SpigotConsoleColors.DARK_RED + "[-] " + SpigotConsoleColors.RESET + "阿巴阿巴");

            }


        }
        return true;
    }

    public static Location getLocation(Location player, Location targetLocation) {
        Location playerLocation = player.clone();
        // 获取从from到to的位置向量
        Vector direction = targetLocation.toVector().subtract(playerLocation.toVector()).normalize();

        // 计算yaw (水平旋转角度)
        float yaw = (float) Math.toDegrees(Math.atan2(direction.getZ(), direction.getX())) - 90F;

        // Minecraft中的yaw范围是-180到180，所以需要将其调整到这个范围内
        if (yaw < -180.0F) {
            yaw += 360.0F;
        } else if (yaw > 180.0F) {
            yaw -= 360.0F;
        }

        // 计算pitch (垂直旋转角度)
        float pitch = (float) -Math.toDegrees(Math.atan2(direction.getY(), Math.sqrt(direction.getX() * direction.getX() + direction.getZ() * direction.getZ())));
        Location location = player;
        location.setYaw(yaw);
        location.setPitch(pitch);
        return location;
    }
}
