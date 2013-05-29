Objective:
-	
<h6>To generate possible visualization(line,charts, etc..) of hadoop data stored in hbase-0.94.7 using crux-aggregation reporting tool.</h6>


Prerequisite:
-
<ol>
	<li>Install Java</li> 
	<li>Install pseudo-cluster hadoop. Refer https://docs.google.com/document/d/1v-J19xwJn-Pw9F8OCgLn04dqKwYkGIOxyqRAIGpmHk0/edit?pli=1</li>
	<li>Install hbase-0.94.7.Refer https://docs.google.com/document/d/1ohAR8BLLX6b4M7-Rw1zCXtj2MFEYzLXilSkiWg-KNLI/edit?pli=1 I prefer to install hbase-0.94.7 because the tar file required to setup crux can be downloaded from https://github.com/mkamithkumar/crux-tar-for-hbase-0.94.7 </li>
	<li>Install Mysql</li>
	<li>Install Apache tomcat. Refer https://gist.github.com/mkamithkumar/5668122</li>
</ol>
Setup Crux:
-
- Download crux from https://github.com/sonalgoyal/crux and extract it to /usr/local(or any of your desired directory)<br/>
- Login to your mysql in terminal <br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<code>hduser@Hadoop:~$ mysql -uroot -hlocalhost -p'your_mysql_root_password'</code>
		<ol>
			<li> Create database for crux in MySQL</li>
				<code>mysql>create databse crux;</code><br/>
				<code>mysql>use crux;</code>
			<li>Create schema by running ${YOUR_CRUX_HOME}/db/schema.sql file in MySQL prompt, </li>
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
	<code>hduser@Hadoop:/usr/local/crux-aggregation$ mvn install:install-file -DgroupId=com.google.code -DartifactId=struts2<code>
</code>-fullhibernatecore-plugin -Dversion=2.2.2-GA-Dpackaging=jar -Dfile=${PATH_TO_struts2-fullhibernatecore-plugin-2.2.2-GA.jar}</code></br/>
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
  - Go to <code>${YOUR_CATALINA_HOME}</code>  then enter bin/startup.sh to start tomcat server
	<code>hduser@Hadoop:/usr/local/tomcat$ sudo bin/startup.sh</code><br />
  - Now your crux is setup goto  http://localhost:8080/crux and define your connection, mapping and report.

Setup Eclipse Project:
-
<ul>
	<li>Download <b>bse-stock-hadoop-crux-reporting</b> git hub repository and extract it</li>
	<li>Open Eclipse and import <b>BSEStock</b> project into workspace</li> 
	<li> Resolve the build path issues by adding all jar files from <code>${YOUR_HADOOP_HOME}</code> and <code>${YOUR_HADOOP_HOME}/lib</code></li>
	<li>Add hbase jar from <code>${YOUR_HBASE_HOME}</code> and zookeeper jar from <code>${YOUR_HBASE_HOME}/lib</code> to project build path</li>  
	<li>Add the jar files in the dependency folder to your eclipse build path</li>
	<li>Create a table in hbase</li>
	<code>hbase(main):006:0>  create 'stockDataComposite','price'</code>
	<li>Copy the <b>BSEStockdata.txt</b> file from the dependency folder to HDFS</li>
	<code>hadoop dfs -copyFromLocal $HOME/Downloads/BSEStockData.txt hdfs://localhost:54310/user/hduser/projects/input/bsestock</code>
	<li>Change the input file path in <b>StockDriver.java</b> to your hdfs path of input file</li>
	<li>Run <b>StockDriver.java</b> as Hadoop Job</li> 
	<li> Open crux web ui http://localhost:8080/crux</li>
	<li>Give <em>Connection Name</em> as <b>HbaseConnection</b> and <em>HBase Zookeeper Location</em> as <b>localhost:2181</b></li>
	<li>Add a mapping by clicking Mapping link with <em>Mapping Name</em> as <b>BSEStockMapping(or your girlfriend's name)</b> </li>
	<li>Add Row Key Alias
	
		<ol>
			<li><em>Alias:</em> <b>stockId</b><br/><em>Length:</em> <b>6</b><br/><em>Value Type:</em> <b>String</b></li>
			<li><em>Alias:</em> <b>date</b><br/><em>Length:</em> <b>8</b><br/><em>Value Type:</em> <b>Long</b></li>
		</ol>
	</li>
	<li>Add Column Alias
		<ol>
			<li><em>Column Family:</em> <b>price</b><br/><em>Qualifier:</em> <b>close</b><br/><em>Alias:</em> <b>closePrice</b><br/><em>Value Type:</em> <b>Float</b></li>
			<li><em>Column Family:</em> <b>price</b><br/><em>Qualifier:</em> <b>open</b><br/><em>Alias:</em> <b>openPrice</b><br/><em>Value Type:</em> <b>Float</b></li>
			<li><em>Column Family:</em> <b>price</b><br/><em>Qualifier:</em> <b>low</b><br/><em>Alias:</em> <b>lowPrice</b><br/><em>Value Type:</em> <b>Float</b></li>
			<li><em>Column Family:</em> <b>price</b><br/><em>Qualifier:</em> <b>high</b><br/><em>Alias:</em> <b>highPrice</b><br/><em>Value Type:</em> <b>Float</b></li>
		</ol>
	</li>
	<li>After adding Row Key and Column Aliases save this mapping.</li>
	<li> Add Report by Clicking Report link  with <em>Report Name</em> as <b>BSEStock report<b>(or your second girlfriend's name) </li>
	<li> Select the mapping your have saved just now from the Mapping drop down</li>
	<li> Select your desired Report Type and Reap the fruit of this project. Enjoy have fun..!!!</li> 
	<li>All info above are taken from http://nubetech.co/crux-visualizing-and-querying-big-data-saved-in-hbase</li>
</ul>
