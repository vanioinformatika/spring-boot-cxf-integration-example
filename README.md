Spring-integration project with CXF WebService endpoint botstrapped with Spring-boot
====================================================================================

Spring-boot project with Spring-integration and CXF WebService MTOM aware endpoint handling large attachements.

To try out:
-----------

1. check out the project from GitHub
1. install JDK 1.8+ (Java8)
1. install Maven 3.0.3+
1. set JAVA_HOME environment variable to home of the installed JDK 1.8
1. go to the project directory and issue mvn clean spring-boot:run
1. open a browser and enter the following address: http://localhost:8080/services/contentStoreCxf?wsdl
1. use the WSDL to create a client of your preference

