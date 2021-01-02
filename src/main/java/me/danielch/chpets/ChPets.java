package me.danielch.chpets;

import java.util.HashMap;
import java.util.Map;
import me.danielch.chpets.commands.Pets;
import me.danielch.chpets.listeners.OnInvClick;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class ChPets extends JavaPlugin {

    public String prefix = ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "[" + ChatColor.AQUA + "ChPets" + ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "] ";
    public HashMap<Player, ArmorStand> armorsList;

    @Override
    public void onEnable() {
        this.armorsList = new HashMap<>();
        this.getServer().getPluginManager().registerEvents(new OnInvClick(), this);
        this.getCommand("pets").setExecutor(new Pets());
    }

    @Override
    public void onDisable() {
        for (Map.Entry<Player, ArmorStand> entry : armorsList.entrySet()) {
            armorsList.get(entry.getKey()).remove();
        }
        armorsList.clear();
    }

    public ItemStack setSkin(ItemStack item, Player p) {
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        meta.setOwningPlayer(p);
        item.setItemMeta(meta);
        return item;
    }

    public void despawnArmor(Player p) {
        if (armorsList.containsKey(p)) {
            armorsList.get(p).remove();
            armorsList.remove(p);
        }
    }

    public void spawnArmor(Player p) {
        armorsList.put(p, getNewArmorStand(getLocationArmor(p), false, true, p));
    }

    public ArmorStand getNewArmorStand(Location location, boolean visible, boolean mini, Player p) {
        ArmorStand as = location.getWorld().spawn(location, ArmorStand.class);

        as.setBasePlate(false);
        as.setArms(true);
        as.setVisible(visible);
        as.setInvulnerable(true);
        as.setCanPickupItems(false);
        as.setGravity(false);
        as.setSmall(mini);
        as.getEquipment().setHelmet(setSkin(new ItemStack(Material.PLAYER_HEAD), p));
        as.setCustomName("§5Mini " + p.getName());
        as.setCustomNameVisible(true);

        return as;
    }

    public Location getLocationArmor(Player p) {
        Location lo = p.getLocation();
        lo.setYaw(p.getLocation().getYaw());

        if (p.getFacing() == BlockFace.NORTH) {
            lo.setX(lo.getX() + 1.5);
        }
        if (p.getFacing() == BlockFace.SOUTH) {
            lo.setX(lo.getX() - 1.5);
        }
        if (p.getFacing() == BlockFace.WEST) {
            lo.setZ(lo.getZ() - 1.5);
        }
        if (p.getFacing() == BlockFace.EAST) {
            lo.setZ(lo.getZ() + 1.5);
        }
        return lo;
    }

    public static ChPets getInstance(){
        return getPlugin(ChPets.class);
    }

}
