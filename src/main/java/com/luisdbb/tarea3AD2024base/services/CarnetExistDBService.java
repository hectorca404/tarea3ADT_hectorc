package com.luisdbb.tarea3AD2024base.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luisdbb.tarea3AD2024base.repositorios.CarnetExistDBRepository;

@Service
public class CarnetExistDBService {

	@Autowired
	private CarnetExistDBRepository carnetExistDBRepository;

	public void almacenarCarnet(String nombreSubcoleccion, String nombreRecurso, String contenidoXML) {

		carnetExistDBRepository.almacenarCarnet(nombreSubcoleccion, nombreRecurso, contenidoXML);

	}
}