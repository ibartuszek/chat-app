import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.4.5"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.5.0"
	kotlin("plugin.spring") version "1.5.0"
	kotlin("plugin.serialization") version "1.5.0"
	id("io.gitlab.arturbosch.detekt") version "1.16.0"
}

group = "com.example.chatapp"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
	// detekt
	jcenter()
}

dependencies {
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.2.0")
	implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-websocket")
	testImplementation("de.flapdoodle.embed:de.flapdoodle.embed.mongo:2.2.0")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

detekt {
	val mainFolder = "build-config"
	allRules = true
	config = files(projectDir.resolve(mainFolder).resolve("detekt.yml"))
	baseline = file(projectDir.resolve(mainFolder).resolve("baseline.xml"))

	reports {
		html.enabled = true
		xml.enabled = false
		txt.enabled = false
		sarif.enabled = false
	}
}
