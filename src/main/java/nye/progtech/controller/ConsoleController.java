package nye.progtech.controller;

import nye.progtech.Colors;
import nye.progtech.fileUtils.FileLoader;
import nye.progtech.repository.DBRepositoryImpl;
import nye.progtech.repository.DBRepositoryInterface;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class ConsoleController {
    private Scanner scanner;

    public ConsoleController() {
        this.scanner = new Scanner(System.in);
    }

    public void closeScanner() {
        if (scanner != null) {
            scanner.close();
        }
    }



    public String promptForUserName() {
        System.out.println(Colors.ANSI_GREEN + "Szia Idegen! Kérlek add meg a neved: " + Colors.ANSI_RESET);
        return scanner.nextLine();
    }

    public void greetUser(String userName) {
        System.out.println(Colors.ANSI_GREEN + "Üdvözöllek, " + userName + "! Kezdjünk is neki a játéknak." + Colors.ANSI_RESET);
    }

    //Game
    public String askForGameAction() {
        System.out.println(Colors.ANSI_YELLOW + "Az alábbi parancsok közül választhatsz, de mindig vedd figyelembe, ");
        System.out.println("hogy az irányok az éppen aktuális irány alapján kerülnek végrehajtásra!");
        System.out.println("forward: Lépés előre.");
        System.out.println("backward: Lépés hátra.");
        System.out.println("left: Lépés balra.");
        System.out.println("right: Lépés jobbra.");
        System.out.println("direction east|west|north|south: Hős irányának beállítása.");
        System.out.println("fire: Nyíl kilővése az aktuális irányba.");
        System.out.println("save: Játékállás elmentése.");
        System.out.println("exit: Kilépés a játékból." + Colors.ANSI_RESET);
        System.out.println("Adj meg egy parancsot: ");
        return scanner.nextLine();
    }

    //Editor
    public int askForBoardSize() {
        System.out.println("Add meg a pálya méretét: ");
        return scanner.nextInt();
    }

    public int askForHeroRow() {
        System.out.println("Add meg a hős sorát: ");
        return scanner.nextInt();
    }
    public char askForHeroColumn() {
        System.out.println("Add meg a hős oszlopát: ");
        return scanner.next().charAt(0);
    }
    public char askForHeroDirection() {
        System.out.println("Add meg a hős irányát (N, E, S, W): ");
        scanner.nextLine();
        return scanner.next().charAt(0);
    }
    public String askForMapName() {
        System.out.println("Add meg a pálya nevét: ");
        scanner.nextLine();
        return scanner.nextLine();
    }

    public String askForEditorInput() {
        System.out.println("Add meg a pályaelemet a következő formátumban: [add|delete], sorszám, oszlop, pályaelem");
        System.out.println("Pályaelem lehet a következő: U - Wumpus, P - Verem, G - Arany");
        System.out.println("Befejezéshez gépeld be a 'kilép' parancsot.");
        return scanner.nextLine();
    }

    //Main menu
    public void displayMainMenu() {
        System.out.println(Colors.ANSI_BLUE + "\n--- Főmenü ---" + Colors.ANSI_RESET);
        for (MenuOption option : MenuOption.values()) {
            System.out.println(option.getValue() + ". " + option.getDescription());
        }
    }
    public void displayFormatSelectorMenu() {
        System.out.println(Colors.ANSI_BLUE + "--- Formátumok ---" + Colors.ANSI_RESET);
        for (FileFormat format : FileFormat.values()) {
            System.out.println(format.getValue() + ". " + format.getDescription());
        }
    }

    public MenuOption getSelectedOption() {
        try {
            int selectedOptionIndex = scanner.nextInt();
            return MenuOption.fromInt(selectedOptionIndex);
        } catch (InputMismatchException e) {
            scanner.nextLine();
            return MenuOption.fromInt(-1);
        }
    }

    public FileFormat getSelectedFileFormat() {
        int selectedOptionIndex = scanner.nextInt();
        return FileFormat.fromInt(selectedOptionIndex);
    }

    public String chooseFileFromDirectory(String directoryPath) {
        List<String> fileNames = FileLoader.listFilesInDirectory(directoryPath);

        if (fileNames.isEmpty()) {
            System.out.println(Colors.ANSI_RED + "Nincsenek betöltendő file-ok." + Colors.ANSI_RESET);
            return null;
        }

        for (int i = 0; i < fileNames.size(); i++) {
            System.out.println((i + 1) + ". " + fileNames.get(i));
        }

        int choice = -1;
        do {
            System.out.println("Válassz egy világot: ");
            while (!scanner.hasNextInt()) {
                System.out.println(Colors.ANSI_RED + "Érvénytelen választás, próbáld újra!" + Colors.ANSI_RESET);
                scanner.next();
            }
            choice = scanner.nextInt();
        } while (choice < 1 || choice > fileNames.size());

        return fileNames.get(choice - 1);
    }

    public String chooseFileFromDB() {
        //DataSource dataSource = DBInitializer.createDataSource();
        DBRepositoryInterface dbRepository = new DBRepositoryImpl();

        List<String> mapNames = dbRepository.getAllMapNames();
        if (mapNames.isEmpty()) {
            System.out.println(Colors.ANSI_YELLOW + "Nincsenek elmentett pályák az adatbázisban." + Colors.ANSI_RESET);
            return null;
        }

        for (int i = 0; i < mapNames.size(); i++) {
            System.out.println((i + 1) + ". " + mapNames.get(i));
        }

        int choice = -1;
        do {
            System.out.println("Válassz egy világot az adatbázisból.");
            while (!scanner.hasNextInt()) {
                System.out.println(Colors.ANSI_RED + "Érvénytelen választás, próbáld újra!" + Colors.ANSI_RESET);
                scanner.next();
            }
            choice = scanner.nextInt();
        } while (choice < 1 || choice > mapNames.size());

        return mapNames.get(choice - 1);
    }
}
