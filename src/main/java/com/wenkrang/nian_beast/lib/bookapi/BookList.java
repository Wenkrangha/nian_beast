package com.wenkrang.nian_beast.lib.bookapi;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class BookList {

    public static List<Object> thelist = null;

    public static void showBookList(Player player, List<Object> list) {
        thelist = list;
        Inventory inventory = Bukkit.createInventory(null, 54, (String) thelist.get(1));
        ItemStack Background = new ItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE);
        ItemMeta itemMeta = Background.getItemMeta();
        itemMeta.setDisplayName(" ");
        Background.setItemMeta(itemMeta);
        for (int i = 0; i < 8; i++) {
            inventory.setItem(i, Background);
        }
        for (int i = 45; i < 53; i++) {
            inventory.setItem(i, Background);
        }
    }
}
