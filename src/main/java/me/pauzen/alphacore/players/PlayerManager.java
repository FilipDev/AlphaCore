/*
 *  Created by Filip P. on 2/2/15 11:16 PM.
 */

package me.pauzen.alphacore.players;

import me.pauzen.alphacore.core.managers.ModuleManager;
import me.pauzen.alphacore.listeners.ListenerImplementation;
import me.pauzen.alphacore.updater.UpdateEvent;
import me.pauzen.alphacore.updater.UpdateType;
import me.pauzen.alphacore.utils.loading.LoadPriority;
import me.pauzen.alphacore.utils.loading.Priority;
import me.pauzen.alphacore.utils.misc.Tickable;
import me.pauzen.alphacore.utils.reflection.Nullify;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.*;

@Priority(LoadPriority.FIRST)
public class PlayerManager extends ListenerImplementation implements ModuleManager<PlayerWrapper> {

    @Nullify
    private static PlayerManager manager;

    @Nullify
    private Map<UUID, Map<Class<? extends PlayerWrapper>, PlayerWrapper>> players = new HashMap<>();

    @Nullify
    private List<Class<? extends PlayerWrapper>> playerWrappers = new ArrayList<>();

    public static PlayerManager getManager() {
        return manager;
    }

    @Override
    public void onEnable() {
        addPlayerWrapper(CorePlayer.class);
        for (Player player : Bukkit.getOnlinePlayers()) {
            registerModule(new CorePlayer(player));
        }
    }

    public void addPlayerWrapper(Class<? extends PlayerWrapper> playerWrapper) {
        playerWrappers.add(playerWrapper);
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onPlayerJoin(PlayerJoinEvent e) {
        for (Class<? extends PlayerWrapper> playerWrapper : playerWrappers) {
            try {
                PlayerWrapper wrapper = playerWrapper.getConstructor(Player.class).newInstance(e.getPlayer());
                registerModule(wrapper);
                wrapper.load();
            } catch (ReflectiveOperationException e1) {
                e1.printStackTrace();
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerQuit(PlayerQuitEvent e) {
        for (Class<? extends PlayerWrapper> playerWrapper : playerWrappers) {
            destroyWrapper(e.getPlayer(), playerWrapper);
        }
    }
    
    @EventHandler
    public void onUpdate(UpdateEvent e) {
        if (e.getUpdateType() == UpdateType.TICK) {
            for (PlayerWrapper playerWrapper : getModules()) {
                if (playerWrapper instanceof Tickable) {
                    ((Tickable) playerWrapper).tick();
                }
            }
        }
    }
    
    public <T> T getWrapper(Player player, Class<T> playerClass) {
        return (T) players.get(player.getUniqueId()).get(playerClass);
    }

    public void destroyWrapper(Player player, Class<? extends PlayerWrapper> playerClass) {
        PlayerWrapper module = getWrapper(player, playerClass);
        if (module != null) {
            unregisterModule(module);
        }
    }

    @Override
    public void nullify() {
        for (Map.Entry<UUID, Map<Class<? extends PlayerWrapper>, PlayerWrapper>> uuidMapEntry : players.entrySet()) {
            for (Map.Entry<Class<? extends PlayerWrapper>, PlayerWrapper> classPlayerWrapperEntry : uuidMapEntry.getValue().entrySet()) {
                unregisterModule(classPlayerWrapperEntry.getValue());
            }
        }
        players.clear();
        ModuleManager.super.nullify();
    }

    @Override
    public String getName() {
        return "players";
    }

    @Override
    public Collection<PlayerWrapper> getModules() {
        List<PlayerWrapper> wrappers = new ArrayList<>(playerWrappers.size() * players.size());

        for (Map.Entry<UUID, Map<Class<? extends PlayerWrapper>, PlayerWrapper>> uuidMapEntry : players.entrySet()) {
            for (Map.Entry<Class<? extends PlayerWrapper>, PlayerWrapper> classPlayerWrapperEntry : uuidMapEntry.getValue().entrySet()) {
                wrappers.add(classPlayerWrapperEntry.getValue());
            }
        }

        return wrappers;
    }

    @Override
    public void registerModule(PlayerWrapper module) {
        players.putIfAbsent(module.getUniqueId(), new HashMap<>());
        players.get(module.getUniqueId()).put(module.getClass(), module);
    }

    @Override
    public void unregisterModule(PlayerWrapper module) {
        module.save();
        players.get(module.getUniqueId()).remove(module.getClass());
    }
}
