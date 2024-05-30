package TSPAlgorithms;

import Graphs.Graph;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * The TSPUniformCost class implements the Uniform Cost Search algorithm for solving the Traveling Salesman Problem (TSP)
 */
public class TSPUniformCost {
    private final Graph graph; // The graph on which the search will be performed

    /**
     * Constructor to initialize the Uniform Cost Search algorithm with the specified graph.
     *
     * @param graph -> The graph on which the algorithm will run
     */
    public TSPUniformCost(Graph graph) {
        this.graph = graph;
        if (graph == null) {
            System.err.println("Graph cannot be null");
        }
    }

    /**
     * Method to search for the minimum cost path
     *
     * @param start -> The starting city index
     * @return : The best path found
     */
    public Path search(int start) {
        if (start < 0 || start >= graph.getNumCities()) {
            System.err.println("Invalid start city index");
            return null;
        }

        PriorityQueue<Path> pq = new PriorityQueue<>(Comparator.comparingInt(Path::getCost)); // Priority queue for paths
        pq.add(new Path(start)); // Add the start path

        Path bestPath = null;

        while (!pq.isEmpty()) {
            Path path = pq.poll(); // Extract the path with the minimum cost

            if (path.visitedAll(graph.getNumCities())) {
                path.add(start, graph.getDistance(path.getLast(), start)); // Add the return path to the start city
                if (bestPath == null || path.getCost() < bestPath.getCost()) {
                    bestPath = path; // Update the best path found so far
                }
            } else {
                for (int i = 0; i < graph.getNumCities(); i++) {
                    if (!path.contains(i) && graph.getDistance(path.getLast(), i) > 0) {
                        Path newPath = new Path(path);
                        newPath.add(i, graph.getDistance(path.getLast(), i)); // Add a new node to the path
                        pq.add(newPath); // Add the new path to the queue
                    }
                }
            }
        }
        return bestPath; // Return the best path found
    }

    /**
     * The Path class represents a path in the TSP problem
     */
    public static class Path {
        private final List<Integer> nodes; // List of nodes (cities) in the path
        private int cost; // Total cost of the path

        /**
         * Constructor for a new path with a single start node
         *
         * @param start -> The starting city index
         */
        public Path(int start) {
            if (start < 0) {
                System.err.println("Invalid start city index");
                this.nodes = new ArrayList<>();
                this.cost = 0;
                return;
            }
            this.nodes = new ArrayList<>();
            this.nodes.add(start);
            this.cost = 0;
        }

        /**
         * Constructor to copy an existing path
         *
         * @param path -> The existing path to copy
         */
        public Path(Path path) {
            if (path == null) {
                System.err.println("Path cannot be null");
                this.nodes = new ArrayList<>();
                this.cost = 0;
                return;
            }
            this.nodes = new ArrayList<>(path.nodes);
            this.cost = path.cost;
        }

        /**
         * Method to add a node and its distance to the path
         *
         * @param node -> The node (city) to add
         * @param distance -> The distance from the last node to the new node
         */
        public void add(int node, int distance) {
            if (node < 0 || distance < 0) {
                System.err.println("Node and distance must be non-negative");
                return;
            }
            this.nodes.add(node);
            this.cost += distance;
        }

        /**
         * Method to check if a node is in the path
         *
         * @param node -> The node to check
         * @return : True if the node is in the path, False otherwise
         */
        public boolean contains(int node) {
            return this.nodes.contains(node);
        }

        /**
         * Method to check if all nodes have been visited.
         *
         * @param numCities -> The total number of cities in the graph
         * @return : True if all nodes have been visited, False otherwise
         */
        public boolean visitedAll(int numCities) {
            if (numCities <= 0) {
                System.err.println("Number of cities must be positive");
                return false;
            }
            return this.nodes.size() == numCities;
        }

        /**
         * Method to get the last node in the path.
         *
         * @return : The last node in the path
         */
        public int getLast() {
            if (this.nodes.isEmpty()) {
                System.err.println("Path is empty");
                return -1;
            }
            return this.nodes.get(this.nodes.size() - 1);
        }

        /**
         * Method to get the total cost of the path.
         *
         * @return The total cost of the path
         */
        public int getCost() {
            return this.cost;
        }

        /**
         * Method to get the list of nodes in the path.
         *
         * @return The list of nodes in the path
         */
        public List<Integer> getNodes() {
            return new ArrayList<>(nodes);
        }
    }
}
