package edu.ntnu.idatt2001.jarandjr.wargames.Units;

import edu.ntnu.idatt2001.jarandjr.wargames.Location;
import edu.ntnu.idatt2001.jarandjr.wargames.ENUMS.TERRAIN;

public abstract class Unit {

    private final String name;
    private int health;
    private final int attack;
    private final int armor;

    private final Location location;

    /**
     * The constructor for the abstract superclass Unit
     *
     * @param  name of the unit. Can not be blank
     * @param health of the unit. Can not be lower than zero
     * @param attack of the unit. Can not be lower than zero
     * @param armor of the unit. Can not be lower than zero
     * @throws IllegalArgumentException if the provided data in the constructor does not match the requirements
     */
    public Unit(String name, int health, int attack, int armor) throws IllegalArgumentException{
        if (name.isBlank())
            throw new IllegalArgumentException("You must provide a name for the Unit.");
        this.name = name;
        if (health < 0)
            throw new IllegalArgumentException("Health can not be lower then zero.");
        this.health = health;
        if (attack < 0)
            throw new IllegalArgumentException("Attack must be greater than zero.");
        this.attack = attack;
        if (armor < 0)
            throw new IllegalArgumentException("Attack must be greater than zero.");
        this.armor = armor;
        this.location = new Location(0,0);
    }

    /**
     * The method for attacking another unit.
     * The attack is blocked if the resistance of the defending unit is greater than the attacking unit's attack
     *
     * @param opponent is the unit being attacked
     * @param terrain is where the combat finds place
     */
    public void attack(Unit opponent, TERRAIN terrain) {
        int previousHealth = opponent.getHealth();
        int resultHealthOpponent = opponent.getHealth() - (this.getAttack() + this.getAttackBonus(terrain)) + (opponent.getArmor() + opponent.getResistBonus(terrain));

        if (this.getAttack() + this.getAttackBonus(terrain) < opponent.getResistBonus(terrain) + opponent.getArmor())
            opponent.setHealth(previousHealth);
        else
            opponent.setHealth(resultHealthOpponent);
    }

    /**
     * The method for getting the name of the unit
     *
     * @return The name as a String
     */
    public String getName() {
        return name;
    }

    /**
     * The method for getting the health of the unit
     *
     * @return The health as an integer
     */
    public int getHealth() {
        return health;
    }

    /**
     * The method for getting the attack of the unit
     *
     * @return The attack as an integer
     */
    public int getAttack() {
        return attack;
    }

    /**
     * The method for getting a unit's armor
     *
     * @return The armor as an integer
     */
    public int getArmor() {
        return armor;
    }

    /**
     * The method for setting a unit's health.
     * A unit's health can not be lower than zero
     *
     * @param health is the health being set as the unit's health
     */
    public void setHealth(int health) {
        this.health = Math.max(health, 0);
    }

    /**
     * The method for setting a Unit's location on the battlefield
     * @param x is the x coordinate on the field
     * @param y is the y coordinate on the field
     */
    public void setLocation(int x, int y) {
        this.location.setX(x);
        this.location.setY(y);
    }

    /**
     * The method for getting a units location
     * @return the location of the unit
     */
    public Location getLocation() {
        return location;
    }

    /**
     * An overrided toString method
     *
     * @return The unit as a String
     */
    @Override
    public String toString() {
        return  name +
                ", Health: " + health +
                ", Attack: " + attack +
                ", Armor: " + armor;
    }

    /**
     * An abstract method for getting a Unit's attackbonus
     *
     * @return the bonus as an integer
     * @param terrain is where the attack takes place
     */
    public abstract int getAttackBonus(TERRAIN terrain);

    /**
     * An abstract method for getting a Unit's resistbonus
     *
     * @return the bonus as an integer
     * @param terrain is where the attack takes place
     */
    public abstract int getResistBonus(TERRAIN terrain);

    /**
     * The method for getting a unit's movement speed
     * The speed symbolises how many steps a unit can take each move
     * @return the number of steps
     */
    public abstract int getNumberOfSteps();
}
