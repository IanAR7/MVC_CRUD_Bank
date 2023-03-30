import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.List;
import com.itextpdf.layout.element.ListItem;
import com.itextpdf.layout.element.Paragraph;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.nio.file.Paths;

public class Report {
    private static String fileClient="files\\clients.txt";
    private static String fileAccount="files\\accounts.txt";
    //Clase que genera un reporte de los clientes con sus respectivas cuentas
    //Utilizo 2 scanners uno para el archivo de clientes y otro para el archivo de cuentas
    //Cada linea de cada archivo se divide y se inserta en un array "parts" para despues imprimirlo y guardarla información un PDF
    public static void printReport(){
        
        //Lama al archivo de clientes para escanearlo
        try (Scanner scanner = new Scanner(Paths.get(fileClient))) {
            //int cont=1;
            File file = new File("reports\\BankReport.pdf");
            PdfWriter pdfWriter = new PdfWriter(file);
            PdfDocument pdfDocument = new PdfDocument(pdfWriter);
            Document document = new Document(pdfDocument);
            final String secretKey = "clave";
            //El ciclo lee linea por linea el archivo para imprimir los clientes
            while (scanner.hasNextLine()) {
                String lineClient = scanner.nextLine();
                String decryptedLine = "";
                decryptedLine= Algorithm.decryptTL(lineClient, secretKey); 
                String[] parts = decryptedLine.split(",");
                int idClient = Integer.valueOf(parts[0]);
                String client = parts[1];
                //Lama al archivo de cuentas para escanearlo
                Scanner scanner2 = new Scanner(Paths.get(fileAccount));
                Paragraph paragraph1 = new Paragraph("CLIENT "+idClient+": "+client);
                document.add(paragraph1);
                Paragraph paragraph2 = new Paragraph("-ACCOUNTS-");
                document.add(paragraph2);
                 //El ciclo lee linea por linea el archivo para imprimir las cuentas
                List list = new List().setSymbolIndent(12).setListSymbol("\u2022");
                while (scanner2.hasNextLine()) {
                    String lineAccount = scanner2.nextLine();
                    String decryptedText = "";
                    decryptedText= Algorithm.decryptTL(lineAccount, secretKey); 
                    String[] parts2 = decryptedText.split(",");
                    int idAccount = Integer.valueOf(parts2[0]);
                    String account = parts2[1];
                    String balance = parts2[2];
                    //Si los ids son iguales signfica que la cuenta corresponde a tal cliente
                    if (idClient==idAccount){
                        list.add(new ListItem(account+": "+balance));
                    }
                }
                document.add(list);
            }          
            document.close();
            pdfWriter.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Error: " +ex.getMessage());
        } catch (IOException ex) {
            System.out.println("Error: " +ex.getMessage());
        }
            
    }
}