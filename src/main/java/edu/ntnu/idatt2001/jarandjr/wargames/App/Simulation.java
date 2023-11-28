package edu.ntnu.idatt2001.jarandjr.wargames.App;

import edu.ntnu.idatt2001.jarandjr.wargames.ENUMS.UNIT_TYPE;
import edu.ntnu.idatt2001.jarandjr.wargames.Location;
import edu.ntnu.idatt2001.jarandjr.wargames.Units.Unit;
import edu.ntnu.idatt2001.jarandjr.wargames.Battle;
import edu.ntnu.idatt2001.jarandjr.wargames.Army;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.animation.Animation;
import javafx.scene.canvas.Canvas;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import static java.lang.Math.abs;
import java.util.Random;



/**
 * The class for simulating a battle on a canvas
 */
public class Simulation {

    private final int MAX_NUMBER_OF_UNITS_PER_ROW = 40;
    private final int UNIT_GRID_LENGTH = 12;
    private final int UNIT_LENGTH = 6;
    int witchArmyCounter;

    private final GraphicsContext gc;
    private final Battle battle;
    Timeline timeline;

    /**
     * The constructor for the class
     * @param canvas the canvas where the battle is displayed
     * @param battle the battle being simulated
     * @throws IllegalArgumentException if either canvas or battle is null
     */
    public Simulation(Canvas canvas, Battle battle) throws IllegalArgumentException{
        if (canvas == null)
            throw new IllegalArgumentException("There was no canvas provided");
        this.gc = canvas.getGraphicsContext2D();
        if (battle == null)
            throw new IllegalArgumentException("No battle was provided");
        this.battle = battle;
        battlePreperations();
        witchArmyCounter = 0;
    }

    /**
     * The method called when the instance is made
     * This method does all the necessary preparations for a battle simulation
     */
    private void battlePreperations() {
        battle.getArmyOne().shuffleArmy();
        battle.getArmyTwo().shuffleArmy();
        clearBattleField();
        drawBattleField();
        displayArmyInfos();
    }

    /**
     * Clears the battlefield so that a new simulation can be done
     */
    private void clearBattleField() {
        gc.clearRect(0, 100, gc.getCanvas().getWidth(), gc.getCanvas().getHeight() - 100);
    }

    /**
     * Draws the battlefield
     * First armyOne on the left and then armyTwo on the right
     */
    private void drawBattleField() {
        drawArmy(battle.getArmyOne(), 3);
        drawArmy(battle.getArmyTwo(), (int)gc.getCanvas().getWidth() - 9);
    }

    /**
     * The method for drawing all the units in an army and setting their location
     *
     * @param army the army being drawn
     * @param x The starting point on the x-axis
     */
    private void drawArmy(Army army, int x) {
        int y = (int)gc.getCanvas().getHeight() - UNIT_GRID_LENGTH;

        int unitCounter = 1;
        for (Unit u : army.getAllUnits()) {
            u.setLocation(x, y);
            drawUnit(u, getColorForArmy(army));
            y -= UNIT_GRID_LENGTH;

            if (unitCounter == MAX_NUMBER_OF_UNITS_PER_ROW) {
                int step = (army.equals(battle.getArmyOne())) ? UNIT_GRID_LENGTH : UNIT_GRID_LENGTH * (-1);
                x += step;
                unitCounter = 0;
                y = (int)gc.getCanvas().getHeight() - UNIT_GRID_LENGTH;
            }
            unitCounter++;
        }
    }

    /**
     * The method for calling the different method for displaying info about the armies
     */
    private void displayArmyInfos() {
        fillInfoSquare();
        strokeArmyInfo(battle.getArmyOne(), 10);
        strokeArmyInfo(battle.getArmyTwo(), gc.getCanvas().getWidth() - 290);
        fillArmyColors();
    }

    /**
     * The method for displaying how the different armies work
     */
    private void fillArmyColors() {
        gc.setStroke(Color.BLACK);
        fillInUnitsSquares(Color.BLUE, 145);
        fillInUnitsSquares(Color.RED, gc.getCanvas().getWidth() - 155);
    }

    /**
     * The method for filling in the unit squares in the infobox
     * @param color the color of the units
     * @param v the placement for the squares in the x-axis
     */
    private void fillInUnitsSquares(Color color, double v) {
        gc.setFill(color);
        double v1 = 25.5;
        for (int i = 0; i < UNIT_TYPE.getNumberOfTypes(); i++) {
            gc.fillRect(v, v1, 9, 9);
            gc.strokeRect(v, v1, 9,9);
            v1 += 20;
        }
    }

    /**
     * The method for stroking info about an army
     *
     * @param army the army where the info is displayed about
     * @param v the placement for the info in the x-axis
     */
    private void strokeArmyInfo(Army army, double v) {
        gc.setLineWidth(1);
        gc.setStroke(Color.WHITE);
        String[] titles = {"Total units: ", "Infantry Units: ", "Ranged Units: ", "Cavalry Units: ", "Commander Units: "};
        int[] sizes = {
                army.getSize(),
                army.getInfantryUnits().size(),
                army.getRangedUnits().size(),
                army.getCavalryUnits().size(),
                army.getCommanderUnits().size()
        };
        int v1 = 15;
        for (int i = 0; i < titles.length; i++) {
            gc.strokeText(titles[i] + sizes[i], v, v1);
            v1 += 20;
        }
    }

    /**
     * The method for filling in the squares where info about the armies are displayed
     */
    private void fillInfoSquare() {
        gc.setFill(Color.valueOf("#392B24"));
        gc.setStroke(Color.valueOf("#906B52"));
        // The squares for both armies
        gc.fillRect(0,0, 300, 100);
        gc.fillRect(gc.getCanvas().getWidth() - 300,0, 300, 100);
        gc.setLineWidth(3);
        // The borders for the squares
        gc.strokeRect(0,0, 300, 100);
        gc.strokeRect(gc.getCanvas().getWidth() - 300,0, 300, 100);
    }

    /**
     * The method for simulating a battle
     * Using a timeline and a simulationStep to simulate the battle
     *
     * @param startBtn the button is disabled while the simulation is running
     */
    public void simulate(Button startBtn) {
            drawBattleField();
            timeline = new Timeline(new KeyFrame(Duration.seconds(0.0010), e -> {
                simulateStep();
                if (!battle.getArmyOne().hasUnits() || !battle.getArmyTwo().hasUnits()) {
                    endBattle(startBtn);
                }
            }));
            timeline.setCycleCount(Animation.INDEFINITE);
            timeline.play();
    }

    /**
     * The method for simulating a step
     * The armies takes turn on doing a move
     * The random unit that is going to advance on the battlefield moves towards the nearest enemy unit
     *
     * If the unit gets to the location of the nearest enemy, they fight to death
     * The unit that dies are removed and the winning unit gets to move to the losers location
     *
     * The structure of the battle falls if the army is smaller than 1 200 units and descends into chaos until only on e army remains
     */
    private void simulateStep() {
        Army attackingArmy = (witchArmyCounter % 2 == 0) ? battle.getArmyOne() : battle.getArmyTwo();
        Army defendingArmy = (!(witchArmyCounter % 2 == 0)) ? battle.getArmyOne() : battle.getArmyTwo();

        Unit unit = (MAX_NUMBER_OF_UNITS_PER_ROW * 30 > attackingArmy.getSize()) ? attackingArmy.getRandom() : attackingArmy.getAllUnits().get(getRandomUnitIndexFromAnArmy(attackingArmy));
        Unit nearestEnemy = getNearestEnemy(unit, defendingArmy);
        Location newLocation = getNewLocation(unit, nearestEnemy);

        if (newLocation.equals(nearestEnemy.getLocation()))
            fightToTheDeath(attackingArmy, unit, nearestEnemy);
        else {
            moveUnit(unit, newLocation, attackingArmy);
            moveUnitShareingLocation(attackingArmy, defendingArmy, unit);
        }
        witchArmyCounter++;
    }

    /**
     * The method for two units fighting to the death
     * The winning unit of the fight advances and take the loser's location on the battlefield
     *
     * @param attackingArmy The army that starts the attack
     * @param unit the unit starting the attack
     * @param enemy the enemy unit starting with defending
     */
    private void fightToTheDeath(Army attackingArmy, Unit unit, Unit enemy) {
        Unit battleWinner = battle.fightToTheDeath(unit, enemy, attackingArmy);
        Unit defendingUnit = battleWinner.equals(unit) ? enemy : unit;
        removeUnit(defendingUnit);
        moveUnit(battleWinner, defendingUnit.getLocation(), attackingArmy);
        displayArmyInfos();
    }

    /**
     * The method for moving a unit that shares the same location as an ally
     * If the unit moves to an enemy, they will have to fight to the death
     *
     * @param attackingArmy the army from where the unit is from
     * @param defendingArmy the army from where the enemy is from
     * @param unit the unit that have moved on the same location as an ally
     */
    private void moveUnitShareingLocation(Army attackingArmy, Army defendingArmy, Unit unit) {
        boolean unitsShareingLocation = true;
        int unitSharingLocationCounter = 0;
        Unit enemy = getNearestEnemy(unit, defendingArmy);

        while(unitsShareingLocation) {
            for (Unit u : attackingArmy.getAllUnits()) {
                if (unit.getLocation().equals(u.getLocation()) && !unit.equals(u)) {
                    Location newLocation = getNewLocationStep(unit, enemy);
                    if (newLocation.equals(enemy.getLocation())) {
                        fightToTheDeath(attackingArmy, unit, enemy);
                        drawUnit(u, getColorForArmy(attackingArmy));
                        unitsShareingLocation = false;
                        break;
                    }else
                        moveUnit(unit, newLocation, attackingArmy);
                    Unit stumbledOnEnemy = getUnitByLocation(unit.getLocation(), defendingArmy);
                    if (stumbledOnEnemy != null) {
                        fightToTheDeath(attackingArmy, unit, stumbledOnEnemy);
                        drawUnit(u, getColorForArmy(attackingArmy));
                        unitsShareingLocation = false;
                        break;
                    }
                    drawUnit(u, getColorForArmy(attackingArmy));
                    unitSharingLocationCounter++;
                }
            }
            if (unitSharingLocationCounter == 0)
                unitsShareingLocation = false;

            unitSharingLocationCounter = 0;
        }
    }

    /**
     * The method for getting the nearest enemy unit
     * The method uses the length of the vector made from one unit to another to decide witch unit that is the closest
     *
     * @param unit The unit looking for the nearest enemy
     * @param enemyArmy The enemy army
     * @return nearest unit from the enemy army
     */
    private Unit getNearestEnemy(Unit unit, Army enemyArmy) {
        Unit enemyUnit = null;
        double lastDistance = Math.sqrt(Math.pow((int)gc.getCanvas().getWidth(), 2) + Math.pow((int)gc.getCanvas().getHeight(), 2));

        for (Unit u : enemyArmy.getAllUnits()) {
            int xVector = u.getLocation().getX() - unit.getLocation().getX();
            int yVector = u.getLocation().getY() - unit.getLocation().getY();
            double distance = Math.sqrt(Math.pow(xVector, 2) + Math.pow(yVector, 2));
            if (distance < lastDistance){
                enemyUnit = u;
            }
            lastDistance = distance;
        }
        return enemyUnit;
    }

    /**
     * The method for getting the new location for a unit depending on how many steps the unit got
     *
     * @param unit The unit getting the new location
     * @param nearestEnemy The nearest enemy for the unit
     * @return the new location
     */
    private Location getNewLocation(Unit unit, Unit nearestEnemy) {
        Location newLocation = null;
        for (int i = 0; i < unit.getNumberOfSteps(); i++) {
            newLocation = getNewLocationStep(unit, nearestEnemy);
        }
        return newLocation;
    }

    /**
     * The method for getting the new location for a unit depending on where the nearest enemy unit is
     * This method priorities moving in the direction of x
     * This method will return the same location as the nearest enemy unit if possible
     *
     * @param unit The unit getting the new location
     * @param nearestEnemy The nearest enemy for the unit
     * @return the new location
     */
    private Location getNewLocationStep(Unit unit, Unit nearestEnemy) {
        int x = unit.getLocation().getX();
        int y = unit.getLocation().getY();
        int enemyX = nearestEnemy.getLocation().getX();
        int enemyY = nearestEnemy.getLocation().getY();

        if (abs(x - enemyX) >= abs(y - enemyY)) {
            if (x < enemyX)
                x += UNIT_GRID_LENGTH;
            else if (x > enemyX)
                x -= UNIT_GRID_LENGTH;
        }else {
            if (y < enemyY)
                y += UNIT_GRID_LENGTH;
            else if (y > enemyY)
                y -= UNIT_GRID_LENGTH;
        }
        return new Location(x,y);
    }

    /**
     * The method for getting a random index from the front row of the army
     * @param army The army where the unit index is retrieved from
     * @return the random index for a unit on the front row of the battlefield
     */
    private int getRandomUnitIndexFromAnArmy(Army army) {
        Random random = new Random();
        return (army.getSize() > MAX_NUMBER_OF_UNITS_PER_ROW) ? random.nextInt(army.getSize() - (1 + MAX_NUMBER_OF_UNITS_PER_ROW),army.getSize()) : random.nextInt(army.getSize());
    }

    /**
     * The method for moving a unit on the battlefield
     *
     * @param unit The unit being moved
     * @param newLocation is the new location for the unit being moved
     * @param army The army witch the unit is from. This is for getting the color of the unit
     */
    private void moveUnit(Unit unit, Location newLocation, Army army) {
        removeUnit(unit);
        unit.setLocation(newLocation.getX(), newLocation.getY());
        drawUnit(unit, getColorForArmy(army));
    }

    /**
     * The method for drawing a unit on the battlefield
     *
     * @param u the unit being drawn
     * @param color The color of the unit
     */
    private void drawUnit(Unit u, Color color) {
        gc.setFill(color);
        gc.fillRect(u.getLocation().getX(), u.getLocation().getY(), UNIT_LENGTH, UNIT_LENGTH);
        gc.strokeRect(u.getLocation().getX(), u.getLocation().getY(), UNIT_LENGTH, UNIT_LENGTH);
    }

    /**
     * The method for removing a unit from the battlefield
     * @param unit The unit being removed
     */
    private void removeUnit(Unit unit) {
        gc.clearRect(unit.getLocation().getX(), unit.getLocation().getY(), UNIT_LENGTH + 1, UNIT_LENGTH + 1);
    }

    /**
     * The method for getting a army's color
     * @param army the army
     * @return the color of the army
     */
    private Color getColorForArmy(Army army) {
        return (army.equals(battle.getArmyOne())) ? Color.BLUE : Color.RED;
    }

    /**
     * The method for getting a unit by an army and a location
     * @param location the location of the unit
     * @param army the army of the unit
     * @return the unit if it matches the location
     */
    private Unit getUnitByLocation(Location location, Army army) {
        for (Unit u : army.getAllUnits()) {
            if (location.equals(u.getLocation()))
                return u;
        }
        return null;
    }

    /**
     * The method for resetting a battle after it is done
     * This stops the timeline and displays the last info about the armies
     * @param startBtn The start button is set to not be disabled anymore
     */
    private void endBattle(Button startBtn) {
        timeline.stop();
        drawWinningArmy();
        displayArmyInfos();
        startBtn.setDisable(false);
        startBtn.setText("Restart Battle");
    }

    /**
     * The method for displaying the winning army
     * Used if any of the units have disappeared or shared location during the chaos of the battle
     */
    private void drawWinningArmy() {
        Army winningArmy = (battle.getArmyOne().hasUnits()) ? battle.getArmyOne() : battle.getArmyTwo();
        cleanUpBattleField(winningArmy);
        Color color = (winningArmy.equals(battle.getArmyOne())) ? Color.BLUE : Color.RED;
        for (Unit u : winningArmy.getAllUnits()) {
            drawUnit(u, color);
        }
    }

    /**
     * The method for moving the remaining units that share the same location at the end of a battle
     * @param army the winning army of the battle
     */
    private void cleanUpBattleField(Army army) {
        boolean unitsShareingLocation = true;
        int unitSharingLocationCounter = 0;

        while(unitsShareingLocation) {
            for (Unit u : army.getAllUnits()) {
                for (Unit y : army.getAllUnits()) {
                    if (u.getLocation().equals(y.getLocation()) && !u.equals(y)) {
                        moveUnit(u, new Location(u.getLocation().getX() + UNIT_GRID_LENGTH, u.getLocation().getY()), army);
                        unitSharingLocationCounter++;
                    }
                }
            }
            if (unitSharingLocationCounter == 0)
                unitsShareingLocation = false;

            unitSharingLocationCounter = 0;
        }
    }
}
