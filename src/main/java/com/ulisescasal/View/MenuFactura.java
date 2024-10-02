package com.ulisescasal.View;
import com.ulisescasal.Objetos.Cliente;
import com.ulisescasal.Objetos.Factura;
import com.ulisescasal.Persistidores.PersistidorDB4o;

import java.util.List;
import java.util.Scanner;

public class MenuFactura {
    private PersistidorDB4o persistidor;
    private Scanner scanner;

    public MenuFactura() {
        this.persistidor = new PersistidorDB4o();
    }

        public void altaFactura() {
            String importe;
            int NRO;
            int ID;
            System.out.println("===== Alta Factura =====");
            System.out.print("1. Ingrese el importe de la factura: ");
            this.scanner.nextLine();
            importe = String.valueOf(this.scanner.nextFloat());
            System.out.print("1. Ingrese el NRO de factura: ");
            NRO = this.scanner.nextInt();
            System.out.println("Ingrese el ID de cliente: ");
            ID = this.scanner.nextInt();
            this.scanner.nextLine();
            this.persistidor.persistir(new Factura(NRO, ID, Float.parseFloat(importe)));
        }

        public void modificacionFactura() {
            String importe;
            int NRO;

            System.out.println("===== Modificacion factura =====");
            System.out.print("1. Ingrese el NRO de factura: ");
            NRO = this.scanner.nextInt();
            this.scanner.nextLine();
            List<Object> salida = this.persistidor.consultar(NRO, new Factura(NRO, 0, 0));
            if (salida != null) {
                PersistidorDB4o.mostrarResultados(salida);
                List<Object> datos = (List<Object>) salida.get(0);
                Factura factura = (Factura) datos.get(0);
                int ID = factura.getID();
                System.out.println("Ingrese el nuevo importe: ");
                importe = String.valueOf(this.scanner.nextFloat());
                this.persistidor.modificar(NRO, new Factura(NRO, ID, Float.parseFloat(importe)));
            }

        }

        public void bajaFactura() {
            int ID;
            System.out.println("===== Baja de Factura =====");
            System.out.print("1. Ingrese el NRO de factura: ");
            ID = scanner.nextInt();
            scanner.nextLine();
            this.persistidor.eliminar(ID, new Factura(ID, 0, 0));
        }

    public void setPersistidor(PersistidorDB4o persistidor) {
        this.persistidor = persistidor;
    }

    public void setScanner(Scanner scanner) {
            this.scanner = scanner;
    }

    public void consultaFactura() {
        int NRO;

        System.out.println("===== Consulta Factura =====");
        System.out.print("1. Ingrese el NRO de factura: ");
        NRO = this.scanner.nextInt();
        this.scanner.nextLine();
        List<Object> salida = this.persistidor.consultar(NRO, new Factura(NRO, 0, 0));
        if (salida != null) {
            PersistidorDB4o.mostrarResultados(salida);
        }
    }
}
