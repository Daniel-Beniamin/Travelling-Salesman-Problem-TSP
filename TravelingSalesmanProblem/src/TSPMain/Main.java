package TSPMain;

import Graphs.*;
import TSPAlgorithms.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        boolean exit = false;
        while (!exit) {
            System.out.println("Select an algorithm to test:");
            System.out.println("1. DFS");
            System.out.println("2. Uniform Cost Search");
            System.out.println("3. A*");
            System.out.println("0. Exit");
            int option = scanner.nextInt();

            switch (option) {
                case 1:
                    testAlgorithm("DFS", scanner);
                    break;
                case 2:
                    testAlgorithm("Uniform Cost Search", scanner);
                    break;
                case 3:
                    testAlgorithm("A*", scanner);
                    break;
                case 0:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid option. Please choose a valid option.");
            }
        }
    }

    private static void testAlgorithm(String algorithm, Scanner scanner) {
        System.out.println("Do you want to test a trivial graph from file, a complex graph from file, or generate a random complex graph?");
        System.out.println("1. Trivial graph from file");
        System.out.println("2. Complex graph 1 from file");
        System.out.println("3. Complex graph 2 from file");
        System.out.println("4. Random complex graph");
        int choice = scanner.nextInt();

        Graph graph;
        switch (choice) {
            case 1:
                graph = GraphGenerator.readGraphFromFile("src\\InputData\\trivialgraph.txt");
                break;
            case 2:
                graph = GraphGenerator.readGraphFromFile("src\\InputData\\complexgraph1.txt");
                break;
            case 3:
                graph = GraphGenerator.readGraphFromFile("src\\InputData\\complexgraph2.txt");
                break;
            case 4:
                System.out.println("Enter the number of cities for the complex graph:");
                int numCities = scanner.nextInt();
                graph = GraphGenerator.generateCompleteGraph(numCities);
                break;
            default:
                System.out.println("Invalid choice. Exiting...");
                return;
        }

        // Save the graph to "graph.txt"
        GraphGenerator.saveGraphToFile(graph, "src\\OutputData\\graph.txt");

        long startTime = System.currentTimeMillis();
        switch (algorithm) {
            case "DFS":
                testDFS(graph);
                break;
            case "Uniform Cost Search":
                testUniformCostSearch(graph);
                break;
            case "A*":
                testAStar(graph);
                break;
            default:
                System.out.println("Invalid algorithm.");
        }
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        System.out.println("Execution time: " + executionTime + " milliseconds");
    }

    private static void testDFS(Graph graph) {
        TSPDFS dfs = new TSPDFS(graph);
        dfs.search(0); // Start the search from city 0
        System.out.println("DFS: Best path: " + dfs.getBestPath());
        System.out.println("DFS: Minimum cost: " + dfs.getBestCost());
    }

    private static void testUniformCostSearch(Graph graph) {
        TSPUniformCost ucs = new TSPUniformCost(graph);
        TSPUniformCost.Path ucsPath = ucs.search(0); // Start the search from city 0
        if (ucsPath != null) {
            System.out.println("UCS: Best path: " + ucsPath.getNodes());
            System.out.println("UCS: Minimum cost: " + ucsPath.getCost());
        }
    }

    private static void testAStar(Graph graph) {
        TSPAStar aStar = new TSPAStar(graph);
        TSPAStar.Path aStarPath = aStar.search(0); // Start the search from city 0
        if (aStarPath != null) {
            System.out.println("A*: Best path: " + aStarPath.getNodes());
            System.out.println("A*: Minimum cost: " + aStarPath.getGCost());
        }
    }
}
