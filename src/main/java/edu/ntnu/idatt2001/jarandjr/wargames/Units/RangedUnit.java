package edu.ntnu.idatt2001.jarandjr.wargames.Units;

import edu.ntnu.idatt2001.jarandjr.wargames.ENUMS.TERRAIN;

public class RangedUnit extends Unit{
    private int numberOfAttacksReceived;

    /**
     * The constructor for the subclass RangedUnit
     * numberOfAttacksReceived is initialized to zero
     *
     * @param name   of the unit. Can not be blank
     * @param health of the unit. Can not be lower than zero
     * @param attack of the unit. Can not be lower than zero
     * @param armor  of the unit. Can not be lower than zero
     * @throws IllegalArgumentException if the provided data in the constructor does not match the requirements
     */
    public RangedUnit(String name, int health, int attack, int armor) throws IllegalArgumentException {
        super(name, health, attack, armor);
        numberOfAttacksReceived = 0;
    }

    /**
     * Simplified constructor for the class RangedUnit
     * Takes in  two parameters and sends two parameters to the super class
     * numberOfAttacksReceived is initialized to zero
     *
     * @param name Name of the unit. Can not be blank
     * @param health Health of te unit. Must be over zero
     * The attack of the unit is 15
     * The armor of the unit is 8
     */
    public RangedUnit(String name, int health) throws IllegalArgumentException {
        super(name, health, 15, 8);
        numberOfAttacksReceived = 0;
    }

    /**
     * The method for getting the RangedUnits attackbonus.
     * The bonus represents the Units ranged advantage in combat
     *
     * @return the attackbonus for the class
     * @param terrain is where the attack takes place
     */
    @Override
    public int getAttackBonus(TERRAIN terrain) {
        int bonus = 3;
        if (terrain.equals(TERRAIN.HILL))
            return bonus + 2;
        if (terrain.equals(TERRAIN.FOREST))
            return bonus - 2;
        return bonus;
    }

    /**
     * The method for getting the RangedUnits resistbonus.
     * The bonus represents the Units ranged advantage.
     * The bonus is lowered after each attack until numberOfAttacks reaches 3
     *
     * @return the resistbonus for the class
     * @param terrain is where the attack takes place
     */
    @Override
    public int getResistBonus(TERRAIN terrain) {
        int bonus = 2;
        for (int i = 2; i > numberOfAttacksReceived; i--) {
            bonus += 2;
        }
        numberOfAttacksReceived++;
        return bonus;
    }

    /**
     * The method for getting a unit's movement speed
     * The speed symbolises how many steps a unit can take each move
     *
     * @return the number of steps
     */
    @Override
    public int getNumberOfSteps() {
        return 1;
    }

}
