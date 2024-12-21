package me.nicolas.animationsAPI;

import me.nicolas.animationsAPI.animations.*;
import me.nicolas.animationsAPI.handlers.ArmorStandHandler;
import me.nicolas.animationsAPI.managers.AnimationManager;
import me.nicolas.animationsAPI.utils.ParticleUtils;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class AnimationsAPI extends JavaPlugin {
    private static AnimationsAPI instance;
    private AnimationManager animationManager;
    private ArmorStandHandler armorStandHandler;
    private ParticleUtils particleUtils;

    @Override
    public void onEnable() {
        instance = this;
        this.animationManager = new AnimationManager(this);
        this.armorStandHandler = new ArmorStandHandler(this);
        this.particleUtils = new ParticleUtils(this);

        // Registrar eventos
        getServer().getPluginManager().registerEvents(armorStandHandler, this);

        // Registrar animaciones predeterminadas
        registerDefaultAnimations();
    }

    private void registerDefaultAnimations() {
        animationManager.registerAnimation("victory", new VictoryAnimation());
        animationManager.registerAnimation("defeat", new DefeatAnimation());
        animationManager.registerAnimation("spawn", new SpawnAnimation());
        animationManager.registerAnimation("portal", new PortalAnimation());
        animationManager.registerAnimation("explosion", new ExplosionAnimation());
        animationManager.registerAnimation("helix", new HelixAnimation());
        animationManager.registerAnimation("wings", new WingsAnimation());
    }

    public void playAnimation(String animationName, Location location) {
        animationManager.playAnimation(animationName, location);
    }

    public void playAnimation(String animationName, Location location, Player player) {
        animationManager.playAnimation(animationName, location, player);
    }

    public static AnimationsAPI getInstance() {
        return instance;
    }

    public AnimationManager getAnimationManager() {
        return animationManager;
    }

    public ArmorStandHandler getArmorStandHandler() {
        return armorStandHandler;
    }

    public ParticleUtils getParticleUtils() {
        return particleUtils;
    }
}