package com.ubb.legoshop.scheduler.model.management;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DirectedGraph {

    // for each noe, save the list of adjacent nodes
    private final Map<String, List<String>> graphMap = new HashMap<>();

    public void addNodeIfNotPresent(String node) {
        graphMap.putIfAbsent(node, new LinkedList<>());
    }

    public void addEdge(String sourceNode, String destNode) {
        graphMap.get(sourceNode).add(destNode);
    }

    public void removeNode(String node) {
        graphMap.remove(node);
    }

    public boolean isCyclic() {
        // Mark all the vertices as not visited and not part of recursion stack
        Map<String, Boolean> visited = new HashMap<>();
        Map<String, Boolean> recStack = new HashMap<>();
        for (String node : graphMap.keySet()) {
            visited.put(node, false);
            recStack.put(node, false);
        }

        // Call the recursive helper function to detect cycle in different DFS trees
        for (String node : graphMap.keySet()) {
            if (isCyclicUtil(node, visited, recStack)) {
                return true;
            }
        }

        return false;
    }

    private boolean isCyclicUtil(String node, Map<String, Boolean> visited, Map<String, Boolean> recStack) {
        // Mark the current node as visited and part of recursion stack
        if (recStack.get(node)) {
            return true;
        }
        if (visited.get(node)) {
            return false;
        }

        visited.put(node, true);
        recStack.put(node, true);

        List<String> adjacentNodes = graphMap.get(node);
        for (String neighbour : adjacentNodes) {
            if (isCyclicUtil(neighbour, visited, recStack)) {
                return true;
            }
        }

        recStack.put(node, false);

        return false;
    }
}
