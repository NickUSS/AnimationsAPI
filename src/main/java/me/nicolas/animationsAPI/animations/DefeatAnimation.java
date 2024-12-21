package me.nicolas.animationsAPI.animations;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;

public class DefeatAnimation extends BaseAnimation {
    @Override
    public void play(Location location) {
        if (isPlaying) return;
        isPlaying = true;

        ArmorStand center = plugin.getArmorStandHandler().createAnimationArmorStand(location);
        armorStands.add(center);

        tasks.add(new BukkitRunnable() {
            double y = 0;
            int ticks = 0;

            @Override
            public void run() {
                if (ticks >= 40) {
                    cleanup();
                    cancel();
                    return;
                }

                y -= 0.05;
                center.teleport(location.clone().add(0, y, 0));
                center.setHeadPose(new EulerAngle(y * 2, 0, 0));

                // Partículas de derrota
                location.getWorld().spawnParticle(Particle.SMOKE,
                        center.getLocation().add(0, 1, 0),
                        5, 0.2, 0.2, 0.2, 0.01);

                ticks++;
            }
        }.runTaskTimer(plugin, 0L, 1L));
    }

    @Override
    public void play(Location location, Player player) {
        play(location);
    }

    @Override
    public void stop() {
        cleanup();
    }
}