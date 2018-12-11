package group244.zaicev.com;

/**
 * Random interface for computers
 */
public interface ComputerRandom {
    /**
     * Try to infect Windows computer
     */
    boolean infectWindows();
    /**
     * Try to infect Linux computer
     */
    boolean infectLinux();
    /**
     * Try to infect Mac computer
     */
    boolean infectMac();
}