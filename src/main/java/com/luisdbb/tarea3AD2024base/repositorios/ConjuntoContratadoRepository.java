package com.luisdbb.tarea3AD2024base.repositorios;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.db4o.ObjectContainer;
import com.db4o.query.Query;
import com.luisdbb.tarea3AD2024base.config.DB4OConnection;
import com.luisdbb.tarea3AD2024base.modelo.ConjuntoContratado;

@Repository
public class ConjuntoContratadoRepository {
	
	private ObjectContainer db = DB4OConnection.obtenerInstancia();
	
	public List<ConjuntoContratado> obtenerTodosConjuntos(){
		Query query = db.query();
		query.constrain(ConjuntoContratado.class);
		return query.execute();
	}

    public void guardarConjunto(ConjuntoContratado conjunto) {
        db.store(conjunto);
        db.commit();
    }
    

    public void cerrarConexion() {
        DB4OConnection.cerrarBD();
    }
}
