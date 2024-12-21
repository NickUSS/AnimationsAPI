package me.nicolas.animationsAPI.animations;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public interface Animation {
    void play(Location location);
    void play(Location location, Player player);
    void stop();
    boolean isPlaying();
}