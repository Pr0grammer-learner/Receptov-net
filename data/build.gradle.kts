plugins {
    alias(libs.plugins.android.library)
    // Remove the google-services plugin here
    // alias(libs.plugins.google.gms.google.services) -> Remove this line
}

android {
    namespace = "com.example.data"
    compileSdk = 34

    defaultConfig {
        minSdk = 26
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.firebase.auth)
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("androidx.room:room-runtime:2.5.0")
    annotationProcessor("androidx.room:room-compiler:2.5.0")
    implementation(project(":domain"))
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
}
