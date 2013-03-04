"AuditSurf" for Alfresco
================================

AuditSurf is a **SURF** app displaying repository usage info based on **audit**: users connected, number of created/read/updated documents displayed by hour/day/week/month, etc. and some monitoring info (requires JMX: memory used, content stores' disk size, lucene indexes disk size, etc.)  


Building the module
-------------------
Check out the project if you have not already done so 

        git clone git://github.com/atolcd/alfresco-auditsurf.git

Ant build scripts are provided to build an AMP file and a WAR file containing the custom files.  

### Build the entire project
Before building, ensure you have edited the `build.properties` file to set the path to your Alfresco SDK (pointing to the **lib** folder).  
To build files, run the following command from the base project directory:

        ant dist-files

### Build the alfresco module (AMP) only
To build the AMP file, run the following command from the 'module-alfresco' directory (if you have edited the `build.properties` file):  
  
        ant dist-amp

Or directly: 

        ant dist-amp -Dalfresco.sdk.lib.dir="PATH-TO-YOUR-ALFRESCO-SDK-LIB-DIRECTORY"

### Build the AuditSurf webapp (WAR) only
To build WAR file, run the following command from the 'surf-application' directory (if you have edited the `build.properties` file):  

        ant dist-war

Or directly: 

        ant dist-war -Dalfresco.sdk.lib.dir="PATH-TO-YOUR-ALFRESCO-SDK-LIB-DIRECTORY"

Installing the module
---------------------

1. Stop Alfresco  
  
2. Use the Alfresco [Module Management Tool](http://wiki.alfresco.com/wiki/Module_Management_Tool) to install the modules in your Alfresco WAR file:

        java -jar alfresco-mmt.jar install com.atolcd.alfresco.audit-vX.X.X.amp $TOMCAT_HOME/webapps/alfresco.war -force

3. Delete the `$TOMCAT_HOME/webapps/alfresco/` and `$TOMCAT_HOME/webapps/share/` folders.  
**Caution:** please ensure you do not have unsaved custom files in the webapp folders before deleting.
4. Copy the `auditsurf.war` WAR file into `tomcat/webapps/`
5. Enabling auditing
6. Start Alfresco


Configuring the module
---------------------
This extension uses [Alfresco audit mechanisms](http://docs.alfresco.com/3.4/topic/com.alfresco.Enterprise_3_4_0.doc/concepts/audit-intro.html). 
So, you have to add a line to your `alfresco-global.properties` file to enable audit :
 - `audit.enabled=true`       on Alfresco 3.4 or higher version
 - `audit.useNewConfig=true`  on Alfresco 3.3

### Remarks
- AuditSurf is compatible with Alfresco V3.3 or 3.x higher version ; JMX obviously needs an Enterprise release

- The AMP audits the following services:
   - FileFolderService
      * create
   - NodeService
      * createNode
   - CheckoutCheckinService
      * checkin
   - ContentService
      * getReader
   - AuthenticationService
      * authenticate
   - PersonService
      * createPerson
   - WorkflowService
      * startWorkflow


Using the module
---------------------
Go to: `http://server:port/auditsurf` (AuditSurf is restricted to Alfresco administrators).  


LICENSE
---------------------
This extension is licensed under `GNU General Public License (GPL)`.  
Created by: [Nicolas CHEVOBBE] (https://github.com/nchevobbe), Damien Deroussiaux, Matthieu Rollin, [Bertrand FOREST] (https://github.com/bforest) and Nicolas Forgeot.  


Our company
---------------------
[Atol Conseils et Développements] (http://www.atolcd.com) is Alfresco [Gold Partner] (http://www.alfresco.com/partners/atol)  
Follow us on twitter [ @atolcd] (https://twitter.com/atolcd)  