Instructions:
-------------
1. Build the project and install it in the local Maven 2 repository:

  mvn install

2. Run the service endpoint using jetty (port 9090):

  cd golf-tournament-service-endpoint
  mvn jetty:run

3. Run the sample client using jetty (port 8080):

  cd golf-tournament-client
  mvn jetty:run

