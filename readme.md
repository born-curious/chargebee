# Credit Management System
This project helps in managing the credits of the user which they purchased for using them against the services 
provided by the company. 

## Installation
The code has been written using jdk17. 

### JDK 17 Installation
https://stackoverflow.com/questions/69875335/macos-how-to-install-java-17 </br>
https://medium.com/miro-engineering/how-to-switch-between-java-lts-versions-8-11-and-17-on-mac-cb6717d1272

### Project Import
1. File > New Project > Project From Existing Sources </br>
![ScreenShot](./resources/Step1.png?raw=true "Step 1")
2. Choose the project </br>
![ScreenShot](./resources/Step2.png?raw=true "Step 2")
3. Select Maven to create the project </br>
![ScreenShot](./resources/Step3.png?raw=true "Step 3")


## Running without Installation
As JDK 17 might not be available on evaluator's local setup, I am providing two jars to run the program without any 
installation. 
The java8 package was produced by changing source and target to 1.8 and java17 package was produced by keeping source 
and target as 17 in pom.xml.

### Java17
java -cp src/main/resources/chargebee-1.0.0.jar com.chargebee.creditmanagement.Main "src/main/resources/input.txt"

### Java8
java -cp src/main/resources/chargebee-1.0.0-java8.jar com.chargebee.creditmanagement.Main "src/main/resources/input.txt"

## Dependencies Required
JUnit5 </br>
Mockito </br>
Mockito-inline </br>