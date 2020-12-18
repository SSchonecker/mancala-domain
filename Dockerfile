# This file is a template, and might need editing before it works on your project.
FROM maven:latest
COPY . /mancala-test
WORKDIR /mancala-test
RUN mvn install
#CMD mvn clean verify
CMD echo "I was here"
#CMD awk -F"," '{ instructions += $4 + $5; covered += $5 } END { print covered, "/", instructions, " instructions covered Total"; print 100*covered/instructions, "% covered" }' target/jacoco/jacoco.csv
