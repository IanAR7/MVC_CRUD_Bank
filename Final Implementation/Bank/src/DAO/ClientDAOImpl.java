package DAO;

import Model.Client;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ClientDAOImpl implements ClientDAO {
    ConnectionDB connector = new ConnectionDB();
    Connection connection;

    @Override
    public void addClient(Client client) {
        try {
            String insertQuery = "INSERT INTO clients (curp, name) VALUES (?, ?)";
            connection = connector.getConnection();
            PreparedStatement statement = connection.prepareStatement(insertQuery);
            statement.setString(1, client.getCurp());
            statement.setString(2, client.getName());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al agregar elemento: " + e.getMessage());
        }
    }

    @Override
    public void removeClient(String idClient) {
        try {
            String deleteQuery = "DELETE FROM clients WHERE curp = ?";
            connection = connector.getConnection();
            PreparedStatement statement = connection.prepareStatement(deleteQuery);
            statement.setString(1, idClient);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al agregar elemento: " + e.getMessage());
        }
    }

    @Override
    public Client showClient(String idClient) {
        Client client = null;
        try {
            String selectQuery = "SELECT * FROM clients WHERE curp = ?";
            connection = connector.getConnection();
            PreparedStatement statement = connection.prepareStatement(selectQuery);
            statement.setString(1, idClient);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String nombre = resultSet.getString("nombre");
                String curp = resultSet.getString("curp");
                client = new Client(nombre, curp);
            }
        } catch (SQLException e) {
            System.out.println("Error al agregar elemento: " + e.getMessage());
        }
        return client;
    }

    @Override
    public List<Client> showAllClients() {
        List<Client> clientes = new ArrayList<>();
        try {        
            String selectQuery = "SELECT * FROM clients";
            connection = connector.getConnection();
            PreparedStatement statement = connection.prepareStatement(selectQuery);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String nombre = resultSet.getString("name");
                String curp = resultSet.getString("curp");
                Client cliente = new Client(nombre, curp);
                clientes.add(cliente);
            }
        } catch (SQLException e) {
            System.out.println("Error al agregar elemento: " + e.getMessage());
        }
        return clientes;
    }
    
    @Override
    public boolean existClient(String idClient){
        try {
            connection = connector.getConnection();
            String searchQuery = "SELECT COUNT(*) FROM clients WHERE curp = ?";
            PreparedStatement statement = connection.prepareStatement(searchQuery);
            statement.setString(1, idClient);

            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);

            connection.close();

            return count > 0;
        } catch (SQLException e) {
            System.out.println("Error al agregar elemento: " + e.getMessage());
        }

        return false;
    }
}

