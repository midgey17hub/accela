# About
- The application uses SpringBoot 2.5.0, with Java 11
- It provides a REST endpoint to a database, which stores Person/Address data.
- The database is H2 in-memory. *(Oracle mode)*
- Liquibase is configured to automatically generate the required tables.
- The default port is 10123.

----

## Fields
Person
- firstName *(required)*
- lastName *(required)*

Address
- street *(required)*
- city
- state
- postalCode
- country *(required)*

----

# Build
Open terminal in project's root directory.

``mvn clean install``

# Run
Open terminal in project's **target** directory *(or wherever the .jar file is stored)*

- ``java -jar jm-0.0.1-SNAPSHOT.jar``

With custom port number XXX

- ``java -Dserver.port=XXX -jar jm-0.0.1-SNAPSHOT.jar``

----

# Interact 
## Command line
**Note:** please replace {id} with actual id in example commands below

#### Add Person
``curl -X PUT -u sa:sa -H "Content-Type: application/json" -d "{ \"firstName\": \"Josh\", \"lastName\": \"Holmes\" }" http://localhost:10123/person/add``
#### Edit Person
``curl -X POST -u sa:sa -H "Content-Type: application/json" -d "{ \"id\": 1, \"firstName\": \"Bruce\", \"lastName\": \"Wayne\" }" http://localhost:10123/person/edit``
#### Get Person
``curl -X GET -u sa:sa http://localhost:10123/person/{id}``
#### Remove Person
``curl -X DELETE -u sa:sa http://localhost:10123/person/remove/{id}``
#### Count People
``curl -X GET -u sa:sa http://localhost:10123/person/count``
#### List People
``curl -X GET -u sa:sa http://localhost:10123/person/list``

#### Add Address
``curl -X PUT -u sa:sa -H "Content-Type: application/json" -d "{ \"street\": \"123 Fake Street\", \"country\": \"Ireland\" }" http://localhost:10123/person/address/add/{person_id}``
#### Edit Address
``curl -X POST -u sa:sa -H "Content-Type: application/json" -d "{ \"id\": 1, \"street\": \"Main Road\", \"city\": \"New York\", \"country\": \"USA\" }" http://localhost:10123/person/address/edit``
#### Get Address
``curl -X GET -u sa:sa http://localhost:10123/person/address/{address_id}``
#### Remove Address
``curl -X DELETE -u sa:sa http://localhost:10123/person/address/remove/{address_id}``

## Postman
A much more pleasant experience would be to use Postman.

- https://www.postman.com/downloads/

- Run the app and choose the **Import** option (under "File", also in "My Workspace")

- Navigate to this file in the project and import it: ``jm/src/main/resources/JM.postman_collection.json``

The Collections tab should now contain "JM/Accela" with all the pre-defined requests inside.

*Each request inherits the Basic Auth from the parent "Accela"*