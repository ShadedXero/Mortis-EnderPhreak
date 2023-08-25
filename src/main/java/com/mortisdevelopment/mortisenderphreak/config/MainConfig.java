package com.mortisdevelopment.mortisenderphreak.config;

import com.mortisdevelopment.mortisenderphreak.data.DataManager;
import com.mortisdevelopment.mortisenderphreak.data.H2Database;
import com.mortisdevelopment.mortisenderphreak.enderphreak.EnderPhreakManager;
import com.mortisdevelopment.mortisenderphreak.enderphreak.EnderPhreakSettings;
import com.mortisdevelopment.mortisenderphreak.enderphreak.EnderPhreakTone;
import com.mortisdevelopment.mortisenderphreak.utils.PasswordState;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class MainConfig extends Config {

    private final ConfigManager configManager;

    public MainConfig(ConfigManager configManager) {
        super("config.yml");
        this.configManager = configManager;
        loadConfig();
    }

    @Override
    public void loadConfig() {
        File file = saveConfig();
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        DataManager dataManager = getDatabase(config.getConfigurationSection("database"));
        if (dataManager == null) {
            return;
        }
        EnderPhreakSettings settings = getSettings(config.getConfigurationSection("settings"));
        if (settings == null) {
            return;
        }
        configManager.getManager().setEnderPhreakManager(new EnderPhreakManager(dataManager, settings));
        loadTones(config.getConfigurationSection("tones"));
        configManager.getManager().getEnderPhreakManager().loadMessages(config.getConfigurationSection("messages"));
    }

    private DataManager getDatabase(ConfigurationSection section) {
        if (section == null) {
            return null;
        }
        String fileName = section.getString("file");
        if (fileName == null) {
            return null;
        }
        File file = new File(getPlugin().getDataFolder(), fileName);
        String username = section.getString("username");
        String password = section.getString("password");
        return new DataManager(new H2Database(file, username, password));
    }

    private EnderPhreakSettings getSettings(ConfigurationSection section) {
        if (section == null) {
            return null;
        }
        PasswordState state;
        try {
            state = PasswordState.valueOf(section.getString("no-password"));
        }catch (IllegalArgumentException | NullPointerException exp) {
            exp.printStackTrace();
            return null;
        }
        boolean remember = section.getBoolean("remember-password");
        boolean reset = section.getBoolean("reset-check");
        int distance = section.getInt("distance");
        long cooldownTime = section.getLong("cooldown-time");
        int length = section.getInt("length");
        boolean broadcast = section.getBoolean("broadcast");
        boolean broadcastOpen = section.getBoolean("broadcast-open");
        int stealAmount = section.getInt("steal-amount");
        long stealTime = section.getLong("steal-time");
        return new EnderPhreakSettings(state, remember, reset, distance, cooldownTime, length, broadcast, broadcastOpen, stealAmount, stealTime);
    }

    private void loadTones(ConfigurationSection section) {
        if (section == null) {
            return;
        }
        for (String key : section.getKeys(false)) {
            Sound sound;
            try {
                sound = Sound.valueOf(section.getString(key));
            }catch (IllegalArgumentException | NullPointerException exp) {
                exp.printStackTrace();
                continue;
            }
            char number = key.charAt(0);
            EnderPhreakTone tone = new EnderPhreakTone(number, sound);
            configManager.getManager().getEnderPhreakManager().getTones().add(tone);
        }
    }
}
