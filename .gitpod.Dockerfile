FROM gitpod/workspace-full

# Install Java 17 (you can also use 11 if preferred)
RUN sudo apt-get update \
 && sudo apt-get install -y openjdk-17-jdk maven \
 && java -version \
 && mvn -version