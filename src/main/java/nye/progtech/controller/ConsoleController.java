/**
 * Ez a package tartalmazza az összes controllert.
 */

package nye.progtech.controller;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import nye.progtech.Colors;
import nye.progtech.fileutils.FileLoader;
import nye.progtech.repository.DBRepositoryImpl;
import nye.progtech.repository.DBRepositoryInterface;

/**
 * Console Controller osztály.
 */
public class ConsoleController {
    /**
     * scanner objektum.
     */
    private static Scanner scanner;

    /**
     * Console controller konstruktor.
     */
    public ConsoleController() {
        scanner = new Scanner(System.in);
    }

    /**
     * scanner bezárása.
     */
    public static void closeScanner() {
            scanner.close();
    }

    /**
     * Bekéri a felhasználó nevét.
     *
     * @return scanner.nextLine()
     */
    public final String promptForUserName() {
        System.out.println(Colors.ANSI_GREEN
                + "Szia Idegen! Kérlek add meg a neved: "
                + Colors.ANSI_RESET);
        return scanner.nextLine();
    }

    /**
     * Felhasználó üdvözlése.
     *
     * @param userName felhasználó neve
     */
    public final void greetUser(final String userName) {
        System.out.println(Colors.ANSI_GREEN
                + "Üdvözöllek, "
                + userName
                + "! Kezdjünk is neki a játéknak."
                + Colors.ANSI_RESET);
    }

    /**
     * Game: Játékos interakciók bekérése.
     *
     * @return scanner.nextLine()
     */
    public String askForGameAction() {
        System.out.println(Colors.ANSI_YELLOW
                + "Az alábbi parancsok közül választhatsz, "
                + "de mindig vedd figyelembe, ");
        System.out.println("hogy az irányok az éppen aktuális irány "
                + "alapján kerülnek végrehajtásra!");
        System.out.println("forward: Lépés előre.");
        System.out.println("backward: Lépés hátra.");
        System.out.println("left: Lépés balra.");
        System.out.println("right: Lépés jobbra.");
        System.out.println("direction east|west|north|south: "
                + "Hős irányának beállítása.");
        System.out.println("fire: Nyíl kilővése az aktuális irányba.");
        System.out.println("save: Játékállás elmentése.");
        System.out.println("exit: Kilépés a játékból." + Colors.ANSI_RESET);
        System.out.println("Adj meg egy parancsot: ");
        return scanner.nextLine();
    }

    //Editor
    /**
     * Pálya méretének bekérése.
     *
     * @return scanner.nextInt()
     */
    public static int askForBoardSize() {
        System.out.println("Add meg a pálya méretét: ");
        return scanner.nextInt();
    }

    /**
     * Hős sorának bekérése.
     *
     * @return scanner.nextInt()
     */
    public static int askForHeroRow() {
        System.out.println("Add meg a hős sorát: ");
        return scanner.nextInt();
    }

    /**
     * Hős oszlopának bekérése.
     *
     * @return scanner.next().charAt(0)
     */
    public static char askForHeroColumn() {
        System.out.println("Add meg a hős oszlopát: ");
        return scanner.next().charAt(0);
    }

    /**
     * Hős irányának bekérése.
     *
     * @return scanner.next().charAt(0)
     */
    public static char askForHeroDirection() {
        String input;
        do {
            System.out.println("Add meg a hős irányát (N, E, S, W): ");
            input = scanner.next().toUpperCase();
            if (!input.equals("N")
                    && !input.equals("E")
                    && !input.equals("S")
                    && !input.equals("W")) {
                System.out.println(Colors.ANSI_RED
                        + "Érvénytelen irány. Próbáld újra!"
                        + Colors.ANSI_RESET);
            }
        } while (!input.equals("N")
                && !input.equals("E")
                && !input.equals("S")
                && !input.equals("W"));
        return input.charAt(0);
    }

    /**
     * Pálya nevének bekérése.
     *
     * @return scanner.nextLine()
     */
    public static String askForMapName() {
        System.out.println("Add meg a pálya nevét: ");
        scanner.nextLine();
        return scanner.nextLine();
    }

    /**
     * Map Editor interakciók bekérése.
     *
     * @return scanner.nextLine()
     */
    public static String askForEditorInput() {
        System.out.println("Add meg a pályaelemet a következő formátumban: "
                        + "[add|delete], sorszám, oszlop, pályaelem");
        System.out.println("Pályaelem lehet a következő: "
                + "U - Wumpus, P - Verem, G - Arany");
        System.out.println("Befejezéshez gépeld be a 'kilép' parancsot.");
        return scanner.nextLine();
    }

    //Main menu

    /**
     * Főmenü megjelenítése.
     */
    public final void displayMainMenu() {
        System.out.println(Colors.ANSI_BLUE
                + "\n--- Főmenü ---"
                + Colors.ANSI_RESET);
        for (MenuOption option : MenuOption.values()) {
            System.out.println(option.getValue()
                    + ". "
                    + option.getDescription());
        }
    }

    /**
     * Formátum választó menü megjelenítése.
     */
    public final void displayFormatSelectorMenu() {
        System.out.println(Colors.ANSI_BLUE
                + "--- Formátumok ---"
                + Colors.ANSI_RESET);
        for (FileFormat format : FileFormat.values()) {
            System.out.println(format.getValue()
                    + ". "
                    + format.getDescription());
        }
    }

    /**
     * A kiválasztott opció.
     *
     * @return a kiválasztott menu opció int-je
     */
    public static MenuOption getSelectedOption() {
        try {
            int selectedOptionIndex = scanner.nextInt();
            return MenuOption.fromInt(selectedOptionIndex);
        } catch (InputMismatchException e) {
            scanner.nextLine();
            return MenuOption.fromInt(-1);
        }
    }

    /**
     * A kiválasztott file formátum.
     *
     * @return A kiválasztott file formátum int-je.
     */
    public static FileFormat getSelectedFileFormat() {
        int selectedOptionIndex = scanner.nextInt();
        return FileFormat.fromInt(selectedOptionIndex);
    }

    /**
     * File mappából kiválasztása.
     *
     * @param directoryPath könyvtár neve
     *
     * @return file-ok neve
     */
    public static String chooseFileFromDirectory(final String directoryPath) {
        List<String> fileNames = FileLoader.listFilesInDirectory(directoryPath);

        if (fileNames.isEmpty()) {
            System.out.println(Colors.ANSI_RED
                    + "Nincsenek betöltendő file-ok."
                    + Colors.ANSI_RESET);
            return null;
        }

        for (int i = 0; i < fileNames.size(); i++) {
            System.out.println((i + 1) + ". " + fileNames.get(i));
        }

        int choice;
        do {
            System.out.println("Válassz egy világot: ");
            while (!scanner.hasNextInt()) {
                System.out.println(Colors.ANSI_RED
                        + "Érvénytelen választás, próbáld újra!"
                        + Colors.ANSI_RESET);
                scanner.next();
            }
            choice = scanner.nextInt();
        } while (choice < 1 || choice > fileNames.size());

        return fileNames.get(choice - 1);
    }

    /**
     * Adatbázisból kiválasztás.
     *
     * @return pályák neve.
     */
    public static String chooseFileFromDB() {
        DBRepositoryInterface dbRepository = new DBRepositoryImpl();

        List<String> mapNames = dbRepository.getAllMapNames();
        if (mapNames.isEmpty()) {
            System.out.println(Colors.ANSI_YELLOW
                    + "Nincsenek elmentett pályák az adatbázisban."
                    + Colors.ANSI_RESET);
            return null;
        }

        for (int i = 0; i < mapNames.size(); i++) {
            System.out.println((i + 1) + ". " + mapNames.get(i));
        }

        int choice;
        do {
            System.out.println("Válassz egy világot az adatbázisból.");
            while (!scanner.hasNextInt()) {
                System.out.println(Colors.ANSI_RED
                        + "Érvénytelen választás, próbáld újra!"
                        + Colors.ANSI_RESET);
                scanner.next();
            }
            choice = scanner.nextInt();
        } while (choice < 1 || choice > mapNames.size());

        return mapNames.get(choice - 1);
    }
}
