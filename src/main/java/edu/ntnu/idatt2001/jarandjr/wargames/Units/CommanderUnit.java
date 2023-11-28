package edu.ntnu.idatt2001.jarandjr.wargames.Units;

public class CommanderUnit extends CavalryUnit{
    /**
     * The constructor for the subclass CommanderUnit
     * Takes in  four parameters and sends two parameters to the super class
     *
     * @param name   of the unit. Can not be blank
     * @param health of the unit. Can not be lower than zero
     * @param attack of the unit. Can not be lower than zero
     * @param armor  of the unit. Can not be lower than zero
     * @throws IllegalArgumentException if the provided data in the constructor does not match the requirements
     */
    public CommanderUnit(String name, int health, int attack, int armor) throws IllegalArgumentException {
        super(name, health, attack, armor);
    }

    /**
     * Simplified constructor for the class CommanderUnit
     * Takes in  two parameters and sends two parameters to the super class
     *
     * @param name Name of the unit. Can not be blank
     * @param health Health of te unit. Must be over zero
     * The attack of the unit is 25
     * The armor of the unit is 15
     */
    public CommanderUnit(String name, int health) throws IllegalArgumentException {
        super(name, health, 25, 15);
    }
}
