/*
 *  Created by Filip P. on 2/2/15 11:16 PM.
 */

package me.pauzen.largeplugincore.points;

import me.pauzen.largeplugincore.fake.FakeMyPlayer;
import me.pauzen.largeplugincore.players.MyPlayer;

import java.util.Comparator;
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

    private TreeSet<MyPlayer> sortedPoints = new TreeSet<>(new Comparator() {
        @Override
        public int compare(Object o1, Object o2) {
            MyPlayer myPlayer1 = (MyPlayer) o1, myPlayer2 = (MyPlayer) o2;
            int difference = myPlayer1.getPoints().getValue() - myPlayer2.getPoints().getValue();
            return difference == 0 ? 1 : difference;
        }
    }
    );

    public TreeSet<MyPlayer> getArrangedPlayers() {
        return sortedPoints;
    }

    private PointManager() {
    }

    public void update() {
        FakeMyPlayer fakeMyPlayer = new FakeMyPlayer(null);
        sortedPoints.add(fakeMyPlayer);
        sortedPoints.remove(fakeMyPlayer);
    }

}
