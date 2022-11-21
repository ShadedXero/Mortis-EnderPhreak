package me.none030.mortisenderphreak.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import static me.none030.mortisenderphreak.methods.CreatingYAMLValues.*;
import static me.none030.mortisenderphreak.methods.CommandMethods.*;
import static me.none030.mortisenderphreak.methods.HackingEnderChest.HackEnderChest;
import static me.none030.mortisenderphreak.methods.HackingEnderChest.InCooldown;
import static me.none030.mortisenderphreak.methods.StorageMethods.getPassword;

public class EnderPhreakCommand implements TabExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("open")) {
                    if (player.hasPermission("enderphreak.open")) {
                        if (isNearEnderChest(player)) {
                            if (isRememberPassword()) {
                                OpenEnderChest(player);
                            } else {
                                String password = getPassword(player.getUniqueId());
                                if (password != null) {
                                    player.sendMessage("Remember Password is off");
                                    player.sendMessage("Please use /enderphreak pass <password>");
                                    PlayTone(player, password);
                                } else {
                                    OpenEnderChest(player);
                                }
                            }
                            return true;
                        } else {
                            player.sendMessage(getMessage("distance"));
                        }
                    }
                }
            }

            if (args.length == 2) {
                if (args[0].equalsIgnoreCase("set")) {
                    if (player.hasPermission("enderphreak.set")) {
                        if (isNearEnderChest(player)) {
                            try {
                                Double.parseDouble(args[1]);
                                int length = getMaxLength();
                                if (args[1].length() <= length) {
                                    SetPassword(player, args[1]);
                                    return true;
                                } else {
                                    player.sendMessage(getMessage("max-length").replace("%length%", String.valueOf(length)));
                                }
                            } catch (NumberFormatException exp) {
                                player.sendMessage(getMessage("invalid-player"));
                            }
                        } else {
                            player.sendMessage(getMessage("distance"));
                        }
                    }
                }
                if (args[0].equalsIgnoreCase("tone")) {
                    if (player.hasPermission("enderphreak.tone")) {
                        if (isNearEnderChest(player)) {
                            try {
                                Double.parseDouble(args[1]);
                                if (args[1].length() <= 10) {
                                    PlayTone(player, args[1]);
                                    return true;
                                } else {
                                    player.sendMessage("You can't play a tone with a number larger than 10");
                                }
                            } catch (NumberFormatException exp) {
                                player.sendMessage("Please enter valid numbers");
                            }
                        } else {
                            player.sendMessage(getMessage("distance"));
                        }
                    }
                }
                if (args[0].equalsIgnoreCase("pass")) {
                    if (player.hasPermission("enderphreak.open")) {
                        if (isNearEnderChest(player)) {
                            try {
                                Double.parseDouble(args[1]);
                                OpenEnderChest(player, args[1]);
                                return true;
                            } catch (NumberFormatException exp) {
                                player.sendMessage("Please enter valid numbers");
                            }
                        } else {
                            player.sendMessage(getMessage("distance"));
                        }
                    }
                }
                if (args[0].equalsIgnoreCase("override")) {
                    if (player.hasPermission("enderphreak.override")) {
                        Player target = Bukkit.getPlayer(args[1]);
                        if (target != null) {
                            OpenEnderChestAdmin(player, target);
                            return true;
                        } else {
                            player.sendMessage(getMessage("invalid-player"));
                        }
                    }
                }
                if (args[0].equalsIgnoreCase("overridereset")) {
                    if (player.hasPermission("enderphreak.override.reset")) {
                        Player target = Bukkit.getPlayer(args[1]);
                        if (target != null) {
                            ResetEnderChest(target);
                            return true;
                        } else {
                            player.sendMessage(getMessage("invalid-player"));
                        }
                    }
                }
                if (args[0].equalsIgnoreCase("hack")) {
                    if (player.hasPermission("enderphreak.hack")) {
                        if (isNearEnderChest(player)) {
                            if (!InCooldown.contains(player)) {
                                Player target = Bukkit.getPlayer(args[1]);
                                if (target != null && !target.equals(player)) {
                                    HackEnderChest(player, target);
                                    return true;
                                } else {
                                    player.sendMessage(getMessage("invalid-player"));
                                }
                            } else {
                                player.sendMessage(getMessage("cooldown"));
                            }
                        } else {
                            player.sendMessage(getMessage("distance"));
                        }
                    }
                }
            }
            if (args.length == 3) {
                if (args[0].equalsIgnoreCase("hack")) {
                    if (player.hasPermission("enderphreak.hack")) {
                        if (isNearEnderChest(player)) {
                            if (!InCooldown.contains(player)) {
                                Player target = Bukkit.getPlayer(args[1]);
                                if (target != null && !target.equals(player)) {
                                    try {
                                        Double.parseDouble(args[2]);
                                        HackEnderChest(player, target, args[2]);
                                        return true;
                                    } catch (NumberFormatException exp) {
                                        player.sendMessage("Please enter valid numbers");
                                    }
                                } else {
                                    player.sendMessage(getMessage("invalid-player"));
                                }
                            } else {
                                player.sendMessage(getMessage("cooldown"));
                            }
                        } else {
                            player.sendMessage(getMessage("distance"));
                        }
                    }
                }
                if (args[0].equalsIgnoreCase("set")) {
                    if (player.hasPermission("enderphreak.set")) {
                        if (isNearEnderChest(player)) {
                            try {
                                Double.parseDouble(args[1]);
                                Double.parseDouble(args[2]);

                                SetPassword(player, args[1], args[2]);
                                return true;
                            } catch (NumberFormatException exp) {
                                player.sendMessage("Please enter valid numbers");
                            }
                        } else {
                            player.sendMessage(getMessage("distance"));
                        }
                    }
                }
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
