/*
 *  Created by Filip P. on 2/2/15 11:16 PM.
 */

package me.pauzen.alphacore.points;

import me.pauzen.alphacore.listeners.ListenerImplementation;
import me.pauzen.alphacore.places.Place;
import me.pauzen.alphacore.players.CorePlayer;
import me.pauzen.alphacore.players.data.trackers.Tracker;
import me.pauzen.alphacore.updater.UpdateEvent;
import me.pauzen.alphacore.updater.UpdateType;
import org.bukkit.event.EventHandler;


//TODO: REMOVE
public class TrackerDisplayer extends ListenerImplementation {

    private CorePlayer corePlayer;
    private Tracker    tracker;
    private Place  place;
    private int        oldLevel;

    @Deprecated()
    public TrackerDisplayer(Place place, CorePlayer corePlayer, Tracker tracker) {
        super();
        this.corePlayer = corePlayer;
        this.tracker = tracker;
        this.place = place;
        this.oldLevel = corePlayer.getPlayer().getLevel();
        corePlayer.getPlayer().setLevel(tracker.getValue());
    }

    public void update() {
        if (place != corePlayer.getCurrentPlace()) {
            revert();
            return;
        }
        corePlayer.getPlayer().setLevel(tracker.getValue());
    }

    public void revert() {
        corePlayer.getPlayer().setLevel(oldLevel);
    }

    @EventHandler
    public void onUpdate(UpdateEvent e) {
        if (e.getUpdateType() == UpdateType.SECOND) {
            CorePlayer.getCorePlayers().stream().filter(corePlayer -> corePlayer.getAttribute(TrackerDisplayer.class, "tracker_displayer") != null).forEach(corePlayer -> corePlayer.getAttribute(TrackerDisplayer.class, "tracker_displayer").update());
        }
    }
}
