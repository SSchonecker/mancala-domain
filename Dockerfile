FROM maven:latest
COPY . /mancala-test
WORKDIR /mancala-test
RUN mvn clean compile
CMD cd src/main/java/nl/sogyo/mancala/domain && javac PitBoard.java && java PitBoard.java
