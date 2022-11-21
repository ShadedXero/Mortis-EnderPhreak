package me.none030.mortisenderphreak.methods;

import me.none030.mortisenderphreak.utils.User;

import java.util.UUID;

import static me.none030.mortisenderphreak.MortisEnderPhreak.database;
import static me.none030.mortisenderphreak.methods.DatabaseStorage.*;
import static me.none030.mortisenderphreak.methods.YAMLStorage.*;

public class StorageMethods {

    public static void StoreData(User user) {
        if (database) {
            StoreDatabaseData(user);
        } else {
            StoreYamlData(user);
        }
    }

    public static void ChangePassword(User user) {
        if (database) {
            ChangeDatabasePassword(user);
        } else {
            ChangeYamlPassword(user);
        }
    }

    public static String getPassword(UUID player) {
        if (database) {
            return getDatabasePassword(player);
        } else {
            return getYamlPassword(player);
        }
    }

    public static void RemoveData(UUID player) {
        if (database) {
            RemoveDatabaseData(player);
        } else {
            RemoveYamlData(player);
        }
    }

}
