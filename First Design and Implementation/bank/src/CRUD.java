import java.nio.file.Paths;
import java.io.File;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.BufferedReader;
//Clase que se encarga del CRUD del CSV
public class CRUD
{
    //Método que crea y agrega clientes
    public static void create(int id, String client ){
        String Clients ="clients.txt";
        Validations validate = new Validations();
        //Se verifica si el archivo existe para abrirlo, si no existe crea uno
        try (FileWriter fw = new FileWriter(Clients, true)){
            //Validación de existencia del cliente
            if(validate.existence(id,client,Clients)){
                StringBuilder sb = new StringBuilder();
                sb.append(id);
                sb.append(',');
                sb.append(client);
                sb.append(',');
                sb.append('\n');
                //Se inserta el nuevo cliente al final del txt
                fw.append(sb.toString());
                fw.close();
            }
        }catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    //Método que crea y agrega cuentas
    public static void create(int id, int account, int balance){
        String Accounts ="accounts.txt";
        Validations validate = new Validations();
        //Se verifica si el archivo existe para abrirlo, si no existe crea uno
        try (FileWriter fw = new FileWriter(Accounts, true)){
            //Validación de existencia de la cuenta
            String stringAccount=String.valueOf(account);
            if(validate.existence(id,stringAccount,Accounts)){
                StringBuilder sb = new StringBuilder();
                sb.append(id);
                sb.append(',');
                sb.append(account);
                sb.append(',');
                sb.append(balance);
                sb.append(',');
                sb.append('\n');
                 //Se inserta la nueva cuenta al final del txt
                fw.append(sb.toString());
                fw.close();
            }
        }catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    //Método  que imprime y lee el archivo dado 
    public static void read(String file){
        //Lama al archivo para escanearlo
        try (Scanner scanner = new Scanner(Paths.get(file))) {
            //El ciclo lee linea por linea el archivo para imprimirlas
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();   
                System.out.println(line+"\n");
            }
        }catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    //Método que borra una linea especifica de un archivo dado
    public static void delete(int id,  String data, String file){
        try {
            //Se crea una variable File que contiene el nombre del archivo original 
            File inFile = new File(file);     
            //Se crea una variable File que contiene el nombre del archivo nuevo 
            File tempFile = new File(inFile.getAbsolutePath() + ".tmp");
            BufferedReader br = new BufferedReader(new FileReader(inFile));
            PrintWriter pw = new PrintWriter(new FileWriter(tempFile));
            String line = null;
            String stringID=String.valueOf(id);
            //Se lee el archivo original para escribir en el nuevo
            //Si se lee los mismos datos dados en el archivo original entonces no se escriben en el nuevo
            //De esta manera parecera que se borro
            while ((line = br.readLine()) != null) {
                if(file.equals("clients.txt")){
                    if (!line.trim().equals(stringID+","+data+",")) {
                        pw.println(line);
                        pw.flush();
                    }
                }
                if(file.equals("accounts.txt")){
                    if (!line.trim().equals(stringID+","+data+",0,")) {
                        pw.println(line);
                        pw.flush();
                    }
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
    //Método que modifica el nombre de un client
    public static void update(int id, String nameclient, String newname){
        try {
            //Se crea una variable File que contiene el nombre del archivo original 
            File inFile = new File("clients.txt");     
            //Se crea una variable File que contiene el nombre del archivo nuevo 
            File tempFile = new File(inFile.getAbsolutePath() + ".tmp");
            BufferedReader br = new BufferedReader(new FileReader(inFile));
            PrintWriter pw = new PrintWriter(new FileWriter(tempFile));
            String line = null;
            String stringID=String.valueOf(id);
            //Se lee el archivo original para escribir en el nuevo
            //Si se lee los mismos datos dados en el archivo original entonces no se escriben en el nuevo
            //En vez de eso en el nuevo archivo se escribe el nuevo nombre de cliente asignado
            while ((line = br.readLine()) != null) {
                if (!line.trim().equals(stringID+","+nameclient+",")) {
                    pw.println(line);
                    pw.flush();
                }else{
                    StringBuilder sb = new StringBuilder();
                    sb.append(id);
                    sb.append(',');
                    sb.append(newname);
                    sb.append(',');
                    sb.append('\n');
                    pw.append(sb.toString());
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