# AnimationsAPI
Una API potente y flexible para crear animaciones con ArmorStands en Spigot/Paper. Perfecta para efectos visuales en tu servidor de Minecraft.

![Version](https://img.shields.io/badge/version-1.0.0-blue.svg)
![Spigot](https://img.shields.io/badge/Spigot-1.19+-green.svg)
![License](https://img.shields.io/badge/license-MIT-red.svg)

## 📋 Características

- ✨ 7 animaciones predefinidas
- 🛡️ Sistema de protección para ArmorStands
- 🎮 Fácil de implementar
- 🔧 API extensible
- 🎯 Sistema de eventos robusto
- 🌈 Efectos de partículas
- 📦 Sistema modular

## 🎮 Animaciones Disponibles

// Victoria
api.playAnimation("victory", location);

// Derrota
api.playAnimation("defeat", location);

// Spawneo
api.playAnimation("spawn", location);

// Portal
api.playAnimation("portal", location);

// Explosión
api.playAnimation("explosion", location);

// Hélice
api.playAnimation("helix", location);

// Alas
api.playAnimation("wings", location);

## 📥 Implementación Básica

```java
public class TuPlugin extends JavaPlugin {
    private AnimationsAPI animationsAPI;

    @Override
    public void onEnable() {
        // Obtener instancia de la API
        animationsAPI = AnimationsAPI.getInstance();
    }

    // Ejemplo de uso en un comando
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;

        // Reproducir diferentes animaciones
        Location loc = player.getLocation();
        
        switch(args[0].toLowerCase()) {
            case "victory":
                animationsAPI.playAnimation("victory", loc, player);
                break;
            case "portal":
                animationsAPI.playAnimation("portal", loc);
                break;
            case "wings":
                animationsAPI.playAnimation("wings", loc, player);
                break;
            case "explosion":
                animationsAPI.playAnimation("explosion", loc);
                break;
            case "helix":
                animationsAPI.playAnimation("helix", loc);
                break;
            case "spawn":
                animationsAPI.playAnimation("spawn", loc);
                break;
            case "defeat":
                animationsAPI.playAnimation("defeat", loc);
                break;
        }
        return true;
    }
}



#  Crear Animación Personalizada
public class MiAnimacion extends BaseAnimation {
    @Override
    public void play(Location location) {
        if (isPlaying) return;
        isPlaying = true;

        ArmorStand stand = plugin.getArmorStandHandler().createAnimationArmorStand(location);
        armorStands.add(stand);

        tasks.add(new BukkitRunnable() {
            double angle = 0;
            int ticks = 0;
            
            @Override
            public void run() {
                if (ticks >= 40) {
                    cleanup();
                    cancel();
                    return;
                }
                
                // Tu código de animación aquí
                angle += Math.PI / 8;
                stand.teleport(location.clone().add(
                    Math.cos(angle) * 0.5,
                    Math.sin(angle) * 0.5,
                    Math.sin(angle) * 0.5
                ));
                
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

// Registrar tu animación
api.getAnimationManager().registerAnimation("mianimacion", new MiAnimacion());
