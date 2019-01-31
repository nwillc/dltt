import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val assertJVersion = "3.11.1"
val jupiterVersion = "5.4.0-RC1"
val slf4jApiVersion = "1.7.25"
val fakerVersion = "0.16"

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
    testImplementation("org.junit.jupiter:junit-jupiter-api:$jupiterVersion")
    testImplementation("org.assertj:assertj-core:$assertJVersion")
    testImplementation("com.github.javafaker:javafaker:$fakerVersion")
    testRuntime("org.junit.jupiter:junit-jupiter-engine:$jupiterVersion")
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
    named<Jar>("jar") {
        manifest.attributes["Automatic-Module-Name"] = "${project.group}.${project.name}"
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