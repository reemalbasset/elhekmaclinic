# Use an official OpenJDK runtime as a parent image
FROM adoptopenjdk:11-jre-hotspot

# VOLUME points to /tmp and since we dockerize spring boot application, we use /tmp as a temporary file store
WORKDIR /app



# Copy the jar file from the host to the container
COPY target/demo-0.0.1-SNAPSHOT.jar /app/ 

# Expose port 8082


# Run the jar file
CMD  ["java", "-jar", "demo-0.0.1-SNAPSHOT.jar"]

# For Spring-Boot project, use the entrypoint below to reduce Tomcat startup time.
#ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar clinicmanagementsystem.jar
