package com.ulisescasal.Persistidores;

import com.db4o.*;
import com.db4o.internal.Null;
import com.db4o.query.*;
import com.ulisescasal.Objetos.Cliente;
import com.ulisescasal.Objetos.Factura;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PersistidorDB4o{
    private static Object lock = new Object();
    private static final String YAPFILENAME;
    private static ObjectContainer db;
    static {
        YAPFILENAME = "C:/Users/UlisesCasal/IdeaProjects/Dase de Datos 2 DB4o/src/main/java/com/ulisescasal/Persistidores/tp2ej1.db";
    }

    public PersistidorDB4o() {
        synchronized (lock) {
            if (db == null || db.ext().isClosed()) {
                db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), YAPFILENAME);
            }
        }
    }

    public String persistir(Object objetoAPersistir) {
        List<Object> resultado;
        synchronized (lock) {
            if (objetoAPersistir != null) {
                try {
                    if (objetoAPersistir instanceof Cliente) {
                        Cliente cliente = (Cliente) objetoAPersistir;
                        resultado = this.consultar(cliente.getID(), cliente);
                    }else{
                        Factura factura = (Factura) objetoAPersistir;
                        resultado = this.consultar(factura.getNRO(), factura);
                    }
                    if (resultado != null) {
                        System.out.println("El objeto ya existe en la BD!");
                        return "El objeto ya existe en la BD!";
                    }else {
                        db.store(objetoAPersistir);
                        db.commit();
                    }
                } catch (Exception e) {
                    return "No se ha podido persistir el objeto en la BD! Intente nuevamente";
                } finally {
                    //cerrar();
                }
                return "Objeto persistido correctamente!";
            }
            return "El objeto a persistir es nulo! Intente nuevamente";
        }
    }

    public void eliminar(int id, Object objetoAEliminar) {
        if (objetoAEliminar != null) {
            if (objetoAEliminar instanceof Cliente) {
                Query query = db.query();
                query.constrain(Cliente.class);
                query.descend("ID").constrain(id);
                ObjectSet<Cliente> result = query.execute();

                if (result.hasNext()) {
                    Cliente cliente = result.next();
                    db.delete(cliente);
                    System.out.println("Cliente eliminado: " + cliente);
                } else {
                    System.out.println("No se encontró ningún cliente con ID " + id);
                }
            }else if (objetoAEliminar instanceof Factura) {
                Query query = db.query();
                query.constrain(Factura.class);
                query.descend("NRO").constrain(id);
                ObjectSet<Factura> result = query.execute();

                if (result.hasNext()) {
                    Factura factura = result.next();
                    db.delete(factura);
                    System.out.println("Factura eliminada: " + factura);
                }
            }
        }
            db.commit();
    }

    public List<Object> consultar(Object id, Object objetoAConsultar) {
        synchronized (lock) {

            Query query = db.query();
            if (id != null && objetoAConsultar != null) {
                try {
                    if (objetoAConsultar instanceof Cliente) {
                        query.constrain(Cliente.class);
                        query.descend("ID").constrain(id);
                        ObjectSet<Cliente> result = query.execute();

                        if (result.hasNext()) {
                            List<Cliente> resultado = new ArrayList<>(result);
                            return Collections.singletonList(resultado);
                        } else {
                            return null;
                        }
                    }
                    if (objetoAConsultar instanceof Factura) {
                        query.constrain(Factura.class);
                        query.descend("NRO").constrain(id);
                        ObjectSet<Factura> result = query.execute();
                        if (result.hasNext()) {
                            List<Factura> resultado = new ArrayList<>(result);
                            return Collections.singletonList(resultado);
                        } else {
                            return null;
                        }
                    }

                } finally {
                    //cerrar();
                }
            } else {
                return null;
            }
            return null;
        }
    }

    public List consultarTodos(List<Object> entidades) {
        synchronized (lock) {
            List resultado = new ArrayList();
            Query query = db.query();
            ObjectSet resultadoQuery;
            try {
                for (Object entidad : entidades) {
                    query.constrain(entidad);
                    resultadoQuery = query.execute();
                    if (resultadoQuery != null) {
                        resultado.add(resultadoQuery);
                    }
                }
            } finally {
                cerrar();
            }
            return resultado;
        }
    }

    public void cerrar() {
        synchronized (lock) {
            if (db != null && !db.ext().isClosed()) {
                db.close();
                db = null;
            }
        }
    }

    public static void mostrarResultados(List<Object> resultados) {
        List<Object> datos = (List<Object>) resultados.get(0);

        if (datos.isEmpty()) {
            System.out.println("No se encontraron resultados.");
        } else {
            int i = 0;
            System.out.println("Resultados encontrados:");
            while (!datos.isEmpty() && i < datos.size()) {
                Object resultado = datos.get(i);
                if (resultado instanceof Cliente) {
                    System.out.println("====================================");
                    System.out.println("Dato de cliente: ");
                    System.out.println("ID: " + ((Cliente) resultado).getID());
                    System.out.println("Nombre: " + ((Cliente) resultado).getNombre());
                }
                if (resultado instanceof Factura) {
                    System.out.println("====================================");
                    System.out.println("Nro de factura: " + ((Factura) resultado).getNRO());
                    System.out.println("ID de cliente: " + ((Factura) resultado).getID());
                    System.out.println("Importe: " + ((Factura) resultado).getImporte());

                }
                i++;

            }
            System.out.println("Total de resultados: " + datos.size());
        }
    }

    public void modificar(int id, Object objeto) {
        if (objeto != null) {
            if (objeto instanceof Cliente) {
                Query query = db.query();
                query.constrain(Cliente.class);
                query.descend("ID").constrain(id);
                ObjectSet<Cliente> result = query.execute();

                if (result != null && result.hasNext()) {
                    Cliente cliente = result.next();
                    cliente.setNombre(((Cliente) objeto).getNombre());
                    db.store(cliente);
                    db.commit();
                    System.out.println("Cliente modificado: ");
                    System.out.println("ID: " + cliente.getID());
                    System.out.println("Nombre: " + cliente.getNombre());
                } else {
                    System.out.println("No se encontró ningún cliente con ID " + id);
                }
            } else if (objeto instanceof Factura) {
                Query query = db.query();
                query.constrain(Factura.class);
                query.descend("NRO").constrain(id);
                ObjectSet<Factura> result = query.execute();

                if (result != null && result.hasNext()) {
                    Factura factura = result.next();
                    factura.setImporte(((Factura) objeto).getImporte());
                    db.store(factura);
                    db.commit();
                    System.out.println("Factura modificada: " + factura);
                } else {
                    System.out.println("No se encontró ninguna factura con ID " + id);
                }
            }
        } else {
            System.out.println("El objeto a modificar es null");
        }
    }
}