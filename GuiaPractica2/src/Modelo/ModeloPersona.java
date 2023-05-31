/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ASUS
 */
public class ModeloPersona extends Persona {

    ConexionPostgres CPG = new ConexionPostgres();

    public ModeloPersona() {

    }

    public List<Persona> listarPersonas() {
        List<Persona> listaPersona = new ArrayList<Persona>();

        try {
            String sql = "select * from persona";
            ResultSet rs = CPG.consultaBD(sql);
            while (rs.next()) {
                Persona persona = new Persona();
                persona.setIdPersona(rs.getString("idPersona"));
                persona.setNombre(rs.getString("nombre"));
                persona.setApellido(rs.getString("apellido"));

                listaPersona.add(persona);

            }
            rs.close();//CIERRO CONEXION
            return listaPersona;
        } catch (SQLException ex) {
            Logger.getLogger(ModeloPersona.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public boolean grabarPersona() {
        String sql;
        sql = "INSERT INTO persona(idPersona,nombre,apellido)";
        sql += "VALUES('" + getIdPersona() + "','" + getNombre() + "','" + getApellido() + "')";
        return CPG.accionBD(sql);

    }

    public boolean ModificarPersona() {
        String sql;
        sql = "update persona set idPersona='" + getIdPersona() + "' ,nombre='" + getNombre() + "',apellido='" + getApellido() + "'where idPersona='" + getIdPersona() + "';";

        return CPG.accionBD(sql);

    }

    public boolean EliminarPersona() {
        String sql;
        sql = "delete from persona where idPersona='" + getIdPersona() + "';";
        return CPG.accionBD(sql);

    }

}
