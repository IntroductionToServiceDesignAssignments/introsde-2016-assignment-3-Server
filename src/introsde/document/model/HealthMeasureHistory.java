package introsde.document.model;

import introsde.document.dao.LifeCoachDao;
import introsde.document.model.Person;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Query;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The persistent class for the "HealthMeasureHistory" database table.
 * 
 */
// entity related to the table HealthMeasureHistory
@Entity
@Table(name = "HealthMeasureHistory")
@NamedQuery(name = "HealthMeasureHistory.findAll", query = "SELECT h FROM HealthMeasureHistory h")
@XmlRootElement
public class HealthMeasureHistory implements Serializable {
	private static final long serialVersionUID = 1L;
	// fields
	@Id
	@GeneratedValue(generator = "sqlite_mhistory")
	@TableGenerator(name = "sqlite_mhistory", table = "sqlite_sequence", pkColumnName = "name", valueColumnName = "seq", pkColumnValue = "HealthMeasureHistory")
	@Column(name = "idMeasureHistory")
	private int idMeasureHistory;

	@Column(name = "timestamp")
	@Temporal(TemporalType.TIMESTAMP)
	private Date timestamp;
	@Column(name = "value")
	private String value;
	@Column(name = "idMeasureDef", insertable = false, updatable = false)
	private int idMeasureDef;
	@Column(name = "idPerson", insertable = false, updatable = false)
	private int idPerson;
	// references
	@ManyToOne
	@JoinColumn(name = "idMeasureDef", referencedColumnName = "idMeasureDef")
	private MeasureDefinition measureDefinition;

	@ManyToOne
	@JoinColumn(name = "idPerson", referencedColumnName = "idPerson")
	private Person person;

	public HealthMeasureHistory() {
	}

	// getter and setter methods
	public int getIdMeasureHistory() {
		return this.idMeasureHistory;
	}

	public void setIdMeasureHistory(int idMeasureHistory) {
		this.idMeasureHistory = idMeasureHistory;
	}

	public Date getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public MeasureDefinition getMeasureDefinition() {
		return measureDefinition;
	}

	public void setMeasureDefinition(MeasureDefinition param) {
		this.measureDefinition = param;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person param) {
		this.person = param;
	}

	// database operations
	// get the History which id correspond to the given id as parameter, return
	// a HealthMeasureHistory
	public static HealthMeasureHistory getHealthMeasureHistoryById(long id) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		HealthMeasureHistory p = em.find(HealthMeasureHistory.class, id);
		LifeCoachDao.instance.closeConnections(em);
		return p;
	}

	// get all the Historys present in the db, return a list of
	// HealthMeasureHistory
	public static List<HealthMeasureHistory> getAll() {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		List<HealthMeasureHistory> list = em
				.createNamedQuery("HealthMeasureHistory.findAll", HealthMeasureHistory.class).getResultList();
		LifeCoachDao.instance.closeConnections(em);
		return list;
	}

	// save a new History in the db
	public static HealthMeasureHistory saveHealthMeasureHistory(HealthMeasureHistory p) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(p);
		tx.commit();
		LifeCoachDao.instance.closeConnections(em);
		return p;
	}

	// get the History which id and idmeasure correspond to the given id and
	// idmeasure as parameter, return a list of HealthMeasureHistory
	public static List<HealthMeasureHistory> getHistoryByIDPersonIdMeasure(long idperson, long idmeasure) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		try {
			Query q = em.createQuery(
					"SELECT h FROM HealthMeasureHistory h WHERE h.idMeasureDef=:measure AND h.idPerson=:idperson",
					HealthMeasureHistory.class);
			q.setParameter("measure", idmeasure);
			q.setParameter("idperson", idperson);
			List<HealthMeasureHistory> list = q.getResultList();
			LifeCoachDao.instance.closeConnections(em);
			return list;
		} catch (Exception e) {
			System.out.println(
					"The database doesn't contain a Measure History with the parameters required, please cheack the parammeters");
			LifeCoachDao.instance.closeConnections(em);

			return null;
		}

	}

	// update the History given as input with the new fields, return the updated
	// history
	public static HealthMeasureHistory updateHealthMeasureHistory(HealthMeasureHistory p) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		p = em.merge(p);
		tx.commit();
		LifeCoachDao.instance.closeConnections(em);
		return p;
	}

	// delete the History givean as input in the db
	public static void removeHealthMeasureHistory(HealthMeasureHistory p) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		p = em.merge(p);
		em.remove(p);
		tx.commit();
		LifeCoachDao.instance.closeConnections(em);
	}

	// get the History which id and idmeasure and mid(IdHistory)correspond to
	// the given id,idmeasure and mid as parameter, return a
	// HealthMeasureHistory
	public static HealthMeasureHistory getHealthMeasureHistoryByIDPersonIdMeasureMid(long idperson, long idmeasure,
			long mid) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		try {
			Query q = em.createQuery(
					"SELECT h FROM HealthMeasureHistory h WHERE h.idMeasureDef=:measure AND h.idPerson=:idperson AND h.idMeasureHistory=:mid",
					HealthMeasureHistory.class);
			q.setParameter("measure", idmeasure);
			q.setParameter("idperson", idperson);
			q.setParameter("mid", mid);
			HealthMeasureHistory health = (HealthMeasureHistory) q.getSingleResult();
			LifeCoachDao.instance.closeConnections(em);
			return health;
		} catch (Exception e) {
			System.out.println(
					"The database doesn't contain a HealthMeasureHistory with the parameters required, please cheack the parammeters");

			LifeCoachDao.instance.closeConnections(em);
			return null;
		}
	}

}
