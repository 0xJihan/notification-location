plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.devtools.ksp") version "2.1.0-1.0.29"
    kotlin("plugin.serialization") version "2.1.0"


}

android {
    namespace = "com.jihan.app"
    compileSdk = 35



    defaultConfig {
        applicationId = "com.jihan.app"
        minSdk = 24
        //noinspection OldTargetApi
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"


        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
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
    }
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
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)


    //* coil image
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)
    //* retrofit networking
    implementation(libs.retrofit)
    implementation(libs.converter.kotlinx.serialization)


    //*koin
    implementation(project.dependencies.platform("io.insert-koin:koin-bom:4.1.0-Beta1"))
    implementation(libs.koin.core)
    implementation(libs.koin.androidx.compose)
    //* google fonts
    implementation(libs.androidx.ui.text.google.fonts)
    implementation(libs.androidx.core.splashscreen)
    //* Navigation
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.kotlinx.serialization.json)

    //* debug
    debugImplementation(libs.leakcanary.android)

    //* Room
    implementation(libs.room.ktx)
    implementation(libs.androidx.room.runtime)

    ksp(libs.androidx.room.compiler)

//     implementation("androidx.datastore:datastore-preferences:1.1.1")
//     implementation("androidx.security:security-crypto:1.1.0-alpha06")

     implementation("io.github.being-eyram:iconsax-android:1.0.0")

//    // Fireabase
//    implementation(platform("com.google.firebase:firebase-bom:33.7.0"))
//    implementation("com.google.firebase:firebase-firestore")
//    implementation("com.google.firebase:firebase-messaging")

//
    implementation("com.google.maps.android:maps-compose:6.2.1")
    implementation("com.google.android.gms:play-services-location:21.3.0")

}



