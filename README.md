Environment Set up:

1. Checkout the code from git hub : https://github.com/lathajb/GOOUT.git

2. Go to that folder enter mvn clean eclipse:eclipse

3. To run mvn spring-boot:run

4. Access the Restfull service endpoint by using : http://localhost:8080/xmlconverter

  headers - Content-Type : application/xml
          Accept : application/xml
          
  Sample Payload : { "name":"latha"}
  
  
  Expected output : <object> 
                      <string name="name">latha</string> 
                    </object> 
