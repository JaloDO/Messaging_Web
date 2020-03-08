package Modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="usuario")
public class Usuario {
	
	@Column(name="id",nullable=false)
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@Column(nullable=false)
	private String nombre;
	@Column(nullable=false)
	private String password;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "emisor")
	private List<Mensaje> enviados = new ArrayList<Mensaje>();
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "receptor")
	private List<Mensaje> recibidos = new ArrayList<Mensaje>();
	
	public Usuario() {
	}

	public Usuario(int id, String nombre, String password, List<Mensaje> enviados, List<Mensaje> recibidos) {
		this.id = id;
		this.nombre = nombre;
		this.password = password;
		this.enviados = enviados;
		this.recibidos = recibidos;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Mensaje> getEnviados() {
		return enviados;
	}

	public void setEnviados(List<Mensaje> enviados) {
		this.enviados = enviados;
	}

	public List<Mensaje> getRecibidos() {
		return recibidos;
	}

	public void setRecibidos(List<Mensaje> recibidos) {
		this.recibidos = recibidos;
	}
	
	public void mostrar() {
		System.out.println("ID: "+id+"\tNombre: "+nombre);
	}
}
