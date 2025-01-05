import dev.icerock.gradle.MRVisibility
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrains.kotlin.serialization)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.kotlinCocoapods)
    alias(libs.plugins.devtools.ksp)
    id("dev.icerock.mobile.multiplatform-resources")
}

kotlin {
    androidTarget {
        compilations.all {
            compileTaskProvider.configure {
                compilerOptions {
                    jvmTarget.set(JvmTarget.JVM_17)
                }
            }
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            export(libs.moko.resources)
            export(libs.moko.graphics)// toUIColor here

            baseName = "shared"
            isStatic = true
        }
    }

    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        version = "1.0"
        ios.deploymentTarget = "16.0"
        podfile = project.file("../iosApp/Podfile")
        framework {
            baseName = "shared"
            isStatic = true
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                //compose
                implementation(libs.kmp.compose.runtime)
                implementation(libs.kmp.compose.foundation)
                implementation(libs.kmp.compose.material3)
                implementation(libs.kmp.compose.ui)
                implementation(libs.kmp.compose.navigation)

                // serialization
                implementation(libs.kotlinx.serialization.json)

                // uri
                implementation(libs.kmp.uri)

                //ktor
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.ktor.client.core)
                implementation(libs.ktor.client.content.negotiation)
                api(libs.ktor.serialization.kotlinx.json)

                //koin
                api(libs.koin.core)
                implementation(libs.koin.compose)
                implementation(libs.koin.composeVM)
                implementation(libs.androidx.lifecycle.viewmodel)

                //datastore
                implementation(libs.androidx.datastore.preferences.core)

                //datetime
                implementation(libs.kotlinx.datetime)

                //paging3
                implementation(libs.paging.common)
                implementation(libs.paging.compose.common)

                //coil
                implementation(libs.landscapist.coil3)

                //image Picker
                implementation(libs.peekaboo.ui)
                implementation(libs.peekaboo.image.picker)

                //moko resource
                api(libs.moko.resources)
                api(libs.moko.resources.compose) // for compose multiplatform

                // Material Icon Extended
                implementation(libs.kmp.compose.material.icons.extended)

                // Window Size
                implementation(libs.kmp.compose.material3.window.size)
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }

        val androidMain by getting {
            dependencies {
                api(libs.koin.androidx.compose)
                implementation(libs.ktor.client.android)

                api(libs.androidx.datastore.preferences)
            }
        }

        val androidUnitTest by getting
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)

            dependencies{
                implementation(libs.ktor.client.darwin)
            }
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }

//        commonMain.dependencies {
//            implementation(libs.kotlinx.coroutines.core)
//            implementation(libs.ktor.client.core)
//            implementation(libs.ktor.client.content.negotiation)
//            implementation(libs.ktor.serialization.kotlinx.json)
//
//            implementation(libs.koin.core)
//        }
//        commonTest.dependencies {
//            implementation(libs.kotlin.test)
//        }
    }
}

android {
    namespace = "com.skymilk.socialapp"
    compileSdk = 35
    defaultConfig {
        minSdk = 26
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

multiplatformResources {
    resourcesPackage.set("com.skymilk.socialapp") // required
    resourcesClassName.set("SharedRes") // optional, default MR
    resourcesVisibility.set(MRVisibility.Internal) // optional, default Public
    iosBaseLocalizationRegion.set("ko") // optional, default "en"
    iosMinimalDeploymentTarget.set("11.0") // optional, default "9.0"
}