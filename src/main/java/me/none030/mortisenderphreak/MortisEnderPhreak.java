package me.none030.mortisenderphreak;

import me.none030.mortisenderphreak.commands.EnderPhreakCommand;
import me.none030.mortisenderphreak.events.EnderPhreakEvents;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Objects;

import static me.none030.mortisenderphreak.methods.DatabaseStorage.initializeDatabase;
import static me.none030.mortisenderphreak.methods.StoringFiles.StoreFiles;

public final class MortisEnderPhreak extends JavaPlugin {

    public static Plugin plugin;
    public static boolean database = false;
    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        StoreFiles();

        File file = new File("plugins/MortisEnderPhreak/", "config.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        ConfigurationSection section = config.getConfigurationSection("config");
        assert section != null;

        if (Objects.requireNonNull(section.getString("data")).equalsIgnoreCase("DATABASE")) {
            database = true;
            initializeDatabase();
        }
        getServer().getPluginCommand("enderphreak").setExecutor(new EnderPhreakCommand());
        getServer().getPluginManager().registerEvents(new EnderPhreakEvents(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
