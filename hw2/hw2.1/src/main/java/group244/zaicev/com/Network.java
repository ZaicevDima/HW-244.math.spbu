package group244.zaicev.com;

import java.util.ArrayList;

/** Network class. */
public class Network {
    private static ComputerRandom random;
    private boolean[][] matrix;
    private Computer[] computers;

    /**
     * Constructor
     * @param OSList operation systems in StringList
     * @param connections adjacency list
     * @param infect number first infect computer
     * @param random computer random
     */
    Network(ArrayList<String> OSList, int[][] connections, int infect, ComputerRandom random) {
        Network.random = random;
        computers = loadComputers(OSList, infect);
        matrix = loadMatrix(connections, OSList.size());
    }

    /**
     * New step
     */
    public void timeStep() {
        ArrayList<Computer> infectList = getInfectList();
        for (int i = 0; i < infectList.size(); i++) {
            infectList.get(i).tryInfect();
        }
    }

    /**
     * Get information about computers
     */
    public String getStatus() {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < computers.length; i++) {
            builder.append(i + 1);
            builder.append(") ");
            builder.append(computers[i].getOperationSystem());

            if (computers[i].isVirus()) {
                builder.append(" is infected");
            }
            builder.append("\n");
        }

        builder.append("\n");
        return builder.toString();
    }

    /**
     * Get network in table
     */
    public String getTable() {
        StringBuilder table = new StringBuilder();

        table.append(" ");
        for (int i = 0; i < matrix.length; i++) {
            table.append("    ");
            table.append(i + 1);
        }
        table.append("\n");

        for (int i = 0; i < matrix.length; i++) {
            table.append(i + 1);
            table.append("    ");

            for (int j = 0; j < matrix.length; j++) {
                table.append(matrix[i][j] ? 1 : 0);
                table.append("    ");
            }
            table.append("\n");
        }

        table.append("\n");
        return table.toString();
    }

    /**
     * Create array of computers
     * @param OSList information about OS
     * @param infect number first infect computer
     */
    private static Computer[] loadComputers(ArrayList<String> OSList, int infect) {
        Computer[] computers = new Computer[OSList.size()];

        for (int i = 0; i < OSList.size(); i++) {
            computers[i] = new Computer(OSList.get(i), random);
        }

        computers[infect - 1].infect();

        return computers;
    }

    /**
     * Create adjacency matrix
     * @param connections information about connection
     * @param size amount computer
     */
    private static boolean[][] loadMatrix(int[][] connections, int size) {
        boolean[][] matrix = new boolean[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = false;
            }
        }

        for (int[] connection : connections) {
            matrix[connection[0] - 1][connection[1] - 1] = true;
            matrix[connection[1] - 1][connection[0] - 1] = true;
        }


        return matrix;
    }

    /**
     * Create new list of infect computers
     */
    private ArrayList<Computer> getInfectList() {
        ArrayList<Computer> infectList = new ArrayList<>();

        for (int i = 0; i < computers.length; i++) {
            if (computers[i].isVirus()) {
                for (int j = 0; j < matrix.length; j++) {
                    if (matrix[i][j] && !computers[j].isVirus()) {
                        infectList.add(computers[j]);
                    }
                }
            }
        }

        return infectList;
    }
}