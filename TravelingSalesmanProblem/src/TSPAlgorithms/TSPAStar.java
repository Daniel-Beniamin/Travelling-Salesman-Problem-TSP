package TSPAlgorithms;

import Graphs.Graph;
import java.util.*;

/**
 * The TSPAStar class implements the A* algorithm to solve the Traveling Salesman Problem (TSP)
 */
public class TSPAStar {
    private final Graph graph; // The graph on which the search will be performed

    /**
     * Constructor that initializes the A* algorithm with the specified graph.
     *
     * @param graph -> The graph representing the cities and distances
     */
    public TSPAStar(Graph graph) {
        this.graph = graph;
    }

    /**
     * Method to search for the minimum cost path using A* algorithm
     *
     * @param start -> The starting city index
     * @return -> A Path object representing the found path with minimum cost, or null if no path is found
     */
    public Path search(int start) {
        // Priority queue to manage the paths based on their f-cost (priority)
        PriorityQueue<Path> pq = new PriorityQueue<>(Comparator.comparingInt(Path::getFCost));
        pq.add(new Path(start, graph)); // Add the initial path starting from 'start'

        // Loop until the priority queue is empty
        while (!pq.isEmpty()) {
            // Extract the path with the minimum f-cost
            Path path = pq.poll();

            // Check if all cities have been visited
            if (path.visitedAll(graph.getNumCities())) {
                // Add the return trip to the start city
                path.add(start, graph.getDistance(path.getLast(), start), graph);
                return path;
            }

            // Explore neighboring cities
            for (int i = 0; i < graph.getNumCities(); i++) {
                if (!path.contains(i) && graph.getDistance(path.getLast(), i) > 0) {
                    // Create a new path by extending the current path
                    Path newPath = new Path(path);
                    newPath.add(i, graph.getDistance(path.getLast(), i), graph);
                    pq.add(newPath); // Add the new path to the priority queue
                }
            }
        }

        // If no path is found, return null
        return null;
    }

    /**
     * Inner class to represent a path in the graph
     */
    public static class Path {
        private final List<Integer> nodes; // List of nodes (cities) in the path
        private int gCost; // Actual cost (g) of the path
        private int hCost; // Heuristic cost (h) to reach the destination
        private int fCost; // Total cost (f = g + h) of the path

        /**
         * Constructor to create a new path with a single start node.
         *
         * @param start -> The starting city index
         * @param graph -> The graph representing the cities and distances
         */
        public Path(int start, Graph graph) {
            nodes = new ArrayList<>(); // Initialize the list of nodes
            nodes.add(start); // Add the start city
            gCost = 0; // Initial gCost is 0
            hCost = heuristic(start, graph, new HashSet<>(nodes)); // Calculate the heuristic cost
            fCost = gCost + hCost; // Calculate the total cost
        }

        /**
         * Constructor to copy an existing path.
         *
         * @param path -> The path to be copied
         */
        public Path(Path path) {
            nodes = new ArrayList<>(path.nodes); // Copy the list of nodes
            gCost = path.gCost; // Copy the actual cost
            hCost = path.hCost; // Copy the heuristic cost
            fCost = path.fCost; // Copy the total cost
        }

        /**
         * Method to add a node and its cost to the path.
         *
         * @param node -> The node (city) to be added
         * @param distance -> The distance from the last node to the new node
         * @param graph -> The graph representing the cities and distances
         */
        public void add(int node, int distance, Graph graph) {
            nodes.add(node); // Add the new node to the list
            gCost += distance; // Update the actual cost
            hCost = heuristic(node, graph, new HashSet<>(nodes)); // Recalculate the heuristic cost
            fCost = gCost + hCost; // Recalculate the total cost
        }

        /**
         * Method to check if the path contains a specific node.
         *
         * @param node -> The node to be checked
         * @return : True if the node is in the path, False otherwise
         */
        public boolean contains(int node) {
            return nodes.contains(node); // Check if the node is in the list
        }

        /**
         * Method to check if all nodes have been visited.
         *
         * @param numCities -> The total number of cities in the graph
         * @return : True if all nodes have been visited, False otherwise
         */
        public boolean visitedAll(int numCities) {
            return nodes.size() == numCities; // Check if the number of visited nodes equals the number of cities
        }

        /**
         * Method to get the last node in the path
         *
         * @return -> The last node (city) in the path
         */
        public int getLast() {
            return nodes.get(nodes.size() - 1); // Return the last node in the list
        }

        /**
         * Method to get the total cost (f-cost) of the path
         *
         * @return : The total cost (f-cost) of the path
         */
        public int getFCost() {
            return fCost; // Return the total cost
        }

        /**
         * Method to get the actual cost (g-cost) of the path
         *
         * @return : The actual cost (g-cost) of the path
         */
        public int getGCost() {
            return gCost; // Return the actual cost
        }

        /**
         * Method to get the list of nodes in the path
         *
         * @return : The list of nodes in the path
         */
        public List<Integer> getNodes() {
            return nodes; // Return the list of nodes
        }

        /**
         * Heuristic function: calculates the cost of the Minimum Spanning Tree (MST)
         * plus the connection costs to unvisited nodes.
         *
         * @param current -> The current node (city)
         * @param graph -> The graph representing the cities and distances
         * @param visited -> The set of visited nodes
         * @return : The heuristic cost
         */
        private int heuristic(int current, Graph graph, Set<Integer> visited) {
            int V = graph.getNumCities(); // Total number of cities
            Set<Integer> unvisited = new HashSet<>(); // Set of unvisited nodes

            // Add unvisited nodes to the set
            for (int i = 0; i < V; i++) {
                if (!visited.contains(i)) {
                    unvisited.add(i);
                }
            }

            // If all nodes are visited, return the distance to the start city
            if (unvisited.isEmpty()) {
                return graph.getDistance(current, 0);
            }

            // Calculate the cost of the MST for the unvisited nodes
            int mstCost = calculateMST(unvisited, graph);

            int minToUnvisited = Integer.MAX_VALUE; // Minimum distance from current node to any unvisited node
            int minFromUnvisited = Integer.MAX_VALUE; // Minimum distance from any unvisited node to the start node

            // Find the minimum distances
            for (int u : unvisited) {
                minToUnvisited = Math.min(minToUnvisited, graph.getDistance(current, u));
                minFromUnvisited = Math.min(minFromUnvisited, graph.getDistance(u, 0));
            }

            // Return the heuristic cost
            return mstCost + minToUnvisited + minFromUnvisited;
        }

        /**
         * Calculates the cost of the Minimum Spanning Tree (MST) using Prim's algorithm
         *
         * @param unvisited -> The set of unvisited nodes
         * @param graph -> The graph representing the cities and distances
         * @return : The cost of the MST
         */
        private int calculateMST(Set<Integer> unvisited, Graph graph) {
            if (unvisited.isEmpty()) {
                return 0;
            }

            PriorityQueue<Edge> pq = new PriorityQueue<>(Comparator.comparingInt(e -> e.weight)); // Priority queue for edges
            int totalCost = 0; // Total cost of the MST
            Set<Integer> inMST = new HashSet<>(); // Set of nodes included in the MST
            int start = unvisited.iterator().next(); // Start node for MST
            inMST.add(start);

            // Add edges from the start node to the priority queue
            for (int u : unvisited) {
                if (u != start) {
                    pq.add(new Edge(start, u, graph.getDistance(start, u)));
                }
            }

            // While there are nodes not included in the MST
            while (inMST.size() < unvisited.size()) {
                Edge edge = pq.poll(); // Get the edge with the minimum weight
                if (inMST.contains(edge.to)) {
                    continue; // Skip if the destination node is already in the MST
                }
                totalCost += edge.weight; // Add the weight to the total cost
                inMST.add(edge.to); // Add the destination node to the MST

                // Add new edges from the newly added node to the priority queue
                for (int u : unvisited) {
                    if (!inMST.contains(u)) {
                        pq.add(new Edge(edge.to, u, graph.getDistance(edge.to, u)));
                    }
                }
            }

            return totalCost; // Return the total cost of the MST
        }

        /**
         * Inner class to represent an edge in the MST graph.
         */
        private static class Edge {
            int from; // Starting node of the edge
            int to; // Ending node of the edge
            int weight; // Weight (distance) of the edge

            /**
             * Constructor to create a new edge.
             *
             * @param from The starting node
             * @param to The ending node
             * @param weight The weight of the edge
             */
            public Edge(int from, int to, int weight) {
                this.from = from;
                this.to = to;
                this.weight = weight;
            }
        }
    }
}

