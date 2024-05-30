package TSPAlgorithms;

import Graphs.Graph;

import java.util.ArrayList;
import java.util.List;

/**
 * The TSPDFS class implements the Depth-First Search (DFS) algorithm for solving the Traveling Salesman Problem (TSP)
 */
public class TSPDFS {
    private final Graph graph; // The graph representing the cities and distances
    private final boolean[] visited; // Array to track visited cities
    private int bestCost; // Minimum cost found
    private List<Integer> bestPath; // Best path found

    /**
     * Constructor for TSPDFS.
     *
     * @param graph -> The graph on which the algorithm will run
     */
    public TSPDFS(Graph graph) {
        this.graph = graph;
        if (graph == null) {
            System.err.println("Graph cannot be null");
            this.bestCost = Integer.MAX_VALUE;
            this.bestPath = new ArrayList<>();
            this.visited = new boolean[0];
        } else {
            this.visited = new boolean[graph.getNumCities()]; // Initialize the visited array
            this.bestCost = Integer.MAX_VALUE; // Initialize the best cost to a very high value
            this.bestPath = new ArrayList<>(); // Initialize the best path as an empty list
        }
    }

    /**
     * Starts the depth-first search from a specified city.
     *
     * @param start -> The starting city index
     */
    public void search(int start) {
        if (start < 0 || start >= graph.getNumCities()) {
            System.err.println("Invalid start city index");
            return;
        }
        List<Integer> path = new ArrayList<>(); // Initialize the current path as an empty list
        path.add(start); // Add the start city to the path
        visited[start] = true; // Mark the start city as visited
        dfs(start, path, 0); // Start the DFS
        visited[start] = false; // Unmark the start city after DFS completes
    }

    /**
     * Recursive function for performing depth-first search.
     *
     * @param current -> The current city index
     * @param path -> The current path of cities
     * @param cost -> The current cost of the path
     */
    private void dfs(int current, List<Integer> path, int cost) {
        // If all cities have been visited, check the return cost to the start city
        if (path.size() == graph.getNumCities()) {
            cost += graph.getDistance(current, path.get(0)); // Add the return cost to the start city
            if (cost < bestCost) { // Check if the current path cost is better than the best found so far
                bestCost = cost; // Update the best cost
                bestPath = new ArrayList<>(path); // Update the best path
                bestPath.add(path.get(0)); // Add the start city to the end to complete the cycle
            }
            return;
        }

        // Explore all the cities
        for (int i = 0; i < graph.getNumCities(); i++) {
            if (!visited[i] && graph.getDistance(current, i) > 0) { // Check if the city is not visited and there's a path
                visited[i] = true; // Mark the city as visited
                path.add(i); // Add the city to the path
                dfs(i, path, cost + graph.getDistance(current, i)); // Recursively perform DFS
                visited[i] = false; // Unmark the city
                path.remove(path.size() - 1); // Remove the city from the path
            }
        }
    }

    /**
     * Gets the best path found by the algorithm.
     *
     * @return : The list of cities representing the best path
     */
    public List<Integer> getBestPath() {
        if (bestPath.isEmpty()) {
            System.err.println("No path found. Please run the search method first.");
            return new ArrayList<>();
        }
        return bestPath;
    }

    /**
     * Gets the minimum cost found by the algorithm
     *
     * @return : The minimum cost
     */
    public int getBestCost() {
        if (bestCost == Integer.MAX_VALUE) {
            System.err.println("No path found. Please run the search method first.");
            return Integer.MAX_VALUE;
        }
        return bestCost;
    }
}
