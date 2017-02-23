package introsde.document.model;

import introsde.document.dao.LifeCoachDao;
import introsde.document.model.MeasureDefinition;
import introsde.document.model.HealthProfile;

import java.io.Serializable;
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
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.persistence.OneToOne;
import javax.persistence.Query;

/**
 * The persistent class for the "HealthProfile" database table.
 * 
 */
// entity related to the table HealthProfile
@Entity
@Table(name = "HealthProfile")
@NamedQuery(name = "HealthProfile.findAll", query = "SELECT h FROM HealthProfile h")
@XmlRootElement(name = "Measure")
public class HealthProfile implements Serializable {
	private static final long serialVersionUID = 1L;
	// fields
	@Id
	@GeneratedValue(generator = "sqlite_healthprofile")
	@TableGenerator(name = "sqlite_healthprofile", table = "sqlite_sequence", pkColumnName = "name", valueColumnName = "seq", pkColumnValue = "HealthProfile")
	@Column(name = "idMeasure")
	private int idMeasure;
	@Column(name = "idMeasureDef", insertable = false, updatable = false)
	private int idMeasureDef;
	@Column(name = "idPerson", insertable = false, updatable = false)
	private int idPerson;
	@Column(name = "value")
	private String value;
	// references
	@OneToOne
	@JoinColumn(name = "idMeasureDef", referencedColumnName = "idMeasureDef", insertable = true, updatable = true)
	private MeasureDefinition measureDefinition;

	@ManyToOne
	@JoinColumn(name = "idPerson", referencedColumnName = "idPerson")
	private Person person;

	public HealthProfile() {
	}

	// getter and setter methods
	public int getIdMeasure() {
		return this.idMeasure;
	}

	public void setIdMeasure(int idMeasure) {
		this.idMeasure = idMeasure;
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

	@XmlTransient
	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	// Database operations
	// get the HealthProfile which id correspond to the given id as parameter,
	// return a HealthProfile
	public static HealthProfile getLifeStatusById(long healthprofileId) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		HealthProfile p = em.find(HealthProfile.class, (int) healthprofileId);
		LifeCoachDao.instance.closeConnections(em);
		return p;
	}

	// get all the HealthProfile present in the db, return a list of
	// HealthProfile
	public static List<HealthProfile> getAll() {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		List<HealthProfile> list = em.createNamedQuery("HealthProfile.findAll", HealthProfile.class).getResultList();
		LifeCoachDao.instance.closeConnections(em);
		return list;
	}

	// save a new HealthProfile in the db
	public static HealthProfile saveLifeStatus(HealthProfile p) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(p);
		tx.commit();
		LifeCoachDao.instance.closeConnections(em);
		return p;
	}

	// update the HealhtProfile given as input with the new fields, return the
	// updated HealthProfile
	public static HealthProfile updateLifeStatus(HealthProfile p) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		p = em.merge(p);
		tx.commit();
		LifeCoachDao.instance.closeConnections(em);
		return p;
	}

	// delete the History givean as input in the db
	public static void removeLifeStatus(HealthProfile p) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		p = em.merge(p);
		em.remove(p);
		tx.commit();
		LifeCoachDao.instance.closeConnections(em);
	}

	// get the HealthProfile which idpersonand and idmeasure correspond to the
	// given idperson and idmeasure as parameter, return a HealthProfile
	public static HealthProfile getHealthProfileByIDs(long idperson, long idmeasure) {

		EntityManager em = LifeCoachDao.instance.createEntityManager();
		try {
			Query q = em.createQuery(
					"SELECT h FROM HealthProfile h WHERE h.idMeasureDef=:measure AND h.idPerson=:idperson",
					HealthProfile.class);
			q.setParameter("measure", (int) idmeasure);
			q.setParameter("idperson", (int) idperson);
			HealthProfile health = (HealthProfile) q.getSingleResult();
			LifeCoachDao.instance.closeConnections(em);
			return health;
		} catch (Exception e) {
			System.out.println(
					"The database doesn't contain a HealthProfile for the person choosen with the parameters required, please cheack the parammeters");

			LifeCoachDao.instance.closeConnections(em);
			return null;
		}
	}
	

	
}
