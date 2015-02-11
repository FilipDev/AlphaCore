/*
 *  Created by Filip P. on 2/7/15 11:08 PM.
 */

package me.pauzen.alphacore.players.data;

import me.pauzen.alphacore.players.CorePlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tracker {

    private int value = 0;
    private String id;

    private Map<Integer, List<Milestone>> mileStones = new HashMap<>();

    public Tracker(String id, int initialValue) {
        this.id = id;
        this.value = initialValue;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void addValue(int value) {
        this.value += value;
    }

    public void subtractValue(int points) {
        value -= points;
    }

    public int getValue() {
        return this.value;
    }
    
    public void updateValue(int newValue) {
        List<Milestone> milestones = getMilestones(newValue);
        
        if (milestones == null) {
            return;
        }
        
        milestones.forEach(Milestone::onReach);
    }
    
    public List<Milestone> checkAndGetMilestones(int value) {
        checkMilestoneListExists(value);
        
        return getMilestones(value);
    }

    public List<Milestone> getMilestones(int value) {
        return mileStones.get(value);
    }

    public void addTracker(CorePlayer corePlayer) {
        corePlayer.getTrackers().put(id, this);
    }
    
    private void checkMilestoneListExists(int milestone) {
        if (mileStones.get(milestone) == null) {
            mileStones.put(milestone, new ArrayList<>());
        }
    }
    
    public void addMilestone(Milestone milestone) {
        checkAndGetMilestones(milestone.getValue()).add(milestone);
    }
    
    
}
