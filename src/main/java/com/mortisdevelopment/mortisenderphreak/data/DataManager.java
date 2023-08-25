package com.mortisdevelopment.mortisenderphreak.data;

import com.mortisdevelopment.mortisenderphreak.MortisEnderPhreak;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class DataManager {

    private final MortisEnderPhreak plugin = MortisEnderPhreak.getInstance();
    private final H2Database database;
    private final Map<UUID, String> passwordByUUID;

    public DataManager(H2Database database) {
        this.database = database;
        this.passwordByUUID = new HashMap<>();
        initializeDatabase();
        loadDatabase();
    }

    private void initializeDatabase() {
        new BukkitRunnable() {
            @Override
            public void run() {
                database.execute("CREATE TABLE IF NOT EXISTS MortisEnderPhreak(uuid tinytext primary key, password tinytext)");
            }
        }.runTask(plugin);
    }

    private void loadDatabase() {
        new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    ResultSet result = database.query("SELECT * FROM MortisEnderPhreak");
                    while (result.next()) {
                        String rawUUID = result.getString("uuid");
                        if (rawUUID == null) {
                            continue;
                        }
                        UUID uuid = UUID.fromString(rawUUID);
                        String password = result.getString("password");
                        if (password == null) {
                            continue;
                        }
                        passwordByUUID.put(uuid, password);
                    }
                }catch (SQLException exp) {
                    exp.printStackTrace();
                }
            }
        }.runTask(plugin);
    }

    public String getPassword(UUID uuid) {
        return passwordByUUID.get(uuid);
    }

    public boolean hasUser(UUID uuid) {
        return passwordByUUID.containsKey(uuid);
    }

    public void addUser(UUID uuid, String password) {
        new BukkitRunnable() {
            @Override
            public void run() {
                database.update("INSERT INTO MortisEnderPhreak(uuid, password) VALUES (?, ?)", uuid.toString(), password);
            }
        }.runTask(plugin);
        passwordByUUID.put(uuid, password);
    }

    public void updateUser(UUID uuid, String password) {
        new BukkitRunnable() {
            @Override
            public void run() {
                database.update("UPDATE MortisEnderPhreak SET password = ? WHERE uuid = ?", password, uuid.toString());
            }
        }.runTask(plugin);
        passwordByUUID.put(uuid, password);
    }

    public void removeUser(UUID uuid) {
        new BukkitRunnable() {
            @Override
            public void run() {
                database.update("DELETE FROM MortisEnderPhreak WHERE uuid = ?", uuid.toString());
            }
        }.runTask(plugin);
        passwordByUUID.remove(uuid);
    }
}
