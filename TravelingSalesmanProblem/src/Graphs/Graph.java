package Graphs;

/**
 * The Graph class represents an undirected graph used for the Traveling Salesman Problem (TSP).
 * The graph is represented by a distance matrix between cities.
 */
public class Graph {
    private final int[][] distances; // The distance matrix between cities
    private final int numCities; // The number of cities in the graph

    /**
     * Constructor to initialize the graph with a specified number of cities.
     *
     * @param numCities -> The number of cities in the graph
     */
    public Graph(int numCities) {
        this.numCities = numCities; // Set the number of cities
        distances = new int[numCities][numCities]; // Initialize the distance matrix with numCities x numCities
        // Initially, all distances are 0 (default for int)
    }

    /**
     * Adds an edge between two cities with a specified distance.
     * Since the graph is undirected, the distance is set in both directions.
     *
     * @param from -> The starting city
     * @param to The -> destination city
     * @param distance  -> The distance between the two cities
     */
    public void addEdge(int from, int to, int distance) {
        distances[from][to] = distance; // Set the distance from city 'from' to city 'to'
        distances[to][from] = distance; // Set the distance from city 'to' to city 'from' (undirected graph)
    }

    /**
     * Gets the distance between two cities.
     *
     * @param from -> The starting city
     * @param to -> The destination city
     * @return The distance between the specified cities
     */
    public int getDistance(int from, int to) {
        return distances[from][to]; // Return the distance between cities 'from' and 'to'
    }

    /**
     * Gets the number of cities in the graph.
     */
    public int getNumCities() {
        return numCities; // Return the total number of cities in the graph
    }
}
