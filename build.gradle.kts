plugins {
    kotlin("js") version "1.8.0"
}

group = "fr.belkahia"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/kotlinx-html/maven") }
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-html-js:0.8.0")
    testImplementation(kotlin("test"))
}

kotlin {
    js {
        binaries.executable()
        browser()
    }
}
