package me.nicolas.animationsAPI.animations;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class HelixAnimation extends BaseAnimation {
    @Override
    public void play(Location location) {
        if (isPlaying) return;
        isPlaying = true;

        ArmorStand center = plugin.getArmorStandHandler().createAnimationArmorStand(location);
        armorStands.add(center);

        tasks.add(new BukkitRunnable() {
            double angle = 0;
            double height = 0;
            int ticks = 0;

            @Override
            public void run() {
                if (ticks >= 60) {
                    cleanup();
                    cancel();
                    return;
                }

                // Doble hélice
                for (int i = 0; i < 2; i++) {
                    double offset = i * Math.PI;
                    double x = Math.cos(angle + offset) * 1.5;
                    double z = Math.sin(angle + offset) * 1.5;
                    Location particleLoc = location.clone().add(x, height, z);

                    location.getWorld().spawnParticle(Particle.END_ROD,
                            particleLoc,
                            3, 0.1, 0.1, 0.1, 0.01);
                }

                angle += Math.PI / 8;
                height += 0.1;

                if (height > 3) height = 0;

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