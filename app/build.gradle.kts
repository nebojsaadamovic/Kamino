plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.masterandroid.kamino"
    compileSdk  = 34

    defaultConfig {
        applicationId = "com.masterandroid.kamino"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        multiDexEnabled=true
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
    implementation(libs.appcompat.v161)
    implementation(libs.material.v1110)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.junit.v115)
    androidTestImplementation(libs.espresso.core.v351)
    implementation (libs.retrofit)
    implementation (libs.converter.gson)
    implementation (libs.logging.interceptor)
    implementation (libs.core)
    implementation (libs.zxing.android.embedded)
    compileOnly(libs.lombok)
    implementation (libs.glide)
    annotationProcessor (libs.compiler)
    implementation(libs.drawerlayout)
    implementation(libs.material.v190)
    implementation(libs.appcompat.v160)
    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1")
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.5.1")
    implementation ("androidx.core:core:1.10.1")
    implementation ("androidx.multidex:multidex:2.0.1")




}