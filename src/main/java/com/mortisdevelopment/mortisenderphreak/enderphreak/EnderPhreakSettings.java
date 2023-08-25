package com.mortisdevelopment.mortisenderphreak.enderphreak;

import com.mortisdevelopment.mortisenderphreak.utils.PasswordState;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

@Getter
public class EnderPhreakSettings {

    private final PasswordState state;
    private final boolean remember;
    private final boolean reset;
    private final int distance;
    private final long cooldown;
    private final int maxLength;
    private final boolean broadcast;
    private final boolean broadcastOpen;
    private final int stealAmount;
    private final long stealTime;

    public EnderPhreakSettings(PasswordState state, boolean remember, boolean reset, int distance, long cooldown, int maxLength, boolean broadcast, boolean broadcastOpen, int stealAmount, long stealTime) {
        this.state = state;
        this.remember = remember;
        this.reset = reset;
        this.distance = distance;
        this.cooldown = cooldown;
        this.maxLength = maxLength;
        this.broadcast = broadcast;
        this.broadcastOpen = broadcastOpen;
        this.stealAmount = stealAmount;
        this.stealTime = stealTime;
    }

    public boolean isValidLength(String password) {
        return password.length() <= maxLength;
    }

    public boolean isNear(Player player) {
        Location location = player.getLocation();
        for (int x = location.getBlockX() - distance; x <= location.getBlockX() + distance; x++) {
            for (int y = location.getBlockY() - distance; y <= location.getBlockY() + distance; y++) {
                for (int z = location.getBlockZ() - distance; z <= location.getBlockZ() + distance; z++) {
                    Block block = location.getWorld().getBlockAt(x, y, z);
                    if (block.getType().equals(Material.ENDER_CHEST)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
