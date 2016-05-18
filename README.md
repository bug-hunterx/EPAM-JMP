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

<<<<<<< HEAD
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
=======

## Homework 2

The goal of second homework is to familirise you with tools and technics used to investigate memory-related issues. 

### Task 1 - VisualVM

First start with VisualVM that provided in JDK. It's located in %JAVA_HOME%\bin folder. 

Your task in this exercise is to run some java application (I recomend to use InternedStringsDemo for this), connect to it with visualVM and see how PermGen or Heap is populating. Take some screenshots for your mentor. 

If it's possible I would recommend to try run code with different java version to see how memory articture changes. 

###Task 2 - GC log

Next one be -verbose:gc flag, which used to get gc log in console. 

I recomend to use some real java application, as example IntelliJ itself. You need to add VM options into idea.exe.vmoptions file to enable gc log. And then run IntellJ from command line by running idea.bat file. 

Don't forget about -Xloggc flag that allows you to redirect GC log into a file. 
I'm pretty sure there's a way to do similar things with eclipse as well. In case if you can't find a way - ask your mentor or me for help. 

Look at memory distribution in visualVM and check GC log. Answer these questions and provide corresponding rows from GC log: 

1. How offen garbage collection happens? 
2. How long full GC usually takes? 

This link will help you understand every details printed in gc log: http://karunsubramanian.com/websphere/troubleshooting-gc-step-by-step-instructions-to-analyze-verbose-gc-logs/

###Task 3 - Heap dump
For this exercise you need to download eclipse MAT tool from here 

1. Run GCDemo with with some limit for heap size (Xmx flag). Also use flag to tell JVM to dump heap if OOM occure (you remember what are that flags, right? :smirk: ) 
2. Add couple objects in app and get a heap eclipse MAT (File - Acquire Heap Dump)
3. Keep adding object untill you get OOM exeption and second dump will be created
4. Use eclipse MAT o compare these two dumps. You can find how to do it here https://www.ibm.com/developerworks/community/blogs/kevgrig/entry/how_to_use_the_memory_analyzer_tool_mat_to_compare_heapdumps_and_system_dumps20?lang=ru
5. Make a screenshot of such comparisong sorted by Shallow Size

###Task 4 - AppDynamic (bonus points) 
Install AppDynamic and use it to monitor any applications' memory. Provide screenshorts and short report of what did you achive with it. 








>>>>>>> 1d430cda92356da89b7dcec5d3f6764ce937bb48

