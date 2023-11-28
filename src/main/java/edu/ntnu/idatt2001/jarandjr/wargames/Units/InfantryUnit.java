package edu.ntnu.idatt2001.jarandjr.wargames.Units;

import edu.ntnu.idatt2001.jarandjr.wargames.ENUMS.TERRAIN;

public class InfantryUnit extends Unit{
    /**
     * The constructor for the subclass InfantryUnit
     *
     * @param name   of the unit. Can not be blank
     * @param health of the unit. Can not be lower than zero
     * @param attack of the unit. Can not be lower than zero
     * @param armor  of the unit. Can not be lower than zero
     * @throws IllegalArgumentException if the provided data in the constructor does not match the requirements
     */
    public InfantryUnit(String name, int health, int attack, int armor) throws IllegalArgumentException {
        super(name, health, attack, armor);
    }

    /**
     * Simplified constructor for the class InfantryUnit
     * Takes in  two parameters and sends two parameters to the super class
     *
     * @param name Name of the unit. Can not be blank
     * @param health Health of te unit. Must be over zero
     * The attack of the unit is 15
     * The armor of the unit is 10
     */
    public InfantryUnit(String name, int health) {
        super(name, health, 15, 10);
    }

    /**
     * The method for getting the InfantryUnits attackbonus.
     * The bonus represents the Units advantage in melee combat
     *
     * @return the attackbonus for the class
     * @param terrain is where the attack takes place
     */
    @Override
    public int getAttackBonus(TERRAIN terrain) {
        int bonus = 2;
        if (terrain.equals(TERRAIN.FOREST))
            bonus += 3;
        return bonus;
    }

    /**
     * The method for getting the InfantryUnits resistbonus.
     * The bonus returned represents the Units weak defense
     *
     * @return the attackbonus for the class
     * @param terrain is where the attack takes place
     */
    @Override
    public int getResistBonus(TERRAIN terrain) {
        int bonus = 1;
        if (terrain.equals(TERRAIN.FOREST))
            return bonus + 2;
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
        return 2;
    }


}
