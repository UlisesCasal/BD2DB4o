package com.ulisescasal.Objetos;

public class Factura {
    private int NRO;
    private int ID;
    private float importe;

    public Factura(int NRO, int ID, float importe) {
        this.NRO = NRO;
        this.ID = ID;
        this.importe = importe;
    }

    public int getNRO() {
        return NRO;
    }

    public void setNRO(int NRO) {
        this.NRO = NRO;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public float getImporte() {
        return importe;
    }

    public void setImporte(float importe) {
        this.importe = importe;
    }
}
