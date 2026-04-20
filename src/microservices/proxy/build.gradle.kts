import org.gradle.api.JavaVersion.VERSION_17
import org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17

plugins {
    kotlin("jvm") version "2.1.0"
    kotlin("plugin.spring") version "2.1.0"
    id("org.springframework.boot") version "3.1.5"
    id("io.spring.dependency-management") version "1.1.3"
}

group = "com.github.lobakov.architecture.cinemaabyss"
version = "1.0.0"

java {
    sourceCompatibility = VERSION_17
}

repositories {
    mavenCentral()
}

extra["springCloudVersion"] = "2023.0.1"

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    implementation("org.springframework.cloud:spring-cloud-starter-gateway")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}

tasks.compileKotlin {
    compilerOptions {
        freeCompilerArgs.set(listOf("-Xjsr305=strict"))
        jvmTarget.set(JVM_17)
    }
}

tasks.compileTestKotlin {
    enabled = false
}

tasks.compileJava {
    enabled = false
}

tasks.compileTestJava {
    enabled = false
}

tasks.test {
    useJUnitPlatform()
}

tasks.jar {
    enabled = false
}

tasks.bootJar {
    enabled = true
}
