package me.none030.mortisenderphreak.methods;

import java.io.File;

import static me.none030.mortisenderphreak.MortisEnderPhreak.plugin;

public class StoringFiles {

    public static void StoreFiles() {

        File file = new File("plugins/MortisEnderPhreak/", "config.yml");
        File file2 = new File("plugins/MortisEnderPhreak/", "data.yml");
        if (!file.exists()) {
            plugin.saveResource("config.yml", true);
        }
        if (!file2.exists()) {
            plugin.saveResource("data.yml", true);
        }
    }
}
