# introsde-2016-assignment-3-server

Davide Lissoni Mat.179878

12/12/2016

I worked in pair with Daniele Dellagiacoma.

The server is called by Daniele Dellagiacoma's client.

Daniele Dellagiacoma's client repository:https://github.com/DanieleDellagiacoma/introsde-2016-assignment-3-client;

Server Heroku application url:https://davidelissoniassignment3.herokuapp.com/;

Service url:https://davidelissoniassignment3.herokuapp.com/ws/people;

WSDL url:https://davidelissoniassignment3.herokuapp.com/ws/people?wsdl;

## 1.Introduction:

This readme is about the server part of the third assignment of the Introduction to Service Design and Engineering
course. The requirements was to develope a project similar to the assignment 2 by using SOAP services.
The server is in charge to provide a personal health profile service and the implementation satisfy all the requirements expected for the assignment.


## 2.Implementation:
The server structure and the database maintain more or less the same structure used in the laboratory and in the assignment 2 as well since the assignment requirements refer to the laboratory exercises.

The program has the following structure:

* **introsde.document.dao**:
  * *LifeCoachDao.java*: class used in order to connect the project model to the database.

* **introsde.document.endpoint**:
  * *PeoplePublisher.java*: service endpoint.
  
* **introsde.document.model**:this package contains the entity classes(java EE component that represents the persistent data maintained in the database lifecoach.sqlite used in this project). The classes contain also methods used to run database operations.
  * *HealthMeasureHistory.java*: refers to the database table HealthMeasureHistory;
  * *HealthProfile.java*: refers to the database table HealthProfile;
  * *MeasureDefinition.java*: refers to the database table MeasureDefinition;
  * *Person*: refers to the database table Person.

* **introsde.document.ws**: this package contains the classes responsible for the service methods available
  * *People.java*: interface for the wsdl service;
  * *PeopleImpl.java*: contains the implementation of the interface methods.
  
* **Other files presents in the repository**:
  * *build.xml*: ANT build script used to compile and execute the project through ANT;
  * *ivy.xml*: contains the project dependencies;
  * *Procfile*: used to perform ant calls during the deployng of the repository on Heroku;
  * *lifecoach.sqlite*: sqlite database.
 
The methods available in the wsdl service reflect exactly the methods required in the assignemnt instructions.

## 3.Deployng:

In order to create the war clone the git repository and then make sure to be located in the introsde-2016-assignment-3-server project folder and digit the command: 
```sh
ant create.war
```
This project has been deployed on heroku using the following buildpack: https://github.com/IntroSDE/heroku-buildpack-ant.git.
This buildpack is used to run ant commands during the deployng in order to initialize the project,create the war and finally start the endpoint class PeoplePublisher through the ant command 'ant start' present in the Procfile.

In order to deploy the project from this repository on Heroku digit the following sequence of commands:

```sh
$ git init

$ git add .

$ git commit -m "commit comments"

$ heroku create --stack cedar --buildpack  https://github.com/IntroSDE/heroku-buildpack-ant.git

$ git push heroku master
```




   

