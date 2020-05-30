import com.statecontainer.buildsrc.AndroidConfig
import com.statecontainer.buildsrc.Dependencies
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("com.statecontainer.buildsrc.coverage")
    id("com.statecontainer.buildsrc.deploy")
}

android {
    compileSdkVersion(AndroidConfig.targetSdkVersion)

    defaultConfig {
        minSdkVersion(AndroidConfig.minSdkVersion)
        targetSdkVersion(AndroidConfig.targetSdkVersion)
        testInstrumentationRunner = AndroidConfig.testInstrumentationRunner
    }

    testOptions.unitTests.apply {
        isIncludeAndroidResources = true
        all(KotlinClosure1<Test, Test>({
            apply {
                testLogging.exceptionFormat = TestExceptionFormat.FULL
                testLogging.events = setOf(
                    TestLogEvent.SKIPPED,
                    TestLogEvent.PASSED,
                    TestLogEvent.FAILED
                )
            }
        }, this))
    }

    lintOptions {
        isWarningsAsErrors = true
    }
}

dependencies {
    implementation(Dependencies.kotlinStdLib)
    implementation(Dependencies.appCompat)
    implementation(Dependencies.coreKtx)
    implementation("androidx.savedstate:savedstate:1.0.0")

    testImplementation(Dependencies.junit)

    androidTestImplementation(Dependencies.testCore)
    androidTestImplementation(Dependencies.espressoCore)
    androidTestImplementation(Dependencies.fragmentTesting)
}