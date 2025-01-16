package com.luisdbb.tarea3AD2024base.view;

import java.util.ResourceBundle;

public enum FxmlView {
    LOGIN {
        @Override
        public String getTitle() {
            return "Login";
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/Login.fxml";
        }
    },
    PRINCIPAL {
        @Override
        public String getTitle() {
            return "Principal";
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
