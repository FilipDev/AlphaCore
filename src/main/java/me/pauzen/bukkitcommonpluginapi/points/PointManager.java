package me.pauzen.bukkitcommonpluginapi.points;

import me.pauzen.bukkitcommonpluginapi.fake.FakeMyPlayer;
import me.pauzen.bukkitcommonpluginapi.players.MyPlayer;

import java.util.TreeSet;

public class PointManager {
    
    private static PointManager pointManager;
    
    public static void registerManager() {
        pointManager = new PointManager();
    }
    
    public static PointManager getPointManager() {
        return pointManager;
    }

    private TreeSet<MyPlayer> sortedPoints = new TreeSet<>((Object o1, Object o2) -> {
            MyPlayer myPlayer1 = (MyPlayer) o1, myPlayer2 = (MyPlayer) o2;
            int difference = myPlayer1.getPoints() - myPlayer2.getPoints();
            return difference == 0 ? 1 : difference;
    });
    
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
