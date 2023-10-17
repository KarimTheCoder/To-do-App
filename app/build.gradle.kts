plugins {
    id("com.android.application")

}

android {
    namespace = "com.example.to_doapp"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.to_doapp"
        minSdk = 24
        targetSdk = 33
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true

    }

    viewBinding{
        enable = true
    }
}

dependencies {

// AndroidX Libraries
    implementation("androidx.appcompat:appcompat:1.6.1") // Use a consistent version
    implementation("com.google.android.material:material:1.9.0") // Use a consistent version
    implementation("androidx.constraintlayout:constraintlayout:2.1.4") // Use a consistent version

// Lifecycle
    implementation("androidx.lifecycle:lifecycle-viewmodel:2.3.1")
    implementation("androidx.lifecycle:lifecycle-livedata:2.3.1")
    implementation("androidx.preference:preference:1.2.0")


// Paging Library
    val pagingVersion = "2.1.2"
    implementation("androidx.paging:paging-runtime:$pagingVersion")
    testImplementation("androidx.paging:paging-common:$pagingVersion")

// Room Library
    val roomVersion = "2.5.2" // Use a consistent version
    implementation("androidx.room:room-runtime:$roomVersion")
    annotationProcessor("androidx.room:room-compiler:$roomVersion")

//// Navigation UI KTX (ensure the versions match your navigation version)
    implementation ("androidx.navigation:navigation-fragment-ktx:2.3.5")
    implementation ("androidx.navigation:navigation-ui-ktx:2.3.5")


    // Unit testing
    // JUnit
    testImplementation ("junit:junit:4.13.2")

    // AndroidX Test
    androidTestImplementation ("androidx.test:runner:1.4.0")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.4.0")

    // AndroidX Test Ext
    androidTestImplementation ("androidx.test.ext:junit:1.1.3")

    // Room Testing
    androidTestImplementation ("androidx.room:room-testing:2.5.2")


}