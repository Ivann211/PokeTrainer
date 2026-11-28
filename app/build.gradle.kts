plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    id("org.jetbrains.kotlin.plugin.serialization") version "2.2.21"
}

android {
    namespace = "com.example.poketrainer"

    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "com.example.poketrainer"
        minSdk = 34
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        debug {
            buildConfigField(
                "String",
                "BASE_URL",
                "\"https://pokeapi.uttics.com/api/\""
            )
        }

        release {
            buildConfigField(
                "String",
                "BASE_URL",
                "\"https://pokeapi.uttics.com/api/\""
            )

            optimization {
                enable = true
                packageScope = setOf(
                    "androidx.**",
                    "kotlin.**",
                    "kotlinx.**"
                )
            }
        }
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {
    // Compose BOM
    implementation(platform(libs.androidx.compose.bom))

    // Jetpack Compose
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation("androidx.compose.material:material-icons-extended")

    // Activity y Android Core
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.core.ktx)

    // Lifecycle y ViewModel
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.10.0")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.10.0")

    // Navigation 3
    implementation("androidx.navigation3:navigation3-runtime:1.2.0")
    implementation("androidx.navigation3:navigation3-ui:1.2.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-navigation3:2.10.0")
    // Kotlin Serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:1.9.0")
    // Retrofit, Gson y OkHttp
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
    // Coil: imágenes de Pokémon
    implementation("io.coil-kt:coil-compose:2.7.0")
    // DataStore: persistencia del token
    implementation("androidx.datastore:datastore-preferences:1.1.1")
    // Corrutinas
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.9.0")
    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
    // Debug
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    debugImplementation(libs.androidx.compose.ui.tooling)
}
