package me.pauzen.bukkitcommonpluginapi.events;

import me.pauzen.bukkitcommonpluginapi.players.MyPlayer;

public class ToggleGodmodeEvent extends MyCallableEvent {
    
    private MyPlayer player;
    private boolean toggled;

    public ToggleGodmodeEvent(MyPlayer player, boolean toggled) {
        this.player = player;
        this.toggled = toggled;
    }

    public MyPlayer getPlayer() {
        return player;
    }

    public boolean isToggled() {
        return toggled;
    }
}
