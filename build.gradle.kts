plugins {
    id("java")
}

group = "ru.tinkoff"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("info.picocli:picocli:4.7.5")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks {
    jar {
        manifest {
            attributes["Main-Class"] = "ru.tinkoff.Main"
        }
    }

    test {
        useJUnitPlatform()
    }

    compileJava {
        options.encoding = "UTF-8"
    }

    compileTestJava {
        options.encoding = "UTF-8"
    }

    register<Jar>("uberJar") {
        archiveClassifier = "uber"

        manifest {
            attributes["Main-Class"] = "ru.tinkoff.Main"
        }

        from(sourceSets.main.get().output)

        dependsOn(configurations.runtimeClasspath)
        from({
            configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) }
        })
    }
}