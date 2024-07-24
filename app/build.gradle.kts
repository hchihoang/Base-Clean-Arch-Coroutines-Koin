plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.devtoolsKsp)
}

android {
    namespace = "com.inetkr.base"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.inetkr.base"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        create("dev")  {
            storeFile = file("../keystore/debug.keystore")
            storePassword = "android"
            keyAlias = "androiddebugkey"
            keyPassword = "android"
        }
        create("release")  {
            storeFile = file("../keystore/intak_key.jks")
            storePassword = "Intak@2024"
            keyAlias = "key_intak"
            keyPassword = "Intak@2024"
        }
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "BASE_URL", "\"https://intak.fansome.biz/\"")
        }
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            isCrunchPngs = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "BASE_URL", "\"https://jsonplaceholder.typicode.com\"")
        }
    }

    flavorDimensions.add("flavors")
    productFlavors {
        create("dev") {
            applicationId = "com.inetkr.base.dev"
            namespace = "com.inetkr.base.dev"
            resValue ("string", "app_name", "Base Project(dev)")
            resValue ("string", "file_provider", "com.inetkr.base.dev.fileProvider")
        }
        create("product") {
            applicationId = "com.inetkr.base"
            namespace = "com.inetkr.base"
            resValue ("string", "app_name", "Base Project")
            resValue ("string", "file_provider", "com.inetkr.base.fileProvider")
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
        viewBinding = true
        buildConfig = true
    }
}

dependencies {
    implementation(libs.lifecycle.extensions)
    implementation(libs.wheelpicker)
    implementation(libs.glide)
    implementation(libs.androidx.legacy.support.v4)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.fragment.ktx)
    androidTestImplementation(libs.runner)
    testImplementation(libs.mockk)
    implementation(libs.retrofit2.kotlin.coroutines.adapter)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.coroutines.core)
    testImplementation(libs.koin.test)
    implementation(libs.koin.android)
    implementation(libs.koin.core)
    implementation(libs.logging.interceptor)
    implementation(libs.converter.gson)
    implementation(libs.retrofit)
    implementation(libs.multidex)
    implementation(libs.recyclerview)
    implementation(libs.cardview)
    implementation(libs.lifecycle)
    implementation(libs.stdlib)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.swiperefreshlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}