package nye.progtech.controller;

import java.util.Scanner;

public class ConsoleController {
    private Scanner scanner;

    public ConsoleController(Scanner scanner) {
        this.scanner = scanner;
    }

    public String promptForUserName() {
        System.out.println("Szia Idegen! Kérlek add meg a neved: ");
        return scanner.nextLine();
    }

    public void greetUser(String userName) {
        System.out.println("Üdvözöllek, " + userName + "! Kezdjünk is neki a játéknak.");
    }
}
