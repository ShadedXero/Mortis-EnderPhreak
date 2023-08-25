package com.mortisdevelopment.mortisenderphreak;

import com.mortisdevelopment.mortisenderphreak.manager.Manager;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public final class MortisEnderPhreak extends JavaPlugin {

    @Getter
    private static MortisEnderPhreak Instance;
    @Getter
    private Manager manager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        Instance = this;
        manager = new Manager();
    }
}
