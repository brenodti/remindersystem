plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("androidx.navigation.safeargs.kotlin")
    id("kotlin-kapt")
}

android {
    namespace = "com.example.remindersystem"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.remindersystem"
        minSdk = 27
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "CALENDARIFIC_API_KEY", "\"${property("CALENDARIFIC_API_KEY")}\"")
        buildConfigField("String", "GOOGLE_API_KEY", "\"${property("GOOGLE_API_KEY")}\"")
        buildConfigField("String", "GOOGLE_SEARCH_ENGINE_ID", "\"${property("GOOGLE_SEARCH_ENGINE_ID")}\"")
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
    buildFeatures{
        buildConfig = true
        viewBinding = true
        dataBinding = true
    }
    dynamicFeatures += setOf(":compose")
}

dependencies {
    val nav_version = "2.6.0"
    val material_version = "1.9.0"
    val room_version = "2.5.2"
    val koin_android_version = "3.4.3"
    val retrofit_version = "2.9.0"

    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")

    //LiveData
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.1")

    //ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")

    //Test
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    //Timber
    implementation("com.jakewharton.timber:timber:5.0.1")

    //Navigation
    //noinspection GradleDependency
    implementation("androidx.navigation:navigation-fragment-ktx:$nav_version")
    //noinspection GradleDependency
    implementation("androidx.navigation:navigation-ui-ktx:$nav_version")

    //Material
    implementation("com.google.android.material:material:$material_version")

    //Room
    implementation("androidx.room:room-runtime:$room_version")
    implementation("androidx.room:room-ktx:$room_version")
    ksp("androidx.room:room-compiler:$room_version")

    //Koin
    implementation("io.insert-koin:koin-android:$koin_android_version")

    //Retrofit
    implementation("com.squareup.retrofit2:retrofit:$retrofit_version")
    implementation("com.squareup.retrofit2:converter-gson:$retrofit_version")

}