# ---- Build ----
FROM maven:3.9-eclipse-temurin-21-alpine AS build
WORKDIR /workspace

# copy poms first (cache)
COPY pom.xml .
COPY todo/pom.xml todo/

# go offline
RUN --mount=type=cache,target=/root/.m2 mvn -B -q -DskipTests dependency:go-offline

# copy sources
COPY . .

# build only the todo module (and deps), then show the target
RUN --mount=type=cache,target=/root/.m2 mvn -B -q -DskipTests -pl todo -am clean package && \
    echo "Built artifacts:" && ls -l todo/target

# ---- Runtime ----
FROM eclipse-temurin:21-jre-alpine
RUN addgroup -S app && adduser -S app -G app
USER app
WORKDIR /app

# runtime stage
COPY --from=build /workspace/todo/target/todo-*.jar /app/app.jar
# ensure we didn't accidentally copy the original
RUN [ -f /app/app.jar ] || (echo "No JAR copied" && exit 1)

EXPOSE 8080
ENTRYPOINT ["sh","-lc","exec \"$JAVA_HOME/bin/java\" $JAVA_OPTS -jar /app/app.jar"]
