plugins {
    kotlin("jvm") version "1.8.20"
    kotlin("plugin.serialization") version "1.8.20"
    `maven-publish`
}

group = "net.cherrycave"
version = "0.3.2"

repositories {
    mavenCentral()
}

dependencies {
    val ktorVersion = "2.3.0"

    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-client-logging:$ktorVersion")
    implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
    implementation("io.ktor:ktor-client-cio:$ktorVersion")

    implementation("com.aventrix.jnanoid:jnanoid:2.0.0")

    implementation("io.github.oshai:kotlin-logging-jvm:4.0.0-beta-22")
    implementation("ch.qos.logback:logback-classic:1.4.7")

    testImplementation(kotlin("test"))
}

tasks {
    test {
        useJUnitPlatform()
    }
}

java {
    withSourcesJar()
    withJavadocJar()
}

publishing {

    repositories {
        maven {
            setUrl("https://maven.stckoverflw.net/private")

            credentials {
                username = System.getenv("MAVEN_USERNAME")
                password = System.getenv("MAVEN_SECRET")
            }
        }
    }
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])

            pom {
                name.set(project.name)
                url.set("https://github.com/CherryCave/birgid")

                developers {
                    developer {
                        name.set("Emma Böcker")
                        email.set("stckoverflw@gmail.com")
                        organizationUrl.set("https://www.stckoverflw.net")
                    }
                }

                scm {
                    connection.set("scm:git:https://github.com/CheeryCave/birgid.git")
                    developerConnection.set("scm:git:https://github.com/CheeryCave/birgid.git")
                    url.set("https://github.com/CheeryCave/birgid.git")
                }
            }
        }
    }
}

kotlin {
    explicitApi()

    jvmToolchain(17)
}


