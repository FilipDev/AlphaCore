package me.pauzen.bukkitcommonpluginapi.listeners;

import me.pauzen.bukkitcommonpluginapi.abilities.Ability;
import me.pauzen.bukkitcommonpluginapi.players.MyPlayer;
import me.pauzen.bukkitcommonpluginapi.players.PlayerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamageEventListener extends ListenerImplementation {
    
    @EventHandler
    public void onEntityDamage(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player)) {
            return;
        }
        
        Player player = (Player) e.getEntity();
        MyPlayer myPlayer = PlayerManager.getPlayerManager().getWrapper(player);

        if (myPlayer.isActivated(Ability.GOD)) {
            e.setCancelled(true);
        }
    }
}
