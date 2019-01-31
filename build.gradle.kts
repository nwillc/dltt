import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val assertJVersion = "3.11.1"
val cliktVersion = "1.6.0"
val fakerVersion = "0.16"
val jupiterVersion = "5.4.0-RC1"
val ktorVersion = "1.0.1"
val slf4jApiVersion = "1.7.25"
val tinyLogVersion = "1.3.5"

plugins {
    kotlin("jvm") version "1.3.20"
    id("com.github.nwillc.vplugin") version "2.3.0"
    id("org.jlleitschuh.gradle.ktlint") version "7.0.0"
}

group = "com.github.nwillc"
version = "1.0-SNAPSHOT"

repositories {
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.slf4j:slf4j-api:$slf4jApiVersion")
    implementation("com.github.ajalt:clikt:$cliktVersion")
    implementation("io.ktor:ktor-server-core:$ktorVersion")
    implementation("io.ktor:ktor-server-netty:$ktorVersion")

    testImplementation("org.junit.jupiter:junit-jupiter-api:$jupiterVersion")
    testImplementation("org.assertj:assertj-core:$assertJVersion")
    testImplementation("com.github.javafaker:javafaker:$fakerVersion")

    testRuntime("org.junit.jupiter:junit-jupiter-engine:$jupiterVersion")
    runtime("org.tinylog:slf4j-binding:$tinyLogVersion")
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
    named<Jar>("jar") {
        manifest.attributes["Automatic-Module-Name"] = "${project.group}.${project.name}"
        manifest.attributes["Main-Class"] = "${project.group}.${project.name}.ServerKt"
        from(Callable { configurations["runtimeClasspath"].map { if (it.isDirectory) it else zipTree(it) } })
    }
    withType<Test> {
        useJUnitPlatform()
        testLogging.showStandardStreams = true
        beforeTest(KotlinClosure1<TestDescriptor, Unit>({ logger.lifecycle("    Running ${this.className}.${this.name}") }))
        afterSuite(KotlinClosure2<TestDescriptor, TestResult, Unit>({ descriptor, result ->
            if (descriptor.parent == null) {
                logger.lifecycle("Tests run: ${result.testCount}, Failures: ${result.failedTestCount}, Skipped: ${result.skippedTestCount}")
            }
            Unit
        }))
    }
}