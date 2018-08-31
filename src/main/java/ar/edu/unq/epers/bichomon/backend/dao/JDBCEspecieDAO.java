package ar.edu.unq.epers.bichomon.backend.dao;

import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Una implementacion de {@link EspecieDAO} que persiste
 * en una base de datos relacional utilizando JDBC
 *
 */
public class JDBCEspecieDAO implements EspecieDAO {

    public JDBCEspecieDAO() {
    }

    @Override
    public void guardar(Especie especie) {
        this.executeWithConnection(conn -> {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO especie (id, nombre, peso, altura, tipo, cantidad) VALUES (?,?,?,?,?,?)");
            ps.setInt(1, especie.getId());
            ps.setString(2, especie.getNombre());
            ps.setInt(3, especie.getPeso());
            ps.setInt(4, especie.getAltura());
            ps.setString(5, especie.getTipo().toString());
            ps.setInt(6, especie.getCantidadBichos());
            //Falta persistir tipo (TipoBicho)
            ps.execute();

            if (ps.getUpdateCount() != 1) {
                throw new RuntimeException("No se inserto la especie " + especie);
            }
            ps.close();

            return null;
        });
    }

    @Override
    public void actualizar(Especie especie) {
        this.executeWithConnection(conn -> {
            PreparedStatement ps = conn.prepareStatement("UPDATE especie set id=?, peso=?, altura=?, tipo=?, cantidad=? where nombre=especie.nombre");
            ps.setInt(1, especie.getId());
            ps.setInt(2, especie.getPeso());
            ps.setInt(3, especie.getAltura());
            ps.setString(4, especie.getTipo().toString());
            ps.setInt(5, especie.getCantidadBichos());

            ps.execute();
            ps.close();

            return null;
        });
    }

    @Override
    public Especie recuperar(String nombre) {
        return this.executeWithConnection(conn -> {
            PreparedStatement ps = conn.prepareStatement("SELECT id, nombre, peso, altura, tipo, cantidad FROM especie WHERE nombre = ?");
            ps.setString(1, nombre);

            ResultSet resultSet = ps.executeQuery();

            Especie especie = null;
            while (resultSet.next()) {
                //si especie no es null aca significa que el while dio mas de una vuelta, eso
                //suele pasar cuando el resultado (resultset) tiene mas de un elemento.
                if (especie != null) {
                    throw new RuntimeException("Existe mas de una especie con el nombre " + nombre);
                }

                especie = new Especie();

                especie.setId(resultSet.getInt("id"));
                especie.setNombre(resultSet.getString("nombre"));
                especie.setPeso(resultSet.getInt("peso"));
                especie.setAltura(resultSet.getInt("altura"));
                especie.setTipo(TipoBicho.valueOf(resultSet.getString("tipo")));
                especie.setCantidadBichos(resultSet.getInt("cantidad"));

            }

            ps.close();
            return especie;
        });
    }

    @Override
    public List<Especie> recuperarTodos() {
        return this.executeWithConnection(conn -> {
            PreparedStatement ps = conn.prepareStatement("SELECT id, nombre, peso, altura, tipo, cantidad FROM especie");

            ResultSet resultSet = ps.executeQuery();

            List<Especie> list = new ArrayList<>();

            while (resultSet.next()) {
                Especie especie = new Especie();

                especie.setId(resultSet.getInt("id"));
                especie.setNombre(resultSet.getString("nombre"));
                especie.setPeso(resultSet.getInt("peso"));
                especie.setAltura(resultSet.getInt("altura"));
                especie.setTipo(TipoBicho.valueOf(resultSet.getString("tipo")));
                especie.setCantidadBichos(resultSet.getInt("cantidad"));

                list.add(especie);
            }

            ps.close();
            return list;
        });
    }

public void deleteAll() {
    this.executeWithConnection(conn -> {
        PreparedStatement ps = conn.prepareStatement("TRUNCATE TABLE especie");
        ps.execute();
        ps.close();
        return null;
    });
}


    /**
     * Ejecuta un bloque de codigo contra una conexion.
     */
    private <T> T executeWithConnection(ConnectionBlock<T> bloque) {
        Connection connection = this.openConnection();
        try {
            return bloque.executeWith(connection);
        } catch (SQLException e) {
            throw new RuntimeException("Error no esperado", e);
        } finally {
            this.closeConnection(connection);
        }
    }

    /**
     * Establece una conexion a la url especificada
     * @return la conexion establecida
     */
    private Connection openConnection() {
        try {
            //La url de conexion no deberia estar harcodeada aca
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/epers_ejemplo_jdbc?user=root&password=root");
        } catch (SQLException e) {
            throw new RuntimeException("No se puede establecer una conexion", e);
        }
    }

    /**
     * Cierra una conexion con la base de datos (libera los recursos utilizados por la misma)
     * @param connection - la conexion a cerrar.
     */
    private void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException("Error al cerrar la conexion", e);
        }
    }



}
