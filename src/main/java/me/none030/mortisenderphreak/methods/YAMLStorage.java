package me.none030.mortisenderphreak.methods;

import me.none030.mortisenderphreak.utils.User;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class YAMLStorage {

    public static void StoreYamlData(User user) {

        try {
            File file = new File("plugins/MortisEnderPhreak/", "data.yml");
            FileConfiguration data = YamlConfiguration.loadConfiguration(file);
            ConfigurationSection dataSection = data.getConfigurationSection("data");
            assert dataSection != null;

            dataSection.set(user.getPlayer().toString(), user.getPassword());
            data.save(file);
        }catch (IOException exp) {
            exp.printStackTrace();
        }
    }

    public static void ChangeYamlPassword(User user) {

        try {
            File file = new File("plugins/MortisEnderPhreak/", "data.yml");
            FileConfiguration data = YamlConfiguration.loadConfiguration(file);
            ConfigurationSection section = data.getConfigurationSection("data");
            assert section != null;

            section.set(user.getPlayer().toString(), user.getPassword());
            data.save(file);
        }catch (IOException exp) {
            exp.printStackTrace();
        }
    }

    public static String getYamlPassword(UUID player) {

        File file = new File("plugins/MortisEnderPhreak/", "data.yml");
        FileConfiguration data = YamlConfiguration.loadConfiguration(file);
        ConfigurationSection section = data.getConfigurationSection("data");
        assert section != null;

        if (section.contains(player.toString())) {
            return section.getString(player.toString());
        }

        return null;
    }

    public static void RemoveYamlData(UUID player) {

        try {
            File file = new File("plugins/MortisEnderPhreak/", "data.yml");
            FileConfiguration data = YamlConfiguration.loadConfiguration(file);
            ConfigurationSection section = data.getConfigurationSection("data");
            assert section != null;

            if (section.contains(player.toString())) {
                section.set(player.toString(), null);
                data.save(file);
            }
        } catch (IOException exp) {
            exp.printStackTrace();
        }
    }

}
