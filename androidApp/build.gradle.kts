plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.devtools.ksp)

    kotlin("plugin.serialization") version "2.0.21"
}

android {
    namespace = "com.skymilk.socialapp.android"
    compileSdk = 34
    defaultConfig {
        applicationId = "com.skymilk.socialapp.android"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(projects.shared)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.material3)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.ui.android)
    debugImplementation(libs.compose.ui.tooling)

    implementation (libs.compose.destinations.core)
    ksp (libs.compose.destinations.ksp)

    // Material Icon Extended
    implementation(libs.androidx.material.icons.extended)

    //size
    implementation(libs.androidx.material3.window.size)

    //serialization
    implementation(libs.ktor.serialization.kotlinx.json)

    //coil
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)

    //paging3
    implementation(libs.androidx.paging.runtime)
    implementation(libs.androidx.paging.compose)

    implementation(libs.splash.screen)
    implementation(libs.koin.androidx.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.accompanist.systemuicontroller)
}