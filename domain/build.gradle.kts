import org.gradle.kotlin.dsl.support.kotlinCompilerOptions

plugins {
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
    alias(libs.plugins.kotlinx.serialization)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies{
    //Coroutines
    implementation(libs.kotlinx.coroutines.core)

    implementation(libs.kotlinx.serialization.json)
}