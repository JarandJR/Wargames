import edu.ntnu.idatt2001.jarandjr.wargames.ENUMS.TERRAIN;
import edu.ntnu.idatt2001.jarandjr.wargames.Units.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The class for testing the Unit-class and all subclasses
 */
public class UnitTest {

    @Test
    @DisplayName("The Units.test to check if an opponent's health can be lowered to less than zero")
    public void testToCheckIfAttackingAnOpponentCanLowerTheirHealthToLessThanZero() {
        final int DEAD_FOOTMAN_HEALTH = 0;
        var footman = new InfantryUnit("footman", 100);
        var archer = new RangedUnit("Archer", 100);
        while (footman.getHealth() > DEAD_FOOTMAN_HEALTH) {
            archer.attack(footman, TERRAIN.PLAINS);
        }
        assertEquals(DEAD_FOOTMAN_HEALTH, footman.getHealth());
    }

    @Test
    @DisplayName("The Units.test to check that the attack method does not heal the unit if their resistance is greater than the attacking value")
    public void testToSeeThatTheAttackMethodDoesNotHealAUnitIfTheirResistanceIsGreaterThanTheAttackValue() {
        var overPoweredUnit = new CommanderUnit("OP", 200, 10, 40);
        var weakUnit = new InfantryUnit("Weak", 100, 1, 10);
        final int STARTING_HEALTH = overPoweredUnit.getHealth();
        weakUnit.attack(overPoweredUnit, TERRAIN.getRandomTerrain());
        assertEquals(overPoweredUnit.getHealth(), STARTING_HEALTH);
    }

    @Test
    @DisplayName("The test to check if A RangedUnit's resistance is lowered by two each time it gets attacked until it reaches the value of 2")
    public void testToGetTheCorrectResistBonusFromARangedUnit() {
        var archer = new RangedUnit("Archer", 100);
        final int ARCHER_RESISTANCE_1 = 6;
        final int ARCHER_RESISTANCE_2 = 4;
        final int ARCHER_RESISTANCE_3 = 2;
        assertEquals(ARCHER_RESISTANCE_1, archer.getResistBonus(TERRAIN.getRandomTerrain()));
        assertEquals(ARCHER_RESISTANCE_2, archer.getResistBonus(TERRAIN.getRandomTerrain()));
        assertEquals(ARCHER_RESISTANCE_3, archer.getResistBonus(TERRAIN.getRandomTerrain()));
        assertEquals(ARCHER_RESISTANCE_3, archer.getResistBonus(TERRAIN.getRandomTerrain()));
    }

    @Test
    @DisplayName("The Units.test to check if CavalryUnit's attack-bonus is higher the first time it attacks")
    public void testToGetTheCorrectAttackBonusFromACommanderUnit() {
        var mountainKing = new CommanderUnit("Mountain King", 180);
        final int COMMANDER_UNIT_INITIAL_ATTACKBONUS = 6;
        final int COMMANDER_UNIT_ATTACKBONUS = 2;
        assertEquals(COMMANDER_UNIT_INITIAL_ATTACKBONUS, mountainKing.getAttackBonus(TERRAIN.HILL));
        assertEquals(COMMANDER_UNIT_ATTACKBONUS, mountainKing.getAttackBonus(TERRAIN.HILL));
        assertEquals(COMMANDER_UNIT_ATTACKBONUS, mountainKing.getAttackBonus(TERRAIN.HILL));
    }

    @Test
    @DisplayName("Creates an instance of the Unit class with a negative value as health, and checks if an IllegalArgumentException is thrown")
    public void testToCreateANewUnitWithANegativeNumberAsHealth() {
        assertThrows(IllegalArgumentException.class, () -> new CommanderUnit("Footman", -10));
    }

    @Test
    @DisplayName("Creates an instance of the Unit class with a blank value as name, and checks if an IllegalArgumentException is thrown")
    public void testToCreateANewUnitWithABlankName() {
        assertThrows(IllegalArgumentException.class, () -> new CommanderUnit("", 10));
    }

    @Test
    @DisplayName("Creates an instance of the Unit class with a negative value as attack, and checks if an IllegalArgumentException is thrown")
    public void testToCreateANewUnitWithANegativeNumberAsAttack() {
        assertThrows(IllegalArgumentException.class, () -> new CommanderUnit("Footman", 10, -10, 10));
    }

    @Test
    @DisplayName("Creates an instance of the Unit class with a negative value as armor, and checks if an IllegalArgumentException is thrown")
    public void testToCreateANewUnitWithANegativeNumberAsArmor() {
        assertThrows(IllegalArgumentException.class, () -> new CommanderUnit("Footman", 10, 10, -10));
    }

    @Test
    @DisplayName("A test to check that the resist bonus for a cavalry unit is correct depending on the terrain")
    public void testToCheckThatTheResistBonusOfACavalryUnitReturnsTheCorrectValueDependingOnTerrain() {
        final int CORRECT_VALUE_FOR_FOREST = 0;
        final int CORRECT_DEFAULT_VALUE = 1;
        Unit unit = new CavalryUnit("cavalryUnit", 100);
        assertEquals(CORRECT_VALUE_FOR_FOREST, unit.getResistBonus(TERRAIN.FOREST));
        assertEquals(CORRECT_DEFAULT_VALUE, unit.getResistBonus(TERRAIN.HILL));
    }

    @Test
    @DisplayName("A test to check that the attack bonus for a cavalry unit is correct depending on the terrain")
    public void testToCheckThatTheAttackBonusOfACavalryUnitReturnsTheCorrectValueDependingOnTerrain() {
        final int CORRECT_DEFAULT_VALUE = 2;
        final int CORRECT_DEFAULT_VALUE_CHARGE_ATTACK = 6;
        final int CORRECT_VALUE_FOR_PLAINS = 4;
        final int CORRECT_VALUE_FOR_PLAINS_CHARGE_ATTACK = 8;
        Unit unit = new CavalryUnit("cavalryUnit", 100);

        assertEquals(CORRECT_VALUE_FOR_PLAINS_CHARGE_ATTACK, unit.getAttackBonus(TERRAIN.PLAINS));
        assertEquals(CORRECT_VALUE_FOR_PLAINS, unit.getAttackBonus(TERRAIN.PLAINS));

        unit = new CavalryUnit("cavalryUnit", 100);
        assertEquals(CORRECT_DEFAULT_VALUE_CHARGE_ATTACK, unit.getAttackBonus(TERRAIN.HILL));
        assertEquals(CORRECT_DEFAULT_VALUE, unit.getAttackBonus(TERRAIN.HILL));
    }

    @Test
    @DisplayName("A test to check that the attack bonus for a ranged unit is correct depending on the terrain")
    public void testToCheckThatTheAttackBonusOfARangedUnitReturnsTheCorrectValueDependingOnTerrain() {
        final int CORRECT_VALUE_FOR_FOREST = 1;
        final int CORRECT_DEFAULT_VALUE = 3;
        final int CORRECT_VALUE_FOR_HILL = 5;
        Unit unit = new RangedUnit("rangedUnit", 100);

        assertEquals(CORRECT_VALUE_FOR_FOREST, unit.getAttackBonus(TERRAIN.FOREST));
        assertEquals(CORRECT_DEFAULT_VALUE, unit.getAttackBonus(TERRAIN.PLAINS));
        assertEquals(CORRECT_VALUE_FOR_HILL, unit.getAttackBonus(TERRAIN.HILL));
    }

    @Test
    @DisplayName("A test to check that the resist and attack bonus for a infantry unit is correct depending on the terrain")
    public void testToCheckThatTheAttackBonusAndResistBonusOfAInfantryUnitReturnsTheCorrectValueDependingOnTerrain() {
        final int CORRECT_DEFAULT_ATTACKBONUS_VALUE = 2;
        final int CORRECT_DEFAULT_RESISTBONUS_VALUE = 1;
        final int CORRECT_VALUE_FOR_ATTACKBONUS_FOREST = 5;
        final int CORRECT_VALUE_FOR_RESISTBONUS_FOREST = 3;
        Unit unit = new InfantryUnit("InfantryUnit", 100);

        assertEquals(CORRECT_VALUE_FOR_ATTACKBONUS_FOREST, unit.getAttackBonus(TERRAIN.FOREST));
        assertEquals(CORRECT_VALUE_FOR_RESISTBONUS_FOREST, unit.getResistBonus(TERRAIN.FOREST));
        assertEquals(CORRECT_DEFAULT_ATTACKBONUS_VALUE, unit.getAttackBonus(TERRAIN.HILL));
        assertEquals(CORRECT_DEFAULT_RESISTBONUS_VALUE, unit.getResistBonus(TERRAIN.HILL));
    }
}
