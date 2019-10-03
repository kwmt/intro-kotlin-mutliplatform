plugins {
  kotlin("multiplatform") version "1.3.40"
}

kotlin {
  jvm()
  js {
    browser()
  }
}

repositories {
  jcenter()
  mavenCentral()
}

val ktorVersion = "1.2.2"
val logbackVersion = "1.2.3"

kotlin.sourceSets["jvmMain"].dependencies {
  implementation(kotlin("stdlib-jdk8"))
  implementation("io.ktor:ktor-server-netty:$ktorVersion")
  implementation("io.ktor:ktor-html-builder:$ktorVersion")
  implementation("ch.qos.logback:logback-classic:$logbackVersion")
}

kotlin.sourceSets["jsMain"].dependencies {
  implementation(kotlin("stdlib-js"))
  implementation("org.jetbrains.kotlinx:kotlinx-html-js:0.6.12")
}

kotlin.sourceSets["commonMain"].dependencies {
  implementation(kotlin("stdlib-common"))
  implementation("org.jetbrains.kotlinx:kotlinx-html-common:0.6.12")
}

val run by tasks.creating(JavaExec::class) {
  group = "application"
  main = "com.jetbrains.handson.introMpp.MainKt"
  kotlin {
    val main = targets["jvm"].compilations["main"]
    dependsOn(main.compileAllTaskName)
    classpath(
            { main.output.allOutputs.files },
            { configurations["jvmRuntimeClasspath"] }
    )
  }
  ///disable app icon on macOS
  systemProperty("java.awt.headless", "true")
}
