plugins {
    id("java")
}

group = "me.flezy"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    val lombok = "org.projectlombok:lombok:1.18.32"

    compileOnly(lombok)
    annotationProcessor(lombok)

    compileOnly(files("libraries/panda.jar"))
}