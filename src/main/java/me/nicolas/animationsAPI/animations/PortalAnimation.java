package me.nicolas.animationsAPI.animations;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class PortalAnimation extends BaseAnimation {
    @Override
    public void play(Location location) {
        if (isPlaying) return;
        isPlaying = true;

        List<ArmorStand> portalStands = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            portalStands.add(plugin.getArmorStandHandler().createAnimationArmorStand(location));
            armorStands.addAll(portalStands);
        }

        tasks.add(new BukkitRunnable() {
            double angle = 0;
            int ticks = 0;

            @Override
            public void run() {
                if (ticks >= 80) {
                    cleanup();
                    cancel();
                    return;
                }

                angle += Math.PI / 16;
                for (int i = 0; i < portalStands.size(); i++) {
                    double offset = (Math.PI * 2 / portalStands.size()) * i;
                    double x = Math.cos(angle + offset) * 1.5;
                    double z = Math.sin(angle + offset) * 1.5;
                    Location standLoc = location.clone().add(x, Math.sin(angle) * 0.5, z);
                    portalStands.get(i).teleport(standLoc);

                    // Efectos de partículas
                    location.getWorld().spawnParticle(Particle.PORTAL,
                            standLoc,
                            5, 0.1, 0.1, 0.1, 0.05);
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