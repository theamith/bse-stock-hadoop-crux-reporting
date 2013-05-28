Objective of bse-stock-hadoop-crux-reporting eclipse project:
	
	To generate possible visualization(line,charts, etc..) of hadoop data stored in hbase-0.94.7 using crux-aggregation reporting tool.

Prerequisite:

	1. Install Java 
	2. Install pseudo-cluster hadoop. Refer https://docs.google.com/document/d/1v-J19xwJn-Pw9F8OCgLn04dqKwYkGIOxyqRAIGpmHk0/edit?pli=1
	3. Install hbase-0.94.7. I prefer to install hbase-0.94.7 because the tar file required to setup crux can be downloaded from 			https://github.com/mkamithkumar/crux-tar-for-hbase-0.94.7 
	4. Install Mysql
	5. Install Apache tomcat

Once you have the prerequisite setup crux:

	1. Download crux from https://github.com/sonalgoyal/crux and extract it to /usr/local(or any of your desired directory)
	2. Login to your mysql in terminal 
		hduser@Hadoop:~$ mysql -uroot -hlocalhost -p'your_mysql_root_password'
			a. Create database for crux in MySQL
				mysql>create databse crux;
				mysql>use crux;
			b. Create schema by running crux/db/schema.sql file in MySQL prompt, 
				mysql>source ${YOUR_CRUX_HOME}/db/schema.sql
			This creates the schema required for saving the report definitions.
	3. a. If your are using hbase-0.94.7 , then download crux.war and crux.tar from 
		https://github.com/mkamithkumar/crux-tar-for-hbase-0.94.7 
	   b. If your using some other version of hbase check whether it is available in
		https://github.com/sonalgoyal/crux/downloads
	   c. If no toher go, buil your own crux.war and crux.tar using Maven
		1. Install Maven
		2. Update hibernate.properties file which is present inside ${CRUX_HOME}/ with your MySQL host, port, dbname, testDbName, user 			   and password.
		3. Download struts2-fullhibernatecore-plugin-2.2.2-GA.jar from http://code.google.com/p/full-hibernate-plugin-for-struts2/
		   downloads/detail?name=struts2-fullhibernatecore-plugin-2.2.2-GA.jar&can=2&q=
		4. Add the jar file to your local repository by the following steps:
			i. In terminal change directory to  ${YOUR_CRUX_HOME}/
				hduser@Hadoop:~$ cd ${YOUR_CRUX_HOME}
			ii. Run the follwing maven command 
				hduser@Hadoop:/usr/local/crux-aggregation$ mvn install:install-file -DgroupId=com.google.code -
				DartifactId=struts2-fullhibernatecore-plugin -Dversion=2.2.2-GA-Dpackaging=jar -Dfile=${PATH_TO_struts2-
				fullhibernatecore-plugin-2.2.2-GA.jar}
			iii. The above will add the truts2-fullhibernatecore-plugin-2.2.2-GA.jar file to your maven repository(by default .m2 					directory)
			iv . Now build maven to generate crux.jar and crux.jar file		
				hduser@Hadoop:/usr/local/crux-aggregation$ mvn install
			v. Now go to ${YOUR_CRUX_HOME}/target folder you can find crux.jar and crux.war file
	   d. Copy crux.jar to ${YOUR_HBASE_HOME}/lib
	   e. Copy crux.war to tomcat/webapps
	4. a. Go to ${YOUR_HBASE_HOME}  then enter bin/start-hbase.sh to start hbase
			hduser@Hadoop:/usr/local/hbase$ sudo bin/start-hbase.sh 
	   b. Then start hbase shell by entering hbase shell
			hduser@Hadoop:/usr/local/hbase$ sudo bin/hbase shell
	5. a. Go to ${YOUR_CATALINA_HOME}  then enter bin/startup.sh to start tomcat server
			hduser@Hadoop:/usr/local/tomcat$ sudo bin/startup.sh
	6. Now your crux is setup goto  http://localhost:8080/crux and define your connection, mapping and report.

Setup Eclipse Project:


