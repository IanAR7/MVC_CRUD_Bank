/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import Service.Algorithm;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author gamecore
 */
public class ConnectionDB { 
    private Connection con;
       
    public Connection getConnection() {
        String URL = "Q+IwMnrNsevgkUhiBQ8xKMca+ygsdQ5kc5gyUqgqflddLkqMYoTQ0jQ9+XGJWRHl";
        String USER = "VTUW6p0Ln1WkA/WGUqQM3A==";
        String PASS = "";
        try{
            String secretKey = "clave";
            String decryptURL = Algorithm.decryptTL(URL, secretKey);
            String decryptUSER = Algorithm.decryptTL(USER, secretKey);
            con = DriverManager.getConnection(decryptURL, decryptUSER,PASS);            
        }catch(SQLException e){
            System.out.println("Error al obtener la conexión: " + e.getMessage());
        }
        return con;
    }
    
    public void closeConnection() {
        try {
            con.close();
        } catch (SQLException e) {
            System.out.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }
    
}
