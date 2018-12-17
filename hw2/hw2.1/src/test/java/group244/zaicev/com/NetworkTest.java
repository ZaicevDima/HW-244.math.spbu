package group244.zaicev.com;

import org.junit.Assert;
import org.junit.Test;
import java.util.ArrayList;
import static org.junit.Assert.*;

public class NetworkTest {

    private ArrayList<String> OSList = new ArrayList<>();
    private int[][] connections = new int[7][2];

    private void createOSList() {
        OSList.add("Windows");
        OSList.add("Windows");
        OSList.add("Linux");
        OSList.add("Windows");
        OSList.add("Linux");
        OSList.add("MacOS");
    }

    private void createConnections() {
        connections[0][0] = 1;
        connections[0][1] = 2;
        connections[1][0] = 1;
        connections[1][1] = 3;
        connections[2][0] = 5;
        connections[2][1] = 3;
        connections[3][0] = 3;
        connections[3][1] = 1;
        connections[4][0] = 4;
        connections[4][1] = 5;
        connections[5][0] = 6;
        connections[5][1] = 5;
        connections[6][0] = 6;
        connections[6][1] = 4;
    }

    private boolean isVirus(String s) {

        return s.contains("is infected");
    }



    /** Create predictable random */
    class PredictableRandom implements ComputerRandom {
        @Override
        public boolean infectWindows() {
            return true;
        }

        @Override
        public boolean infectLinux() {
            return true;
        }

        @Override
        public boolean infectMac() {
            return true;
        }
    }

    @Test
    public void correctInfectionTest() {
        createOSList();
        createConnections();
        int infect = 3;
        Network network = new  Network(OSList, connections, infect, new InfectRandom());
        String[] config = network.getStatus().split("\n");
        assertTrue("Infection error", !isVirus(config[0]) && !isVirus(config[1])
                && isVirus(config[2]) && !isVirus(config[3]) && !isVirus(config[4])
                && !isVirus(config[5]));

        while (!isVirus(config[4]) || !isVirus(config[5])) {
            network.timeStep();
            config = network.getStatus().split("\n");

            if (isVirus(config[3])) {
                assertTrue("Infection error", isVirus(config[2]));
            }

            if (isVirus(config[4])) {
                assertTrue("Infection error", isVirus(config[3]) || isVirus(config[2])
                || isVirus(config[5]));
            }

            if (isVirus(config[5])) {
                assertTrue("Infection error", isVirus(config[1]));
            }
        }
    }

    @Test
    public void correctInfectionPredictableRandomTest() {
        createOSList();
        createConnections();
        int infect = 3;
        Network network = new  Network(OSList, connections, infect, new PredictableRandom());
        String[] config = network.getStatus().split("\n");
        assertTrue("Infection error", !isVirus(config[0]) && !isVirus(config[1])
                && isVirus(config[2]) && !isVirus(config[3]) && !isVirus(config[4])
                && !isVirus(config[5]));

        network.timeStep();
        config = network.getStatus().split("\n");
        assertTrue("Infection error", isVirus(config[0]) && !isVirus(config[1])
                && isVirus(config[2]) && !isVirus(config[3]) && isVirus(config[4])
                && !isVirus(config[5]));

        network.timeStep();
        config = network.getStatus().split("\n");
        assertTrue("Infection error", isVirus(config[0]) && isVirus(config[1])
                && isVirus(config[2]) && isVirus(config[3]) && isVirus(config[4])
                && isVirus(config[5]));
    }


    @Test
    public void secondCorrectInfectionPredictableRandomTest() {
        createOSList();
        createConnections();
        int infect = 2;
        Network network = new  Network(OSList, connections, infect, new PredictableRandom());
        String[] config = network.getStatus().split("\n");
        assertTrue("Infection error", !isVirus(config[0]) && isVirus(config[1])
                && !isVirus(config[2]) && !isVirus(config[3]) && !isVirus(config[4])
                && !isVirus(config[5]));

        network.timeStep();
        config = network.getStatus().split("\n");
        assertTrue("Infection error", isVirus(config[0]) && isVirus(config[1])
                && !isVirus(config[2]) && !isVirus(config[3]) && !isVirus(config[4])
                && !isVirus(config[5]));

        network.timeStep();
        config = network.getStatus().split("\n");
        assertTrue("Infection error", isVirus(config[0]) && isVirus(config[1])
                && isVirus(config[2]) && !isVirus(config[3]) && !isVirus(config[4])
                && !isVirus(config[5]));

        network.timeStep();
        config = network.getStatus().split("\n");
        assertTrue("Infection error", isVirus(config[0]) && isVirus(config[1])
                && isVirus(config[2]) && !isVirus(config[3]) && isVirus(config[4])
                && !isVirus(config[5]));

        network.timeStep();
        config = network.getStatus().split("\n");
        assertTrue("Infection error", isVirus(config[0]) && isVirus(config[1])
                && isVirus(config[2]) && isVirus(config[3]) && isVirus(config[4])
                && isVirus(config[5]));
    }


    @Test
    public void getStructureTest() {
        createOSList();
        createConnections();
        int infect = 3;
        Network network = new  Network(OSList, connections, infect, new InfectRandom());

        Assert.assertEquals(network.getTable(),"     1    2    3    4    5    6\n" +
                "1    0    1    1    0    0    0    \n" +
                "2    1    0    0    0    0    0    \n" +
                "3    1    0    0    0    1    0    \n" +
                "4    0    0    0    0    1    1    \n" +
                "5    0    0    1    1    0    1    \n" +
                "6    0    0    0    1    1    0    \n" +
                "\n");
    }

}