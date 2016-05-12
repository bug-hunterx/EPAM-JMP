# EPAM-JMP

##Setting up environment

### Instal required software

1) Downloadand install JDK 8 http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html

2) Download and install Git bash https://git-scm.com/download/win 

3) Download and install Apache Maven http://maven.apache.org/download.cgi

###Set up your git repo

1) Fork this repository

2) Clone your repository to your local machine 

##Homework submition

When you finished required code locally, push it to your repository on github and create a pull request to this repo.

In pull request mark your mentor (@mentorName) so it can review it. 


## Hometask 1

To complite task one you need to fix DataProcessorTest unit tests. 
All methods in DataProcessor with "7" in a name should be implemented using Java 7, when other should be done with Java 8 streaming api. 

## Hometask 4
In the code what you did on Hometask 3 please create tests to achieve at least 50% test coverage in your new classes. Use IDE plugin to measure.  At least 1 integration test is required. Try to use BDD approach.  

Recommendations will be : 
1. write unit test for getContactNumber (JUnit)
2. write unit tests for your service from hometask3 (Mockito/PowerMock: you have to mock all external/additional services which you use in your solution). This part depends on your own solution. 
3. write integration test for your file writing functionality ( don't use any mocks here) 


##Hometask 5

Scenarios to be implemented for Homework:

1. Find all the accidents by ID(Note: We can use findOne method which will accept the Accident ID as PK).
2. Find all the accidents count groupby all roadsurface conditions .
3. Find all the accidents count groupby accident year and weather condition .( For eg: in year 2009 we need to know the number of accidents based on each weather condition).
4. On a given date,  fetch all the accidents and update the Time based on the below rules
Time Logic: 
MORNING - 6 am to 12 pm
AFTERNOON - 12 pm to 6 pm
EVENING - 6 pm to 12 am
NIGHT - 12 am to 6 am

    
Recommendations:

1. For above scenarios, you can use either Spring JDBC/ORM  or use Spring Data as we discussed in the session.​
2. Use DI (either setter/constructor) for some dependencies.
3. For repositories use Autowiring either declarative or Annotation approach.
4. Please write the entities for all the tables required with all the associations using JPA annotations.
5. DB related properties will be defined in persistence.xml
6. JPA will be configured in spring config file which inturn will search for persistence.xml under META-INF/persistence.xml.

Examples are availabe in below package:

com.epam.demo.springdataexample1 -> example1 discussed in session
com.epam.demo.springdataexample2 -> example2 discussed in session
com.epam.dbrepositories  -> All repositories required for Spring Data will be here.
com.epam.dbservice           -> Service Interface
com.epam.entities               -> Will have the entities required by JPA incase you are using Spring Data JPA aaproach
com.epam.processor. AccidentDBServiceImpl -> will actually interact with Spring Data repositories                


