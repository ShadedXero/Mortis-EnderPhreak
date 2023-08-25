package com.mortisdevelopment.mortisenderphreak.manager;

import com.mortisdevelopment.mortisenderphreak.MortisEnderPhreak;
import com.mortisdevelopment.mortisenderphreak.config.ConfigManager;
import com.mortisdevelopment.mortisenderphreak.enderphreak.EnderPhreakManager;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;

@Getter @Setter
public class Manager {

    private final MortisEnderPhreak plugin = MortisEnderPhreak.getInstance();
    private EnderPhreakManager enderPhreakManager;
    private ConfigManager configManager;

    public Manager() {
        this.configManager = new ConfigManager(this);
        plugin.getServer().getPluginCommand("enderphreak").setExecutor(new EnderPhreakCommand(this));
    }

    public void reload() {
        HandlerList.unregisterAll(plugin);
        Bukkit.getScheduler().cancelTasks(plugin);
        setConfigManager(new ConfigManager(this));
    }
}
