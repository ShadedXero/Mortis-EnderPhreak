package me.none030.mortisenderphreak.methods;

import me.none030.mortisenderphreak.utils.PasswordState;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.Objects;

public class CreatingYAMLValues {

    public static boolean isNearEnderChest(Player player) {

        File file = new File("plugins/MortisEnderPhreak/", "config.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        ConfigurationSection section = config.getConfigurationSection("config");
        assert section != null;

        int distance = section.getInt("distance");

        Location location = player.getLocation();

        for (int x = location.getBlockX() - distance; x <= location.getBlockX() + distance; x++) {
            for (int y = location.getBlockY() - distance; y <= location.getBlockY() + distance; y++) {
                for (int z = location.getBlockZ() - distance; z <= location.getBlockZ() + distance; z++) {

                    Block block = location.getWorld().getBlockAt(x, y, z);
                    if (block.getType().equals(Material.ENDER_CHEST)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public static int getMaxLength() {

        File file = new File("plugins/MortisEnderPhreak/", "config.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        ConfigurationSection section = config.getConfigurationSection("config");
        assert section != null;

        return section.getInt("length");

    }

    public static boolean isRememberPassword() {

        File file = new File("plugins/MortisEnderPhreak/", "config.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        ConfigurationSection section = config.getConfigurationSection("config");
        assert section != null;

        return section.getBoolean("remember-password");

    }

    public static PasswordState getPasswordState() {

        File file = new File("plugins/MortisEnderPhreak/", "config.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        ConfigurationSection section = config.getConfigurationSection("config");
        assert section != null;

        return PasswordState.valueOf(section.getString("no-password"));

    }

    public static long getStealTime() {

        File file = new File("plugins/MortisEnderPhreak/", "config.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        ConfigurationSection section = config.getConfigurationSection("config");
        assert section != null;

        return section.getLong("steal-time");
    }

    public static int getStealAmount() {

        File file = new File("plugins/MortisEnderPhreak/", "config.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        ConfigurationSection section = config.getConfigurationSection("config");
        assert section != null;

        return section.getInt("steal-amount");
    }

    public static boolean isBroadcast() {

        File file = new File("plugins/MortisEnderPhreak/", "config.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        ConfigurationSection section = config.getConfigurationSection("config");
        assert section != null;

        return section.getBoolean("broadcast");
    }

    public static boolean isBroadcastOpen() {

        File file = new File("plugins/MortisEnderPhreak/", "config.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        ConfigurationSection section = config.getConfigurationSection("config");
        assert section != null;

        return section.getBoolean("broadcast-open");
    }

    public static long getCooldownTime() {

        File file = new File("plugins/MortisEnderPhreak/", "config.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        ConfigurationSection section = config.getConfigurationSection("config");
        assert section != null;

        return section.getLong("cooldown-time");
    }

    public static String getMessage(String message) {

        File file = new File("plugins/MortisEnderPhreak/", "config.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        ConfigurationSection section = config.getConfigurationSection("config.messages");
        assert section != null;

        return Objects.requireNonNull(section.getString(message)).replace("&", "§");

    }


}
