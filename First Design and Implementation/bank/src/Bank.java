//Clase principal
public class Bank
{
    //Main que llama a todos las clase del sistema
    public static void main(String[] args) throws Exception
    { 
        //Objetos de las clases 
        Report myReporte = new Report();
        CRUD mycrud = new CRUD();
        //Llamadas
        mycrud.create(123456789,"Octavio Villanueva");
        mycrud.create(123456789,"Octavio Villanueva");
        mycrud.create(987654321,"Gustavo Romero");
        mycrud.create(987654321,"Juan Xix");
        mycrud.create(456123987,"Fernando Valencia");
        mycrud.delete(456123987,"Fernando Valencia", "clients.txt");
        mycrud.update(987654321,"Gustavo Romero","Beatriz Poot");
        mycrud.create(123456789,645789123,2500);
        mycrud.create(987654321,645789123,4000);
        mycrud.create(987654321,147852963,4000);
        
        //mycrud.read("accounts.txt");
        //mycrud.read("clients.txt");
        
        //myReporte.printReport();
    }
}