package com.luisdbb.tarea3AD2024base.repositorios;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.db4o.ObjectContainer;
import com.db4o.query.Query;
import com.luisdbb.tarea3AD2024base.controller.DB4OConnection;
import com.luisdbb.tarea3AD2024base.modelo.ConjuntoContratado;
import com.luisdbb.tarea3AD2024base.modelo.EnvioACasa;

@Repository
public class ConjuntoContratadoRepository {

	private ObjectContainer db = DB4OConnection.obtenerInstancia();

	public List<ConjuntoContratado> obtenerTodosConjuntos() {
		Query query = db.query();
		query.constrain(ConjuntoContratado.class);
		return query.execute();
	}

	public void guardarConjunto(ConjuntoContratado conjunto) {
		db.store(conjunto);
		db.commit();
	}

	public List<ConjuntoContratado> obtenerConjuntosPorIdsEstancia(List<Long> idsEstancia) {
		Query query = db.query();
		query.constrain(ConjuntoContratado.class);
		List<ConjuntoContratado> resultados = query.execute();
		return resultados.stream().filter(c -> idsEstancia.contains(c.getIdEstancia())).toList();
	}

	public List<EnvioACasa> obtenerEnviosPorParada(List<Long> idsEstancia) {
		List<ConjuntoContratado> conjuntos = obtenerConjuntosPorIdsEstancia(idsEstancia);
		return conjuntos.stream().flatMap(c -> c.getServicios().stream()).filter(s -> s instanceof EnvioACasa)
				.map(s -> (EnvioACasa) s).toList();
	}

}
