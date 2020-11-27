package com.mycompany.db.viewer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Database {

    private Connection con;
    private static Database instance;
    private DatabaseMetaData md;
    
//    public Database() {
//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//            this.con = DriverManager.getConnection(
//                "jdbc:mysql://mozart.dis.ulpgc.es/DIU_BD?useSSL=true",
//                "USUARIO",
//                "CLAVE");
//                DatabaseMetaData md = con.getMetaData();
//                String[] types = {"TABLE"};
//                ResultSet rs = md.getTables(null, null, "%", types);
//        } catch (ClassNotFoundException ex) {
//            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (SQLException ex) {
//            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//    
    private Database(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static Database getInstance(){
        if(Database.instance == null){
            Database.instance = new Database();
        }
        
        return Database.instance;
    }
    
    public String[] connect(String username, String password) throws SQLException {
        this.con = DriverManager.getConnection(
                "jdbc:mysql://mozart.dis.ulpgc.es/DIU_BD?useSSL=true",
                username,
                password);
        
        // recuperar los nombres de las tablas y devolverlos
        this.md = con.getMetaData();
        String[] types = {"TABLE"};
        ResultSet rs = md.getTables(null, null, "%", types);
        
        List<String> tablesList = new ArrayList<String>();
        while(rs.next()){
            tablesList.add(rs.getString("TABLE_NAME"));
        }
        
        con.close();
        
        String[] tablesArray = new String[tablesList.size()];
        tablesArray = tablesList.toArray(tablesArray);
        return tablesArray;
    }
}
