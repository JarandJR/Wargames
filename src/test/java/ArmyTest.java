import edu.ntnu.idatt2001.jarandjr.wargames.ENUMS.UNIT_TYPE;
import edu.ntnu.idatt2001.jarandjr.wargames.UnitFactory;
import edu.ntnu.idatt2001.jarandjr.wargames.Units.*;
import edu.ntnu.idatt2001.jarandjr.wargames.Army;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The class for testing the Army-class
 */
public class ArmyTest {

    @Test
    @DisplayName("Creates an object with an empty name to check if an IllegalArgumentException is thrown in both constructors")
    public void testToCreateANewArmyWithABlankNameWithBouthConstructors() {
        assertThrows(IllegalArgumentException.class, () -> new Army(""));
        assertThrows(IllegalArgumentException.class, () -> new Army("", new ArrayList<>()));
    }

    @Test
    @DisplayName("Create an army-object with a list of units of size 5 to check if the constructor behave correctly")
    public void testToCreatAnArmyWithAnAlreadyExistingList() {
        final int ARMY_SIZE_OF_FIVE = 5;
        var list = new ArrayList<>(UnitFactory.makeListOfUnits(5, UNIT_TYPE.INFANTRY_UNIT, "Footman", 100));
        var armyTest = new Army("Name", list);
        assertEquals(armyTest.getSize(), ARMY_SIZE_OF_FIVE);
    }

    @Test
    @DisplayName("Testing to check if it is possible to add several of the same Unit in an army")
    public void testToAddSeveralOfTheSameUnitInAnArmyAndChecksThatTheSizeOfTheArmyIsOne() {
        Army army = new Army("Human army");
        final int NUMBER_OF_DIFFERENT_UNITS_IN_THE_ARMY = 1;
        Unit footman = new InfantryUnit("Footman", 100);
        for (int i = 0; i < 20; i++) {
            army.add(footman);
        }
        assertEquals(army.getSize(), NUMBER_OF_DIFFERENT_UNITS_IN_THE_ARMY);
    }


    /**
     * The test makes an army consisting of:
     *
     * 20 InfantryUnit: name: Footman, Health: 100
     * 10 RangedUnit: name: Archer, Health: 100
     * 10 CavalryUnit: name: Knight, Health: 100
     * 1 mountainKing: name: Mountain King, Health: 180
     *
     * An army containing 41 Units in total
     */
    @Test
    @DisplayName("Testing to check if it is possible to add Units in the Army class")
    public void testToCheckThatTheAddMethodsWorks() {
        final int TEST_DATA_ARMY_SIZE = 41;
        Army army = new Army("Human army");
        ArrayList<Unit> testArmyUnits = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            testArmyUnits.add(UnitFactory.makeUnit(UNIT_TYPE.INFANTRY_UNIT, "footman", 100));
            if (i % 2 == 0) {
                testArmyUnits.add(UnitFactory.makeUnit(UNIT_TYPE.RANGED_UNIT, "archer", 100));
                testArmyUnits.add(UnitFactory.makeUnit(UNIT_TYPE.CAVALRY_UNIT, "knight", 100));
            }
        }
        army.add(UnitFactory.makeUnit(UNIT_TYPE.COMMANDER_UNIT, "Mountain King", 180));
        army.addAll(testArmyUnits);
        assertEquals(army.getSize(), TEST_DATA_ARMY_SIZE);
    }

    @Test
    @DisplayName("Testing to check if it is possible to remove a Random Unit from an Army")
    public void testToCheckThatAUnitCanBeRemovedFromAnArmy() {
        Army army = new Army("Human army");
        for (int i = 0; i < 3; i++) {
            army.add(UnitFactory.makeUnit(UNIT_TYPE.INFANTRY_UNIT, "unit", 100));
        }
        final int TEST_DATA_ARMY_SIZE = army.getSize();
        army.remove(army.getRandom());
        assertTrue(army.getSize() < TEST_DATA_ARMY_SIZE);
    }

    @Test
    @DisplayName("Testing to check if the method hasUnits returns the correct value")
    public void testToCheckIfAnArmyHasUnitsReturnsTheCorrectValue() {
        Army army = new Army("Human army");
        army.add(UnitFactory.makeUnit(UNIT_TYPE.INFANTRY_UNIT, "unit", 100));
        assertTrue(army.hasUnits());
        while (army.hasUnits()) {
            army.remove(army.getRandom());
        }
        assertFalse(army.hasUnits());
    }

    @Test
    @DisplayName("Testing to see that the getInfantryUnits() only return infantry units")
    public void testToSeeThatOnlyInfantryUnitsAreReturnedFromTheMethodGetInfantryUnits() {
        Army army = new Army("Human army");
        InfantryUnit infantry = new InfantryUnit("infantry", 100);
        army.add(infantry);
        army.add(new RangedUnit("ranged", 100));
        army.add(new CavalryUnit("cavalry", 100));
        army.add(new CommanderUnit("commander", 100));

        Army filteredArmy = new Army("Human army");
        filteredArmy.add(infantry);

        assertEquals(filteredArmy.getAllUnits(), army.getInfantryUnits());
    }

    @Test
    @DisplayName("Testing to see that the getRangedUnits() only return ranged units")
    public void testToSeeThatOnlyInfantryUnitsAreReturnedFromTheMethodGetRangedUnits() {
        Army army = new Army("Human army");
        RangedUnit ranged = new RangedUnit("ranged", 100);
        army.add(ranged);
        army.add(new InfantryUnit("infantry", 100));
        army.add(new CavalryUnit("cavalry", 100));
        army.add(new CommanderUnit("commander", 100));

        Army filteredArmy = new Army("Human army");
        filteredArmy.add(ranged);

        assertEquals(filteredArmy.getAllUnits(), army.getRangedUnits());
    }

    @Test
    @DisplayName("Testing to see that the getcavalryUnits() only return cavalry units")
    public void testToSeeThatOnlyInfantryUnitsAreReturnedFromTheMethodGetCavalryUnits() {
        Army army = new Army("Human army");
        CavalryUnit cavalry = new CavalryUnit("cavalry", 100);
        army.add(cavalry);
        army.add(new InfantryUnit("infantry", 100));
        army.add(new RangedUnit("ranged", 100));
        army.add(new CommanderUnit("commander", 100));

        Army filteredArmy = new Army("Human army");
        filteredArmy.add(cavalry);

        assertEquals(filteredArmy.getAllUnits(), army.getCavalryUnits());
    }

    @Test
    @DisplayName("Testing to see that the getCommanderUnits() only return commander units")
    public void testToSeeThatOnlyInfantryUnitsAreReturnedFromTheMethodGetCommanderUnits() {
        Army army = new Army("Human army");
        CommanderUnit commander = new CommanderUnit("commander", 100);
        army.add(commander);
        army.add(new InfantryUnit("infantry", 100));
        army.add(new RangedUnit("ranged", 100));
        army.add(new CavalryUnit("cavalry", 100));

        Army filteredArmy = new Army("Human army");
        filteredArmy.add(commander);

        assertEquals(filteredArmy.getAllUnits(), army.getCommanderUnits());
    }
}
