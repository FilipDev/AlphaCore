/*
 *  Created by Filip P. on 2/2/15 11:16 PM.
 */

package me.pauzen.alphacore.points;

import me.pauzen.alphacore.fake.FakeCorePlayer;
import me.pauzen.alphacore.players.CorePlayer;

import java.util.TreeSet;

public class PointManager {

    private static PointManager manager;

    public static void registerManager() {
        manager = new PointManager();
    }

    public static void unregisterManager() {
        manager = null;
    }

    public static PointManager getManager() {
        return manager;
    }

    private TreeSet<CorePlayer> sortedPoints = new TreeSet<>((o1, o2) -> {
        int difference = o1.getPoints() - o2.getPoints();
        return difference == 0 ? 1 : difference;
    }
    );

    public TreeSet<CorePlayer> getArrangedPlayers() {
        return sortedPoints;
    }

    private PointManager() {
    }

    public void update() {
        FakeCorePlayer fakeMyPlayer = new FakeCorePlayer(null);
        sortedPoints.add(fakeMyPlayer);
        sortedPoints.remove(fakeMyPlayer);
    }

}
