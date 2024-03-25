import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import java.io.FileInputStream
import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    //ksp
    id("com.google.devtools.ksp")
    //hilt
    id("com.google.dagger.hilt.android")
    id("kotlin-parcelize")
    // Menifest에 local-properties 변수를 사용 할 수 있는 플러그인
    id ("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

// properties 파일 로드
val properties = Properties().apply {
    load(FileInputStream(rootProject.file("local.properties")))
}

android {
    namespace = "com.ssafy.sungchef"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.ssafy.sungchef"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        buildConfigField("String", "BASE_URL",getApiKey("BASE_URL"))
        buildConfigField("String", "NATIVE_APP_KEY",getApiKey("NATIVE_APP_KEY"))
    }

    buildTypes {

        debug {
            isMinifyEnabled = false
            manifestPlaceholders["NATIVE_APP_KEY"] = properties["NATIVE_APP_KEY"] as String
        }

        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            // Menifest에 변수를 사용할 수 있음
            manifestPlaceholders["NATIVE_APP_KEY"] = properties["NATIVE_APP_KEY"] as String
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
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")

    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    //glide
    implementation ("com.github.bumptech.glide:compose:1.0.0-beta01")
    //retrofit2
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    //okhttp3
    implementation ("com.squareup.okhttp3:okhttp:4.12.0")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.12.0")
    //hilt
    implementation("com.google.dagger:hilt-android:2.49")
    implementation("androidx.datastore:datastore-core:1.0.0")
    implementation("com.android.identity:identity-credential-android:20231002")
    ksp ("com.google.dagger:hilt-compiler:2.48")
    //navigation
    implementation("androidx.navigation:navigation-compose:2.7.7")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")
    //paging
    implementation("androidx.paging:paging-runtime-ktx:3.2.1")
    implementation("androidx.paging:paging-compose:3.3.0-alpha04")
    //viewmodel
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
    //lottie
    implementation ("com.airbnb.android:lottie-compose:6.4.0")
    // test
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // Datastore
    implementation ("androidx.datastore:datastore-preferences:1.0.0")

    // Kakao Login
    implementation ("com.kakao.sdk:v2-user:2.20.1")

    ksp("com.github.bumptech.glide:glide:4.14.2")
}

fun getApiKey(propertyKey: String): String = gradleLocalProperties(rootDir).getProperty(propertyKey)
