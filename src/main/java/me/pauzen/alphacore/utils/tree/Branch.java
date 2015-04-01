/*
 *  Created by Filip P. on 3/22/15 12:13 AM.
 */

package me.pauzen.alphacore.utils.tree;

import java.util.HashMap;
import java.util.Map;

public class Branch<T> {

    private Map<T, Branch> branchMap = new HashMap<>();

    private T value;

    public Branch(T value) {
        this.value = value;
    }

    public void addBranch(Branch<T> branch) {
        branchMap.put(branch.value, branch);
    }

    public Branch getBranch(T value) {
        return branchMap.get(value);
    }

    public T getValue() {
        return value;
    }

    public Map<T, Branch> getBranchMap() {
        return branchMap;
    }
}