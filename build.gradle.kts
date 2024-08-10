plugins {
    kotlin("jvm") version "2.0.0"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "indi.stream"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.eclipse.jgit:org.eclipse.jgit:6.10.0.202406032230-r")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:2.0.0")
    implementation("org.eclipse.jgit:org.eclipse.jgit.ssh.apache:6.10.0.202406032230-r")
    implementation("org.yaml:snakeyaml:2.2")
    testImplementation(kotlin("test"))
}

val java
    get() = components.getByName("java") as AdhocComponentWithVariants

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "indi.stream.repositorychecker.MainKt"
    }
}