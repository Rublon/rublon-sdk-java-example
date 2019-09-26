Configuration:

1) Add new application in Rublon Admin Console (with type "Custom integration using Java SDK").
2) Configure src/config.cfg file by modifying all properties.
   You can fetch RUBLON_SYSTEM_TOKEN and RUBLON_SECRET_KEY from Admin Console Panel after adding an application.
   RUBLON_API_SERVER is URL to the Rublon CORE (by default is: https://core.rublon.net)
   USER_PASSWORD property is used only to validate a login form for this demo application, so enter the value you want.
3) Download Java SDK directory from the Rublon GitHub (https://github.com/Rublon/rublon-sdk-java).
4) Go to the root directory of Java SDK and run `mvn clean install` command. [Maven must be installed] - To generate Build in Maven it is necessary to use JDK version above 1.8.
5) Jar file of this library should be created in a target/ directory and in local repository of Maven.
6) Under root Rublon Java SDK Example directory run `mvn clean package` command. The RublonExample-0.0.1.war file should be created in a target directory.
7) Download, configure, and start application server.
8) Deploy an Application on Server and launch. If you use Apache Tomcat, then deploy WAR to context "/".
9) When example has been correctly loaded, you can switch Login Form from 2Factor method to the Passwordless login method and back to authenticate the user.