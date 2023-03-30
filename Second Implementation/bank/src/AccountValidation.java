import java.nio.file.Paths;
import java.util.Scanner;

public class AccountValidation
{
    //Método  que valida si existe el dato mandado, devolviendo un booleano
    public static boolean existenceAccount(int id, int data, String file){
        //Se revisa line por linea el archivo mandado
        try (Scanner scanner = new Scanner(Paths.get(file))) {
            final String secretKey = "clave";
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String decryptedText = "";
                decryptedText = Algorithm.decryptTL(line, secretKey); 
                if (decryptedText!=null){
                    String[] cadenas = decryptedText.split(",");
                    int cadena1 = Integer.valueOf(cadenas[0]);
                    int cadena2 = Integer.valueOf(cadenas[1]);
                    //Si los datos dados existen en el CSV entonces se envia una alerta y se retorna un false
                    if (cadena1==id){
                        return false;
                    }
                    if (cadena2==data){
                        return false;
                    }
                }
            }        
        }catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
         //Si no se encontraron coinciderncias en el archivo se retorna un true
        return true;
    }
 
}