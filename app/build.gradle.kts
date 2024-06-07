plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.google.android.libraries.mapsplatform.secrets.gradle.plugin)
    id("kotlin-parcelize")
}

android {
    namespace = "com.spidex.safe"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.spidex.safe"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        compose = true
        dataBinding = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation ("com.google.code.gson:gson:2.11.0")

    //Animated Navigation Bar
    implementation("com.exyte:animated-navigation-bar:1.0.0")

    //Constraint Layout Compose
    implementation ("androidx.constraintlayout:constraintlayout-compose:1.0.1")

    //Navigation
    implementation("androidx.navigation:navigation-compose:2.7.7")

    //Material3
    implementation ("androidx.compose.material3:material3:1.2.1")
    implementation("androidx.compose.material:material:1.0.0")

    //Card
    implementation("androidx.compose.material:material:1.6.7")

    //system ui
    implementation ("com.google.accompanist:accompanist-systemuicontroller:0.32.0")

    //for google map
    implementation("com.google.android.gms:play-services-maps:18.2.0")
    implementation("com.google.maps.android:maps-compose:5.0.2")

    // Location Services for fetching device location
    implementation("com.google.android.gms:play-services-location:21.3.0")

    //calculating the distance
    implementation("com.google.maps.android:maps-compose-utils:3.8.2")

    //for animation
    implementation ("com.airbnb.android:lottie-compose:6.4.1")


    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.play.services.maps)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.media3.common)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}