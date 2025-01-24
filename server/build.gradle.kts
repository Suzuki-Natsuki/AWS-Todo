plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    id("org.springframework.boot") version "3.4.1"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "com.dig"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // kayleeが追加してくれたやつ
    implementation(platform("software.amazon.awssdk:bom:2.30.5"))
    implementation("software.amazon.awssdk:dynamodb-enhanced")


    // 公式ドキュメントの推奨どおりに、ビルドやテスト実行時にMockitoエージェントを先に読み込ませる
    testImplementation("org.mockito:mockito-inline:5.2.0")

    // mockito-inline ではなく 通常の mockito-core を利用する
    testImplementation("org.mockito:mockito-core:5.2.0") // inlineではない
	// ---- (JUnit5などのテストライブラリ) ----
	testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
