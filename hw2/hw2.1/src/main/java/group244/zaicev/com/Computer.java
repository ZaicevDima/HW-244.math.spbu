package group244.zaicev.com;

/**
 * Computer class
 */
public class Computer {
    private String operationSystem;
    private static ComputerRandom random;
    private boolean isVirus;

    /**
     * Constructor
     */
    Computer(String System, ComputerRandom random) {
        isVirus = false;
        operationSystem = System;
        Computer.random = random;
    }

    /**
     * Try to infect the computer
     */
    public void tryInfect() {
        if (isVirus) {
            return;
        }

        switch (operationSystem) {
            case "Windows":
                isVirus = random.infectWindows();
                break;
            case "Linux":
                isVirus = random.infectLinux();
                break;
            case  "MacOS":
                isVirus = random.infectMac();
                break;
            default:
                break;
        }
    }

    /**
     * Infect the computer
     */
    public void infect() {
        isVirus = true;
    }

    /**
     * Get operation system
     */
    public String getOperationSystem() {
        return operationSystem;
    }

    /**
     * Checks computer infection
     */
    public boolean isVirus() {
        return isVirus;
    }
}