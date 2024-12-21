package me.nicolas.animationsAPI.handlers;

import me.nicolas.animationsAPI.AnimationsAPI;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;

import java.util.HashSet;
import java.util.Set;

public class ArmorStandHandler implements Listener {
    private final AnimationsAPI plugin;
    private final Set<ArmorStand> activeArmorStands;

    public ArmorStandHandler(AnimationsAPI plugin) {
        this.plugin = plugin;
        this.activeArmorStands = new HashSet<>();
    }

    public ArmorStand createAnimationArmorStand(Location location) {
        ArmorStand armorStand = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
        armorStand.setVisible(false);
        armorStand.setGravity(false);
        armorStand.setInvulnerable(true);
        armorStand.setSmall(true);
        armorStand.setMarker(true);
        activeArmorStands.add(armorStand);
        return armorStand;
    }

    @EventHandler
    public void onArmorStandManipulate(PlayerArmorStandManipulateEvent event) {
        if (activeArmorStands.contains(event.getRightClicked())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onArmorStandDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof ArmorStand &&
                activeArmorStands.contains(event.getEntity())) {
            event.setCancelled(true);
        }
    }

    public void removeArmorStand(ArmorStand armorStand) {
        activeArmorStands.remove(armorStand);
        armorStand.remove();
    }
}