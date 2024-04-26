plugins {
    id("java")
}

group = "me.flezy"
version = "1.0-SNAPSHOT"

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

repositories {
    mavenCentral()
}

dependencies {
    val lombok = "org.projectlombok:lombok:1.18.32"

    compileOnly(lombok)
    annotationProcessor(lombok)

    compileOnly(files("libraries/panda.jar"))
}

tasks.register<Copy>("copyJar") {
    dependsOn("jar")

    from("build/libs/sumo-evento-1.0-SNAPSHOT.jar")
    into("server/plugins/")
}