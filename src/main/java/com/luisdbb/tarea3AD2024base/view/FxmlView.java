package com.luisdbb.tarea3AD2024base.view;

import java.util.ResourceBundle;

public enum FxmlView {
	REGPEREGRINO {
		@Override
		public String getTitle() {
			return "Registro Peregrino";
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/RegPeregrino.fxml";
		}
	},
	FORGOTPASS {
		@Override
		public String getTitle() {
			return "Contraseña olvidada";
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/ForgotPass.fxml";
		}
	},
	EXPORTPEREGRINO {
		@Override
		public String getTitle() {
			return "Exportar Peregrinos";
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/ExportPeregrino.fxml";
		}
	},
	ADMIN {
		@Override
		public String getTitle() {
			return "Menu Admin";
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/Admin.fxml";
		}
	},
	RESPARADA {
		@Override
		public String getTitle() {
			return "Menu Responsable Parada";
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/ResParada.fxml";
		}
	},
	CREARPARADA {
		@Override
		public String getTitle() {
			return "Crear parada";
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/CrearParada.fxml";
		}
	},
	EDITPEREGRINO {
		@Override
		public String getTitle() {
			return "Editar Peregrino";
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/EditPeregrino.fxml";
		}
	},
	EXPORTPARADA {
		@Override
		public String getTitle() {
			return "Exportar Parada";
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/ExportParada.fxml";
		}
	},
	PEREGRINO {
		@Override
		public String getTitle() {
			return "Menu Peregrino";
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/Peregrino.fxml";
		}
	},
	SELLARALOJAR {
		@Override
		public String getTitle() {
			return "Sellar Alojar";
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/SellarAlojar.fxml";
		}
	},
	MENUSERVICIOS {
		@Override
		public String getTitle() {
			return "Menu servicios";
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/MenuServices.fxml";
		}
	},
	VERENVIOS {
		@Override
		public String getTitle() {
			return "Envios Realizados Actualmente";
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/VerEnvios.fxml";
		}
	},
	CREARSERVICIO {
		@Override
		public String getTitle() {
			return "Crear Servicio";
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/CrearServicio.fxml";
		}
	},
	EDITARSERVICIO {
		@Override
		public String getTitle() {
			return "Editar Servicio";
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/EditarServicio.fxml";
		}
	},

	PRINCIPAL {
		@Override
		public String getTitle() {
			return "Programa Peregrinos ADT - DESIN - Héctor Castañé Álvarez";
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/Principal.fxml";
		}
	};

	public abstract String getTitle();

	public abstract String getFxmlFile();

	String getStringFromResourceBundle(String key) {
		return ResourceBundle.getBundle("Bundle").getString(key);
	}
}
