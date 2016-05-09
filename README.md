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

In this task, instead of csv files, you will use HSQLDB as datasource.
To initialize homework - run HsqlInitTest.runThisTestToBuildHSQLDBLocally()
It will create database files in root folder of your project - jmpdb.*

After that run test AccidentsControllerIntegrationTest.testFindOne.
It will connect to your local database and get one accident by Id.

As you can see AccidentControllerBasicImpl is quite dummy :)
Improve it with some framework - Spring Data with JPA or Spring JDBC - up to you.

