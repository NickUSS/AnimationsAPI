package me.nicolas.animationsAPI.managers;

import me.nicolas.animationsAPI.AnimationsAPI;
import me.nicolas.animationsAPI.animations.Animation;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class AnimationManager {
    private final AnimationsAPI plugin;
    private final Map<String, Animation> animations;

    public AnimationManager(AnimationsAPI plugin) {
        this.plugin = plugin;
        this.animations = new HashMap<>();
    }

    public void registerAnimation(String name, Animation animation) {
        animations.put(name.toLowerCase(), animation);
    }

    public void playAnimation(String name, Location location) {
        Animation animation = animations.get(name.toLowerCase());
        if (animation != null) {
            animation.play(location);
        }
    }

    public void playAnimation(String name, Location location, Player player) {
        Animation animation = animations.get(name.toLowerCase());
        if (animation != null) {
            animation.play(location, player);
        }
    }

    public void stopAnimation(String name) {
        Animation animation = animations.get(name.toLowerCase());
        if (animation != null) {
            animation.stop();
        }
    }

    public boolean hasAnimation(String name) {
        return animations.containsKey(name.toLowerCase());
    }
}