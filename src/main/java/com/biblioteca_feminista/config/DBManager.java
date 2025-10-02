package com.biblioteca_feminista.config;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBManager {

    private static final String URL = ;
    private static final String USER = ;
    private static final String PASS = ;
    private static Connection connection;;
    
    public static Connection init (){
        
        try{
            connection = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("conexión exitosa");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return connection;
    }

    public static void close (){
        try {
            connection.close();
            System.out.println("Desconexión exitosa");
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

    }






}
