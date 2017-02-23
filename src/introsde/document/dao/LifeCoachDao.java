package introsde.document.dao;

import introsde.document.model.Person;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public enum LifeCoachDao {
	instance;
	private EntityManagerFactory emf;

	// entity manager init
	private LifeCoachDao() {
		if (emf != null) {
			emf.close();
		}
		emf = Persistence.createEntityManagerFactory("introsde-jpa");
	}

	// entity manager creation
	public EntityManager createEntityManager() {
		return emf.createEntityManager();
	}

	// entity manager close
	public void closeConnections(EntityManager em) {
		em.close();
	}

	// transaction
	public EntityTransaction getTransaction(EntityManager em) {
		return em.getTransaction();
	}

	public EntityManagerFactory getEntityManagerFactory() {
		return emf;
	}

	// get the person with the id pass as parameter, return a person
	public static Person getPersonById(Long personId) {
		EntityManager em = instance.createEntityManager();
		Person p = em.find(Person.class, personId);
		instance.closeConnections(em);
		return p;
	}

	// get all the person in the db, return a list
	public static List<Person> getAll() {
		EntityManager em = instance.createEntityManager();
		List<Person> list = em.createNamedQuery("Person.findAll", Person.class).getResultList();
		instance.closeConnections(em);
		return list;
	}

}
