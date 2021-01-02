
package me.danielch.chpets.listeners;

import me.danielch.chpets.ChPets;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class OnInvClick implements Listener {

    private static final ChPets main = ChPets.getInstance();

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player p = (Player) event.getWhoClicked();

        ItemStack clicked = event.getCurrentItem();

        if (event.getView().getTitle().equals("§5Choose your Pet:")) {
            if (clicked.getType() == Material.PLAYER_HEAD) {
                Location lo = p.getLocation();
                lo.setY(lo.getY() + 1.5);
                if (main.armorsList.containsKey(p)) {
                    p.getWorld().spawnParticle(Particle.EXPLOSION_NORMAL, lo, 10);
                    p.getWorld().playSound(lo, Sound.BLOCK_BEACON_DEACTIVATE, 10, 10);
                    main.despawnArmor(p);
                    p.sendMessage(main.prefix + ChatColor.GOLD + "Pet disappeared!");
                } else {
                    main.spawnArmor(p);
                    p.getWorld().spawnParticle(Particle.TOTEM, lo, 10);
                    p.getWorld().playSound(lo, Sound.BLOCK_BEACON_ACTIVATE, 10, 10);
                    p.sendMessage(main.prefix + ChatColor.GOLD + "Pet appeared!");
                }
                p.closeInventory();
            }
            event.setCancelled(true);
        }

    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        if (main.armorsList.containsKey(p)) {
            main.armorsList.get(p).teleport(main.getLocationArmor(p));
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        main.despawnArmor(e.getPlayer());
    }

    @EventHandler
    public void OnInteractAtEntity(PlayerInteractAtEntityEvent e) {
        if (e.getRightClicked().getType() == EntityType.ARMOR_STAND) {
            if (e.getRightClicked().getCustomName().startsWith("§5Mini")) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPortal(PlayerPortalEvent event) {
        Player p = event.getPlayer();
        if (main.armorsList.containsKey(p)) {
            main.despawnArmor(p);
            main.getServer().getScheduler().scheduleSyncDelayedTask(main, new Runnable() {
                public void run() {
                    main.spawnArmor(p);
                }
            }, 20);

        }
    }

}
