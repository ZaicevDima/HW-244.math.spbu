package group244.zaicev.com;


import java.util.ArrayList;
import java.util.Scanner;

/**
 * Main class
 */
public class Main {

    /**
     * dialog window for entering operation system names
     * @param in Scanner
     * @return operation system names in String
     */
    private static ArrayList<String> enterNameOS(Scanner in) {
        System.out.println("Enter the number of operation systems: ");
        int amountOS = in.nextInt();
        ArrayList<String> OSList = new ArrayList<String>();
        for (int i = 0; i < amountOS; i++) {
            System.out.println("Enter the name of operation system Windows, Linux or MacOS:");
            String name = in.next();
            while (!isCorrectName(name)) {
                System.out.println("Enter the correct name of operation system:");
                name = in.next();
            }
            OSList.add(name);
        }
        return OSList;

    }

    /**
     * dialog window for entering computer connections
     * @param in Scanner
     * @return computer connections in String
     */
    private static int[][] enterComputerConnections(Scanner in, int amountConnection, int amountComputer) {
        int[][] connections = new int[amountConnection][2];
        for (int i = 0; i < amountConnection; i++) {
            System.out.println("Enter the numbers of the first and second computers: ");
            connections[i][0] = in.nextInt();
            connections[i][1] = in.nextInt();
            while (!isCorrectConnection(connections[i][0], amountComputer) && isCorrectConnection(connections[i][1], amountComputer)) {
                System.out.println("Enter the correct numbers of the first and second computers: ");
                connections[i][0] = in.nextInt();
                connections[i][1] = in.nextInt();
            }
        }
        return connections;
    }

    /**
     * Checks the correctness of the operation system name
     * @param name operation system
     */
    private static boolean isCorrectName(String name) {
        return name.equals("Windows") || name.equals("Linux") || name.equals("MacOS");
    }

    /**
     * Checks the correctness of the connection
     * @param computerNumber computer number
     * @param amountComputer amount computer
     */
    private static boolean isCorrectConnection(int computerNumber, int amountComputer) {
        return (computerNumber > 0) && (computerNumber <= amountComputer);
    }

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        ArrayList<String> OSList = enterNameOS(in);

        System.out.println("Enter the number of connection systems: ");
        int amountConnection = in.nextInt();

        int[][] connections = enterComputerConnections(in, amountConnection, OSList.size());
        System.out.println("enter infect computer: ");
        int infect = in.nextInt();

        Network network = new Network(OSList, connections, infect, new InfectRandom());
        System.out.println(network.getTable());
        System.out.println(network.getStatus());

        for (int i = 0; i < 30; i++) {
            network.timeStep();
            System.out.println(network.getStatus());
        }
    }
}