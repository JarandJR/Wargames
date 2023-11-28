package edu.ntnu.idatt2001.jarandjr.wargames.ENUMS;

import java.util.Random;

/**
 * The different terrains supported in the application
 */
public enum TERRAIN {
    HILL,
    PLAINS,
    FOREST;

    /**
     * The method for getting the number of terrains
     * This is for listing the terrains in the application
     * @return the number of terrains
     */
    public static int getNumberOfTerrains() {
        return 3;
    }

    /**
     * The method for getting a random terrain
     * @return the selected terrain
     */
    public static TERRAIN getRandomTerrain() {
        Random random = new Random() ;
        int index = random.nextInt(3);
        return switch (index) {
            case 0 -> HILL;
            case 1 -> PLAINS;
            case 2 -> FOREST;
            default -> null;
        };
    }

    /**
     * The method for getting a terrain with a string
     * @param terrain a string of the terrain requested
     * @return the Terrain of the string
     */
    public static TERRAIN getTerrain(String terrain) {
        return switch (terrain) {
            case "HILL" -> HILL;
            case "PLAINS" -> PLAINS;
            case "FOREST" -> FOREST;
            default -> throw new IllegalArgumentException("Could not read classname of object using '" + terrain + "'");
        };
    }
}
