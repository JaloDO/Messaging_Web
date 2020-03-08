package Modelo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="mensaje")
public class Mensaje {
	@Column(name="id",nullable=false)
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int codigo;
	
	@ManyToOne
	@JoinColumn(name="emisor", referencedColumnName = "id")
	private Usuario emisor;
	
	@ManyToOne
	@JoinColumn(name="receptor", referencedColumnName = "id")
	private Usuario receptor;
	
	@Column(nullable=false)
	private String contenido;
	
	@Column(nullable=false)
	@Temporal(TemporalType.DATE)
	private Date fecha;
	
	public Mensaje() {
	}

	public Mensaje(int codigo, Usuario emisor, Usuario receptor, String contenido, Date fecha) {
		this.codigo = codigo;
		this.emisor = emisor;
		this.receptor = receptor;
		this.contenido = contenido;
		this.fecha = fecha;
	}


	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public Usuario getEmisor() {
		return emisor;
	}

	public void setEmisor(Usuario emisor) {
		this.emisor = emisor;
	}

	public Usuario getReceptor() {
		return receptor;
	}

	public void setReceptor(Usuario receptor) {
		this.receptor = receptor;
	}

	public String getContenido() {
		return contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Date getFecha() {
		return fecha;
	}	
	
}
