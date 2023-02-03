plugins {
    kotlin("multiplatform") version "1.8.10"
}

group = "fr.belkahia"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/kotlinx-html/maven") }
}

kotlin {
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
    }
}
