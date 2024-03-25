// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    // Menifest에 local-properties 변수를 사용할 수 있게 하는 라이브러리
    dependencies {
        classpath("com.google.android.libraries.mapsplatform.secrets-gradle-plugin:secrets-gradle-plugin:2.0.1")
    }
}

plugins {
    id("com.android.application") version "8.2.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    id("com.google.dagger.hilt.android") version "2.48" apply false
    id("com.google.devtools.ksp") version "1.9.0-1.0.12" apply false
}