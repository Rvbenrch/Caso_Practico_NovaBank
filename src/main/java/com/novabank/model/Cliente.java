package com.novabank.model;

public class Cliente {

    private Long id;
    private String nombre;
    private String apellidos;
    private String dni;
    private String email;
    private String telefono;

    // Constructor vacío requerido por JDBC
    public Cliente() {}

    // GETTERS Y SETTERS
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public static class Builder {

        private Long id;
        private String nombre;
        private String apellidos;
        private String dni;
        private String email;
        private String telefono;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder nombre(String nombre) {
            this.nombre = nombre;
            return this;
        }

        public Builder apellidos(String apellidos) {
            this.apellidos = apellidos;
            return this;
        }

        public Builder dni(String dni) {
            this.dni = dni;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder telefono(String telefono) {
            this.telefono = telefono;
            return this;
        }

        public Cliente build() {
            Cliente c = new Cliente();
            c.setId(id);
            c.setNombre(nombre);
            c.setApellidos(apellidos);
            c.setDni(dni);
            c.setEmail(email);
            c.setTelefono(telefono);
            return c;
        }
    }
}