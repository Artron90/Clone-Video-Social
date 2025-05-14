plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.navigation.android)
    alias(libs.plugins.ksp.android)
}


val testApiKey: String by project
val baseUrl: String by project

android {
    namespace = "com.clone.android.testvideo"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.clone.android.testvideo"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String","TEST_API_KEY", testApiKey)
        buildConfigField("String","BASE_URL", baseUrl)
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

    flavorDimensions += "app"
    productFlavors {
        register("staging") {
            dimension = "app"
            applicationIdSuffix = ".staging"
            versionNameSuffix = "-beta"
            resValue("string", "app_name", "Clone Video Social Staging")
        }
        register("production") {
            dimension = "app"
            resValue("string", "app_name", "Clone Video Social")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
        compose = true
    }
}

dependencies {

    implementation (libs.androidx.core.ktx)
    implementation (libs.androidx.appcompat)
    implementation (libs.material)
    implementation (libs.androidx.constraintlayout)
    implementation (libs.androidx.lifecycle.livedata.ktx)
    implementation (libs.androidx.lifecycle.viewmodel.ktx)
    implementation (libs.androidx.lifecycle.runtime.ktx)
    implementation (libs.androidx.fragment.ktx)
    implementation (libs.kotlinx.coroutines.android)
    implementation (libs.androidx.recyclerview)
    implementation (libs.androidx.navigation.fragment.ktx)
    implementation (libs.androidx.navigation.ui.ktx)
    implementation (libs.androidx.navigation.dynamic.features.fragment)
    implementation (libs.kotlin.reflect)
    implementation (libs.logging.interceptor)
    implementation (libs.viewbinding)
    implementation (libs.feature.delivery.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.runtime.livedata)
    implementation(libs.androidx.material3)

    // RxJava
    implementation(libs.io.reactivex.rxjava3.rxjava)
    implementation(libs.rxandroid)


    // exoplayer
    implementation (libs.androidx.media3.exoplayer)
    implementation (libs.androidx.media3.exoplayer)
    implementation(libs.androidx.media3.exoplayer.hls)
    implementation (libs.androidx.media3.ui)
    implementation (libs.androidx.media3.exoplayer.dash)

    // rooms
    implementation (libs.androidx.room.ktx)
    implementation (libs.androidx.room.runtime)
    ksp (libs.androidx.room.compiler)

    // hilt
    implementation (libs.hilt.android)
    ksp (libs.hilt.compiler)
    debugImplementation(libs.androidx.ui.tooling)
    implementation(libs.androidx.hilt.navigation.compose)
    ksp(libs.androidx.hilt.compiler)

    // retrofit2
    implementation (libs.retrofit)
    implementation (libs.converter.gson)
    implementation (libs.adapter.rxjava3)

    // okhttp3
    implementation (libs.okhttp)
    implementation (libs.androidx.media3.datasource.okhttp)

    testImplementation (libs.junit)
    androidTestImplementation (libs.androidx.junit)
    androidTestImplementation (libs.androidx.espresso.core)
}