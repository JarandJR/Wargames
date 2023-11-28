import edu.ntnu.idatt2001.jarandjr.wargames.Units.InfantryUnit;
import edu.ntnu.idatt2001.jarandjr.wargames.ENUMS.UNIT_TYPE;
import edu.ntnu.idatt2001.jarandjr.wargames.UnitFactory;
import edu.ntnu.idatt2001.jarandjr.wargames.Units.Unit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class UnitFactoryTest {

    @Test
    @DisplayName("A test to check that the method makeUnit can make the unit requested")
    public void testCheckThatACorrectUnitIsMade() {
        UNIT_TYPE unitType = UNIT_TYPE.INFANTRY_UNIT;
        String name = "infantryUnit";
        int health = 100;
        Unit unit = new InfantryUnit(name, health);
        Unit unitMade = UnitFactory.makeUnit(unitType, name, health);
        assertEquals(unit.getName(), unitMade.getName());
        assertEquals(unit.getHealth(), unitMade.getHealth());
        assertTrue(unitMade instanceof InfantryUnit);
    }

    @Test
    @DisplayName("A test to check that the method makeListOfUnits can make the list requested")
    public void testCheckThatACorrectListOfUnitsIsMade() {
        UNIT_TYPE unitType = UNIT_TYPE.INFANTRY_UNIT;
        String name = "infantryUnit";
        int health = 100;

        Unit unit = new InfantryUnit(name, health);
        ArrayList<Unit> list = new ArrayList<>();
        list.add(unit);
        list.add(new InfantryUnit(name, health));

        var listBeingMade = UnitFactory.makeListOfUnits(2, unitType, name, health);
        assertEquals(list.size(), listBeingMade.size());

        for (int i = 0; i < listBeingMade.size(); i++) {
            assertEquals(list.get(i).getName(), listBeingMade.get(i).getName());
            assertEquals(list.get(i).getHealth(), listBeingMade.get(i).getHealth());
            assertTrue(listBeingMade.get(i) instanceof InfantryUnit);
        }
    }
}
