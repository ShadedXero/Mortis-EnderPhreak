package me.none030.mortisenderphreak.methods;

import me.none030.mortisenderphreak.utils.PasswordState;
import me.none030.mortisenderphreak.utils.User;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;

import static me.none030.mortisenderphreak.MortisEnderPhreak.plugin;
import static me.none030.mortisenderphreak.methods.CreatingYAMLValues.*;
import static me.none030.mortisenderphreak.methods.StorageMethods.*;

public class CommandMethods {

    public static void SetPassword(Player player, String password) {

        File file = new File("plugins/MortisEnderPhreak/", "config.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        ConfigurationSection section = config.getConfigurationSection("config");
        assert section != null;

        if (getPassword(player.getUniqueId()) == null) {
            StoreData(new User(player.getUniqueId(), password));
            player.sendMessage(getMessage("set-password"));
        } else {
            boolean resetCheck = section.getBoolean("reset-check");
            if (resetCheck) {
                player.sendMessage("Please enter the old password");
                player.sendMessage("Usage: /enderphreak set <new-pass> <old-pass>");
            } else {
                ChangePassword(new User(player.getUniqueId(), password));
                player.sendMessage(getMessage("set-password"));
            }
        }
    }

    public static void SetPassword(Player player, String newPass, String oldPass) {

        File file = new File("plugins/MortisEnderPhreak/", "config.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        ConfigurationSection section = config.getConfigurationSection("config");
        assert section != null;

        if (getPassword(player.getUniqueId()) == null) {
            StoreData(new User(player.getUniqueId(), newPass));
            player.sendMessage(getMessage("set-password"));
        } else {
            boolean resetCheck = section.getBoolean("reset-check");
            if (resetCheck) {
                String pass = getPassword(player.getUniqueId());
                if (oldPass.equals(pass)) {
                    ChangePassword(new User(player.getUniqueId(), newPass));
                    player.sendMessage(getMessage("set-password"));
                } else {
                    player.sendMessage(getMessage("wrong-password"));
                }

            } else {
                ChangePassword(new User(player.getUniqueId(), newPass));
                player.sendMessage(getMessage("set-password"));
            }
        }
    }

    public static void PlayTone(Player player, String numbers) {

        File file = new File("plugins/MortisEnderPhreak/", "config.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        ConfigurationSection tones = config.getConfigurationSection("config.tones");
        assert tones != null;

        int count = 1;
        for (int i = 0; i < numbers.length(); i++) {
            count = count + 1;
            int number = Integer.parseInt(numbers.substring(i, i + 1));
            Sound sound = Sound.valueOf(tones.getString(String.valueOf(number)));
            new BukkitRunnable() {
                @Override
                public void run() {
                    player.playSound(player.getLocation(), sound, 1, 1);
                }
            }.runTaskLater(plugin, count * 20L);
        }
        player.sendMessage(getMessage("play-tone"));
    }

    public static void OpenEnderChest(Player player) {

        PasswordState state = getPasswordState();

        if (getPassword(player.getUniqueId()) == null) {
            if (state.equals(PasswordState.NOTHING)) {
                player.openInventory(player.getEnderChest());
                if (isBroadcastOpen()) {
                    player.sendMessage(getMessage("broadcast-open").replace("%x%", String.valueOf(player.getLocation().getBlockX())).replace("%y%", String.valueOf(player.getLocation().getBlockY())).replace("%z%", String.valueOf(player.getLocation().getBlockZ())));
                }
            }
            if (state.equals(PasswordState.PROMPT)) {
                player.openInventory(player.getEnderChest());
                player.sendMessage(getMessage("prompt"));
                if (isBroadcastOpen()) {
                    player.sendMessage(getMessage("broadcast-open").replace("%x%", String.valueOf(player.getLocation().getBlockX())).replace("%y%", String.valueOf(player.getLocation().getBlockY())).replace("%z%", String.valueOf(player.getLocation().getBlockZ())));
                }
            }
            if (state.equals(PasswordState.LOCK)) {
                player.sendMessage(getMessage("prompt"));
            }
        } else {
            player.openInventory(player.getEnderChest());
            if (isBroadcastOpen()) {
                player.sendMessage(getMessage("broadcast-open").replace("%x%", String.valueOf(player.getLocation().getBlockX())).replace("%y%", String.valueOf(player.getLocation().getBlockY())).replace("%z%", String.valueOf(player.getLocation().getBlockZ())));
            }
        }
    }

    public static void OpenEnderChest(Player player, String password) {

        if (!isRememberPassword()) {
            String pass = getPassword(player.getUniqueId());
            if (pass != null) {
                if (password.equals(pass)) {
                    OpenEnderChest(player);
                } else {
                    player.sendMessage(getMessage("wrong-password"));
                    PlayTone(player, pass);
                }
            } else {
                OpenEnderChest(player);
            }
        } else {
            OpenEnderChest(player);
        }
    }

    public static void OpenEnderChestAdmin(Player player, Player target) {

        player.openInventory(target.getEnderChest());

    }

    public static void ResetEnderChest(Player player) {

        player.getEnderChest().clear();
        RemoveData(player.getUniqueId());
        player.sendMessage(getMessage("reset-password"));

    }
}
