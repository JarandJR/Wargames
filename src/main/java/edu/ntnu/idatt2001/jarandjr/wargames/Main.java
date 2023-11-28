package edu.ntnu.idatt2001.jarandjr.wargames;

import edu.ntnu.idatt2001.jarandjr.wargames.ENUMS.UNIT_TYPE;
import edu.ntnu.idatt2001.jarandjr.wargames.Units.*;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Army armyOne = makeArmy("Human Army");
        Army armyTwo = makeArmy("Orcish Horde");
        Battle battle = new Battle(armyOne, armyTwo);
        System.out.println("The winner: \n" + battle.simulate());
        System.out.println("Size of " + armyOne.getName() + " " + armyOne.getSize() + "\n" + "Size of " + armyTwo.getName() + " " + armyTwo.getSize());
    }

    /**
     * The static method for making an army to simulate a battle
     *
     * @param name is the name of the army being made. This decides the name of the units in the army
     * @return an instance of the Army class with an army consisting of 801 Units
     */
    private static Army makeArmy(String name) {
        var list = new ArrayList<Unit>();
        String infantryUnit = "Grunt";
        String rangedUnit = "Spearman";
        String cavalryUnit = "Raider";
        String commanderUnit = "Gul'dan";

        if (name.equals("Human Army")){
            infantryUnit = "Footman";
            rangedUnit = "Archer";
            cavalryUnit = "Knight";
            commanderUnit = "Mountain King";
        }

        list.addAll(UnitFactory.makeListOfUnits(500, UNIT_TYPE.INFANTRY_UNIT, infantryUnit, 100));
        list.addAll(UnitFactory.makeListOfUnits(200,UNIT_TYPE.RANGED_UNIT, rangedUnit, 100));
        list.addAll(UnitFactory.makeListOfUnits(100,UNIT_TYPE.CAVALRY_UNIT, cavalryUnit, 100));
        list.add(UnitFactory.makeUnit(UNIT_TYPE.COMMANDER_UNIT, commanderUnit, 180));
        return new Army(name, list);
    }
}
