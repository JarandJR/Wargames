package edu.ntnu.idatt2001.jarandjr.wargames;

import edu.ntnu.idatt2001.jarandjr.wargames.ENUMS.UNIT_TYPE;
import edu.ntnu.idatt2001.jarandjr.wargames.ENUMS.TERRAIN;
import edu.ntnu.idatt2001.jarandjr.wargames.Units.*;

import java.io.IOException;
import java.util.ArrayList;
import java.io.FileWriter;
import java.util.Objects;
import java.util.Scanner;
import java.util.List;
import java.io.File;

public class FileManagement {

    private static final String COMMA = ",";
    private static final String NEWLINE = "\n";

    /**
     * The method for reading an army from a file
     *
     * @param file is the file being read
     * @return The army
     * @throws IOException if the file is not supported, if the data in the file is invalid, or if the file does not exist
     */
    public static Army readArmyFromFile(File file) throws IOException {
        if (!file.getName().endsWith(".csv"))
            throw new IOException("The file format is unsupported. Only \".csv\"-files are supported");

        List<Unit> units = new ArrayList<>();
        String armyName;
        try (Scanner scanner = new Scanner(file)) {
            armyName = scanner.nextLine();
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                String[] unitInfo = line.split(COMMA);
                if (unitInfo.length != 3)
                    throw new IOException("Invalid line data '" + line + "'. Make sure the file has the format \"unit type,unit name,unit health\"");

                String unitType = unitInfo[0];
                String unitName = unitInfo[1];
                int unitHealth;
                try {
                    unitHealth = Integer.parseInt(unitInfo[2]);
                }catch (NumberFormatException e) {
                    throw new NumberFormatException("A unit's health must be an Integer, (" + e.getMessage() + ")");
                }

                Unit unit = UnitFactory.makeUnit(UNIT_TYPE.getType(unitType), unitName, unitHealth);
                units.add(unit);
            }
        }catch (IOException e) {
            throw new IOException("Unable to read army data from file, '" + file.getName() + "': " + e.getMessage());
        }
        return new Army(armyName, units);
    }

    /**
     * The method for writing an army consisting of a list of units to a file
     *
     * @param army the army being written as a file
     * @param file the file that is being written on
     * @throws IOException if the file format is not supported or if the file is a directory so that the file can not be created
     */
    public static void writeArmyToFile(Army army, File file) throws IOException {
        List<Unit> units = army.getAllUnits();
        if (!file.getName().endsWith(".csv"))
            throw new IOException("The file format is unsupported. Only \".csv\"-files are supported");

        try (FileWriter filewriter = new FileWriter(file)) {
            filewriter.write(army.getName() + NEWLINE);
            StringBuilder line = new StringBuilder();
            units.forEach(u -> line.append(u.getClass().getSimpleName()).append(COMMA).append(u.getName()).append(COMMA).append(u.getHealth()).append(NEWLINE));
            filewriter.write(String.valueOf(line));
        }catch (IOException e) {
            throw new IOException("Unable to write to file: (" + e.getMessage() + ").");
        }
    }

    /**
     * The method for writing a list of armies to a file
     * This is used for storing the armies from the application
     *
     * @param list is the list of armies being written down
     * @throws IOException if the method was unable to write to the file
     */
    public static void writeListOfArmiesToFile(List<Army> list) throws IOException {
        writeListOfArmiesToFile(list, new File("src/main/resources/ArmyFiles/listOfArmies.csv"));
    }

    /**
     * The method for reading the stored armies from a file
     * This is used for reading the stored data for the application
     *
     * @return the list of armies
     * @throws IOException if the format of the file is wrong
     */
    public static List<Army> readListOfArmiesFromFile() throws IOException{
        return readListOfArmiesFromFile(new File("src/main/resources/ArmyFiles/listOfArmies.csv"));
    }

    /**
     * The method for writing a list of armies to a file
     *
     * @param list is the list of armies being written down
     * @param file the file being written to
     * @throws IOException if the method was unable to write to the file
     */
    public static void writeListOfArmiesToFile(List<Army> list, File file) throws IOException{
        try(FileWriter fileWriter = new FileWriter(file)){
            for (Army a : list) {

                fileWriter.write(a.getName() + NEWLINE);
                StringBuilder line = new StringBuilder();
                if (!a.getInfantryUnits().isEmpty())
                    line.append(a.getInfantryUnits().get(0).getClass().getSimpleName()).append(COMMA).append((UNIT_TYPE.INFANTRY_UNIT)).append(COMMA).append(a.getInfantryUnits().get(0).getHealth()).append(COMMA).append(a.getInfantryUnits().size()).append(NEWLINE);
                if (!a.getRangedUnits().isEmpty())
                    line.append(a.getRangedUnits().get(0).getClass().getSimpleName()).append(COMMA).append((UNIT_TYPE.RANGED_UNIT)).append(COMMA).append(a.getRangedUnits().get(0).getHealth()).append(COMMA).append(a.getRangedUnits().size()).append(NEWLINE);
                if (!a.getCavalryUnits().isEmpty())
                    line.append(a.getCavalryUnits().get(0).getClass().getSimpleName()).append(COMMA).append((UNIT_TYPE.CAVALRY_UNIT)).append(COMMA).append(a.getCavalryUnits().get(0).getHealth()).append(COMMA).append(a.getCavalryUnits().size()).append(NEWLINE);
                if (!a.getCommanderUnits().isEmpty())
                    line.append(a.getCommanderUnits().get(0).getClass().getSimpleName()).append(COMMA).append((UNIT_TYPE.COMMANDER_UNIT)).append(COMMA).append(a.getCommanderUnits().get(0).getHealth()).append(COMMA).append(a.getCommanderUnits().size()).append(NEWLINE);

                fileWriter.write(String.valueOf(line));
            }
        }catch (IOException e){
            throw new IOException("Unable to write list to file: (" + e.getMessage() + ").");
        }
    }

    /**
     * The method for reading the stored armies from a file
     *
     * @param file the file being read
     * @return the list of armies
     * @throws IOException if the format of the file is wrong
     */
    public static List<Army> readListOfArmiesFromFile( File file) throws IOException{
        List<Army> listOfArmies = new ArrayList<>();

        try(Scanner scanner = new Scanner(file)){
            Army army = null;
            while (scanner.hasNext()){
                String line = scanner.nextLine();
                String[] info = line.split(COMMA);
                if (info.length == 1){
                    if (army != null)
                        listOfArmies.add(army);
                    army = new Army(line);
                }
                else {
                    if (info.length != 4)
                        throw new IOException("Invalid line data '" + line + "'. Make sure the file has the format \"unit type,unit name,unit health\"");

                    String unitType = info[0];
                    String unitName = info[1];
                    int unitHealth;
                    try {
                        unitHealth = Integer.parseInt(info[2]);
                    } catch (NumberFormatException e) {
                        throw new NumberFormatException("A unit's health must be an Integer, (" + e.getMessage() + ")");
                    }
                    int numberOfUnitType;
                    try {
                        numberOfUnitType = Integer.parseInt(info[3]);
                    } catch (NumberFormatException e) {
                        throw new NumberFormatException("Number of units must be an integer. (" + e.getMessage() + ")");
                    }

                    try{
                        Objects.requireNonNull(army).addAll(UnitFactory.makeListOfUnits(numberOfUnitType, UNIT_TYPE.getType(unitType), unitName, unitHealth));
                    }catch (NullPointerException e){
                        throw new IOException("The army units are being added to is null: (" + e.getMessage() + ").");
                    }
                }
            }
            listOfArmies.add(army);
        }catch (IOException e){
            throw new IOException("Unable to read file: (" + e.getMessage() + ").");
        }
        return listOfArmies;
    }

    /**
     * The method for writing a battleClass to a file
     *
     * @param battle the instance being written to the file
     * @throws IOException if the method was unable to write to the file
     */
    public static void writeBattleToFile(Battle battle) throws IOException{
        try(FileWriter fileWriter = new FileWriter("src/main/resources/BattleFile/listBattleInfo.csv")){
            fileWriter.write(battle.getArmyOne().getName() + COMMA + battle.getArmyTwo().getName() + COMMA + battle.getTerrain());
        }catch (IOException e){
            throw new IOException("Unable to write list to file: (" + e.getMessage() + ").");
        }
    }

    /**
     * The method for reading a battleClass from a file
     *
     * @return the battle instance
     * @throws IOException if the method was unable to read the file
     */
    public static Battle readBattleInfoFromFile() throws IOException{
        File fileBeingRead = new File("src/main/resources/BattleFile/listBattleInfo.csv");
        List<Army> listOfArmies = readListOfArmiesFromFile();
        return getBattle(fileBeingRead, listOfArmies);
    }

    /**
     * The method for reading a battleClass from a file with a provided armyList file
     *
     * @return the battle instance
     * @throws IOException if the method was unable to read the file
     */
    public static Battle readBattleInfoFromFile(File armyListFile) throws IOException{
        File fileBeingRead = new File("src/main/resources/BattleFile/listBattleInfo.csv");
        List<Army> listOfArmies = readListOfArmiesFromFile(armyListFile);
        return getBattle(fileBeingRead, listOfArmies);
    }

    /**
     * The method for reading a battle instance with a list of armies
     *
     * @param fileBeingRead the file being read
     * @param listOfArmies the list of armies
     * @return the battle instance
     * @throws IOException if something goes wrong when reading the battle file
     */
    private static Battle getBattle(File fileBeingRead, List<Army> listOfArmies) throws IOException {
        Battle battle;

        try(Scanner scanner = new Scanner(fileBeingRead)){
            String line = scanner.nextLine();
            String[] info = line.split(COMMA);

            String armyOneName = info[0];
            String armyTwoName = info[1];
            String terrain = info[2];

            Army armyOne = null;
            Army armyTwo = null;
            for (Army a : listOfArmies) {
                if (a.getName().equals(armyOneName))
                    armyOne = a;
                else if (a.getName().equals(armyTwoName))
                    armyTwo = a;
            }
            if (armyOne == null || armyTwo == null)
                throw new IllegalArgumentException("Unable to find armies)");
            else
                battle = new Battle(armyOne, armyTwo, TERRAIN.getTerrain(terrain));
        }catch (IOException e){
            throw new IOException("Unable to read file: (" + e.getMessage() + ").");
        }
        return battle;
    }
}
