package org.example.veterinaria.daos;

import org.example.veterinaria.beans.Mascota;
import org.example.veterinaria.beans.Especie;
import org.example.veterinaria.beans.Veterinario;
import org.example.veterinaria.beans.Dueno;
import java.sql.*;
import java.util.ArrayList;

public class MascotaDao extends DaoBase{

    // listar mascotas
    public ArrayList<Mascota> listarMascotas() {
        ArrayList<Mascota> lista = new ArrayList<>();
        // consulta general
        String sql = "SELECT m.idmascota, m.nombre, m.edad, m.peso, " +
                "e.nombre AS especie, v.nombre AS veterinario, d.nombre AS dueno " +
                "FROM mascota m " +
                "INNER JOIN especie e ON m.especie_id = e.idespecie " +
                "INNER JOIN veterinario v ON m.veterinario_id = v.idveterinario " +
                "INNER JOIN dueno d ON m.dueno_id = d.iddueno";

        try (Connection conn = this.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Mascota m = new Mascota();
                m.setIdMascota(rs.getInt("idmascota"));
                m.setNombre(rs.getString("nombre"));
                m.setEdad(rs.getInt("edad"));
                m.setPeso(rs.getDouble("peso"));

                // Instanciar y setear la Especie
                Especie e = new Especie();
                e.setNombre(rs.getString("especie"));
                m.setEspecie(e);

                // Instanciar y setear el Veterinario
                Veterinario v = new Veterinario();
                v.setNombre(rs.getString("veterinario"));
                m.setVeterinario(v);

                // Instanciar y setear el Dueño
                Dueno d = new Dueno();
                d.setNombre(rs.getString("dueno"));
                m.setDueno(d);

                lista.add(m);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return lista;
    }

    // filtrar mascotas por especie
    public ArrayList<Mascota> listarMascotasPorEspecie(int idEspecie) {
        ArrayList<Mascota> lista = new ArrayList<>();
        String sql = "SELECT m.idmascota, m.nombre, m.edad, m.peso, " +
                "e.nombre AS especie, v.nombre AS veterinario, d.nombre AS dueno " +
                "FROM mascota m " +
                "INNER JOIN especie e ON m.especie_id = e.idespecie " +
                "INNER JOIN veterinario v ON m.veterinario_id = v.idveterinario " +
                "INNER JOIN dueno d ON m.dueno_id = d.iddueno " +
                "WHERE m.especie_id = ?";

        try (Connection conn = this.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idEspecie);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Mascota m = new Mascota();
                    m.setIdMascota(rs.getInt("idmascota"));
                    m.setNombre(rs.getString("nombre"));
                    m.setEdad(rs.getInt("edad"));
                    m.setPeso(rs.getDouble("peso"));

                    Especie e = new Especie();
                    e.setNombre(rs.getString("especie"));
                    m.setEspecie(e);

                    Veterinario v = new Veterinario();
                    v.setNombre(rs.getString("veterinario"));
                    m.setVeterinario(v);

                    Dueno d = new Dueno();
                    d.setNombre(rs.getString("dueno"));
                    m.setDueno(d);

                    lista.add(m);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return lista;
    }

    @Override
    public void crear(Object entidad) {
        // transformamos el Object genérico a una Mascota
        Mascota m = (Mascota) entidad;

        String sql = "INSERT INTO mascota (nombre, edad, peso, especie_id, veterinario_id, dueno_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = this.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, m.getNombre());
            pstmt.setInt(2, m.getEdad());
            pstmt.setDouble(3, m.getPeso());
            pstmt.setInt(4, m.getEspecie().getIdEspecie());
            pstmt.setInt(5, m.getVeterinario().getIdVeterinario());
            pstmt.setInt(6, m.getDueno().getIdDueno());

            pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void borrar(int id) {
        String sql = "DELETE FROM mascota WHERE idmascota = ?";
        try (Connection conn = this.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // poblar comboboxes desde la base de datos

    public ArrayList<Especie> listarEspecies() {
        ArrayList<Especie> lista = new ArrayList<>();
        String sql = "SELECT * FROM especie";
        try (Connection conn = this.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Especie e = new Especie();
                e.setIdEspecie(rs.getInt("idespecie"));
                e.setNombre(rs.getString("nombre"));
                lista.add(e);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return lista;
    }

    public ArrayList<Veterinario> listarVeterinarios() {
        ArrayList<Veterinario> lista = new ArrayList<>();
        String sql = "SELECT * FROM veterinario";
        try (Connection conn = this.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Veterinario v = new Veterinario();
                v.setIdVeterinario(rs.getInt("idveterinario"));
                v.setNombre(rs.getString("nombre"));
                // se podría setear la especialidad a futuro
                lista.add(v);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return lista;
    }

    public ArrayList<Dueno> listarDuenos() {
        ArrayList<Dueno> lista = new ArrayList<>();
        String sql = "SELECT * FROM dueno";
        try (Connection conn = this.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Dueno d = new Dueno();
                d.setIdDueno(rs.getInt("iddueno"));
                d.setNombre(rs.getString("nombre"));
                lista.add(d);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return lista;
    }
}

