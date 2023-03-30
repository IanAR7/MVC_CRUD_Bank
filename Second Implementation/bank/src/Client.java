public class Client {
    public static String file="files\\clients.txt";
    
    public static void createClient(int id, String client){
        if(ClientValidation.existenceClient(id,client,file)){
            ClientDAO.createClient(id,client);
        }
    }
    public static void readClient(){
        ClientDAO.readClient();
    }
    public static void updateClient(int id, String nameClient, String newName){
        ClientDAO.updateClient(id, nameClient, newName);
    }
    public static void deleteClient(int id,  String client){
        ClientDAO.deleteClient(id, client);
    }
}
