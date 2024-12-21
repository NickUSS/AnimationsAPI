package me.nicolas.animationsAPI.animations;

import me.nicolas.animationsAPI.AnimationsAPI;
import org.bukkit.entity.ArmorStand;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseAnimation implements Animation {
    protected final AnimationsAPI plugin;
    protected List<ArmorStand> armorStands;
    protected List<BukkitTask> tasks;
    protected boolean isPlaying;

    public BaseAnimation() {
        this.plugin = AnimationsAPI.getInstance();
        this.armorStands = new ArrayList<>();
        this.tasks = new ArrayList<>();
        this.isPlaying = false;
    }

    protected void cleanup() {
        tasks.forEach(BukkitTask::cancel);
        armorStands.forEach(ArmorStand::remove);
        tasks.clear();
        armorStands.clear();
        isPlaying = false;
    }

    @Override
    public boolean isPlaying() {
        return isPlaying;
    }
}