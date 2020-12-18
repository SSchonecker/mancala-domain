# This file is a template, and might need editing before it works on your project.
FROM maven:3.5-jdk-11 as BUILD
WORKDIR /usr/mancala-java
COPY . /usr/mancala-java
RUN mvn clean verify
CMD awk -F"," '{ instructions += $4 + $5; covered += $5 } END { print covered, "/", instructions, " instructions covered Total"; print 100*covered/instructions, "% covered" }' target/jacoco/jacoco.csv
