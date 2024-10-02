package com.ulisescasal.View;

import com.db4o.ObjectSet;
import com.ulisescasal.Objetos.Cliente;
import com.ulisescasal.Persistidores.PersistidorDB4o;

import java.util.List;
import java.util.Scanner;

public class MenuCliente {
    private PersistidorDB4o persistidor;
    private Scanner scanner;

    public MenuCliente() {
        this.persistidor = new PersistidorDB4o();
    }


    public void altaCliente(){
        String nombre;
        int ID;
        int opcion = 0;
        System.out.println("===== Alta cliente =====");
        System.out.print("1. Ingrese el nombre del cliente: ");
        this.scanner.nextLine();
        nombre = this.scanner.nextLine();
        System.out.print("1. Ingrese el ID númerico del usuario: ");
        ID = this.scanner.nextInt();
        persistidor.persistir(new Cliente(ID, nombre));

    }

    public void modificacionCliente(){
        String nombre;
        int ID;

        System.out.println("===== Modificacion cliente =====");
        System.out.print("1. Ingrese el ID del cliente: ");
        ID = this.scanner.nextInt();
        this.scanner.nextLine();
        List<Object> salida = this.persistidor.consultar(ID, new Cliente(ID, ""));
        if (salida != null) {
            PersistidorDB4o.mostrarResultados(salida);
            System.out.println("Ingrese el nuevo nombre: ");
            nombre = this.scanner.nextLine();
            this.persistidor.modificar(ID, new Cliente(ID, nombre));
        }

    }

    public void bajaCliente(){
        int ID;
        System.out.println("===== Baja cliente =====");
        System.out.print("1. Ingrese el ID del cliente: ");
        ID = scanner.nextInt();
        scanner.nextLine();
        this.persistidor.eliminar(ID, new Cliente(ID, ""));
    }

    public void consultaCliente() {
        String nombre;
        int ID;
        int opcion = 0;

        System.out.println("===== Consulta cliente =====");
        System.out.print("1. Ingrese el ID del cliente: ");
        ID = this.scanner.nextInt();
        this.scanner.nextLine();
        List<Object> salida = this.persistidor.consultar(ID, new Cliente(ID, ""));
        if (salida != null) {
            PersistidorDB4o.mostrarResultados(salida);
        }
    }

    public void setPersistidor(PersistidorDB4o persistidor) {
        this.persistidor = persistidor;
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }
}
