package Controller;
import DAO.AccountDAO;
import DAO.ClientDAO;
import Model.Account;
import Service.Algorithm;
import Model.Client;
import Service.Validation;
import java.util.List;
import java.util.Random;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.ListItem;
import com.itextpdf.layout.element.Paragraph;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Controller {
    private ClientDAO clientDAO;
    private AccountDAO accountDAO;
    private Validation validator;
    private Algorithm algorithm;
    String secretKey = "clave";
    

    public Controller(ClientDAO clientDAO, AccountDAO accountDAO, Validation validator, Algorithm algorithm) {
        this.clientDAO = clientDAO;
        this.accountDAO = accountDAO;
        this.validator = validator;
        this.algorithm = algorithm;
    }

    public void addClient(String nameClient, String curpClient) {
        if (validator.validateCURP(curpClient)){
            String encryptCURP = algorithm.encryptTL(curpClient, secretKey); 
            String encryptName = algorithm.encryptTL(nameClient, secretKey); 
            if (existClient(encryptCURP)){
                System.out.println("Cliente existente");
            }else{
                Client client = new Client(encryptName, encryptCURP);
                clientDAO.addClient(client);
                // Se crea una Account para el client
                Account account = new Account(encryptCURP, generateID());
                accountDAO.addAccount(encryptCURP, account);
                System.out.println("Cliente agregado correctamente.");
                System.out.println("ID de Cuenta:"+ account.getIdAccount());
            }
        }
    }

    public void removeClient(String idClient) {
        String encryptCURP = algorithm.encryptTL(idClient, secretKey);
        if (existClient(encryptCURP)){
            clientDAO.removeClient(idClient);
            // Se eliminan todas las Accounts del client
            accountDAO.removeAllAccounts(idClient);
            System.out.println("Cliente eliminado correctamente.");
        }else{
            System.out.println("Cliente no existente");
        }
    }
    
    public Client showClient(String idClient) {
        return clientDAO.showClient(idClient);
    }

    public List<Client> showAllClients() {
        return clientDAO.showAllClients();
    }

    public void addAccount(String curpClient) {
        String encryptCURP = algorithm.encryptTL(curpClient, secretKey);
        if (existClient(encryptCURP)){
            Account account = new Account(encryptCURP, generateID());
            accountDAO.addAccount(encryptCURP, account);
            System.out.println("Cuenta agregada al cliente correctamente."); 
            System.out.println("ID de Cuenta:"+ account.getIdAccount());
        }else{
            System.out.println("Cliente no existente");
        }  
    }

    public void removeAccount(String idClient, int idAccount) {
        String encryptCURP = algorithm.encryptTL(idClient, secretKey);
        if (existClient(encryptCURP)){
            accountDAO.removeAccount(idClient, idAccount);
            System.out.println("Cuenta eliminada correctamente.");
        }else{
            System.out.println("Cliente no existente");
        }
    }

    public List<Account> showAccounts(String idClient) {
        return accountDAO.showAccounts(idClient);
    }
    
    public void deposit(String idClient, int idAccount, double amount){
        String encryptCURP = algorithm.encryptTL(idClient, secretKey);
        if (existClient(encryptCURP)){
            accountDAO.deposit( encryptCURP,  idAccount,  amount);
            System.out.println("Depósito realizado.");
        }else{
            System.out.println("Cliente no existente");
        } 
    }
    
    public void withdraw(String idClient, int idAccount, double amount){
        String encryptCURP = algorithm.encryptTL(idClient, secretKey);
        if (existClient(encryptCURP)){
            accountDAO.withdraw( encryptCURP,  idAccount,  amount);
            System.out.println("Retiro realizado.");
        }else{
            System.out.println("Cliente no existente");
        } 
    }
    
    public void createReport() {
        try{
            File file = new File("src\\Reports\\BankReport.pdf");
            PdfWriter pdfWriter = new PdfWriter(file);
            PdfDocument pdfDocument = new PdfDocument(pdfWriter);
            Document document = new Document(pdfDocument);                               
            List<Client> clients = showAllClients();
            if (clients.isEmpty()) {
                Paragraph pg2 = new Paragraph("No hay clientes registrados.");
                document.add(pg2);
            } else {
                Paragraph pg = new Paragraph("\n==== CLIENTES ====");
                document.add(pg);
                for (Client client : clients) {
                    String decryptCURP = algorithm.decryptTL(client.getCurp(), secretKey);
                    String decryptName = algorithm.decryptTL(client.getName(), secretKey);
                    com.itextpdf.layout.element.List list = new com.itextpdf.layout.element.List().setSymbolIndent(12).setListSymbol("\u2022");
                    Paragraph pg1 = new Paragraph("\nNombre: " + decryptName);
                    document.add(pg1);
                    Paragraph pg2 = new Paragraph("\nCURP: " + decryptCURP);
                    document.add(pg2);
                    List<Account> accounts = showAccounts(client.getCurp());
                    if (accounts.isEmpty()) {
                        Paragraph pg3 = new Paragraph("El client no tiene cuentas registradas.");
                        document.add(pg3);
                    } else {
                        Paragraph pg3 = new Paragraph(" == Cuentas del cliente ==");
                        document.add(pg3);
                        for (Account account : accounts) {
                            list.add(new ListItem(account.getIdAccount()+": "+account.getBalance()));
                        }
                        document.add(list);
                    }
                    System.out.println("");
                }
                document.close();
                pdfWriter.close();
                System.out.println("Reporte creado correctamente");
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Error: " +ex.getMessage());
        } catch (IOException ex) {
            System.out.println("Error: " +ex.getMessage());
        }
    }
    
    private int generateID() {
        Random random = new Random();
        int min = 100_000_000; // Mínimo valor de 8 dígitos
        int max = 999_999_999; // Máximo valor de 9 dígitos
        return random.nextInt(max - min + 1) + min;
    }
    
    private boolean existClient(String idClient){
        return clientDAO.existClient(idClient);
    }
    
}

