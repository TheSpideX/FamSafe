plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.google.android.libraries.mapsplatform.secrets.gradle.plugin)
    id("kotlin-parcelize")
    alias(libs.plugins.google.gms.google.services)
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")

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
        viewBinding = true
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

    //for code generation
    implementation("com.aventrix.jnanoid:jnanoid:2.0.0")

    //hilt
    implementation("com.google.dagger:hilt-android:2.49")
    kapt("com.google.dagger:hilt-android-compiler:2.44")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

    //
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.2")

    //Pusher
    implementation ("com.pusher:pusher-java-client:2.4.4")
    implementation ("com.pusher:push-notifications-android:1.9.2")

    // Google Maps
    implementation ("com.google.android.gms:play-services-maps:18.2.0")
    implementation ("com.google.maps.android:maps-compose:5.0.3")
    implementation ("com.google.android.gms:play-services-location:21.3.0")
    implementation("com.google.maps.android:maps-compose-utils:5.0.3")
    implementation ("com.google.maps.android:maps-compose-widgets:5.0.3")
    implementation("com.google.gms:google-services:4.4.2")

    //permission
    implementation ("com.google.accompanist:accompanist-permissions:0.35.1-alpha")

    // Coroutines (for async operations)
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    // Firebase
    implementation (platform("com.google.firebase:firebase-bom:33.1.0"))
    implementation ("com.google.firebase:firebase-analytics-ktx")
    implementation ("com.google.firebase:firebase-auth-ktx")
    implementation ("com.google.firebase:firebase-database-ktx")
    implementation ("com.google.firebase:firebase-messaging-ktx")
    implementation ("com.google.firebase:firebase-storage-ktx")
    implementation ("com.google.firebase:firebase-messaging:24.0.0")
    implementation ("com.google.firebase:firebase-installations:18.0.0")
    implementation("com.google.firebase:firebase-iid:21.1.0")


    implementation ("com.google.code.gson:gson:2.11.0")

    //Animated Navigation Bar
    implementation("com.exyte:animated-navigation-bar:1.0.0")

    //Constraint Layout Compose
    implementation ("androidx.constraintlayout:constraintlayout-compose:1.0.1")

    //Navigation
    implementation("androidx.navigation:navigation-compose:2.7.7")

    //Material3
    implementation ("androidx.compose.material3:material3:1.2.1")

    //Card
    implementation("androidx.compose.material:material:1.6.8")

    //system ui
    implementation ("com.google.accompanist:accompanist-systemuicontroller:0.32.0")


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
    implementation(libs.firebase.auth)
    implementation(libs.androidx.runtime.livedata)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}