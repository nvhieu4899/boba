FROM gradle:6.7.1-jdk11 AS TEMP_BUILD_IMAGE
RUN gradle --version && java -version

ENV DB_NAME=boba
ENV CONNECTIONSTRING=mongodb+srv://nvhieu:nguyenvanhieu@cluster0.oyhgv.gcp.mongodb.net/boba?retryWrites=true&w=majority

WORKDIR /app

# Only copy dependency-related files
COPY build.gradle settings.gradle /app/

# Only download dependencies
# Eat the expected build failure since no source code has been copied yet
RUN gradle clean build --no-daemon > /dev/null 2>&1 || true

# Copy all files
COPY ./ /app/

# Do the actual build
RUN gradle clean build --no-daemon

RUN cp ./build/libs/*.jar app.jar

ENTRYPOINT ["java","-jar" ,"app.jar"]
EXPOSE 8080
