plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.myapplication"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.myapplication"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation(platform("com.google.firebase:firebase-bom:33.6.0"))
    implementation("com.google.firebase:firebase-analytics")
    implementation ("de.hdodenhof:circleimageview:3.1.0")
    implementation ("io.github.maitrungduc1410:AVLoadingIndicatorView:2.1.4")
    implementation ("in.championswimmer:SimpleFingerGestures_Android_Library:1.1")
    implementation ("com.github.chivorns:smartmaterialspinner:2.0.0")
    implementation ("com.google.android.material:material:1.9.0")
    implementation ("com.hbb20:ccp:2.7.1")
    implementation("com.asksira.android:loopingviewpager:1.4.1")
    implementation ("com.github.Foysalofficial:PageIndicatorView:16.0")
    implementation ("com.google.android.gms:play-services-maps:18.1.0")
    implementation ("com.google.android.libraries.places:places:2.5.0")
//    implementation ("com.android.support:appcompat-v7:27.1.1")
//    implementation ("com.android.support:recyclerview-v7:27.1.1")
//    implementation ("com.android.support:support-core-ui:27.1.1")

    implementation(platform("com.google.firebase:firebase-bom:33.6.0"))

    // When using the BoM, you don't specify versions in Firebase library dependencies
    implementation(platform("com.google.firebase:firebase-bom:33.6.0"))

    // Add the dependency for the Firebase Authentication library
    // When using the BoM, you don't specify versions in Firebase library dependencies
    implementation("com.google.firebase:firebase-auth")
    // Add the dependency for the Firebase SDK for Google Analytics
    implementation("com.google.firebase:firebase-analytics")




    val room_version = "2.6.1"

    implementation("androidx.room:room-runtime:$room_version")
    implementation ("com.github.bumptech.glide:glide:4.16.0")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.2.0")
    implementation("androidx.activity:activity:1.9.2")
    implementation("com.google.firebase:firebase-database:21.0.0")
    testImplementation("junit:junit:4.13.2")
    implementation ("com.android.volley:volley:1.2.1")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
}