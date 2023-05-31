/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.ModeloPersona;
import Modelo.Persona;
import Vista.MenuPrincipal;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import javax.swing.JOptionPane;

import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ASUS
 */
public class ControladorPersona {

    private ModeloPersona modelo;
    private MenuPrincipal vista;

    public ControladorPersona(ModeloPersona modelo, MenuPrincipal vista) {
        this.modelo = modelo;
        this.vista = vista;
        vista.setVisible(true);
        cargaPersonas();
    }

    public void iniciaControl() {

        vista.getBtnNuevo().addActionListener(l -> abrirDialogo("Crear"));
        vista.getBtnEditar().addActionListener(l -> abrirDialogo("Editar"));
        vista.getBtnEliminar().addActionListener(l -> abrirDialogo("Eliminar"));
        vista.getBtnAceptar().addActionListener(l -> crearEditarPersona());

    }

    private void crearEditarPersona() {
        if (vista.getDialogPersona().getTitle().contentEquals("Crear")) {

            ModeloPersona p = new ModeloPersona();
            vista.getTxtIdpersona().setEnabled(true);

            if (vista.getTxtIdpersona().equals("") || vista.getTxtNombre().equals("") || vista.getTxtNombre().equals("")) {

                JOptionPane.showMessageDialog(null, "POR FAVOR LLENE LOS DATOS");

            } else {
                String cedula = vista.getTxtIdpersona().getText();
                String nombre = vista.getTxtNombre().getText();
                String apellido = vista.getTxtApellido().getText();

                if (cedulaExiste(cedula)) {
                    JOptionPane.showMessageDialog(null, "La cédula ya existe. Por favor, ingrese una cédula diferente.");
                } else if (!validarCedula(cedula)) {
                    JOptionPane.showMessageDialog(null, "La cédula debe tener 10 dígitos.");
                } else if (!validarNombre(nombre)) {
                    JOptionPane.showMessageDialog(null, "El nombre debe ser valido");
                } else if (!validarApellido(apellido)) {
                    JOptionPane.showMessageDialog(null, "El apellido debe ser valido");

                } else {
                    p.setIdPersona(cedula);
                    p.setNombre(nombre);
                    p.setApellido(apellido);

                    if (p.grabarPersona()) {

                        JOptionPane.showMessageDialog(vista, "DATOS CREADOS");
                        vista.getDialogPersona().setVisible(false);
                        cargaPersonas();
                        limpiar();
                    } else {
                        JOptionPane.showMessageDialog(vista, "ERROR AL GRABAR DATOS");
                    }
                }
            }
        } else if (vista.getDialogPersona().getTitle().contentEquals("Editar")) {

            ModeloPersona p = new ModeloPersona();

            if (vista.getTxtIdpersona().equals("") || vista.getTxtNombre().equals("") || vista.getTxtApellido().equals("")) {
                JOptionPane.showMessageDialog(null, "POR FAVOR LLENE LOS DATOS");
            } else {
                String cedula = vista.getTxtIdpersona().getText();
                String nombre = vista.getTxtNombre().getText();
                String apellido = vista.getTxtApellido().getText();

                if (!validarCedula(cedula)) {
                    JOptionPane.showMessageDialog(null, "El campo de cédula debe tener 10 dígitos.");
                } else if (!validarNombre(nombre)) {
                    JOptionPane.showMessageDialog(null, "El nombre debe ser valido");
                } else if (!validarApellido(apellido)) {
                    JOptionPane.showMessageDialog(null, "El apellido debe ser valido");

                } else {
                    p.setIdPersona(cedula);
                    p.setNombre(nombre);
                    p.setApellido(apellido);

                    if (p.ModificarPersona()) {

                        JOptionPane.showMessageDialog(vista, "DATOS CREADOS");
                        vista.getDialogPersona().setVisible(false);
                        cargaPersonas();
                        limpiar();
                    } else {
                        JOptionPane.showMessageDialog(vista, "ERROR AL GRABAR DATOS");
                    }
                }
            }

        } else if (vista.getDialogPersona().getTitle().contentEquals("Eliminar")) {
            ModeloPersona p = new ModeloPersona();
            p.setIdPersona(vista.getTxtIdpersona().getText());
            if (p.EliminarPersona()) {

                JOptionPane.showMessageDialog(vista, "DATOS ELIMINADOS");

                vista.getDialogPersona().setVisible(false);
                cargaPersonas();

            } else {
                JOptionPane.showMessageDialog(vista, "ERROR AL GRABAR DATOS");
            }

        }
    }

    public void limpiar() {
        vista.getTxtIdpersona().setText("");
        vista.getTxtNombre().setText("");
        vista.getTxtApellido().setText("");
    }

    private void abrirDialogo(String ce) {

        vista.getDialogPersona().setLocationRelativeTo(null);
        vista.getDialogPersona().setSize(750, 600);
        vista.getDialogPersona().setTitle(ce);
        vista.getDialogPersona().setVisible(true);
        if (vista.getDialogPersona().getTitle().contentEquals("Crear")) {

        } else if (vista.getDialogPersona().getTitle().contentEquals("Editar")) {

            LlenarDatos();

        } else if (vista.getDialogPersona().getTitle().contentEquals("Eliminar")) {
            LlenarDatos();
        }
    }

    public void cargaPersonas() {
        DefaultTableModel mJtable;
        mJtable = (DefaultTableModel) vista.getTblPersonas().getModel();
        mJtable.setNumRows(0);
        List<Persona> listaP = modelo.listarPersonas();
        listaP.stream().forEach(p -> {
            String[] rowData = {p.getIdPersona(), p.getNombre(), p.getApellido()};
            mJtable.addRow(rowData);
        }
        );
    }

    public void LlenarDatos() {

        List<Persona> listper = modelo.listarPersonas();
        int selectedRow = vista.getTblPersonas().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Para que los datos se llenen, debe seleccionar un elemento de la tabla");
        } else {
            String selectedId = vista.getTblPersonas().getValueAt(selectedRow, 0).toString();
            Optional<Persona> matchingPersona = listper.stream()
                    .filter(p -> selectedId.equals(p.getIdPersona()))
                    .findFirst();

            if (matchingPersona.isPresent()) {
                Persona p = matchingPersona.get();
                vista.getTxtIdpersona().setText(p.getIdPersona());
                vista.getTxtIdpersona().setEnabled(false);
                vista.getTxtNombre().setText(p.getNombre());
                vista.getTxtApellido().setText(p.getApellido());

            } else {
                JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento válido de la tabla.");
            }
        }
    }

    private boolean cedulaExiste(String cedula) {
        List<Persona> listaPersonas = modelo.listarPersonas();

        for (Persona persona : listaPersonas) {
            if (persona.getIdPersona().equals(cedula)) {
                return true;
            }
        }

        return false;
    }

    public boolean validarNombre(String nombre) {

        return nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]{2,50}");
    }

    public boolean validarApellido(String apellido) {

        return apellido.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]{2,50}");
    }

    public boolean validarCedula(String cedula) {

        return cedula.matches("\\d{10}");
    }

}
