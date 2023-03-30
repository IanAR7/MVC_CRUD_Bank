import java.nio.file.Paths;
import java.io.File;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.BufferedReader;

public class ClientDAO extends Client{
    //Método que crea y agrega clientes
    public static void createClient(int id, String client){
        //Se verifica si el archivo existe para abrirlo, si no existe crea uno
        try (FileWriter fw = new FileWriter(file, true)){  
            StringBuilder sb = new StringBuilder();
            sb.append(id);
            sb.append(',');
            sb.append(client);
            sb.append(',');
            final String secretKey = "clave";
            //Se encripta la linea de texto con el id y el cliente
            String encryptedText = "";
            encryptedText = Algorithm.encryptTL(sb.toString(), secretKey);
            //Se inserta la linea de texto al final del txt
            fw.append(encryptedText);
            fw.append("\n");
            fw.close();
        }catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    //Método  que imprime y lee el archivo dado 
    public static void readClient(){
        //Lama al archivo para escanearlo
        try (Scanner scanner = new Scanner(Paths.get(file))) {
            //El ciclo lee linea por linea el archivo para imprimirlas
            final String secretKey = "clave";
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String decryptedLine = "";
                decryptedLine= Algorithm.decryptTL(line, secretKey); 
                System.out.println(decryptedLine+"\n");
            }
        }catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    //Método que modifica el nombre de un client
    public static void updateClient(int id, String nameClient, String newName){
        try {
            //Se crea una variable File que contiene el nombre del archivo original 
            File inFile = new File(file);     
            //Se crea una variable File que contiene el nombre del archivo nuevo 
            File tempFile = new File(inFile.getAbsolutePath() + ".tmp");
            BufferedReader br = new BufferedReader(new FileReader(inFile));
            PrintWriter pw = new PrintWriter(new FileWriter(tempFile));
            String line = null;
            String stringID=String.valueOf(id);
            final String secretKey = "clave";
            //Se lee el archivo original para escribir en el nuevo
            //Si se lee los mismos datos dados en el archivo original entonces no se escriben en el nuevo
            //En vez de eso en el nuevo archivo se escribe el nuevo nombre de cliente asignado
            while ((line = br.readLine()) != null) {
                String decryptedLine = "";
                decryptedLine= Algorithm.decryptTL(line, secretKey); 
                if (!decryptedLine.trim().equals(stringID+","+nameClient+",")) {
                    pw.println(line);
                    pw.flush();
                }else{
                    StringBuilder sb = new StringBuilder();
                    sb.append(id);
                    sb.append(',');
                    sb.append(newName);
                    sb.append(',');
                    String encryptedText = "";
                    encryptedText = Algorithm.encryptTL(sb.toString(), secretKey);
                    pw.append(encryptedText);
                    pw.append("\n");
                }
            }
            pw.close();
            br.close();
            //Borrar el archivo origional
            inFile.delete();
            //Renombrar el archivo nuevo con el nombre del archivo original
            tempFile.renameTo(inFile);
        }catch(Exception e){
            System.out.println("Error: " + e.getMessage());
        }
    }
    //Método que borra una linea especifica de un archivo dado
    public static void deleteClient(int id,  String client){
        try {
            //Se crea una variable File que contiene el nombre del archivo original 
            File inFile = new File(file);     
            //Se crea una variable File que contiene el nombre del archivo nuevo 
            File tempFile = new File(inFile.getAbsolutePath() + ".tmp");
            BufferedReader br = new BufferedReader(new FileReader(inFile));
            PrintWriter pw = new PrintWriter(new FileWriter(tempFile));
            String line = null;
            String stringID=String.valueOf(id);
            final String secretKey = "clave";
            String data=String.valueOf(client);
            //Se lee el archivo original para escribir en el nuevo
            //Si se lee los mismos datos dados en el archivo original entonces no se escriben en el nuevo
            //De esta manera parecera que se borro
            while ((line = br.readLine()) != null) {
                String decryptedLine = "";
                decryptedLine= Algorithm.decryptTL(line, secretKey); 
                if (!decryptedLine.trim().equals(stringID+","+data+",")) {
                    pw.println(line);
                    pw.flush();
                }
            }
            pw.close();
            br.close();
            //Borrar el archivo origional
            inFile.delete();
            //Renombrar el archivo nuevo con el nombre del archivo original
            tempFile.renameTo(inFile);
        }catch(Exception e){
            System.out.println("Error: " + e.getMessage());
        }
    }
}
