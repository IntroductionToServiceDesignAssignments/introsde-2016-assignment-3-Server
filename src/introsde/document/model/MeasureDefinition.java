package introsde.document.model;

import introsde.document.dao.LifeCoachDao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The persistent class for the "MeasureDefinition" database table.
 * 
 */
// entity related to the table MeasureDefinition
@Entity
@Table(name = "MeasureDefinition")
@NamedQuery(name = "MeasureDefinition.findAll", query = "SELECT m FROM MeasureDefinition m")
@XmlRootElement
public class MeasureDefinition implements Serializable {
	private static final long serialVersionUID = 1L;
	// fields
	@Id
	@GeneratedValue(generator = "sqlite_measuredef")
	@TableGenerator(name = "sqlite_measuredef", table = "sqlite_sequence", pkColumnName = "name", valueColumnName = "seq", pkColumnValue = "MeasureDefinition")
	@Column(name = "idMeasureDef")
	private int idMeasureDef;

	@Column(name = "measureName")
	private String measureName;

	@Column(name = "measureType")
	private String measureType;

	public MeasureDefinition() {
	}

	// getter and setter methods
	public int getIdMeasureDef() {
		return this.idMeasureDef;
	}

	public void setIdMeasureDef(int idMeasureDef) {
		this.idMeasureDef = idMeasureDef;
	}

	public String getMeasureName() {
		return this.measureName;
	}

	public void setMeasureName(String measureName) {
		this.measureName = measureName;
	}

	public String getMeasureType() {
		return this.measureType;
	}

	public void setMeasureType(String measureType) {
		this.measureType = measureType;
	}

	// database operations
	// get the MeasureDefinition which id correspond to the given id as
	// parameter, return a MeasureDefinition
	public static MeasureDefinition getMeasureDefinitionById(long personId) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		MeasureDefinition p = em.find(MeasureDefinition.class, (int) personId);
		LifeCoachDao.instance.closeConnections(em);
		return p;
	}

	// get all the MeasureDefinition present in the db, return a list of
	// MeasureDefinition
	public static List<MeasureDefinition> getAll() {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		List<MeasureDefinition> list = em.createNamedQuery("MeasureDefinition.findAll", MeasureDefinition.class)
				.getResultList();
		LifeCoachDao.instance.closeConnections(em);
		return list;
	}

	// save a new MeasureDefinition in the db
	public static MeasureDefinition saveMeasureDefinition(MeasureDefinition p) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(p);
		tx.commit();
		LifeCoachDao.instance.closeConnections(em);
		return p;
	}

	// update the MeasureDefinition given as input with the new fields, return
	// the updated MeasureDefinition
	public static MeasureDefinition updateMeasureDefinition(MeasureDefinition p) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		p = em.merge(p);
		tx.commit();
		LifeCoachDao.instance.closeConnections(em);
		return p;
	}

	// delete the MeasureDefinition givean as input in the db
	public static void removeMeasureDefinition(MeasureDefinition p) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		p = em.merge(p);
		em.remove(p);
		tx.commit();
		LifeCoachDao.instance.closeConnections(em);
	}

	// query the database and get the id of the MeasureDefinition which name
	// correspond to the nname passed as parameters, return the id
	public static int getIDByMeasureName(String measureType) {

		EntityManager em = LifeCoachDao.instance.createEntityManager();
		try {
			Query q = em.createQuery("SELECT m.idMeasureDef FROM MeasureDefinition m WHERE m.measureName=:measure",
					MeasureDefinition.class);
			q.setParameter("measure", measureType);
			int id = (Integer) q.getSingleResult();
			LifeCoachDao.instance.closeConnections(em);
			return id;
		} catch (Exception e) {
			System.out.println("The database doesn't contain the Measure required, please cheack the parammeters");

			LifeCoachDao.instance.closeConnections(em);
			return 0;
		}

	}

}
