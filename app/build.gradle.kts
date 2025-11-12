import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.shoaib.weatherapp"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.shoaib.weatherapp"
        android.buildFeatures.buildConfig = true
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        val localProps = project.rootProject.file("local.properties")
        val properties = Properties()
        properties.load(localProps.inputStream())
        val apiKey = properties.getProperty("API_KEY") ?: ""



        buildConfigField("String", "API_KEY", "\"$apiKey\"")

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
    kotlinOptions {
        jvmTarget = "11"
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
    implementation(libs.androidx.room.common.jvm)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //  Navigation (Compose)
    implementation("androidx.navigation:navigation-compose:2.8.3")

    //  Lifecycle & ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.6")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.8.6")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    // Dagger 2
    implementation("com.google.dagger:dagger:2.51")
    ksp("com.google.dagger:dagger-compiler:2.51")

    //  Room Database
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    ksp("androidx.room:room-compiler:2.6.1")

    // SQLCipher (for encryption)
    implementation("net.zetetic:android-database-sqlcipher:4.5.4")

    // Needed for Room + SQLCipher compatibility
    implementation("androidx.sqlite:sqlite:2.4.0")
    implementation("androidx.security:security-crypto:1.1.0-alpha06")


    //  Retrofit / OkHttp / Gson
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
    implementation("com.google.code.gson:gson:2.11.0")

    //  DataStore
    implementation("androidx.datastore:datastore-preferences:1.1.1")

    // Location Services
    implementation("com.google.android.gms:play-services-location:21.3.0")

    implementation("io.coil-kt:coil-compose:2.4.0") // or latest version

// Unit Testing
    testImplementation("junit:junit:4.13.2")

// Mockito (core + Kotlin support)
    testImplementation("org.mockito:mockito-core:5.12.0")
    testImplementation("org.mockito.kotlin:mockito-kotlin:5.2.1")

// Coroutines test utilities
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.8.1")

// Architecture components testing (for LiveData/Flow)
    testImplementation("androidx.arch.core:core-testing:2.2.0")

    implementation("androidx.navigation:navigation-compose:2.8.2")

    implementation("androidx.compose.material:material-icons-extended:1.6.7")

}
