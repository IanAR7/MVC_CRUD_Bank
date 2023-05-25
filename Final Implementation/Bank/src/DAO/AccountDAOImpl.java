package DAO;

import Model.Account;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountDAOImpl implements AccountDAO {

    ConnectionDB connector = new ConnectionDB();
    Connection connection = connector.getConnection();


    @Override
    public void addAccount(String idClient, Account account) {
        try {
            String insertQuery = "INSERT INTO accounts (idClient, idAccount, balance) VALUES (?, ?, ?)";        
            connection = connector.getConnection();
            PreparedStatement statement = connection.prepareStatement(insertQuery);
            statement.setString(1, idClient);
            statement.setInt(2, account.getIdAccount());
            statement.setDouble(3, account.getBalance());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al agregar elemento: " + e.getMessage());
        }
    }

    @Override
    public void removeAllAccounts(String idClient) {
        try {
            String deleteQuery = "DELETE FROM accounts WHERE idClient = ?";
            connection = connector.getConnection();
            PreparedStatement statement = connection.prepareStatement(deleteQuery);
            statement.setString(1, idClient);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al agregar elemento: " + e.getMessage());
        }
    }
    
    @Override
    public void removeAccount(String idClient, int idAccount) {
        try {
            String deleteQuery = "DELETE FROM accounts WHERE idClient = ? AND idAccount = ?";
            connection = connector.getConnection();
            PreparedStatement statement = connection.prepareStatement(deleteQuery);
            statement.setString(1, idClient);
            statement.setInt(2, idAccount);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al agregar elemento: " + e.getMessage());
        }
    }
    
    
    @Override
    public void deposit(String idClient, int idAccount, double amount){
        try {
            String balanceQuery = "SELECT balance FROM accounts WHERE idClient = ? AND idAccount = ?";
            connection = connector.getConnection();
            PreparedStatement statement = connection.prepareStatement(balanceQuery);
            statement.setString(1, idClient);
            statement.setInt(2, idAccount);
            ResultSet resultBalance = statement.executeQuery();

            if (resultBalance.next()) {
                double currentBalance = resultBalance.getDouble("balance");
                double newBalance = currentBalance + amount;

                String updateBalance = "UPDATE accounts SET balance = ? WHERE idClient = ? AND idAccount = ?";
                PreparedStatement updateBalanceQuery = connection.prepareStatement(updateBalance);
                updateBalanceQuery.setDouble(1, newBalance);
                updateBalanceQuery.setString(2, idClient);
                updateBalanceQuery.setInt(3, idAccount);
                updateBalanceQuery.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void withdraw(String idClient, int idAccount, double amount){
        try {
            String balanceQuery = "SELECT balance FROM accounts WHERE idClient = ? AND idAccount=?";
            connection = connector.getConnection();
            PreparedStatement statement = connection.prepareStatement(balanceQuery);
            statement.setString(1, idClient);
            statement.setInt(2, idAccount);
            ResultSet resultBalance = statement.executeQuery();

            if (resultBalance.next()) {
                double currentBalance = resultBalance.getDouble("balance");

                if (currentBalance >= amount) {
                    double newBalance = currentBalance - amount;

                    String updateBalance = "UPDATE accounts SET balance = ? WHERE idClient = ? AND idAccount=?";
                    PreparedStatement updateBalanceQuery = connection.prepareStatement(updateBalance);
                    updateBalanceQuery.setDouble(1, newBalance);
                    updateBalanceQuery.setString(2, idClient);
                    updateBalanceQuery.setInt(3, idAccount);
                    updateBalanceQuery.executeUpdate();

                } else {
                    System.out.println("Saldo insuficiente para realizar el retiro.");
                }
                
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Account> showAccounts(String curpClient) {
        List<Account> accounts = new ArrayList<>();
        try {
            String selectQuery = "SELECT * FROM accounts WHERE idClient = ?";
            connection = connector.getConnection();
            PreparedStatement statement = connection.prepareStatement(selectQuery);
            statement.setString(1, curpClient);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String idClient = resultSet.getString("idClient");
                int idAccount = resultSet.getInt("idAccount");
                Account account = new Account(idClient, idAccount);
                accounts.add(account);
            }
        } catch (SQLException e) {
            System.out.println("Error al agregar elemento: " + e.getMessage());
        }
        return accounts;
    }
    
    
    
}

