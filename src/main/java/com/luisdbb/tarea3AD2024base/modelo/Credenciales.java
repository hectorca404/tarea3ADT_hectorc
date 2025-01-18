package com.luisdbb.tarea3AD2024base.modelo;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "credenciales")
public class Credenciales {
    // ATRIBUTOS
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_usuario", nullable = false, unique = true)
    private String nombreUsuario;

    @Column(nullable = false)
    private String contrasena;
    
    @Column(nullable = false)
    private String correo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Perfil perfil;

    @ManyToOne
    @JoinColumn(name = "peregrino_id", nullable = true)
    private Peregrino peregrino;

    @ManyToOne
    @JoinColumn(name = "parada_id", nullable = true)
    private Parada parada;

    // CONSTRUCTORES
    public Credenciales() {}

    public Credenciales(String nombreUsuario, String contrasena, String correo,Perfil perfil) {
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        this.correo = correo;
        this.perfil = perfil;
    }

    // GETTERS Y SETTERS
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public Peregrino getPeregrino() {
        return peregrino;
    }

    public void setPeregrino(Peregrino peregrino) {
        this.peregrino = peregrino;
    }

    public Parada getParada() {
        return parada;
    }

    public void setParada(Parada parada) {
        this.parada = parada;
    }
    
    // METODOS ENTITY
    
    @Override
    public int hashCode() {
        return Objects.hash(nombreUsuario.toLowerCase(), contrasena, perfil, peregrino, parada);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Credenciales other = (Credenciales) obj;
        return Objects.equals(nombreUsuario.toLowerCase(), other.nombreUsuario.toLowerCase())
                && Objects.equals(contrasena, other.contrasena)
                && perfil == other.perfil
                && Objects.equals(peregrino, other.peregrino)
                && Objects.equals(parada, other.parada);
    }

    @Override
    public String toString() {
        return "Credenciales Usuario: " + nombreUsuario +
               "\nPerfil: " + perfil +
               "\nPeregrino: " + peregrino.getId() +
               "\nParada: " + parada.getId();
    }
}

