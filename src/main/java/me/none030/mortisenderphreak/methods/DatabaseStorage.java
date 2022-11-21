package me.none030.mortisenderphreak.methods;

import me.none030.mortisenderphreak.utils.User;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.sql.*;
import java.util.Objects;
import java.util.UUID;

public class DatabaseStorage {

    public static Connection connection;

    public static Connection getConnection() {

        try {
            if (connection != null) {
                return connection;
            }
            File file = new File("plugins/MortisEnderPhreak/", "config.yml");
            FileConfiguration config = YamlConfiguration.loadConfiguration(file);
            ConfigurationSection section = config.getConfigurationSection("config.database");

            assert section != null;
            String url = "jdbc:mysql://" + section.getString("host") + ":" + section.getString("port") + "/" + section.getString("database");
            String user = section.getString("user");
            String password = section.getString("password");

            connection = DriverManager.getConnection(url, user, password);

            System.out.println("Connected to database.");

            return connection;
        } catch (SQLException exp) {
            exp.printStackTrace();
        }
        return null;
    }

    public static void initializeDatabase() {

        try {
            Statement statement = Objects.requireNonNull(getConnection()).createStatement();

            //Create the player_stats table
            String sql = "CREATE TABLE IF NOT EXISTS EnderChests(uuid varchar(100) primary key, pass varchar(100))";

            statement.execute(sql);

            statement.close();

        } catch (SQLException exp) {
            exp.printStackTrace();
            System.out.println("Could not initialize database.");
        }
    }

    public static void StoreDatabaseData(User user) {

        try {
            PreparedStatement stmt = Objects.requireNonNull(getConnection()).prepareStatement("INSERT INTO EnderChests(uuid, pass) VALUES(?, ?)");
            stmt.setString(1, user.getPlayer().toString());
            stmt.setString(2, user.getPassword());
            stmt.execute();

        }catch (SQLException exp){
            exp.printStackTrace();
        }
    }

    public static void ChangeDatabasePassword(User user) {

        try {
            PreparedStatement stmt = Objects.requireNonNull(getConnection()).prepareStatement("UPDATE EnderChests SET pass = ? WHERE uuid = ?");
            stmt.setString(1, user.getPassword());
            stmt.setString(2, user.getPlayer().toString());
            stmt.executeUpdate();
        } catch (SQLException exp) {
            exp.printStackTrace();
        }
    }

    public static String getDatabasePassword(UUID player) {

        try {
            PreparedStatement stmt = Objects.requireNonNull(getConnection()).prepareStatement("SELECT pass FROM EnderChests WHERE uuid = ?;");
            stmt.setString(1, player.toString());
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("pass");
            }
        } catch (SQLException exp) {
            exp.printStackTrace();
        }
        return null;
    }

    public static void RemoveDatabaseData(UUID player) {

        try {
            PreparedStatement stmt = Objects.requireNonNull(getConnection()).prepareStatement("DELETE FROM EnderChests WHERE uuid = ?;");
            stmt.setString(1, player.toString());
        } catch (SQLException exp) {
            exp.printStackTrace();
        }
    }
}
