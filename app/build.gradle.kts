plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.example.childmonitoringapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.childmonitoringapp"
        minSdk = 27
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    // Force Firebase dependencies to use a single version of firebase-common
    configurations.all {
        resolutionStrategy {
            force("com.google.firebase:firebase-common:21.0.1")
        }
    }
}

dependencies {
    // Firebase BOM for consistent dependency versions
    implementation(platform("com.google.firebase:firebase-bom:32.3.1")) // This is the recommended BOM version

    // Firebase dependencies (These will automatically pick up versions from the BOM)
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-firestore")

    // Other dependencies
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)

    // Test dependencies
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espressoCore)
}
