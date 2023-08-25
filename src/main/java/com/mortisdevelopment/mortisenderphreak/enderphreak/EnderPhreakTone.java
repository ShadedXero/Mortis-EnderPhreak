package com.mortisdevelopment.mortisenderphreak.enderphreak;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class EnderPhreakTone {

    private final char number;
    private final Sound sound;

    public EnderPhreakTone(char number, Sound sound) {
        this.number = number;
        this.sound = sound;
    }

    public boolean isNumber(char number) {
        return this.number == number;
    }

    public void playSound(Player player) {
        player.getWorld().playSound(player.getLocation(), sound, 1, 1);
    }
}
