package edu.ntnu.idatt2001.jarandjr.wargames.Units;

import edu.ntnu.idatt2001.jarandjr.wargames.ENUMS.TERRAIN;

public class CavalryUnit extends Unit{
    private boolean hasAttacked;
    /**
     * The constructor for the subclass CavalryUnit
     * Takes in  four parameters and sends two parameters to the super class
     * hasAttacked is initialized to false
     *
     * @param name   of the unit. Can not be blank
     * @param health of the unit. Can not be lower than zero
     * @param attack of the unit. Can not be lower than zero
     * @param armor  of the unit. Can not be lower than zero
     * @throws IllegalArgumentException if the provided data in the constructor does not match the requirements
     */
    public CavalryUnit(String name, int health, int attack, int armor) throws IllegalArgumentException {
        super(name, health, attack, armor);
        hasAttacked = false;
    }

    /**
     * Simplified constructor for the class CavalryUnit
     * Takes in  two parameters and sends two parameters to the super class
     * hasAttacked is initialized to false
     *
     * @param name Name of the unit. Can not be blank
     * @param health Health of te unit. Must be over zero
     * The attack of the unit is 20
     * The armor of the unit is 12
     */
    public CavalryUnit(String name, int health) throws IllegalArgumentException {
        super(name, health, 20, 12);
        hasAttacked = false;
    }

    /**
     * The method for getting the CavalryUnits attackbonus.
     * CavalryUnits attack with more damage the first time they attack an opponent (charge)
     * The bonus represents the Units this advantage
     *
     * @return the bonus attack-damage
     * @param terrain is where the attack takes place
     */
    @Override
    public int getAttackBonus(TERRAIN terrain) {
        int bonus = 2;
        if (!hasAttacked)
            bonus += 4;
        hasAttacked = true;
        return terrain.equals(TERRAIN.PLAINS) ? (bonus + 2): bonus;
    }

    /**
     * The method for getting the CavalryUnits resistbonus.
     * The bonus represents the Units small advantage in melee combat
     *
     * @return the resistbonus for the class
     * @param terrain is where the attack takes place
     */
    @Override
    public int getResistBonus(TERRAIN terrain) {
        return terrain.equals(TERRAIN.FOREST) ? 0 : 1;
    }

    /**
     * The method for getting a unit's movement speed
     * The speed symbolises how many steps a unit can take each move
     *
     * @return the number of steps
     */
    @Override
    public int getNumberOfSteps() {
        return 3;
    }


}
