package edu.ntnu.idatt2001.jarandjr.wargames;

import edu.ntnu.idatt2001.jarandjr.wargames.Units.*;

import java.util.stream.Collectors;
import java.util.Collections;
import java.util.ArrayList;
import java.util.Objects;
import java.util.List;

public class Army {
    private final String name;
    private List<Unit> units;

    /**
     * The constructor for creating an instance of the Army class with an already exciting list
     *
     * @param name of the army
     * @param units the list of the units in the army
     * @throws IllegalArgumentException if the name is empty
     */
    public Army(String name, List<Unit> units) throws IllegalArgumentException{
        if (name.isBlank())
            throw new IllegalArgumentException("You must provide a name for the army.");
        this.name = name;
        if (!units.isEmpty()) {
            this.units = new ArrayList<>();
            addAll(units);
        }
        this.units = units;
    }

    /**
     * the constructor for making an instance of the Army class
     *
     * @param name of the army
     * @throws IllegalArgumentException if the name is empty
     */
    public Army(String name) throws IllegalArgumentException{
        if (name.isBlank())
            throw new IllegalArgumentException("You must provide a name for the army.");
        this.name = name;
        units = new ArrayList<>();
    }

    /**
     * The method for getting the name of the army
     * @return the name as a String
     */
    public String getName() {
        return name;
    }

    /**
     * The method for adding a Unit to the list of units
     * Checks if the unit already exists in the list
     * @param unit the unit that is being added to the list
     */
    public void add(Unit unit) {
        if (!units.contains(unit))
            units.add(unit);
    }

    /**
     * The method for adding a list of Unites to the army
     * @param units the list of unites being added to the army
     */
    public void addAll(List<Unit> units) {
        for (Unit unit : units){
            this.add(unit);
        }
    }

    /**
     * The method for removing a unit from the army
     * @param unit is the unit being removed from the army
     */
    public void remove(Unit unit) {
        units.remove(unit);
    }

    /**
     * The method to check if an army has any Units in it
     * @return True or false depending on if the army is empty or not
     */
    public boolean hasUnits() {
        return !units.isEmpty();
    }

    /**
     * The method for getting all the unites in the army
     * @return the list of Units
     */
    public List<Unit> getAllUnits() {
        return units;
    }

    /**
     * The method for getting a random Unit from the army
     * @return the random unit
     */
    public Unit getRandom() {
        java.util.Random randomUnitIndex = new java.util.Random();
        return units.get(randomUnitIndex.nextInt(units.size()));
    }

    /**
     * The method for getting the size of the army
     * @return the size of the army
     */
    public int getSize() {
        return units.size();
    }

    /**
     * The method for getting all the infantry units from the army
     * @return the list of units in the army filtered to only consist of infantry units
     */
    public List<Unit> getInfantryUnits() {
        return units.stream().filter(u -> u instanceof InfantryUnit).collect(Collectors.toList());
    }

    /**
     * The method for getting all the ranged units from the army
     * @return the list of units in the army filtered to only consist of ranged units
     */
    public List<Unit> getRangedUnits() {
        return units.stream().filter(u -> u instanceof RangedUnit).collect(Collectors.toList());
    }

    /**
     * The method for getting all the cavalry units from the army
     * @return the list of units in the army filtered to only consist of cavalry units
     */
    public List<Unit> getCavalryUnits() {
        return units.stream().filter(u -> u instanceof CavalryUnit).filter(u -> !(u instanceof CommanderUnit)).collect(Collectors.toList());
    }

    /**
     * The method for getting all the commander units from the army
     * @return the list of units in the army filtered to only consist of commander units
     */
    public List<Unit> getCommanderUnits() {
        return units.stream().filter(u -> u instanceof CommanderUnit).collect(Collectors.toList());
    }

    /**
     * The method for shuffling an army
     */
    public void shuffleArmy() {
        Collections.shuffle(units);
    }

    /**
     * Overrided toString method using a stringbuilder
     * @return the army as String
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(this.name + "\n");
        for (Unit unit : units) {
            sb.append(unit.toString()).append("\n");
        }
        return sb.toString();
    }

    /**
     * Overrided equals method
     * @param o is the object being compared
     * @return true or false depending on if the object is equal or not
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Army army = (Army) o;
        return Objects.equals(name, army.name) && Objects.equals(units, army.units);
    }

    /**
     * Overrided hasCode method
     * @return the hashCode of the object
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, units);
    }
}
