# SM
SM Simultion

Two simple projects were built as an attempt to simulate what we learned in the sessions presented to us by Rajaie and Ahmad 
like the provision between the SM and the ME. also to present some of the new technologies we learned such as spring boot 
using Gradle to build the REST/SOAP web services that connected with the Aerospike NoSQL Database to perform the crud operations
on the records saved in the set in an Aerospike namespace.

The first project was a simulation to the service manager operations such as the crud operations of the bundles using a REST API 
to create/ read/ update/ delete bundles from the aerospike Database. also, to simulate the provision operation to provide an existing 
bundle in the SM Database to the ME Database using REST API that called to send a SOAP request to add the bundle to the ME database.

The second project was a simulation of the managed element operations in which a SOAP web service was built to add and get bundles 
from the aerospike database. The SOAP web service was used by the SM to provide a bundle from its database to the ME database.

The projects can be run using the jar file which was built using the command "java -jar" or "./gradlew bootRun"

*When trying to run the projects there must be an aerospike database server downloaded and run on the machine.
