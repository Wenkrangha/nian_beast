package com.wenkrang.nian_beast.command;

import com.wenkrang.nian_beast.Entity.entity;
import com.wenkrang.nian_beast.lib.SpigotConsoleColors;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(strings.length == 0) {
            gethelp(commandSender);
        } else {
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
            if (strings[0].equalsIgnoreCase("testwo")) {
                if (commandSender.isOp()) {
                    if (commandSender instanceof Player) {
                        Player player = (Player) commandSender;
                        if (player.getPlayer().getWorld().getName().equalsIgnoreCase("end")) {
                            // 如果玩家眼睛所处的方块高度小于320
                            if (player.getPlayer().getEyeLocation().getBlockY() < 320) {
                                Random random = new Random();
                                // 随机数小于30时
                                if (random.nextInt(100) < 30) {
                                    boolean hasblock = false;
                                    // 遍历玩家眼睛所处的方块以上1到319的方块
                                    for (int i = player.getPlayer().getEyeLocation().getBlockY() + 1; i < 319; i++) {
                                        Location clone = player.getPlayer().getEyeLocation().clone();
                                        clone.setY(i);
                                        // 如果方块不是空气
                                        if (!clone.getBlock().getBlockData().getMaterial().equals(Material.AIR)) {
                                            hasblock = true;
                                        }
                                    }
                                    // 如果玩家眼睛所处的方块以上没有方块
                                    if (!hasblock) {
                                        // 在玩家眼睛所处的方块周围寻找空气方块
                                        for (int i = 0; i < 3; i++) {
                                            entity.getEntitytwo(findAirBlock(player.getPlayer().getEyeLocation()));
                                        }
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
        }


        return true;
    }
}
