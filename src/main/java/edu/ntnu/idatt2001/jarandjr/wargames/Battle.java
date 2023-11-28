package edu.ntnu.idatt2001.jarandjr.wargames;

import edu.ntnu.idatt2001.jarandjr.wargames.ENUMS.TERRAIN;
import edu.ntnu.idatt2001.jarandjr.wargames.Units.Unit;

import java.util.Objects;


public class Battle {
    private final Army armyOne;
    private final Army armyTwo;
    private final TERRAIN terrain;

    /**
     * The constructor for creating an instance of the Battle class with two armies
     * if the terrain of the battle is not provided, a random one is chosen instead
     *
     * @param armyOne is one of the two armies participating in the battle
     * @param armyTwo is the other army participating in the battle
     * @throws IllegalArgumentException if an army is empty because you can not battle with no units to battle with
     */
    public Battle(Army  armyOne, Army armyTwo) throws IllegalArgumentException{
        if (!armyOne.hasUnits() || !armyTwo.hasUnits())
            throw new IllegalArgumentException("You can not battle with an empty army!");
        this.armyOne = armyOne;
        this.armyTwo = armyTwo;
        this.terrain = TERRAIN.getRandomTerrain();
    }

    /**
     * The constructor for creating an instance of the Battle class with two armies and a terrain
     *
     * @param armyOne is one of the two armies participating in the battle
     * @param armyTwo is the other army participating in the battle
     * @param terrain is the terrain where the battle takes place
     * @throws IllegalArgumentException if an army is empty because you can not battle with no units to battle with
     */
    public Battle(Army  armyOne, Army armyTwo, TERRAIN terrain) throws IllegalArgumentException{
        if (!armyOne.hasUnits() || !armyTwo.hasUnits())
            throw new IllegalArgumentException("You can not battle with an empty army!");
        this.armyOne = armyOne;
        this.armyTwo = armyTwo;
        this.terrain = terrain;
    }

    /**
     * The method for simulating a battle between two armies
     * The army witch attacks first is chosen randomly between either armyOne or armyTwo depending on if the value from random.nextDouble() is greater or lower than 50%
     * The armies takes turn in witch one of them that is the defending army and witch of them that is the attacking army
     * The attacking army gets a random unit to attack a random unit from the defending army
     * If the defending army's health drops to Zero it gets removed from the defending army
     * If either one of the armies has no units lef the simulation is over
     *
     * @return the winning army, witch is the one of the two armies that has any units left
     */
    public Army simulate() {
        int counter = 0;
        Army attackingArmy;
        Army defendingArmy;

        while (armyOne.hasUnits() && armyTwo.hasUnits()){
            attackingArmy = (counter % 2 == 0) ? armyOne : armyTwo;
            defendingArmy = (!(counter % 2 == 0)) ? armyOne : armyTwo;

            Unit attackingUnit = attackingArmy.getRandom();
            Unit defendingUnit = defendingArmy.getRandom();
            attackingUnit.attack(defendingUnit, terrain);

            if (defendingUnit.getHealth() == 0)
                defendingArmy.remove(defendingUnit);

            counter++;
        }
        return armyOne.hasUnits() ? armyOne : armyTwo;
    }

    /**
     * The method for two units fighting to the death
     * The units takes turn on attacking each other until one of them dies
     * When the defending unit dies, it will be removed from the battlefield and the army it belongs to
     *
     * @param firstAttacker the unit starting the attack
     * @param firstDefender the unit starting with defending
     * @param attackingArmy the army where the attacking unit is from
     * @return the winner of the battle
     */
    public Unit fightToTheDeath(Unit firstAttacker, Unit firstDefender, Army attackingArmy) {
        Unit attackingUnit = null;
        Unit defendingUnit = null;
        Army defendingArmy = attackingArmy.equals(armyOne) ? armyTwo : armyOne;
        Army deadUnitArmy = null;
        int attackCounter = 0;

        while (firstAttacker.getHealth() != 0 && firstDefender.getHealth() != 0) {
            attackingUnit = (attackCounter % 2 == 0) ? firstAttacker : firstDefender;
            defendingUnit = (!(attackCounter % 2 == 0)) ? firstAttacker : firstDefender;
            deadUnitArmy = (!(attackCounter % 2 == 0)) ? attackingArmy : defendingArmy;

            attackingUnit.attack(defendingUnit, terrain);

            attackCounter++;
        }
        Objects.requireNonNull(deadUnitArmy).remove(defendingUnit);
        return attackingUnit;
    }

    /**
     * Get method for getting armyOne
     * @return armyOne
     */
    public Army getArmyOne() {
        return armyOne;
    }

    /**
     * The method for getting armyTwo
     * @return armyTwo
     */
    public Army getArmyTwo() {
        return armyTwo;
    }

    /**
     * The method for getting the terrain of a battle
     * @return the terrain
     */
    public TERRAIN getTerrain() {
        return terrain;
    }

    /**
     * Overrided toString method
     *
     * @return Battle as String
     */
    @Override
    public String toString() {
        return "Battle between: \n" +
                armyOne.getName() + " number of Units: " + armyOne.getSize() +
                "\nand \n" +
                armyTwo.getName() + " number of Units: " + armyTwo.getSize() +
                "\nin the: " + terrain;
    }
}
