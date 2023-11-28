import edu.ntnu.idatt2001.jarandjr.wargames.ENUMS.UNIT_TYPE;
import edu.ntnu.idatt2001.jarandjr.wargames.UnitFactory;
import edu.ntnu.idatt2001.jarandjr.wargames.Units.*;
import edu.ntnu.idatt2001.jarandjr.wargames.Battle;
import edu.ntnu.idatt2001.jarandjr.wargames.Army;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class BattleTest {

    /**
     * The two armies used as Units.test-armies
     */
    Army armyOne = makeArmy("Human Army");
    Army armyTwo = makeArmy("Orcish Horde");

    /**
     * The method for making a Units.test edu.ntnu.idatt2001.jarandjr.wargames.App.Army depending on the name of the army
     *
     * @param name is the name of the army being made. This decides the name of the units in the army
     * @return an instance of the edu.ntnu.idatt2001.jarandjr.wargames.App.Army class with an army consisting of 801 Units
     */
    private Army makeArmy(String name) {
        var list = new ArrayList<Unit>();
        String infantryUnit = "Grunt";
        String rangedUnit = "Spearman";
        String cavalryUnit = "Raider";
        String commanderUnit = "Gul'dan";

        if (name.equals("Human edu.ntnu.idatt2001.jarandjr.wargames.App.Army")){
            infantryUnit = "Footman";
            rangedUnit = "Archer";
            cavalryUnit = "Knight";
            commanderUnit = "Mountain King";
        }

        list.addAll(UnitFactory.makeListOfUnits(500, UNIT_TYPE.INFANTRY_UNIT, infantryUnit, 100));
        list.addAll(UnitFactory.makeListOfUnits(100, UNIT_TYPE.CAVALRY_UNIT, cavalryUnit, 100));
        list.addAll(UnitFactory.makeListOfUnits(200, UNIT_TYPE.RANGED_UNIT, rangedUnit, 100));
        list.add(new CommanderUnit(commanderUnit, 180));

        return new Army(name, list);
    }


    @Test
    @DisplayName("Checks that one army has Units left and the other does not after a simulated battle")
    public void testToCheckIfTheMethodSimulateReturnsTheCorrectWinner() {
        Battle battle = new Battle(armyOne, armyTwo);

        battle.simulate();
        if (armyOne.hasUnits()) {
            assertTrue(armyOne.getSize() > 0);
            assertEquals(0, armyTwo.getSize());
        }
        else {
            assertTrue(armyTwo.getSize() > 0);
            assertEquals(0, armyOne.getSize());
        }
    }

    @Test
    @DisplayName("Checks if creating a instance of th class .Battle with an empty army throws an IllegalArgumentException")
    public void testToCreateAnInstanceOfBattleWithAnEmptyArmy() {
        Army army = new Army("Test-army");
        assertThrows(IllegalArgumentException.class, () -> new Battle(army, armyOne));
    }

    @Test
    @DisplayName("Checks to se if the battle between the armies is close to a 50/50 battle by comparing the difference in wins with ten percent of the number of runs")
    public void testToCheckIfItIsAFairBattleThatIsCloseToAFiftyFiftyChanceOfWinning() {
        final int NUMBER_OF_RUNS = 1000;
        final double PERCENT_ACCEPTED = 0.1;
        final double MAX_DIFFERENCE_IN_WINS = (double)(NUMBER_OF_RUNS) * PERCENT_ACCEPTED;
        int winnerHuman = 0;
        int winnerOrch = 0;
        Army armyHuman;
        Army armyOrch;
        Battle battle;

        for (int i = 0; i < NUMBER_OF_RUNS; i++) {
            armyHuman = makeArmy("Human Army");
            armyOrch = makeArmy("Orcish Horde");
            battle = new Battle(armyHuman, armyOrch);

            battle.simulate();
            if (armyHuman.hasUnits())
                winnerHuman++;
            else
                winnerOrch++;
        }

        int differenceInWins;
        if (winnerHuman < winnerOrch)
            differenceInWins = winnerOrch-winnerHuman;
        else
            differenceInWins = winnerHuman - winnerOrch;
        assertTrue(MAX_DIFFERENCE_IN_WINS > differenceInWins);
    }
}
