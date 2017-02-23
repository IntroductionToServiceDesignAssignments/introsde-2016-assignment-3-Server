package introsde.document.ws;
import introsde.document.model.HealthMeasureHistory;
import introsde.document.model.HealthProfile;
import introsde.document.model.Person;
import introsde.document.model.MeasureDefinition;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.WebResult;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;
//service definition
@WebService
@SOAPBinding(style = Style.DOCUMENT, use=Use.LITERAL) 
public interface People {
    @WebMethod(operationName="readPerson")
    @WebResult(name="person") 
    public Person readPerson(@WebParam(name="personId") long id);
 
    @WebMethod(operationName="readPersonList")
    @WebResult(name="people") 
    public List<Person> readPersonList();
 
    @WebMethod(operationName="createPerson")
    @WebResult(name="personId") 
    public Person createPerson(@WebParam(name="person") Person person);
 
    @WebMethod(operationName="updatePerson")
    @WebResult(name="personId") 
    public Person updatePerson(@WebParam(name="person") Person person);
    
    @WebMethod(operationName="deletePerson")
    @WebResult(name="personId") 
    public int deletePerson(@WebParam(name="personId") long id);
    
    @WebMethod(operationName="updatePersonMeasure")
    @WebResult(name="hpId") 
    public HealthProfile updatePersonMeasure(@WebParam(name="personId") long id, @WebParam(name="healthProfile") HealthProfile hp);
    
    @WebMethod(operationName="readPersonHistorys")
    @WebResult(name="history") 
    public List <HealthMeasureHistory> readPersonHistory(@WebParam(name="personId") long id, @WebParam(name="measureType") String measureType);
    
    @WebMethod(operationName="readMeasureTypes")
    @WebResult(name="measureTypes") 
    public List<MeasureDefinition> readMeasureTypes();
    
    @WebMethod(operationName="readPersonMeasure")
    @WebResult(name="personMeasure") 
    public HealthMeasureHistory readPersonMeasure(@WebParam(name="personId") long id, @WebParam(name="measureType") String measureType, @WebParam(name="mid") long mid);
    
    @WebMethod(operationName="savePersonMeasure")
    @WebResult(name="save") 
    public HealthProfile savePersonMeasure(@WebParam(name="personId") long id, @WebParam(name="healthProfile") HealthProfile hp);
    
}