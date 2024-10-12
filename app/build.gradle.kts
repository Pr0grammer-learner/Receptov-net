plugins {
    alias(libs.plugins.android.application)
    id("com.google.gms.google-services") // This is correctly placed
}

android {
    namespace = "com.example.receptov_net"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.receptov_net"
        minSdk = 26
        targetSdk = 34
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
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.firebase.auth)
    implementation("de.hdodenhof:circleimageview:3.1.0")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.picasso:picasso:2.71828")
    implementation(project(":data"))
    implementation(project(":domain"))
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // Firebase BOM to ensure compatibility between Firebase libraries
    implementation(platform("com.google.firebase:firebase-bom:33.4.0"))

    // Firebase Analytics and other Firebase libraries
    implementation("com.google.firebase:firebase-analytics")
}

