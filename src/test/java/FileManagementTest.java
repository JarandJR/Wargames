import edu.ntnu.idatt2001.jarandjr.wargames.Units.InfantryUnit;
import edu.ntnu.idatt2001.jarandjr.wargames.FileManagement;
import edu.ntnu.idatt2001.jarandjr.wargames.ENUMS.TERRAIN;
import edu.ntnu.idatt2001.jarandjr.wargames.Battle;
import edu.ntnu.idatt2001.jarandjr.wargames.Army;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class FileManagementTest {

    @Test
    @DisplayName("A test to check if the method writeToFile() does not throw an IOException")
    public void testToCheckIfTheMethodWriteArmyToFileDoesNotThrowIOException() {
        var testArmy = new Army("Test army");
        testArmy.add(new InfantryUnit("test footman", 100));
        testArmy.add(new InfantryUnit("test footman", 100));

        try {
            FileManagement.writeArmyToFile(testArmy, new File("src/main/resources/ArmyTestfiles/testFileWithWrongFormat.csv"));
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    @DisplayName("A test to check if the method writeArmyToFile() ThrowsAnException when the file does not end with '.csv'")
    public void testToCheckThatAFileWithTheWrongFormatThrowsIOException() {
        assertThrows(IOException.class, () -> FileManagement.writeArmyToFile(new Army("test army"), new File("src/main/resources/Armyfile/testFileWithWrongFormatEndingWithTxt.txt")));
    }

    @Test
    @DisplayName("A test to check if the method writeArmyToFile() Throws An IOException when the file is a directory instead of a csv file")
    public void testToCheckThatWritingToADirectoryAndNotAFileThrowsIOException() {
        assertThrows(IOException.class, () -> FileManagement.writeArmyToFile(new Army("test army"), new File("src/main/resources/Armyfile")));
    }


    @Test
    @DisplayName("A test to see that the method readArmyFromFile() does not throw an IOException when the file format is correct")
    public void testToCheckThatTheMethodReadArmyFromFileWithTheRightFormatDoesNotThrowAnIOException() {
        Army testArmy = new Army("Test army");
        testArmy.add(new InfantryUnit("test footman", 100));

        Army armyRead = null;
        try {
            armyRead = FileManagement.readArmyFromFile(new File("src/main/resources/ArmyTestfiles/testFileWithRightFormat.csv"));
        } catch (IOException e) {
            fail();
        }
        assertEquals(testArmy.getName(), armyRead.getName());
        assertEquals(testArmy.getSize(), armyRead.getSize());

    }

    @Test
    @DisplayName("A test to check if the method readArmyFromFile() Throws An IOException when the file includes four commas instead of two")
    public void testToCheckIfAFileWithTheWrongFormatToManyCommasThrowsIOException() {
        assertThrows(IOException.class, () -> FileManagement.readArmyFromFile(new File("src/main/resources/ArmyTestfiles/testFileWithWrongFormatToManyCommas.csv")));
    }

    @Test
    @DisplayName("A test to check if the method readArmyFromFile() Throws An IOException when the file is a directory instead of a csv file")
    public void testToCheckThatReadingFromAFileEndingWithTxtThrowsIOException() {
        assertThrows(IOException.class, () -> FileManagement.readArmyFromFile(new File("src/main/resources/ArmyTestfiles/testFileWithWrongFormatEndingWithTxt.txt")));
    }

    @Test
    @DisplayName("A test to check if the method readArmyFromFile() Throws An NumberFormatException when the file does not include an integer as the health of a unit")
    public void testToCheckIfAFileWithTheWrongFormatThrowsNumberFormatExceptionWhenTheHealthIsNotAnInteger() {
        assertThrows(NumberFormatException.class, () -> FileManagement.readArmyFromFile(new File("src/main/resources/ArmyTestfiles/testFileWithWrongFormatNoIntegerAsHealth.csv")));
    }

    @Test
    @DisplayName("A test to check if the method readArmyFromFile() Throws An IOException when the file includes a class name that does not match any of the classes that inherits Unit")
    public void testToCheckIfAFileWithTheWrongFormatThrowsIOExceptionWhenTheClassNameDoesNotMatchAnyUnitsClassNames() {
        assertThrows(IllegalArgumentException.class, () -> FileManagement.readArmyFromFile(new File("src/main/resources/ArmyTestfiles/testFileWithWrongFormatNoMatchingClassName.csv")));
    }

    @Test
    @DisplayName("A test to check if the method readArmyFromFile() Throws An IOException when the file that is being read does not exist")
    public void testToCheckIfReadingAFileThatDoesNotExistThrowsIOException() {
        assertThrows(IOException.class, () -> FileManagement.readArmyFromFile(new File("src/main/resources/ArmyTestfiles/testFileThatDoesNotExist.csv")));
    }

    @Test
    @DisplayName("A test to check that a list of armies can be stored in a the listOfArmies.csv file")
    public void testToCheckThatAListOfArmiesCanBeWrittenToAFile() {
        List<Army> listOfArmies = new ArrayList<>();
        listOfArmies.add(new Army("Test"));
        listOfArmies.add(new Army("Test 2"));
        try{
            FileManagement.writeListOfArmiesToFile(listOfArmies, new File("src/main/resources/ArmyTestfiles/testListOfArmies.csv"));
        }catch (IOException e){
            fail();
        }
    }

    @Test
    @DisplayName("A test to check that a list of armies can be read from the listOfArmies.csv file")
    public void testToCheckThatAListOfArmiesCanBeReadFromAFile() {
        List<Army> listOfArmies = new ArrayList<>();
        listOfArmies.add(new Army("Test"));
        listOfArmies.add(new Army("Test 2"));
        File file = new File("src/main/resources/ArmyTestfiles/testListOfArmies.csv");
        try {
            FileManagement.writeListOfArmiesToFile(listOfArmies, file);
        } catch (IOException e) {
            fail();
        }

        List<Army> listReadFromFile = null;
        try {
            listReadFromFile = FileManagement.readListOfArmiesFromFile(file);
        } catch (IOException e) {
            fail();
        }

        Assertions.assertEquals(listOfArmies, listReadFromFile);
    }

    @Test
    @DisplayName("A test to check that a battle instance can be stored in a the listBattleInfo file")
    public void testToCheckThatABattleCanBeWrittenToAFile() {

        Army armyOne = new Army("ArmyOne");
        armyOne.add(new InfantryUnit("UnitArmyOne", 100));

        Army armyTwo = new Army("ArmyTwo");
        armyTwo.add(new InfantryUnit("UnitArmyTwo", 100));

        List<Army> listOfArmies = new ArrayList<>();
        listOfArmies.add(armyOne);
        listOfArmies.add(armyTwo);
        try {
            FileManagement.writeListOfArmiesToFile(listOfArmies, new File("src/main/resources/ArmyTestfiles/testListOfArmies.csv"));
        } catch (IOException e) {
            fail();
        }

        Battle battle = new Battle(listOfArmies.get(0), listOfArmies.get(1), TERRAIN.HILL);
        try{
            FileManagement.writeBattleToFile(battle);
        }catch (IOException e){
            fail();
        }
    }

    @Test
    @DisplayName("A test to check that a battle instance can be read from listBattleInfo file")
    public void testToCheckThatABattleCanBeReadFromAFile() {
        Army armyOne = new Army("ArmyOne");
        armyOne.add(new InfantryUnit("UnitArmyOne", 100));

        Army armyTwo = new Army("ArmyTwo");
        armyTwo.add(new InfantryUnit("UnitArmyTwo", 100));

        List<Army> listOfArmies = new ArrayList<>();
        listOfArmies.add(armyOne);
        listOfArmies.add(armyTwo);
        try {
            FileManagement.writeListOfArmiesToFile(listOfArmies, new File("src/main/resources/ArmyTestfiles/testListOfArmies.csv"));
        } catch (IOException e) {
            fail();
        }

        Battle battle = new Battle(armyOne, armyTwo, TERRAIN.HILL);
        try{
            FileManagement.writeBattleToFile(battle);
        }catch (IOException e){
            fail();
        }

        Battle readBattle = null;
        try {
            readBattle = FileManagement.readBattleInfoFromFile(new File("src/main/resources/ArmyTestfiles/testListOfArmies.csv"));
        } catch (IOException e) {
            fail();
        }

        assertEquals(battle.getArmyOne().getName(), readBattle.getArmyOne().getName());
        assertEquals(battle.getArmyTwo().getName(), readBattle.getArmyTwo().getName());
        assertEquals(battle.getTerrain(), readBattle.getTerrain());
    }
}
