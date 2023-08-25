package com.mortisdevelopment.mortisenderphreak.manager;

import net.kyori.adventure.text.TextReplacementConfig;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class EnderPhreakCommand implements TabExecutor {

    private final Manager manager;

    public EnderPhreakCommand(Manager manager) {
        this.manager = manager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) {
            return false;
        }
        if (args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission("enderphreak.reload")) {
                sender.sendMessage("NO_PERMISSION");
                return false;
            }
            manager.reload();
            sender.sendMessage("RELOAD");
            return true;
        }
        if (!(sender instanceof Player)) {
            return false;
        }
        Player player = (Player) sender;
        if (args[0].equalsIgnoreCase("open")) {
            if (!player.hasPermission("enderphereak.open")) {
                player.sendMessage("NO_PERMISSION");
                return false;
            }
            if (!manager.getEnderPhreakManager().getSettings().isNear(player)) {
                player.sendMessage(manager.getEnderPhreakManager().getMessage("DISTANCE"));
                return false;
            }
            if (manager.getEnderPhreakManager().getSettings().isRemember()) {
                manager.getEnderPhreakManager().openEnderChest(player);
                return true;
            }
            String password = manager.getEnderPhreakManager().getDataManager().getPassword(player.getUniqueId());
            if (password == null) {
                manager.getEnderPhreakManager().openEnderChest(player);
                return true;
            }
            manager.getEnderPhreakManager().playTones(player, password);
            player.sendMessage(manager.getEnderPhreakManager().getMessage("NO_REMEMBER"));
            player.sendMessage(manager.getEnderPhreakManager().getMessage("PASS_USAGE"));
            player.sendMessage(manager.getEnderPhreakManager().getMessage("PLAY_TONE"));
            return true;
        }
        if (args[0].equalsIgnoreCase("set")) {
            if (!player.hasPermission("enderphreak.set")) {
                player.sendMessage(manager.getEnderPhreakManager().getMessage("NO_PERMISSION"));
                return false;
            }
            if (args.length < 2) {
                player.sendMessage(manager.getEnderPhreakManager().getMessage("SET_USAGE"));
                return false;
            }
            if (!manager.getEnderPhreakManager().getSettings().isNear(player)) {
                player.sendMessage(manager.getEnderPhreakManager().getMessage("DISTANCE"));
                return false;
            }
            if (args.length >= 3) {
                String oldPassword = args[2];
                if (!manager.getEnderPhreakManager().isPassword(player, oldPassword)) {
                    manager.getEnderPhreakManager().playTones(player, oldPassword);
                    player.sendMessage(manager.getEnderPhreakManager().getMessage("WRONG_PASSWORD"));
                    return false;
                }
                String newPassword = args[1];
                if (!manager.getEnderPhreakManager().getSettings().isValidLength(newPassword)) {
                    player.sendMessage(manager.getEnderPhreakManager().getMessage("MAX_LENGTH").replaceText(TextReplacementConfig.builder().match("%length%").replacement(String.valueOf(manager.getEnderPhreakManager().getSettings().getMaxLength())).build()));
                    return false;
                }
                if (!manager.getEnderPhreakManager().isValidPassword(newPassword)) {
                    player.sendMessage(manager.getEnderPhreakManager().getMessage("INVALID_NUMBER"));
                    return false;
                }
                manager.getEnderPhreakManager().setPassword(player, newPassword);
                manager.getEnderPhreakManager().playTones(player, newPassword);
                player.sendMessage(manager.getEnderPhreakManager().getMessage("PASSWORD_SET"));
                return true;
            }else {
                if (manager.getEnderPhreakManager().getDataManager().hasUser(player.getUniqueId())) {
                    manager.getEnderPhreakManager().playTones(player, manager.getEnderPhreakManager().getDataManager().getPassword(player.getUniqueId()));
                    player.sendMessage(manager.getEnderPhreakManager().getMessage("WRONG_PASSWORD"));
                    return false;
                }
                String password = args[1];
                if (!manager.getEnderPhreakManager().getSettings().isValidLength(password)) {
                    player.sendMessage(manager.getEnderPhreakManager().getMessage("MAX_LENGTH").replaceText(TextReplacementConfig.builder().match("%length%").replacement(String.valueOf(manager.getEnderPhreakManager().getSettings().getMaxLength())).build()));
                    return false;
                }
                if (!manager.getEnderPhreakManager().isValidPassword(password)) {
                    player.sendMessage(manager.getEnderPhreakManager().getMessage("INVALID_NUMBER"));
                    return false;
                }
                manager.getEnderPhreakManager().setPassword(player, password);
                manager.getEnderPhreakManager().playTones(player, password);
                player.sendMessage(manager.getEnderPhreakManager().getMessage("PASSWORD_SET"));
                return true;
            }
        }
        if (args[0].equalsIgnoreCase("tone")) {
            if (!player.hasPermission("enderphreak.tone")) {
                player.sendMessage(manager.getEnderPhreakManager().getMessage("NO_PERMISSION"));
                return false;
            }
            if (args.length < 2) {
                player.sendMessage(manager.getEnderPhreakManager().getMessage("TONE_USAGE"));
                return false;
            }
            if (!manager.getEnderPhreakManager().getSettings().isNear(player)) {
                player.sendMessage(manager.getEnderPhreakManager().getMessage("DISTANCE"));
                return false;
            }
            String password = args[1];
            if (password.length() > 10) {
                player.sendMessage("You can't play a tone with a number larger than 10");
                return false;
            }
            if (!manager.getEnderPhreakManager().isValidPassword(password)) {
                player.sendMessage(manager.getEnderPhreakManager().getMessage("INVALID_NUMBER"));
                return false;
            }
            manager.getEnderPhreakManager().playTones(player, password);
            player.sendMessage(manager.getEnderPhreakManager().getMessage("PLAY_TONE"));
            return true;
        }
        if (args[0].equalsIgnoreCase("pass")) {
            if (!player.hasPermission("enderphreak.open")) {
                player.sendMessage(manager.getEnderPhreakManager().getMessage("NO_PERMISSION"));
                return false;
            }
            if (args.length < 2) {
                player.sendMessage(manager.getEnderPhreakManager().getMessage("PASS_USAGE"));
                return false;
            }
            if (!manager.getEnderPhreakManager().getSettings().isNear(player)) {
                player.sendMessage(manager.getEnderPhreakManager().getMessage("DISTANCE"));
                return false;
            }
            String password = args[1];
            if (!manager.getEnderPhreakManager().isValidPassword(password)) {
                player.sendMessage(manager.getEnderPhreakManager().getMessage("INVALID_NUMBER"));
                return false;
            }
            if (!manager.getEnderPhreakManager().isPassword(player, password)) {
                manager.getEnderPhreakManager().playTones(player, password);
                player.sendMessage(manager.getEnderPhreakManager().getMessage("WRONG_PASSWORD"));
                return false;
            }
            manager.getEnderPhreakManager().openEnderChest(player);
            return true;
        }
        if (args[0].equals("override")) {
            if (!player.hasPermission("enderphreak.override")) {
                player.sendMessage(manager.getEnderPhreakManager().getMessage("NO_PERMISSION"));
                return false;
            }
            if (args.length < 2) {
                player.sendMessage(manager.getEnderPhreakManager().getMessage("OVERRIDE_USAGE"));
                return false;
            }
            Player target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                player.sendMessage(manager.getEnderPhreakManager().getMessage("INVALID_TARGET"));
                return false;
            }
            manager.getEnderPhreakManager().openEnderChest(player, target);
            return true;
        }
        if (args[0].equalsIgnoreCase("overridereset")) {
            if (!player.hasPermission("enderphreak.override.reset")) {
                player.sendMessage(manager.getEnderPhreakManager().getMessage("NO_PERMISSION"));
                return false;
            }
            if (args.length < 2) {
                player.sendMessage(manager.getEnderPhreakManager().getMessage("OVERRIDE_RESET_USAGE"));
                return false;
            }
            Player target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                player.sendMessage(manager.getEnderPhreakManager().getMessage("INVALID_TARGET"));
                return false;
            }
            manager.getEnderPhreakManager().reset(target);
            player.sendMessage(manager.getEnderPhreakManager().getMessage("RESET_PASSWORD"));
            return true;
        }
        if (args[0].equalsIgnoreCase("hack")) {
            if (!player.hasPermission("enderphreak.hack")) {
                player.sendMessage(manager.getEnderPhreakManager().getMessage("NO_PERMISSION"));
                return false;
            }
            if (args.length < 2) {
                player.sendMessage(manager.getEnderPhreakManager().getMessage("HACK_USAGE"));
                return false;
            }
            if (!manager.getEnderPhreakManager().getSettings().isNear(player)) {
                player.sendMessage(manager.getEnderPhreakManager().getMessage("DISTANCE"));
                return false;
            }
            if (manager.getEnderPhreakManager().getInCooldown().contains(player.getUniqueId())) {
                player.sendMessage(manager.getEnderPhreakManager().getMessage("COOLDOWN"));
                return false;
            }
            Player target = Bukkit.getPlayer(args[1]);
            if (target == null || target.equals(player)) {
                player.sendMessage(manager.getEnderPhreakManager().getMessage("INVALID_TARGET"));
                return false;
            }
            if (args.length >= 3) {
                String password = args[2];
                if (!manager.getEnderPhreakManager().isPassword(target, password)) {
                    manager.getEnderPhreakManager().playTones(target, password);
                    player.sendMessage(manager.getEnderPhreakManager().getMessage("WRONG_PASSWORD"));
                    return false;
                }
                manager.getEnderPhreakManager().openEnderChest(player, target);
                manager.getEnderPhreakManager().onHack(player, target);
                manager.getEnderPhreakManager().playTones(player, password);
                return true;
            }else {
                if (manager.getEnderPhreakManager().getDataManager().hasUser(target.getUniqueId())) {
                    manager.getEnderPhreakManager().playTones(player, manager.getEnderPhreakManager().getDataManager().getPassword(player.getUniqueId()));
                    player.sendMessage(manager.getEnderPhreakManager().getMessage("WRONG_PASSWORD"));
                    return false;
                }
                manager.getEnderPhreakManager().openEnderChest(player, target);
                manager.getEnderPhreakManager().onHack(player, target);
                return true;
            }
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1) {
            List<String> arguments = new ArrayList<>();
            arguments.add("open");
            arguments.add("set");
            arguments.add("tone");
            arguments.add("pass");
            arguments.add("override");
            arguments.add("overridereset");
            arguments.add("hack");
            return arguments;
        }
        return null;
    }
}
