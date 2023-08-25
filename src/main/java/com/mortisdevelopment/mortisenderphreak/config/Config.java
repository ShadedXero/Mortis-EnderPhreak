package com.mortisdevelopment.mortisenderphreak.config;

import com.mortisdevelopment.mortisenderphreak.MortisEnderPhreak;

import java.io.File;

public abstract class Config {

    private final MortisEnderPhreak plugin = MortisEnderPhreak.getInstance();
    private final String fileName;

    public Config(String fileName) {
        this.fileName = fileName;
    }

    public abstract void loadConfig();

    public File saveConfig() {
        File file = new File(plugin.getDataFolder(), fileName);
        if (!file.exists()) {
            plugin.saveResource(fileName, true);
        }
        return file;
    }

    public MortisEnderPhreak getPlugin() {
        return plugin;
    }

    public String getFileName() {
        return fileName;
    }
}
