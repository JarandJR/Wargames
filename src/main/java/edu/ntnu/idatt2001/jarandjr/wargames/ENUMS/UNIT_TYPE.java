package edu.ntnu.idatt2001.jarandjr.wargames.ENUMS;

/**
 * The different unit types supported in the application
 */
public enum UNIT_TYPE {
    INFANTRY_UNIT,
    RANGED_UNIT,
    CAVALRY_UNIT,
    COMMANDER_UNIT;

    /**
     * The method for getting a unit type by a string of the type
     * @param unitType the string of the unit type
     * @return the Unit type requested
     */
    public static UNIT_TYPE getType(String unitType) {
        return switch (unitType) {
            case "InfantryUnit" -> INFANTRY_UNIT;
            case "RangedUnit" -> RANGED_UNIT;
            case "CavalryUnit" -> CAVALRY_UNIT;
            case "CommanderUnit" -> COMMANDER_UNIT;
            default -> throw new IllegalArgumentException("Could not read classname of object using '" + unitType + "'");
        };
    }

    /**
     * The method for getting the number of unit types
     * This is used for listing the types in the application
     * @return the number of types
     */
    public static int getNumberOfTypes() {
        return 4;
    }
}
