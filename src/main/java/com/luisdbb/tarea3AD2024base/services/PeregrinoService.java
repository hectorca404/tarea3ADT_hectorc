package com.luisdbb.tarea3AD2024base.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.luisdbb.tarea3AD2024base.modelo.Carnet;
import com.luisdbb.tarea3AD2024base.modelo.Credenciales;
import com.luisdbb.tarea3AD2024base.modelo.Estancia;
import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.modelo.ParadasPeregrinos;
import com.luisdbb.tarea3AD2024base.modelo.Peregrino;
import com.luisdbb.tarea3AD2024base.modelo.Perfil;
import com.luisdbb.tarea3AD2024base.repositorios.CredencialesRepository;
import com.luisdbb.tarea3AD2024base.repositorios.EstanciaRepository;
import com.luisdbb.tarea3AD2024base.repositorios.ParadasPeregrinosRepository;
import com.luisdbb.tarea3AD2024base.repositorios.PeregrinoRepository;

@Service
public class PeregrinoService {

	private final PeregrinoRepository peregrinoRepository;
	private final ParadasPeregrinosRepository paradasPeregrinosRepository;
	private final CredencialesRepository credencialesRepository;
	private final EstanciaRepository estanciaRepository;
	private final CarnetExistDBService carnetExistDBService;

	public PeregrinoService(PeregrinoRepository peregrinoRepository,
			ParadasPeregrinosRepository paradasPeregrinosRepository, CredencialesRepository credencialesRepository,
			EstanciaRepository estanciaRepository, CarnetExistDBService carnetExistDBService) {
		this.peregrinoRepository = peregrinoRepository;
		this.paradasPeregrinosRepository = paradasPeregrinosRepository;
		this.credencialesRepository = credencialesRepository;
		this.estanciaRepository = estanciaRepository;
		this.carnetExistDBService = carnetExistDBService;

	}

	@Transactional
	public void registrarPeregrino(String nombreUsuario, String contrasena, String correo, String nombre,
			String apellido, String nacionalidad, Parada paradaInicio) {
		if (credencialesRepository.findByNombreUsuario(nombreUsuario).isPresent()) {
			throw new IllegalArgumentException("El nombre de usuario ya existe");
		}

		Peregrino peregrino = new Peregrino(null, nombre, apellido, nacionalidad, paradaInicio);

		Peregrino peregrinoGuardado = peregrinoRepository.save(peregrino);
		Long totalRegistros = paradasPeregrinosRepository.count();
		Long orden = totalRegistros + 1;
		ParadasPeregrinos relacion = new ParadasPeregrinos(orden, peregrinoGuardado, paradaInicio);

		paradasPeregrinosRepository.save(relacion);

		Credenciales credenciales = new Credenciales();
		credenciales.setNombreUsuario(nombreUsuario);
		credenciales.setContrasena(contrasena);
		credenciales.setCorreo(correo);
		credenciales.setPerfil(Perfil.PEREGRINO);
		credenciales.setPeregrino(peregrinoGuardado);

		credencialesRepository.save(credenciales);

		String nombreFichero = peregrinoGuardado.getNombre().replaceAll(" ", "_") + "_P" + peregrinoGuardado.getId()
				+ ".xml";

		String xmlContent = generarXMLCarnet(peregrinoGuardado);

		String nombreSubcoleccion = peregrinoGuardado.getCarnet().getParadaInicio().getNombre();

		carnetExistDBService.almacenarCarnet(nombreSubcoleccion, nombreFichero, xmlContent);

		exportarPeregrinoXML(peregrinoGuardado);

	}

	public List<Parada> obtenerParadas(Long peregrinoId) {
		return paradasPeregrinosRepository.findByPeregrinoId(peregrinoId).stream().map(ParadasPeregrinos::getParada)
				.toList();
	}

	public List<Estancia> obtenerEstancias(Long peregrinoId) {
		return estanciaRepository.findByPeregrinoId(peregrinoId);
	}

	@Transactional
	public void guardarCambiosPeregrino(Peregrino peregrino, String nuevoCorreo) {
		peregrinoRepository.save(peregrino);

		Credenciales credenciales = credencialesRepository.findByPeregrino(peregrino).orElseThrow(
				() -> new IllegalArgumentException("No se encontraron credenciales asociadas al peregrino"));

		credenciales.setCorreo(nuevoCorreo);

		credencialesRepository.save(credenciales);
	}

	@Transactional
	public void actualizarCarnet(Carnet carnet) {
		Peregrino peregrino = peregrinoRepository.findByCarnetId(carnet.getId())
				.orElseThrow(() -> new IllegalArgumentException(
						"No se encontró un peregrino asociado al carnet con ID: " + carnet.getId()));

		peregrino.getCarnet().setDistancia(carnet.getDistancia());
		peregrino.getCarnet().setnVips(carnet.getnVips());

		peregrinoRepository.save(peregrino);
	}

	public void exportarPeregrinoXML(Peregrino peregrino) {
		String pathExportacion = "src/main/resources/peregrinosXML/";

		List<ParadasPeregrinos> paradasPeregrinos = paradasPeregrinosRepository.findByPeregrinoId(peregrino.getId());

		paradasPeregrinos.sort(Comparator.comparing(pp -> pp.getId().getOrden()));

		List<Estancia> estancias = obtenerEstancias(peregrino.getId());

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDate fechaExpCarnet = peregrino.getCarnet().getFechaexp();
		String fechaExpFormateada = (fechaExpCarnet != null) ? fechaExpCarnet.format(formatter) : "Fecha no disponible";

		String nombreFichero = peregrino.getNombre().replaceAll(" ", "_") + "_P" + peregrino.getId() + ".xml";
		String fichero = pathExportacion + nombreFichero;

		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.newDocument();

			Element carnetElement = doc.createElement("carnet");
			doc.appendChild(carnetElement);

			Element idElement = doc.createElement("id");
			idElement.setTextContent(String.valueOf(peregrino.getCarnet().getId()));
			carnetElement.appendChild(idElement);

			Element fechaExpElement = doc.createElement("fechaexp");
			fechaExpElement.setTextContent(fechaExpFormateada);
			carnetElement.appendChild(fechaExpElement);

			Element expedidoEnElement = doc.createElement("expedidoen");
			expedidoEnElement.setTextContent(peregrino.getCarnet().getParadaInicio().getNombre());
			carnetElement.appendChild(expedidoEnElement);

			Element peregrinoElement = doc.createElement("peregrino");
			carnetElement.appendChild(peregrinoElement);

			Element nombreElement = doc.createElement("nombre");
			nombreElement.setTextContent(peregrino.getNombre());
			peregrinoElement.appendChild(nombreElement);

			Element nacionalidadElement = doc.createElement("nacionalidad");
			nacionalidadElement.setTextContent(peregrino.getNacionalidad());
			peregrinoElement.appendChild(nacionalidadElement);

			Element hoyElement = doc.createElement("hoy");
			hoyElement.setTextContent(LocalDate.now().format(formatter));
			carnetElement.appendChild(hoyElement);

			Element distanciaTotalElement = doc.createElement("distanciatotal");
			distanciaTotalElement.setTextContent(String.valueOf(peregrino.getCarnet().getDistancia()));
			carnetElement.appendChild(distanciaTotalElement);

			Element paradasElement = doc.createElement("paradas");
			carnetElement.appendChild(paradasElement);

			int orden = 1;
			for (ParadasPeregrinos pp : paradasPeregrinos) {
				Parada parada = pp.getParada();
				Element paradaElement = doc.createElement("parada");

				Element ordenElement = doc.createElement("orden");
				ordenElement.setTextContent(String.valueOf(orden++));
				paradaElement.appendChild(ordenElement);

				Element nombreParadaElement = doc.createElement("nombre");
				nombreParadaElement.setTextContent(parada.getNombre());
				paradaElement.appendChild(nombreParadaElement);

				Element regionElement = doc.createElement("region");
				regionElement.setTextContent("" + parada.getRegion());
				paradaElement.appendChild(regionElement);

				paradasElement.appendChild(paradaElement);
			}

			Element estanciasElement = doc.createElement("estancias");
			carnetElement.appendChild(estanciasElement);

			for (Estancia estancia : estancias) {
				Element estanciaElement = doc.createElement("estancia");

				Element idEstanciaElement = doc.createElement("id");
				idEstanciaElement.setTextContent(String.valueOf(estancia.getId()));
				estanciaElement.appendChild(idEstanciaElement);

				Element fechaEstanciaElement = doc.createElement("fecha");
				fechaEstanciaElement.setTextContent(estancia.getFecha().format(formatter));
				estanciaElement.appendChild(fechaEstanciaElement);

				Element paradaEstanciaElement = doc.createElement("parada");
				paradaEstanciaElement.setTextContent(estancia.getParada().getNombre());
				estanciaElement.appendChild(paradaEstanciaElement);

				if (estancia.isVip()) {
					Element vipElement = doc.createElement("vip");
					vipElement.setTextContent("Sí");
					estanciaElement.appendChild(vipElement);
				}

				estanciasElement.appendChild(estanciaElement);
			}

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");

			try (OutputStream outputStream = new FileOutputStream(new File(fichero))) {
				transformer.transform(new DOMSource(doc), new StreamResult(outputStream));
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error al exportar el peregrino XML: " + e.getMessage());
		}
	}

	private String generarXMLCarnet(Peregrino peregrino) {
		
		List<ParadasPeregrinos> paradasPeregrinos = paradasPeregrinosRepository.findByPeregrinoId(peregrino.getId());
		paradasPeregrinos.sort(Comparator.comparing(pp -> pp.getId().getOrden()));
		List<Estancia> estancias = obtenerEstancias(peregrino.getId());

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDate fechaExpCarnet = peregrino.getCarnet().getFechaexp();
		String fechaExpFormateada = (fechaExpCarnet != null) ? fechaExpCarnet.format(formatter) : "Fecha no disponible";
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.newDocument();

			Element carnetElement = doc.createElement("carnet");
			doc.appendChild(carnetElement);

			Element idElement = doc.createElement("id");
			idElement.setTextContent(String.valueOf(peregrino.getCarnet().getId()));
			carnetElement.appendChild(idElement);

			Element fechaExpElement = doc.createElement("fechaexp");
			fechaExpElement.setTextContent(fechaExpFormateada);
			carnetElement.appendChild(fechaExpElement);

			Element expedidoEnElement = doc.createElement("expedidoen");
			expedidoEnElement.setTextContent(peregrino.getCarnet().getParadaInicio().getNombre());
			carnetElement.appendChild(expedidoEnElement);

			Element peregrinoElement = doc.createElement("peregrino");
			carnetElement.appendChild(peregrinoElement);

			Element nombreElement = doc.createElement("nombre");
			nombreElement.setTextContent(peregrino.getNombre()+" "+peregrino.getApellido());
			peregrinoElement.appendChild(nombreElement);

			Element nacionalidadElement = doc.createElement("nacionalidad");
			nacionalidadElement.setTextContent(peregrino.getNacionalidad());
			peregrinoElement.appendChild(nacionalidadElement);

			Element hoyElement = doc.createElement("hoy");
			hoyElement.setTextContent(LocalDate.now().format(formatter));
			carnetElement.appendChild(hoyElement);

			Element distanciaTotalElement = doc.createElement("distanciatotal");
			distanciaTotalElement.setTextContent(String.valueOf(peregrino.getCarnet().getDistancia()));
			carnetElement.appendChild(distanciaTotalElement);

			Element paradasElement = doc.createElement("paradas");
			carnetElement.appendChild(paradasElement);

			int orden = 1;
			for (ParadasPeregrinos pp : paradasPeregrinos) {
				Parada parada = pp.getParada();
				Element paradaElement = doc.createElement("parada");

				Element ordenElement = doc.createElement("orden");
				ordenElement.setTextContent(String.valueOf(orden++));
				paradaElement.appendChild(ordenElement);

				Element nombreParadaElement = doc.createElement("nombre");
				nombreParadaElement.setTextContent(parada.getNombre());
				paradaElement.appendChild(nombreParadaElement);

				Element regionElement = doc.createElement("region");
				regionElement.setTextContent("" + parada.getRegion());
				paradaElement.appendChild(regionElement);

				paradasElement.appendChild(paradaElement);
			}

			Element estanciasElement = doc.createElement("estancias");
			carnetElement.appendChild(estanciasElement);

			for (Estancia estancia : estancias) {
				Element estanciaElement = doc.createElement("estancia");

				Element idEstanciaElement = doc.createElement("id");
				idEstanciaElement.setTextContent(String.valueOf(estancia.getId()));
				estanciaElement.appendChild(idEstanciaElement);

				Element fechaEstanciaElement = doc.createElement("fecha");
				fechaEstanciaElement.setTextContent(estancia.getFecha().format(formatter));
				estanciaElement.appendChild(fechaEstanciaElement);

				Element paradaEstanciaElement = doc.createElement("parada");
				paradaEstanciaElement.setTextContent(estancia.getParada().getNombre());
				estanciaElement.appendChild(paradaEstanciaElement);

				if (estancia.isVip()) {
					Element vipElement = doc.createElement("vip");
					vipElement.setTextContent("Sí");
					estanciaElement.appendChild(vipElement);
				}

				estanciasElement.appendChild(estanciaElement);
			}

			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

			StringWriter writer = new StringWriter();
			transformer.transform(new DOMSource(doc), new StreamResult(writer));

			return writer.toString();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Error generando XML del carnet: ");
		}
	}

}
