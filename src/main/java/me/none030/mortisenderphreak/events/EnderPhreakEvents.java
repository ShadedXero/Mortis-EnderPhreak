package me.none030.mortisenderphreak.events;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;

import static me.none030.mortisenderphreak.methods.CreatingYAMLValues.getMessage;
import static me.none030.mortisenderphreak.methods.CreatingYAMLValues.getStealAmount;
import static me.none030.mortisenderphreak.methods.HackingEnderChest.StoleItems;

public class EnderPhreakEvents implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {

        Player player = (Player) e.getWhoClicked();

        if (e.getClickedInventory() == null) {
            return;
        }

        if (e.getClickedInventory().getType().equals(InventoryType.ENDER_CHEST)) {
            if (StoleItems.containsKey(player.getUniqueId())) {
                int stoleItems = StoleItems.get(player.getUniqueId());
                if (stoleItems >= getStealAmount()) {
                    e.setCancelled(true);
                    player.sendMessage(getMessage("steal-amount"));
                } else {
                    StoleItems.put(player.getUniqueId(), stoleItems + 1);
                }
            }
        }
    }

    @EventHandler
    public void onDrag(InventoryDragEvent e) {

        Player player = (Player) e.getWhoClicked();

        if (e.getInventory().getType().equals(InventoryType.ENDER_CHEST)) {
            if (StoleItems.containsKey(player.getUniqueId())) {
                e.setCancelled(true);
            }
        }

    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {

        Player player = e.getPlayer();
        Block block = e.getClickedBlock();

        if (block != null) {
            if (e.getAction().isRightClick()) {
                if (block.getType().equals(Material.ENDER_CHEST)) {
                    e.setCancelled(true);
                    player.performCommand("enderphreak open");
                }
            }
        }
    }
}
