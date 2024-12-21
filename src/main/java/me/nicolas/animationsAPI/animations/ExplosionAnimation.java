package me.nicolas.animationsAPI.animations;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class ExplosionAnimation extends BaseAnimation {
    @Override
    public void play(Location location) {
        if (isPlaying) return;
        isPlaying = true;

        List<ArmorStand> explosionStands = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            explosionStands.add(plugin.getArmorStandHandler().createAnimationArmorStand(location));
            armorStands.addAll(explosionStands);
        }

        tasks.add(new BukkitRunnable() {
            int ticks = 0;

            @Override
            public void run() {
                if (ticks >= 30) {
                    cleanup();
                    cancel();
                    return;
                }

                double radius = ticks * 0.2;
                for (int i = 0; i < explosionStands.size(); i++) {
                    double angle = (Math.PI * 2 * i) / explosionStands.size();
                    double x = Math.cos(angle) * radius;
                    double z = Math.sin(angle) * radius;
                    double y = Math.sin(ticks * 0.2) * 0.5;

                    Location standLoc = location.clone().add(x, y, z);
                    explosionStands.get(i).teleport(standLoc);

                    // Efectos de partículas
                    location.getWorld().spawnParticle(Particle.FLAME,
                            standLoc,
                            3, 0.1, 0.1, 0.1, 0.01);
                }

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