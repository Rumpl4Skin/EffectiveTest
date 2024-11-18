plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
  //  id("kotlin-kapt")
    id("androidx.navigation.safeargs.kotlin")

    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.example.effectivetest"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.effectivetest"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }

}

dependencies {

    implementation(project(":domain"))
    implementation(project(":data"))

    // Views/Fragments integration
    implementation(libs.androidx.navigation.fragment)
    implementation(libs.androidx.navigation.ui)
    //Coroutines
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    //DI
    implementation("com.google.dagger:hilt-android:2.52")
    //kapt("com.google.dagger:hilt-compiler:2.52")
    implementation("com.google.dagger:dagger-compiler:2.52")
    ksp("com.google.dagger:dagger-compiler:2.52")
    //kapt ("com.google.dagger:hilt-android-compiler:2.28-alpha")

    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    //RecyclerView
    implementation("com.hannesdorfmann:adapterdelegates4:4.3.2")
    implementation("androidx.recyclerview:recyclerview:1.3.2")

    //Glide
    implementation("com.github.bumptech.glide:glide:4.16.0")
    //kapt("com.github.bumptech.glide:compiler:4.16.0")
    ksp ("com.github.bumptech.glide:ksp:4.16.0")
    implementation( "com.caverock:androidsvg:1.2.1")

    //pagination
    implementation("androidx.paging:paging-runtime:3.3.4")

    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
