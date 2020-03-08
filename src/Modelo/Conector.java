package Modelo;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;



public class Conector {

	private EntityManager em = null;

	public Conector() {
		try {
			
			em = Persistence.createEntityManagerFactory("CHAT").createEntityManager();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}

	public void cerrar() {
		try {
			em.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}
	
	public Usuario iniciarSesion(Usuario u) {
		Usuario resultado = null;
		
		try {
			Query consulta = em.createQuery("from Usuario where nombre = :nombre and password = :clave");
			consulta.setParameter("nombre", u.getNombre());
			consulta.setParameter("clave", u.getPassword());
			List<Usuario> r = consulta.getResultList();
			if(!r.isEmpty()) {
				resultado=r.get(0);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return resultado;
	}
	
	public boolean existeUsername(Usuario u) {
		boolean resultado = false;
		
		try {
			Query consulta = em.createQuery("from Usuario where nombre = :nombre");
			consulta.setParameter("nombre", u.getNombre());
			List<Usuario> r = consulta.getResultList();
			if(!r.isEmpty()) {
				resultado=true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return resultado;
	}
	
	public Usuario obtenerDestinatario(Usuario u) {
		Usuario resultado = null;
		
		try {
			Query consulta = em.createQuery("from Usuario where nombre = :nombre");
			consulta.setParameter("nombre", u.getNombre());
			List<Usuario> r = consulta.getResultList();
			if(!r.isEmpty()) {
				resultado=r.get(0);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return resultado;
	}
	
	public Usuario obtenerUsuario(Usuario u) {
		Usuario resultado = null;
		
		try {
			resultado = em.find(Usuario.class, u.getId());
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return resultado;
	}
	
	public boolean registrarUsuario(Usuario u) {
		boolean resultado = false;
		EntityTransaction t = null;
		try {
			t = em.getTransaction();
			t.begin();
			em.persist(u);
			t.commit();
			resultado = true;
			em.clear();
		}catch(Exception e) {
			t.rollback();
			e.printStackTrace();
		}
		
		return resultado;
	}
	
	public boolean modificarUsuario(Usuario u) {
		boolean resultado = false;
		EntityTransaction t = null;
		try {
			t=em.getTransaction();
			t.begin();
			em.merge(u);
			t.commit();
			resultado = true;
			em.clear();
		}catch(Exception e) {
			t.rollback();
			e.printStackTrace();
		}
		
		return resultado;
	}
	
	public boolean enviarMensaje(Mensaje m) {
		boolean resultado = false;
		EntityTransaction t = null;
		try {
			t = em.getTransaction();
			t.begin();
			em.persist(m);
			t.commit();
			resultado = true;
			em.clear();
		}catch(Exception e) {
			t.rollback();
			e.printStackTrace();
		}
		
		return resultado;
	}
	
	public List<Mensaje> mensajesEnviados(Usuario u){
		List<Mensaje> resultado = null;
		
		try {
			Query consulta = em.createQuery("from Mensaje where emisor.id = :emisor");
			consulta.setParameter("emisor", u.getId());
			resultado = consulta.getResultList();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return resultado;
	}
	
	public List<Mensaje> mensajesRecibidos(Usuario u){
		List<Mensaje> resultado = null;
		
		try {
			Query consulta = em.createQuery("from Mensaje where receptor.id = :receptor");
			consulta.setParameter("receptor", u.getId());
			resultado = consulta.getResultList();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return resultado;
	}
	
	public boolean borrarMensaje(Mensaje m) {
		boolean resultado = false;
		EntityTransaction t = null;
		try {
			t = em.getTransaction();
			t.begin();
			em.remove(m);
			t.commit();
			resultado = true;
			em.clear();
		} catch (Exception e) {
			t.rollback();
			e.printStackTrace();
		}
		return resultado;
	}
	
	public Mensaje existeMensaje(int id) {
		Mensaje resultado = null;
		
		try {
			Query consulta = em.createQuery("from Mensaje where id = :id");
			consulta.setParameter("id", id);
			List<Mensaje> r = consulta.getResultList();
			if(!r.isEmpty()) {
				resultado=r.get(0);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return resultado;
	}
	
}
