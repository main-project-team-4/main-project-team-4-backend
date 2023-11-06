FROM openjdk:17

WORKDIR /app

COPY build/libs/demo-0.0.1-SNAPSHOT.jar app.jar
COPY script/set_prod_env.sh set_prod_env.sh

EXPOSE 8080

CMD [\
    "/bin/bash", "-c",\
    "chmod +x set_prod_env.sh && source set_prod_env.sh && java -jar app.jar"\
]
