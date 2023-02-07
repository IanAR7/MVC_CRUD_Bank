package freedb;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

//Clase que desencripta los datos del archivo dado
//Utiliza el metodo decryptTextLine de la clase Encrypt para desencriptar los datos
public class Decrypt {
    public static String decrypt(String file){
        try {
            final String secretKey = "clave";
            File file = new File(file);
            Scanner scan = new Scanner(file);
            String encryptedText = Encrypt.encryptTextLine(scan.nextLine(), secretKey);
            String Text = Encrypt.decryptTextLine(encryptedText, secretKey);
            return Text;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Decrypt.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }

}

