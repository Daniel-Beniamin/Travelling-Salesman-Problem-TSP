package Graphs;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/**
 * The GraphGenerator class is used to generate and save complete graphs.
 */
public class GraphGenerator {

    /**
     * Generates a complete graph with a specified number of cities.
     *
     * @param numCities -> The number of cities in the graph
     * @return -> A Graph object representing the complete graph generated
     */
    public static Graph generateCompleteGraph(int numCities) {
        // Check if the number of cities is positive
        if (numCities <= 0) {
            System.err.println("Number of cities must be positive.");
            return null;
        }

        // Create a new Graph object with the specified number of cities
        Graph graph = new Graph(numCities);
        Random random = new Random();

        // Iterate over each pair of cities and add an edge with a random distance
        for (int i = 0; i < numCities; i++) {
            for (int j = i + 1; j < numCities; j++) {
                int distance = random.nextInt(100) + 1; // Generate a random distance between 1 and 100
                graph.addEdge(i, j, distance); // Add the edge to the graph
            }
        }

        return graph; // Return the generated graph
    }

    /**
     * Saves the graph to a specified file.
     *
     * @param graph -> The Graph object to be saved
     * @param filename -> The name of the file where the graph will be saved
     */
    public static void saveGraphToFile(Graph graph, String filename) {
        // Check if the graph object is null
        if (graph == null) {
            System.err.println("Graph is null. Cannot save to file.");
            return;
        }

        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(+graph.getNumCities() + "\n"); // Write the number of cities to the file

            // Iterate over each pair of cities and write the edge information to the file
            for (int i = 0; i < graph.getNumCities(); i++) {
                for (int j = 0; j < graph.getNumCities(); j++) {
                    if (i != j) {
                        writer.write(i + " " + j + " " + graph.getDistance(i, j) + "\n"); // Write edge information
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Failed to save graph to file: " + filename); // Handling IO exception
            e.printStackTrace();
        }
    }

    /**
     * Reads a graph from a specified file.
     *
     * @param filename -> The name of the file from which the graph will be read
     * @return -> A Graph object representing the graph read from the file
     */
    public static Graph readGraphFromFile(String filename) {
        Graph graph = null;

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line = reader.readLine();
            // Check if the file is empty or does not contain the number of cities
            if (line == null || line.trim().isEmpty()) {
                System.err.println("The file is empty or does not contain the number of cities.");
                return null;
            }

            int numCities = Integer.parseInt(line.trim()); // Parse the number of cities
            graph = new Graph(numCities); // Create a new Graph object

            // Read each subsequent line to get the edges and their distances
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue; // Skip empty lines
                }

                String[] parts = line.split("\\s+"); // Split the line by whitespace
                // Check if the line has the correct format (from, to, distance)
                if (parts.length != 3) {
                    System.err.println("Incorrect line format: " + line);
                    continue;
                }

                try {
                    int from = Integer.parseInt(parts[0]); // Parse the starting city
                    int to = Integer.parseInt(parts[1]); // Parse the ending city
                    int distance = Integer.parseInt(parts[2]); // Parse the distance
                    graph.addEdge(from, to, distance); // Add the edge to the graph
                } catch (NumberFormatException e) {
                    System.err.println("Failed to parse line: " + line); // Handling parsing exception
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            System.err.println("Failed to read graph from file: " + filename); // Handling IO exception
            e.printStackTrace();
        }

        return graph; // Return the graph read from the file
    }
}

