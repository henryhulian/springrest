$JAVA_HOME/bin/java  -server -d64 -Xms128m -Xmx2048m -XX:MaxPermSize=218M -Djava.net.preferIPv4Stack=true -Djgroups.tcp.address=172.28.10.66 -Djava.rmi.server.hostname=172.28.10.66 -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=2010 -Dcom.sun.management.jmxremote.local.only=false -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false  -jar restserver-0.0.1.jar