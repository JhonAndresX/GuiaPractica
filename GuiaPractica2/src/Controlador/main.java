/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Controlador.ControladorPersona;
import Modelo.ModeloPersona;
import Vista.MenuPrincipal;

/**
 *
 * @author ASUS
 */
public class main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ModeloPersona modelo = new ModeloPersona();
        MenuPrincipal vista = new MenuPrincipal();
        ControladorPersona controlador = new ControladorPersona(modelo, vista);
        controlador.iniciaControl();
    }

}
