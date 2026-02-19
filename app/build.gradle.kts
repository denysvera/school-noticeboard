plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.nativkod.schoolnoticeboard"
    compileSdk {
        version = release(36) {
            minorApiLevel = 1
        }
    }

    defaultConfig {
        applicationId = "com.nativkod.schoolnoticeboard"
        minSdk = 28
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

       // testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        testInstrumentationRunner = "com.nativkod.schoolnoticeboard.HiltTestRunner"
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
    kotlin {
        jvmToolchain(17)
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    // Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui.tooling.preview)
    debugImplementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.navigation.compose)

    // Lifecycle
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)

    // Coroutines
    implementation(libs.kotlinx.coroutines.android)

    // Room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.paging)
    ksp(libs.androidx.room.compiler)

    // Paging
    implementation(libs.androidx.paging.runtime)
    implementation(libs.androidx.paging.compose)

    // Network
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.moshi)
    implementation(libs.okhttp.core)
    implementation(libs.okhttp.logging)
    implementation(libs.moshi.kotlin)

    // Images
    implementation(libs.coil.compose)

    // Security
    implementation(libs.androidx.security.crypto)

    // Hilt
     implementation(libs.hilt.android)
     ksp(libs.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

    // Unit tests
    testImplementation(libs.junit4)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.runner)
    androidTestImplementation(libs.androidx.room.testing)
    androidTestImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.turbine)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    androidTestImplementation(libs.hilt.android.testing)
    kspAndroidTest(libs.hilt.compiler)

}
