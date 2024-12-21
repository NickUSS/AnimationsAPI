package me.nicolas.animationsAPI.animations;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;

public class VictoryAnimation extends BaseAnimation {
    @Override
    public void play(Location location) {
        if (isPlaying) return;
        isPlaying = true;

        ArmorStand center = plugin.getArmorStandHandler().createAnimationArmorStand(location);
        armorStands.add(center);

        tasks.add(new BukkitRunnable() {
            double angle = 0;
            int ticks = 0;

            @Override
            public void run() {
                if (ticks >= 60) {
                    cleanup();
                    cancel();
                    return;
                }

                angle += Math.PI / 8;
                double height = Math.sin(angle) * 0.5;

                center.teleport(location.clone().add(0, height, 0));
                center.setHeadPose(new EulerAngle(angle, angle, 0));

                // Añadir partículas
                plugin.getParticleUtils().createParticleCircle(
                        center.getLocation(),
                        Particle.HAPPY_VILLAGER,
                        1.0,
                        8
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