FROM maven:latest
COPY . /mancala-test
WORKDIR /mancala-test
RUN mvn clean compile
CMD mvn clean verify && awk -F"," '{ instructions += $4 + $5; covered += $5 } END { print covered, "/", instructions, " instructions covered Total"; print 100*covered/instructions, "% covered" }' target/site/jacoco/jacoco.csv
