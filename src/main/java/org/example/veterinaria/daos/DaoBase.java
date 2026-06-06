package org.example.veterinaria.daos;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public abstract class DaoBase {
    public Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/veterinaria",
                "root",
                "12345678");
    }

    public abstract void crear(Object obj);
    public abstract void borrar(int id);

}
