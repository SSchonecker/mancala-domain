FROM maven:latest
COPY . /mancala-test
WORKDIR /mancala-test
RUN mvn clean compile
CMD echo "I was here"
