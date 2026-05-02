
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.ksp)


}

android {
    namespace = "com.am.moviesfix"
    compileSdk {
        version = release(36) {
            minorApiLevel = 1
        }
    }

    defaultConfig {
        applicationId = "com.am.moviesfix"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        val tmdbKey: String = project.findProperty("TMDB_API_KEY")?.toString() ?: ""

        buildConfigField("String", "TMDB_API_KEY", "\"$tmdbKey\"")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

    }
    buildFeatures {
        buildConfig = true
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
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.glide)
    implementation(libs.retrofit)
    implementation(libs.gsonConverter)
    implementation(libs.okhttpLogging)
    implementation(libs.lifecycle.viewmodel)
    implementation(libs.lifecycle.livedata)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    implementation(libs.splash.screen)
    ksp(libs.room.compiler)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}