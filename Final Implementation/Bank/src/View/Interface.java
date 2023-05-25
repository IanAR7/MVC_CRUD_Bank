package View;
import Controller.Controller;
import java.util.Scanner;

public class Interface {
    private Controller controller;
    private Scanner scanner;

    public Interface(Controller controller) {
        this.controller = controller;
        scanner = new Scanner(System.in);
    }

    public void menu() {
        int option = 0;
        while (option != 6) {
            System.out.println("==== Menú ====");
            System.out.println("1. Agregar cliente");
            System.out.println("2. Eliminar cliente");
            System.out.println("3. Agregar cuenta");
            System.out.println("4. Eliminar cuenta");
            System.out.println("5. Reporte de banco");
            System.out.println("6. Salir");
            System.out.print("Ingrese una opción: ");
            option = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea

            switch (option) {
                case 1:
                    addClient();
                    break;
                case 2:
                    removeClient();
                    break;
                case 3:
                    addAccount();
                    break;
                case 4:
                    removeAccount();
                    break;
                case 5:
                    createReport();
                    break;
                case 6:
                    System.out.println("¡Hasta luego!");
                    break;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
                    break;
            }
        }
    }

    private void addClient() {
        System.out.print("Ingrese el nombre del cliente: ");
        String nameClient = scanner.nextLine();

        System.out.print("Ingrese el CURP del cliente: ");
        String curpClient = scanner.nextLine();
        
        controller.addClient(nameClient, curpClient);
    }

    private void removeClient() {
        System.out.print("Ingrese el CURP del cliente a eliminar: ");
        String idClient = scanner.nextLine();
        scanner.nextLine(); // Consumir el salto de línea

        controller.removeClient(idClient);
    }
    
    private void addAccount() {
        System.out.print("Ingrese el CURP del cliente: ");
        String curpClient = scanner.nextLine();
        
        controller.addAccount(curpClient);
    }

    private void removeAccount() {
        System.out.print("Ingrese el CURP del cliente: ");
        String curpClient = scanner.nextLine();
        System.out.print("Ingrese el ID de la cuenta a eliminar: ");
        int idAccount = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea

        controller.removeAccount(curpClient, idAccount);
    }
    
    private void createReport() {
        controller.createReport();
    }
}

