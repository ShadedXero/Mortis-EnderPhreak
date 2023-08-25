package com.mortisdevelopment.mortisenderphreak.enderphreak;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;

public class EnderPhreakListener implements Listener {

    private final EnderPhreakManager enderPhreakManager;

    public EnderPhreakListener(EnderPhreakManager enderPhreakManager) {
        this.enderPhreakManager = enderPhreakManager;
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        Player player = (Player) e.getPlayer();
        enderPhreakManager.getHacking().remove(player.getUniqueId());
        enderPhreakManager.getStolenByUUID().remove(player.getUniqueId());
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        if (e.getClickedInventory() == null || !e.getClickedInventory().getType().equals(InventoryType.ENDER_CHEST) || !enderPhreakManager.getHacking().contains(player.getUniqueId())) {
            return;
        }
        Integer number = enderPhreakManager.getStolenByUUID().get(player.getUniqueId());
        if (number != null) {
            if (number >= enderPhreakManager.getSettings().getStealAmount()) {
                e.setCancelled(true);
                player.sendMessage(enderPhreakManager.getMessage("STEAL_AMOUNT"));
                return;
            }
            enderPhreakManager.getStolenByUUID().put(player.getUniqueId(), number + 1);
        }else {
            enderPhreakManager.getStolenByUUID().put(player.getUniqueId(), 1);
        }
    }

    @EventHandler
    public void onDrag(InventoryDragEvent e) {
        Player player = (Player) e.getWhoClicked();
        if (!e.getInventory().getType().equals(InventoryType.ENDER_CHEST) || !enderPhreakManager.getHacking().contains(player.getUniqueId())) {
            return;
        }
        e.setCancelled(true);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        Block block = e.getClickedBlock();
        if (block == null || !e.getAction().isRightClick() || !block.getType().equals(Material.ENDER_CHEST)) {
            return;
        }
        e.setCancelled(true);
        player.performCommand("enderphreak open");
    }
}
