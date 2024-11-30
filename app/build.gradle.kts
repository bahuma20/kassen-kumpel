import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.dagger.hilt.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.room)
    alias(libs.plugins.kotlin.compose.compiler)
}

android {
    namespace = "io.bahuma.kassenkumpel"
    compileSdk = 35

    defaultConfig {
        applicationId = "io.bahuma.kassenkumpel"
        minSdk = 30
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        manifestPlaceholders["appAuthRedirectScheme"] =
            "io.bahuma.kassenkumpel" // Needed for SumUp SDK
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        compose = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

kotlin {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_17)
    }
}

room {
    schemaDirectory("$projectDir/schemas")
}

composeCompiler {
    includeSourceInformation = true
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.room.room.runtime)
    implementation(libs.androidx.room.room.ktx)
    implementation(libs.hilt.android)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.navigation.compose)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.compose.material.material.icons.extended)
    implementation(libs.sumup.merchant.sdk)
    implementation(libs.net.openid.appauth) // Needed for SumUp SDK
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.okhttp)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    ksp(libs.hilt.android.compiler)
    ksp(libs.androidx.room.compiler)
}