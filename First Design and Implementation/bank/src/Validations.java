import java.nio.file.Paths;
import java.util.Scanner;

public class Validations
{
    //Método  que valida si existe el dato mandado, devolviendo un booleano
    public static boolean existence(int id, String data, String file){
        //Se revisa line por linea el archivo mandado
        try (Scanner scanner = new Scanner(Paths.get(file))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] cadenas = line.split(",");
                int cadena1 = Integer.valueOf(cadenas[0]);
                String cadena2 = cadenas[1];
                //Si los datos dados existen en el CSV entonces se envia una alerta y se retorna un false
                if (cadena1==id){
                    return false;
                }
                if (cadena2.equals(data)){
                    return false;
                }
            }        
        }catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
         //Si no se encontraron coinciderncias en el archivo se retorna un true
        return true;
    }
 
}