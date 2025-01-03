plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.compose.compiler)

    kotlin("plugin.serialization") version "2.0.21"
}

android {
    namespace = "com.skymilk.socialapp.android"
    compileSdk = 35
    defaultConfig {
        applicationId = "com.skymilk.socialapp.android"
        minSdk = 26
        targetSdk = 35
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

    //serialization
    implementation(libs.ktor.serialization.kotlinx.json)

    implementation(libs.splash.screen)
    implementation(libs.koin.androidx.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.accompanist.systemuicontroller)
}