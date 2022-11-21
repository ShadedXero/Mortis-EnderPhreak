package me.none030.mortisenderphreak.methods;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static me.none030.mortisenderphreak.MortisEnderPhreak.plugin;
import static me.none030.mortisenderphreak.methods.CreatingYAMLValues.*;
import static me.none030.mortisenderphreak.methods.CommandMethods.PlayTone;
import static me.none030.mortisenderphreak.methods.StorageMethods.getPassword;

public class HackingEnderChest {

    public static List<Player> InCooldown = new ArrayList<>();
    public static HashMap<UUID, Integer> StoleItems = new HashMap<>();

    public static void HackEnderChest(Player player, Player target) {

        String password = getPassword(target.getUniqueId());

        if (password == null) {
            player.openInventory(target.getEnderChest());
            InCooldown.add(player);
            StoleItems.put(player.getUniqueId(), 0);
            new BukkitRunnable() {
                @Override
                public void run() {
                    InCooldown.remove(player);
                }
            }.runTaskLater(plugin, getCooldownTime() * 20);
            new BukkitRunnable() {
                @Override
                public void run() {
                    player.closeInventory();
                    StoleItems.remove(player.getUniqueId());
                }
            }.runTaskLater(plugin, getStealTime() * 20);
            if (isBroadcastOpen()) {
                target.sendMessage(getMessage("broadcast-open").replace("%x%", String.valueOf(player.getLocation().getBlockX())).replace("%y%", String.valueOf(player.getLocation().getBlockY())).replace("%z%", String.valueOf(player.getLocation().getBlockZ())));
            }
            if (isBroadcast()) {
                target.sendMessage(getMessage("broadcast").replace("%x%", String.valueOf(player.getLocation().getBlockX())).replace("%y%", String.valueOf(player.getLocation().getBlockY())).replace("%z%", String.valueOf(player.getLocation().getBlockZ())));
            }
        } else {
            if (isBroadcast()) {
                target.sendMessage(getMessage("broadcast").replace("%x%", String.valueOf(player.getLocation().getBlockX())).replace("%y%", String.valueOf(player.getLocation().getBlockY())).replace("%z%", String.valueOf(player.getLocation().getBlockZ())));
            }
            player.sendMessage("The player's enderchest has a password.");
            player.sendMessage("Use /enderphreak hack <player_name> <password>");
            PlayTone(player, password);
        }
    }

    public static void HackEnderChest(Player player, Player target, String password) {

        String pass = getPassword(target.getUniqueId());

        if (pass != null) {
            if (password.equals(pass)) {
                player.openInventory(target.getEnderChest());
                InCooldown.add(player);
                StoleItems.put(player.getUniqueId(), 0);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        InCooldown.remove(player);
                    }
                }.runTaskLater(plugin, getCooldownTime() * 20);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        player.closeInventory();
                        StoleItems.remove(player.getUniqueId());
                    }
                }.runTaskLater(plugin, getStealTime() * 20);
                if (isBroadcastOpen()) {
                    target.sendMessage(getMessage("broadcast-open").replace("%x%", String.valueOf(player.getLocation().getBlockX())).replace("%y%", String.valueOf(player.getLocation().getBlockY())).replace("%z%", String.valueOf(player.getLocation().getBlockZ())));
                }
                if (isBroadcast()) {
                    target.sendMessage(getMessage("broadcast").replace("%x%", String.valueOf(player.getLocation().getBlockX())).replace("%y%", String.valueOf(player.getLocation().getBlockY())).replace("%z%", String.valueOf(player.getLocation().getBlockZ())));
                }
            } else {
                InCooldown.add(player);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        InCooldown.remove(player);
                    }
                }.runTaskLater(plugin, getCooldownTime() * 20);
                player.sendMessage("Wrong Password");
                PlayTone(player, pass);
                if (isBroadcastOpen()) {
                    target.sendMessage(getMessage("broadcast-open").replace("%x%", String.valueOf(player.getLocation().getBlockX())).replace("%y%", String.valueOf(player.getLocation().getBlockY())).replace("%z%", String.valueOf(player.getLocation().getBlockZ())));
                }
            }
        } else {
            player.openInventory(target.getEnderChest());
            InCooldown.add(player);
            StoleItems.put(player.getUniqueId(), 0);
            new BukkitRunnable() {
                @Override
                public void run() {
                    InCooldown.remove(player);
                }
            }.runTaskLater(plugin, getCooldownTime() * 20);
            new BukkitRunnable() {
                @Override
                public void run() {
                    player.closeInventory();
                    StoleItems.remove(player.getUniqueId());
                }
            }.runTaskLater(plugin, getStealTime() * 20);
            if (isBroadcastOpen()) {
                target.sendMessage(getMessage("broadcast-open").replace("%x%", String.valueOf(player.getLocation().getBlockX())).replace("%y%", String.valueOf(player.getLocation().getBlockY())).replace("%z%", String.valueOf(player.getLocation().getBlockZ())));
            }
            if (isBroadcast()) {
                target.sendMessage(getMessage("broadcast").replace("%x%", String.valueOf(player.getLocation().getBlockX())).replace("%y%", String.valueOf(player.getLocation().getBlockY())).replace("%z%", String.valueOf(player.getLocation().getBlockZ())));
            }
        }
    }

}
