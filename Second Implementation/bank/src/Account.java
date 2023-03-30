public class Account {
    public static String file="files\\accounts.txt";
    public static void createAccount(int id, int account, int balance){
        if(AccountValidation.existenceAccount(id,account,file)){
            AccountDAO.createAccount(id,account, balance);
        }
    }
    public static void readAccount(){
        AccountDAO.readAccount();
    }
    public static void deleteAccount(int id,  int account){
        AccountDAO.deleteAccount(id, account);
    }
}
