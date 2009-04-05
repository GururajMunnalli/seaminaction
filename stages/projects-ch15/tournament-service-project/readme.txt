Instructions:
-------------
1. Build the project and install it in the local Maven 2 repository:

  mvn install

2. Run the service endpoint using jetty (port 9090):

  cd tournament-service-endpoint
  mvn jetty:run

Note: This step will start an H2 database over TCP and seed it with tournament data.

3. Run the test client using jetty (port 9091):

  cd tournament-service-test-client
  mvn jetty:run

4. Visit the test client in the browser to verify that the list of upcoming
   golf tournaments can be retrieved from the service

  http://localhost:9091/tournament-service-client

5. Run the Seam-based client using jetty (port 9094):

  cd tournament-service-seam-client
  mvn jetty:run

6. Visit the Seam application in the browser to verify that the list of upcoming
   golf tournaments can be retrieved from the service

  http://localhost:9094/tournament-service-client

7. Run the tournament manager web application, which uses Seam for the web
   layer and Spring for the backend:

  cd tournament-manager-webapp
  mvn jetty:run

Note: The tournament manager webapp depends on the database started in step #2.

8. Access the tournament manager application in the browser to search, create,
   and update tournaments:

  http://localhost:8080/tournament-manager

Note: You should see the tournaments update in the test clients when you modify
them in the manager application.
