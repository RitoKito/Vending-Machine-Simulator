**SOFT2412 Assignment 2 - Scrum Software Development Project**
 
--------
Running
--------

This is a vending machine application that, currently, allows the user to login as an anonymous user. Once this is done, they can view a list of products, 
and select specific items for purchase, including buying multiple items at once. They can then check out and choose a payment option (currently only cash payments are supported)
, and then receive their change (currently through the console).

To run the application, input the following into your terminal in the 08_G2_Ass2 directory:
```gradle
gradle run
```
This will then open a JavaFX window, in which the user can use the application as guided by the user interface provided.


--------
Testing
--------

To run test cases, and exclude the UI from testing, first you will have to delete the WindowManager file. (Make sure to back this file up in a seperate location).  
After this has been done, run ``` gradle test ```.

This will then generate the JUnit and jacoco test reports. The jacoco test report will be generated in ```build/customJacocoReportDir``` to view the code coverage.