# Introduction #

In order to use the HTTPS sheme in Seam, you have to first enable JBoss AS to accept HTTP connections over SSL. This page describes the necessary steps using a self-signed certificate.

## Details ##

In this example I'm only using a self-signed certificate, but the procedure would be more or less the same even if you are going to use a certificate from a Certification Authority.

  1. Generate the keystore using the command below. For the most part, you can just make up stuff for the responses. However, remember the password you provide and use the same password for the keystore and the key. When it asks for your first and last name, you should enter the hostname used for JBoss AS (i.e., localhost).
```
keytool -genkey -keyalg RSA -keystore jbossas.keystore -validity NUMBER_OF_DAYS
```
  1. Move the generated file the conf directory of the JBoss AS default domain (or the one you are using):
```
mv jbossas.keystore ${jboss.home}/server/default/conf/
```
  1. Open the file ${jboss.home}/server/default/deploy/jboss-web.deployer/server.xml in your editor, remove the XML comment around the SSL-connector, and modify the attributes to match the configuration shown here:
```
<Connector port="8443" protocol="HTTP/1.1" SSLEnabled="true"
           maxThreads="150" scheme="https" secure="true"
           clientAuth="false" sslProtocol="TLS" address="${jboss.bind.address}"
           keystoreFile="${jboss.server.home.dir}/conf/jbossas.keystore"
           keystorePass="PASSWORD_FOR_KEYSTORE"/>
```
  1. Now you should be able to access your application through https. The URL will begin with https instead of http and you need to include the port number if the port you provided in the configuration is anything other than 443:
```
https://localhost:8443
```

## Additional Resources ##

Check out the JBoss Wiki for all the details about using SSL with JBoss AS: http://wiki.jboss.org/wiki/SSLSetup