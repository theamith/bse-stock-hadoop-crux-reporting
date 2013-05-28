Objective:
-	
<h6>To generate possible visualization(line,charts, etc..) of hadoop data stored in hbase-0.94.7 using crux-aggregation reporting tool.</h6>


Pre-requisite:
-
<ol>
	<li>Install Java</li> 
	<li>Install pseudo-cluster hadoop. Refer https://docs.google.com/document/d/1v-J19xwJn-Pw9F8OCgLn04dqKwYkGIOxyqRAIGpmHk0/edit?pli=1</li>
	<li>Install hbase-0.94.7. I prefer to install hbase-0.94.7 because the tar file required to setup crux can be downloaded from https://github.com/mkamithkumar/crux-tar-for-hbase-0.94.7 </li>
	<li>Install Mysql</li>
	<li>Install Apache tomcat</li>
</ol>
Once you have the prerequisite setup crux:
-
- Download crux from https://github.com/sonalgoyal/crux and extract it to /usr/local(or any of your desired directory)<br/>
- Login to your mysql in terminal <br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<code>hduser@Hadoop:~$ mysql -uroot -hlocalhost -p'your_mysql_root_password'</code>
		<ol>
			<li> Create database for crux in MySQL</li>
				<code>mysql>create databse crux;</code>
				<code>mysql>use crux;</code>
			<li>Create schema by running crux/db/schema.sql file in MySQL prompt, </li>
				<code>mysql>source ${YOUR_CRUX_HOME}/db/schema.sql</code>
				This creates the schema required for saving the report definitions
		</ol>
- Generate crux.jar and crux.war file:
<ol>
 <li>If your are using hbase-0.94.7 , then download crux.war and crux.tar from https://github.com/mkamithkumar/crux-tar-for-hbase-0.94.7</li> 
 <li>If your using some other version of hbase check whether it is available in https://github.com/sonalgoyal/crux/downloads</li>
 <li>If no other go, buil your own crux.war and crux.tar using Maven</li>
</ol>
 Configure Maven:
  - Install Maven
  - Update hibernate.properties file which is present inside <code> ${CRUX_HOME}/ </code> with your MySQL host, port, dbname, testDbName, user 			   and password.
  - Download struts2-fullhibernatecore-plugin-2.2.2-GA.jar from http://code.google.com/p/full-hibernate-plugin-for-struts2/downloads/detail?name=struts2-fullhibernatecore-plugin-2.2.2-GA.jar&can=2&q=
  - Add the jar file to your local repository by the following steps:<br/>

 Generate crux.jar and crux.tar file:
  - In terminal change directory to  <code>${YOUR_CRUX_HOME}/</code><br/>
	<code>hduser@Hadoop:~$ cd ${YOUR_CRUX_HOME}</code><br/>
  - Run the follwing maven command
	<code>hduser@Hadoop:/usr/local/crux-aggregation$ mvn install:install-file -DgroupId=com.google.code -DartifactId=struts2-fullhibernatecore-plugin -Dversion=2.2.2-GA-Dpackaging=jar -Dfile=${PATH_TO_struts2-fullhibernatecore-plugin-2.2.2-GA.jar}</code></br/>
  - The above will add the truts2-fullhibernatecore-plugin-2.2.2-GA.jar file to your maven repository(by default .m2 directory)
  - Now build maven to generate crux.jar and crux.jar file		
	<code> hduser@Hadoop:/usr/local/crux-aggregation$ mvn install</code><br/>
  - Now go to <code>${YOUR_CRUX_HOME}/target </code>folder you can find crux.jar and crux.war file
  - Copy crux.jar to <code>${YOUR_HBASE_HOME}/lib</code></br>
  - Copy crux.war to <code>tomcat/webapps</code></br>
- Start hbase
  - Go to <code>${YOUR_HBASE_HOME}</code>  then enter bin/start-hbase.sh to start hbase
	<code>hduser@Hadoop:/usr/local/hbase$ sudo bin/start-hbase.sh </code><br/>
  - Then start hbase shell by entering hbase shell
	<code>hduser@Hadoop:/usr/local/hbase$ sudo bin/hbase shell</code><br />
- Start tomcat
  - Go to ${YOUR_CATALINA_HOME}  then enter bin/startup.sh to start tomcat server
	<code>hduser@Hadoop:/usr/local/tomcat$ sudo bin/startup.sh</code><br />
  - Now your crux is setup goto  http://localhost:8080/crux and define your connection, mapping and report.

Setup Eclipse Project:
-


