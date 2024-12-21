package me.nicolas.animationsAPI.animations;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;

public class SpawnAnimation extends BaseAnimation {
    @Override
    public void play(Location location) {
        if (isPlaying) return;
        isPlaying = true;

        ArmorStand center = plugin.getArmorStandHandler().createAnimationArmorStand(location.clone().subtract(0, 2, 0));
        armorStands.add(center);

        tasks.add(new BukkitRunnable() {
            double height = 0;
            int ticks = 0;

            @Override
            public void run() {
                if (ticks >= 40) {
                    cleanup();
                    cancel();
                    return;
                }

                height += 0.1;
                center.teleport(location.clone().subtract(0, 2-height, 0));
                center.setHeadPose(new EulerAngle(0, Math.PI * height, 0));

                // Efecto de partículas
                plugin.getParticleUtils().createParticleCircle(
                        center.getLocation(),
                        Particle.END_ROD,
                        1.0 - (height/2),
                        16
                );

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