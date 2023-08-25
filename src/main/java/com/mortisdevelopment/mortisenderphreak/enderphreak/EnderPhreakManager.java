package com.mortisdevelopment.mortisenderphreak.enderphreak;

import com.mortisdevelopment.mortisenderphreak.MortisEnderPhreak;
import com.mortisdevelopment.mortisenderphreak.data.DataManager;
import com.mortisdevelopment.mortisenderphreak.manager.CoreManager;
import lombok.Getter;
import net.kyori.adventure.text.TextReplacementConfig;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

@Getter
public class EnderPhreakManager extends CoreManager {

    private final MortisEnderPhreak plugin = MortisEnderPhreak.getInstance();
    private final DataManager dataManager;
    private final EnderPhreakSettings settings;
    private final List<EnderPhreakTone> tones;
    private final Set<Character> numbers;
    private final Set<UUID> inCooldown;
    private final Set<UUID> hacking;
    private final Map<UUID, Integer> stolenByUUID;

    public EnderPhreakManager(DataManager dataManager, EnderPhreakSettings settings) {
        this.dataManager = dataManager;
        this.settings = settings;
        this.tones = new ArrayList<>();
        this.numbers = getNumbers();
        this.inCooldown = new HashSet<>();
        this.hacking = new HashSet<>();
        this.stolenByUUID = new HashMap<>();
        plugin.getServer().getPluginManager().registerEvents(new EnderPhreakListener(this), plugin);
    }

    private Set<Character> getNumbers() {
        Set<Character> numbers = new HashSet<>();
        numbers.add('0');
        numbers.add('1');
        numbers.add('2');
        numbers.add('3');
        numbers.add('4');
        numbers.add('5');
        numbers.add('6');
        numbers.add('7');
        numbers.add('8');
        numbers.add('9');
        return numbers;
    }

    public void onHack(Player player, Player target) {
        Location location = player.getLocation();
        TextReplacementConfig x = TextReplacementConfig.builder().match("%x%").replacement(String.valueOf(location.getX())).build();
        TextReplacementConfig y = TextReplacementConfig.builder().match("%y%").replacement(String.valueOf(location.getY())).build();
        TextReplacementConfig z = TextReplacementConfig.builder().match("%z%").replacement(String.valueOf(location.getZ())).build();
        if (settings.isBroadcast()) {
            target.sendMessage(getMessage("BROADCAST").replaceText(x).replaceText(y).replaceText(z));
        }
        if (settings.isBroadcastOpen()) {
            target.sendMessage(getMessage("BROADCAST_OPEN").replaceText(x).replaceText(y).replaceText(z));
        }
        inCooldown.add(player.getUniqueId());
        new BukkitRunnable() {
            @Override
            public void run() {
                inCooldown.remove(player.getUniqueId());
            }
        }.runTaskLater(plugin, settings.getCooldown() * 20L);
        hacking.add(player.getUniqueId());
        new BukkitRunnable() {
            long seconds = 0;
            @Override
            public void run() {
                seconds++;
                if (!hacking.contains(player.getUniqueId())) {
                    cancel();
                }
                if (seconds > settings.getStealTime()) {
                    player.closeInventory();
                    hacking.remove(player.getUniqueId());
                    cancel();
                }
            }
        }.runTaskTimer(plugin, 0L, 20L);
    }

    public boolean isValidPassword(String password) {
        for (int i = 0; i < password.length(); i++) {
            char number = password.charAt(i);
            if (numbers.contains(number)) {
                continue;
            }
            return false;
        }
        return true;
    }

    public boolean isPassword(Player player, String password) {
        if (!dataManager.hasUser(player.getUniqueId())) {
            return true;
        }
        return password.equals(dataManager.getPassword(player.getUniqueId()));
    }

    public void setPassword(Player player, String password) {
        if (dataManager.hasUser(player.getUniqueId())) {
            dataManager.updateUser(player.getUniqueId(), password);
        }else {
            dataManager.addUser(player.getUniqueId(), password);
        }
    }

    public void reset(Player player) {
        dataManager.removeUser(player.getUniqueId());
        if (settings.isReset()) {
           player.getEnderChest().clear();
        }
        player.sendMessage(getMessage("RESET_PASSWORD"));
    }

    public void openEnderChest(Player player) {
        Location location = player.getLocation();
        TextReplacementConfig x = TextReplacementConfig.builder().match("%x%").replacement(String.valueOf(location.getX())).build();
        TextReplacementConfig y = TextReplacementConfig.builder().match("%y%").replacement(String.valueOf(location.getY())).build();
        TextReplacementConfig z = TextReplacementConfig.builder().match("%z%").replacement(String.valueOf(location.getZ())).build();
        if (dataManager.hasUser(player.getUniqueId())) {
            player.openInventory(player.getEnderChest());
            playTones(player, dataManager.getPassword(player.getUniqueId()));
            player.sendMessage(getMessage("OPENED"));
            if (settings.isBroadcastOpen()) {
                player.sendMessage(getMessage("BROADCAST_OPEN").replaceText(x).replaceText(y).replaceText(z));
            }
            return;
        }
        switch (settings.getState()) {
            case NOTHING:
                player.openInventory(player.getEnderChest());
                break;
            case PROMPT:
                player.openInventory(player.getEnderChest());
                player.sendMessage(getMessage("PROMPT"));
                break;
            case LOCK:
                player.sendMessage(getMessage("PROMPT"));
                return;
        }
        player.sendMessage(getMessage("OPENED"));
        if (settings.isBroadcastOpen()) {
            player.sendMessage(getMessage("BROADCAST_OPEN").replaceText(x).replaceText(y).replaceText(z));
        }
    }

    public void openEnderChest(Player player, Player target) {
        player.openInventory(target.getEnderChest());
        player.sendMessage(getMessage("OPENED"));
    }

    public void playTones(Player player, String password) {
        new BukkitRunnable() {
            int index = 0;
            @Override
            public void run() {
                playTone(player, password.charAt(index));
                if ((index + 1) >= password.length()) {
                    cancel();
                }else {
                    index++;
                }
            }
        }.runTaskTimer(plugin, 0L, 20L);
    }

    public void playTone(Player player, char number) {
        for (EnderPhreakTone tone : tones) {
            if (tone.isNumber(number)) {
                tone.playSound(player);
            }
        }
    }
}
