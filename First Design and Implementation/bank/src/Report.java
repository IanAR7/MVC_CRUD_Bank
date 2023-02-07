import java.nio.file.Paths;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Report{
    //Clase que genera un reporte de los clientes con sus respectivas cuentas
    //Utilizo 2 scanners uno para el archivo de clientes y otro para el archivo de cuentas
    //Cada linea de cada archivo se divide y se inserta en un array "parts" para despues imprimirlo y guardarla información un PDF
    public static void printReport(){
        //Lama al archivo de clientes para escanearlo
        try (Scanner scanner = new Scanner(Paths.get("clients.txt"))) {
            //El ciclo lee linea por linea el archivo para imprimir los clientes
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                int idClient = Integer.valueOf(parts[0]);
                String Client = parts[1];
                //Lama al archivo de cuentas para escanearlo
                Scanner scanner2 = new Scanner(Paths.get("accounts.txt"));
                System.out.println("CLIENTE: "+Client);
                System.out.println("-CUENTAS-");
                 //El ciclo lee linea por linea el archivo para imprimir las cuentas
                while (scanner2.hasNextLine()) {
                    String line2 = scanner2.nextLine();
                    String[] parts2 = line2.split(",");
                    int idAccount = Integer.valueOf(parts2[0]);
                    int Account = Integer.valueOf(parts2[1]);
                    //Si los ids son iguales signfica que la cuenta corresponde a tal cliente
                    if (idClient==idAccount){
                        System.out.println(Account);
                    }
                }
            }
        }catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}