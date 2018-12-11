package group244.zaicev.com;

import java.util.Random;

/** Random class for computers infections */
public class InfectRandom implements ComputerRandom {
    private static Random random;

    /** Create random */
    InfectRandom(){
        random = new Random();
    }

    @Override
    public boolean infectWindows() {
        double WINDOWS = 0.5;
        return random.nextDouble() < WINDOWS;
    }

    @Override
    public boolean infectLinux() {
        double LINUX = 0.1;
        return random.nextDouble() < LINUX;
    }

    @Override
    public boolean infectMac() {
        double MAC = 0.2;
        return random.nextDouble() < MAC;
    }
}