package me.nicolas.animationsAPI.utils;

import me.nicolas.animationsAPI.AnimationsAPI;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.scheduler.BukkitRunnable;

public class ParticleUtils {
    private final AnimationsAPI plugin;

    public ParticleUtils(AnimationsAPI plugin) {
        this.plugin = plugin;
    }

    public void createParticleCircle(Location center, Particle particle, double radius, int particles) {
        for (int i = 0; i < particles; i++) {
            double angle = 2 * Math.PI * i / particles;
            Location particleLoc = center.clone().add(
                    radius * Math.cos(angle),
                    0,
                    radius * Math.sin(angle)
            );
            center.getWorld().spawnParticle(particle, particleLoc, 1, 0, 0, 0, 0);
        }
    }

    public void createParticleHelix(Location center, Particle particle, double radius, double height) {
        new BukkitRunnable() {
            double y = 0;
            double angle = 0;

            @Override
            public void run() {
                if (y > height) {
                    cancel();
                    return;
                }

                double x = radius * Math.cos(angle);
                double z = radius * Math.sin(angle);
                Location particleLoc = center.clone().add(x, y, z);

                center.getWorld().spawnParticle(particle, particleLoc, 1, 0, 0, 0, 0);

                y += 0.1;
                angle += Math.PI / 8;
            }
        }.runTaskTimer(plugin, 0L, 1L);
    }
}