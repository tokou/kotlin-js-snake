buildscript {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.10")
        classpath("com.android.tools.build:gradle:7.4.0-beta02")
    }
}

plugins {
    kotlin("multiplatform") version "1.8.10"
    id("com.android.application") version "7.4.0-beta02"
}

group = "fr.belkahia"
version = "1.0-SNAPSHOT"

kotlin {
    android()

    js {
        binaries.executable()
        browser()
    }

    val ncursesMacosVersion = "6.4"
    val macosHomebrewCellar = "/opt/homebrew/Cellar"
    val ncursesMacosPath = "$macosHomebrewCellar/ncurses/$ncursesMacosVersion"
    val macosNcursesIncludes = arrayOf(
        "$ncursesMacosPath/include/",
        "$ncursesMacosPath/include/ncursesw"
    )
    macosX64 {
        compilations.getByName("main") { cinterops { val ncurses by creating { includeDirs(*macosNcursesIncludes) } } }
        binaries.executable { entryPoint("main") }
    }
    macosArm64 {
        compilations.getByName("main") { cinterops { val ncurses by creating { includeDirs(*macosNcursesIncludes) } } }
        binaries.executable { entryPoint("main") }
    }
    linuxX64 {
        compilations.getByName("main") { cinterops { val ncurses by creating } }
        binaries.executable { entryPoint("main") }
    }

    sourceSets {
        val commonMain by getting {}

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }

        val jsMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-html-js:0.8.0")
            }
        }

        val nativeMain by creating { dependsOn(commonMain) }
        val linuxX64Main by getting { dependsOn(nativeMain) }
        val macosX64Main by getting { dependsOn(nativeMain) }
        val macosArm64Main by getting { dependsOn(nativeMain) }

        val nativeTest by creating { dependsOn(commonTest) }
        val linuxX64Test by getting { dependsOn(nativeTest) }
        val macosX64Test by getting { dependsOn(nativeTest) }
        val macosArm64Test by getting { dependsOn(nativeTest) }

        val androidMain by getting {
            dependencies {
                implementation(platform("androidx.compose:compose-bom:2023.01.00"))
                implementation("androidx.compose.material3:material3")
                implementation("com.google.android.material:material:1.8.0")
                implementation("androidx.compose.ui:ui-tooling-preview")
                implementation("androidx.activity:activity-compose:1.6.1")
            }
        }

        val androidUnitTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation("junit:junit:4.13.2")
                implementation("androidx.test:core:1.5.0")
                implementation("androidx.test:core-ktx:1.5.0")
            }
        }

        val androidInstrumentedTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation("androidx.test:runner:1.5.2")
                implementation("androidx.test:rules:1.5.0")
                implementation("androidx.test.espresso:espresso-core:3.5.1")
                implementation("androidx.test.uiautomator:uiautomator:2.2.0")
                implementation("androidx.compose.ui:ui-test-junit4:1.4.0-alpha05")
            }
        }
    }
}

android {
    compileSdk = 33
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    compileSdk = 33
    namespace =  "fr.belkahia.snake.android"
    defaultConfig {
        applicationId = "fr.belkahia.snake.android"
        minSdk = 21
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.1-dev-k1.8.10-c312d77f4cb"
    }
}
