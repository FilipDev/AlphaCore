/*
 *  Created by Filip P. on 2/2/15 11:16 PM.
 */

package me.pauzen.alphacore.points;

import me.pauzen.alphacore.fake.FakeCorePlayer;
import me.pauzen.alphacore.players.CorePlayer;
import me.pauzen.alphacore.utils.misc.Todo;
import me.pauzen.alphacore.utils.reflection.Nullifiable;
import me.pauzen.alphacore.utils.reflection.Nullify;

import java.util.TreeSet;

@Todo("Allow use for any Tracker.")
public class TrackerLeaderboard implements Nullifiable {

    @Nullify
    private static TrackerLeaderboard manager;

    public static void registerManager() {
        manager = new TrackerLeaderboard();
    }

    public static TrackerLeaderboard getManager() {
        return manager;
    }

    private TreeSet<CorePlayer> sortedPoints = new TreeSet<>((o1, o2) -> {
        int difference = o1 - o2.getPoints();
        return difference == 0 ? 1 : difference;
    }
    );

    public TreeSet<CorePlayer> getArrangedPlayers() {
        return sortedPoints;
    }

    private TrackerLeaderboard() {
    }

    public void update() {
        FakeCorePlayer fakeMyPlayer = new FakeCorePlayer(null);
        sortedPoints.add(fakeMyPlayer);
        sortedPoints.remove(fakeMyPlayer);
    }

}
