package introsde.document.ws;

import introsde.document.model.HealthMeasureHistory;
import introsde.document.model.HealthProfile;
import introsde.document.model.Person;
import introsde.document.model.MeasureDefinition;

import java.util.Date;
import java.util.List;

import javax.jws.WebService;

//Service Implementation

@WebService(endpointInterface = "introsde.document.ws.People", serviceName = "PeopleService")
public class PeopleImpl implements People {

	// give all the Personal information plus current measures of one Person
	// identified by {id}
	@Override
	public Person readPerson(long id) {
		System.out.println("---> Reading Person by id = " + id);
		Person p = Person.getPersonById(id);
		if (p != null) {
			System.out.println("---> Found Person by id = " + id + " => " + p.getfirstname());
		} else {
			System.out.println("---> Didn't find any Person with  id = " + id);
		}
		return p;
	}

	// list all the people in the database
	@Override
	public List<Person> readPersonList() {
		return Person.getAll();
	}

	// create a new Person and return the newly created Person with its assigned
	// id
	@Override
	public Person createPerson(Person person) {
		Person.savePerson(person);
		return person;
	}

	// update the Personal information of the Person identified by {id}
	@Override
	public Person updatePerson(Person person) {
		Person.updatePerson(person);
		return person;
	}

	// delete the Person identified by {id} from the system
	@Override
	public int deletePerson(long id) {
		Person p = Person.getPersonById(id);
		if (p != null) {
			Person.removePerson(p);
			return 0;
		} else {
			return -1;
		}
	}

	// update the measure identified with {m.mid}, related to the Person
	// identified by {id}
	@Override
	public HealthProfile updatePersonMeasure(long id, HealthProfile hp) {
		MeasureDefinition measureType = hp.getMeasureDefinition();
		int idMeasure = measureType.getIdMeasureDef();
		HealthProfile ls = HealthProfile.getHealthProfileByIDs(id, idMeasure);

		if (ls.getPerson().getIdPerson() == id) {
			hp.setIdMeasure(ls.getIdMeasure());
			hp.setPerson(Person.getPersonById(id));
			HealthProfile.updateLifeStatus(hp);
			return hp;
		} else {
			return null;
		}
	}

	// return the list of values (the history) of {measureType} (e.g. weight)
	// for Person identified by {id}
	@Override
	public List<HealthMeasureHistory> readPersonHistory(long id, String measureType) {
		int idMeasure = MeasureDefinition.getIDByMeasureName(measureType);
		List<HealthMeasureHistory> History = HealthMeasureHistory.getHistoryByIDPersonIdMeasure(id, idMeasure);
		return History;
	}

	// return the list of measures
	@Override
	public List<MeasureDefinition> readMeasureTypes() {
		List<MeasureDefinition> measures = MeasureDefinition.getAll();
		return measures;
	}

	// return the value of {measureTypeHistory} (e.g. weight) identified by
	// {mid} for Person identified by {id}
	@Override
	public HealthMeasureHistory readPersonMeasure(long id, String measureType, long mid) {
		int idMeasure = MeasureDefinition.getIDByMeasureName(measureType);
		HealthMeasureHistory Health = HealthMeasureHistory.getHealthMeasureHistoryByIDPersonIdMeasureMid(id, idMeasure,
				mid);
		return Health;
	}

	// save a new measure object {m} (e.g. weight) of Person identified by {id}
	// and archive the old value in the history
	// if the helthprofile ia new for the measure passed, just save a new health
	// profile
	@Override
	public HealthProfile savePersonMeasure(long id, HealthProfile measure) {

		MeasureDefinition measureType = measure.getMeasureDefinition();
		int idMeasure = measureType.getIdMeasureDef();
		HealthProfile health = HealthProfile.getHealthProfileByIDs(id, idMeasure);
		Person p = Person.getPersonById(id);
		MeasureDefinition m = MeasureDefinition.getMeasureDefinitionById(idMeasure);
		HealthProfile healthProfile = new HealthProfile();
		if (health == null) {
			healthProfile.setMeasureDefinition(m);
			healthProfile.setValue(measure.getValue());
			healthProfile.setPerson(p);
			HealthProfile.saveLifeStatus(healthProfile);

		} else {
			String Oldvalue = health.getValue();
			HealthMeasureHistory history = new HealthMeasureHistory();
			history.setPerson(p);
			history.setMeasureDefinition(m);
			history.setValue(Oldvalue);
			history.setTimestamp(new Date());
			HealthMeasureHistory.saveHealthMeasureHistory(history);
			HealthProfile.removeLifeStatus(health);
			healthProfile.setValue(measure.getValue());
			healthProfile.setPerson(p);
			healthProfile.setMeasureDefinition(m);
			HealthProfile.saveLifeStatus(healthProfile);
		}

		return healthProfile;
	}

}
