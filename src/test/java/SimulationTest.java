import edu.ntnu.idatt2001.jarandjr.wargames.Units.InfantryUnit;
import edu.ntnu.idatt2001.jarandjr.wargames.App.Simulation;
import edu.ntnu.idatt2001.jarandjr.wargames.Battle;
import edu.ntnu.idatt2001.jarandjr.wargames.Army;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import javafx.scene.canvas.Canvas;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

public class SimulationTest {

    @Test
    @DisplayName("Test to check that a creating an instance of the simulation class does not throw an IllegalArgumentException when both battle and canvas are not equal to null")
    public void testToCreateASimulationInstanceWithoutThrowingIllegalArgumentException() {
        try {
            Army armyOne = new Army("name");
            Army armyTwo = new Army("Name");
            armyOne.add(new InfantryUnit("name", 100));
            armyTwo.add(new InfantryUnit("name", 100));
            Simulation sim = new Simulation(new Canvas(), new Battle(armyOne, armyTwo));
        } catch (IllegalArgumentException e) {
            fail();
        }
    }

    @Test
    @DisplayName("Creates an instance of the simulation class with an battle class equal to null to check that the contractor throws an IllegalArgumentException")
    public void testToCreateASimulationInstanceWithABattleClassEqualNull() {
        Battle battle = null;
        assertThrows(IllegalArgumentException.class, () -> new Simulation(new Canvas(), battle));
    }

    @Test
    @DisplayName("Creates an instance of the simulation class with a canvas class equal to null to check that the contractor throws an IllegalArgumentException")
    public void testToCreateASimulationInstanceWithACanvasClassEqualNull() {
        Canvas canvas = null;
        Army armyOne = new Army("name");
        Army armyTwo = new Army("Name");
        armyOne.add(new InfantryUnit("name", 100));
        armyTwo.add(new InfantryUnit("name", 100));
        assertThrows(IllegalArgumentException.class, () -> new Simulation(canvas, new Battle(armyOne, armyTwo)));
    }
}
