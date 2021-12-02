import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED
import org.gradle.api.tasks.testing.logging.TestLogEvent.PASSED
import org.gradle.api.tasks.testing.logging.TestLogEvent.SKIPPED

plugins {
    jacoco
    id("com.github.ben-manes.versions")
    id("com.dorongold.task-tree")
    kotlin("jvm")
}

val junitVersion: String by project
val kotestVersion: String by project
val kotlinVersion: String by project
val jacksonVersion: String by project

dependencies {
    implementation(kotlin("reflect"))

    implementation("com.fasterxml.jackson.core:jackson-databind:$jacksonVersion")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")

    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlinVersion")

    testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-json:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-sql:$kotestVersion")

    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation("io.kotest:kotest-property:$kotestVersion")

    testImplementation(platform("org.junit:junit-bom:$junitVersion"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("org.junit.jupiter:junit-jupiter-params")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    testRuntimeOnly("org.junit.platform:junit-platform-console")
}

java { toolchain { languageVersion.set(JavaLanguageVersion.of(11)) } }

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "11"
        kotlinOptions {
            jvmTarget = "11"
            javaParameters = true
            freeCompilerArgs = listOf("-Xjvm-default=all")
        }
    }

    compileTestKotlin {
        kotlinOptions.jvmTarget = "11"
    }

    test {
        useJUnitPlatform()
        testLogging {
            events(PASSED, SKIPPED, FAILED)
        }
        finalizedBy(jacocoTestReport) // report is always generated after tests run
    }

    jacocoTestReport {
        dependsOn(test) // tests are required to run before generating the report
    }

    jacocoTestCoverageVerification {
        violationRules {
            rule {
                enabled = true

                limit {
                    counter = "LINE"
                    value = "TOTALCOUNT"
                    minimum = "1.0".toBigDecimal()
                }
            }
        }
    }
}


tasks.withType<DependencyUpdatesTask> {
    rejectVersionIf {
        this.candidate.version.contains("alpha", ignoreCase = true) ||
                this.candidate.version.contains("beta", ignoreCase = true) ||
                this.candidate.version.contains("rc", ignoreCase = true) ||
                this.candidate.version.contains("m", ignoreCase = true)
    }

    // optional parameters
    checkForGradleUpdate = true
    outputFormatter = "json"
    outputDir = "build/dependencyUpdates"
    reportfileName = "report"
}
