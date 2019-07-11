==================================================================================
--------------------------------------READ-ME (Initial) ------------------------------------
==================================================================================

Copy the source code into local directory and import into eclipse/STS IDE as maven project

NOTE: After importing into IDE if faces any error regarding the Annotation then folow the below step
1. add the below line in sts.ini file
-javaagent:E:\sts-bundle\sts-3.8.4.RELEASE\lombok-1.18.0.jar
 download the lombok-1.18.0.jar from internet or you can get it from your local .m2 folder

--------------------------------------------------------------------------------------
After successfully import,configure the below parameter in application.properties file

server.port=11111

#logging.file=/home/citi-application.log (for linux)
or
logging.file=D:\\citi-POC-Test\\citi-application.log (for windows)


#mongodb
spring.data.mongodb.host=localhost
spring.data.mongodb.port=27017 (port)
spring.data.mongodb.database=hello (database name)


--------------------------------------------------------------
After the ablove configuration, run maven command to build the executable jar file

use command: (from the path of pom.xml)
> mvn clean package

after successful execute of maven build, a new jar will be created on target folder
cd /target 

run the jar use command (for testing )

java -jar citi-ms-dev.jar

now application is ready for use
---------------------------------------------------------------------------------

for the reference of input  request format and output response format refer below URL:

http://localhost:<port-number>/swagger-ui.html (from browser)

from this above URL you will find all the input  request format and output response format with sample JSON format
Hit the service URL from SOAP UI/ POSTMAN


for example:
http://localhost:<port-number>/citi/getReportDetails
REQUEST:
{
"fromDate": "2018-08-18",	
"toDate": "2018-08-20"
}

RESPONSE:


 {
   "responseCode": 200,
   "responseMessage": "Success",
   "clientReport":    [
            {
         "investorName": "Bank of Coimbatore ",
         "program": "Coromandel Train Company",
         "limit": "USD 100 mn",
         "ccySold": "EUR, USD",
         "investorPricing": "60 bps",
         "supplierExcluded": "ABC Ltd, Xyz Ltd",
         "supplierCountiesPermitted": "Europe, US",
         "obligorCodesPermitted": "Ericsson AB, LM Ericsson & Ericsson GMBH",
         "minTenor": 15,
         "maxTenor": 200,
         "minBookingMargin": 5,
         "maximumNumberOfNotesAllowed": 5,
         "minimumNoteSize": 100000,
         "lastUpdated": "2018-08-19T17:12:35.765+0000",
         "toDate": "2018-08-19T17:12:35.765+0000",
         "fromDate": "2018-08-19T17:12:35.766+0000"
      },
            {
         "investorName": "Bank of Coimbatore ",
         "program": "Coromandel Train Company",
         "limit": "USD 100 mn",
         "ccySold": "EUR, USD",
         "investorPricing": "60 bps",
         "supplierExcluded": "ABC Ltd, Xyz Ltd",
         "supplierCountiesPermitted": "Europe, US",
         "obligorCodesPermitted": "Ericsson AB, LM Ericsson & Ericsson GMBH",
         "minTenor": 15,
         "maxTenor": 200,
         "minBookingMargin": 5,
         "maximumNumberOfNotesAllowed": 5,
         "minimumNoteSize": 100000,
         "lastUpdated": "2018-08-19T17:21:37.545+0000",
         "toDate": "2018-08-19T17:21:37.546+0000",
         "fromDate": "2018-08-19T17:21:37.546+0000"
      }
          
   ]
}




Another test is devlop to insert some dummy data into database

http://localhost:<port-number>/citi/setReportDetails

REQUEST: (as per your require value)

     {
         "investorName": "Bank of Coimbatore ",
         "program": "Coromandel Train Company",
         "limit": "USD 100 mn",
         "ccySold": "EUR, USD",
         "investorPricing": "60 bps",
         "supplierExcluded": "ABC Ltd, Xyz Ltd",
         "supplierCountiesPermitted": "Europe, US",
         "obligorCodesPermitted": "Ericsson AB, LM Ericsson & Ericsson GMBH",
         "minTenor": 15,
         "maxTenor": 200,
         "minBookingMargin": 5,
         "maximumNumberOfNotesAllowed": 5,
         "minimumNoteSize": 100000,
         "lastUpdated": "2018-08-19T17:12:35.765+0000",
         "toDate": "2018-08-19T17:12:35.765+0000",
         "fromDate": "2018-08-19T17:12:35.766+0000"
      }
