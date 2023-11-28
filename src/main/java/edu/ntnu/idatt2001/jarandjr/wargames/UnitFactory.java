package edu.ntnu.idatt2001.jarandjr.wargames;

import edu.ntnu.idatt2001.jarandjr.wargames.ENUMS.UNIT_TYPE;
import edu.ntnu.idatt2001.jarandjr.wargames.Units.*;

import java.util.ArrayList;
import java.util.List;

public class UnitFactory {

    /**
     * The method for making a single unit
     *
     * @param unitType is the type of unit
     * @param nameOfUnit is the name of the units being made
     * @param healthOfUnit is the health of the units being made
     * @return the unit that is made
     */
    public static Unit makeUnit(UNIT_TYPE unitType, String nameOfUnit, int healthOfUnit) {
        return switch (unitType) {
            case INFANTRY_UNIT -> new InfantryUnit(nameOfUnit, healthOfUnit);
            case RANGED_UNIT -> new RangedUnit(nameOfUnit, healthOfUnit);
            case CAVALRY_UNIT -> new CavalryUnit(nameOfUnit, healthOfUnit);
            case COMMANDER_UNIT -> new CommanderUnit(nameOfUnit, healthOfUnit);
        };
    }

    /**
     * The method for making a list of units
     *
     * @param numberOfUnits is the number of units being added to the list
     * @param unitType is the type of unit
     * @param nameOfUnit is the name of the units being made
     * @param healthOfUnit is the health of the units being made
     * @return the list of units
     */
    public static List<Unit> makeListOfUnits(int numberOfUnits, UNIT_TYPE unitType, String nameOfUnit, int healthOfUnit) {
        ArrayList<Unit> list = new ArrayList<>();
        for (int i = 0; i < numberOfUnits; i++) {
            list.add(makeUnit(unitType, nameOfUnit, healthOfUnit));
        }
        return list;
    }
}
