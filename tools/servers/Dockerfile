# This is an exmaple dockerfile for a 1D6 server
# You will need to make the image yourself, sorry

# Set up Image
FROM ubuntu:22.04
RUN apt-get update && apt-get install -y openjdk-8-jdk
FROM ubuntu/jre:8-22.04_edge
WORKDIR /

# Set up game
ADD modloader.jar .
ADD server.jar .

# Its go time.
CMD ["java", "-jar", "modloader.jar", "-cr", "-t=server.jar"]
