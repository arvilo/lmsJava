plugins {
    java
    application
}

repositories {
    mavenCentral()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

sourceSets {
    main {
        java.srcDirs("src/main/java")
    }
}

application {
    mainClass.set("az.arvilo.lmsJava.Main")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}
