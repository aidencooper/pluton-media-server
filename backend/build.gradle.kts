plugins {
	java
	id("org.springframework.boot") version "4.0.5"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "net.aidencooper.pluton"
version = "0.0.1-SNAPSHOT"
description = "Demo project for Spring Boot"

dependencyManagement {
	imports {
		mavenBom("org.springframework.modulith:spring-modulith-bom:2.0.5")
	}
}

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(25)
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
    annotationProcessor("org.projectlombok:lombok")

    compileOnly("org.projectlombok:lombok")

    developmentOnly("org.springframework.boot:spring-boot-devtools")

    implementation("com.github.kokorin.jaffree:jaffree:2024.08.29")
	implementation("org.hibernate.orm:hibernate-community-dialects")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:3.0.2")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-webmvc")

	runtimeOnly("org.xerial:sqlite-jdbc")

	testImplementation("org.springframework.boot:spring-boot-starter-data-jpa-test")
	testImplementation("org.springframework.boot:spring-boot-starter-validation-test")
	testImplementation("org.springframework.boot:spring-boot-starter-webmvc-test")

	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
