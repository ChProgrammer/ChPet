package me.danielch.chpets.commands;

import me.danielch.chpets.ChPets;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Pets implements CommandExecutor {

    private static final ChPets main = ChPets.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player p = (Player) sender;

            Inventory inv = Bukkit.createInventory(null, 27, "§5Choose your Pet:");

            for (int i = 0; i <= 26; i++) {
                if (i == 13) {
                    inv.setItem(i, main.setSkin(new ItemStack(Material.PLAYER_HEAD), p));
                } else {
                    inv.setItem(i, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
                }
            }

            p.openInventory(inv);
            return true;
        }
        return false;
    }
}
