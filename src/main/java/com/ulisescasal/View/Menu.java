package com.ulisescasal.View;

import com.ulisescasal.Objetos.Cliente;
import com.ulisescasal.Objetos.Factura;
import com.ulisescasal.Persistidores.PersistidorDB4o;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {
    private Scanner scanner;
    private MenuCliente menuCliente;
    private MenuFactura menuFactura;
    private PersistidorDB4o persistidor;

    public Menu() {
        scanner = new Scanner(System.in);
        persistidor = new PersistidorDB4o();
        menuCliente = new MenuCliente();
        menuFactura = new MenuFactura();
        menuCliente.setPersistidor(persistidor);
        menuFactura.setPersistidor(persistidor);
    }

    public void mostrarMenu() {
        Scanner scanner = new Scanner(System.in);
        menuCliente.setScanner(scanner);
        menuFactura.setScanner(scanner);
        int opcion;

        do {
            System.out.println("\n--- Menú Principal ---");
            System.out.println("1. Alta cliente");
            System.out.println("2. Modificacion cliente");
            System.out.println("3. Consulta cliente");
            System.out.println("4. Baja cliente");
            System.out.println("5. Alta factura");
            System.out.println("6. Modificacion factura");
            System.out.println("7. Consulta factura");
            System.out.println("8. Baja factura");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");

            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    this.menuCliente.altaCliente();
                    break;
                case 2:
                    this.menuCliente.modificacionCliente();
                    break;
                case 3:
                    this.menuCliente.consultaCliente();
                    break;
                case 4:
                    this.menuCliente.bajaCliente();
                    break;
                case 5:
                    this.menuFactura.altaFactura();
                    break;
                case 6:
                    this.menuFactura.modificacionFactura();
                    break;
                case 7:
                    this.menuFactura.consultaFactura();
                    break;
                case 8:
                    this.menuFactura.bajaFactura();
                    break;
                case 0:
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, intente de nuevo.");
            }
        } while (opcion != 0);

        scanner.close();
    }
}
