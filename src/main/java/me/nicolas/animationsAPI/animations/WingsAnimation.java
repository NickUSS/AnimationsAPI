package me.nicolas.animationsAPI.animations;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class WingsAnimation extends BaseAnimation {
    @Override
    public void play(Location location) {
        if (isPlaying) return;
        isPlaying = true;

        ArmorStand center = plugin.getArmorStandHandler().createAnimationArmorStand(location);
        armorStands.add(center);

        tasks.add(new BukkitRunnable() {
            double wingAngle = 0;
            int ticks = 0;

            @Override
            public void run() {
                if (ticks >= 100) {
                    cleanup();
                    cancel();
                    return;
                }

                // Crear alas con partículas
                double wingspan = 3.0;
                int particlesPerWing = 20;

                for (int wing = 0; wing < 2; wing++) {
                    double wingOffset = wing == 0 ? 0 : Math.PI;

                    for (int i = 0; i < particlesPerWing; i++) {
                        double progress = (double) i / particlesPerWing;
                        double curveHeight = Math.sin(progress * Math.PI) * 1.5;

                        Vector direction = new Vector(
                                Math.cos(wingAngle + wingOffset) * progress * wingspan,
                                curveHeight,
                                Math.sin(wingAngle + wingOffset) * progress * wingspan
                        );

                        Location particleLoc = location.clone().add(direction);
                        location.getWorld().spawnParticle(
                                wing == 0 ? Particle.END_ROD : Particle.END_ROD,
                                particleLoc,
                                1, 0, 0, 0, 0
                        );
                    }
                }

                wingAngle += Math.sin(ticks * 0.1) * 0.1;
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