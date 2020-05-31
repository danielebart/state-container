import com.statecontainer.buildsrc.AndroidConfig
import com.statecontainer.buildsrc.Dependencies
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("com.statecontainer.buildsrc.coverage")
    id("com.statecontainer.buildsrc.deploy")
    id("kotlin-kapt")
}

android {
    compileSdkVersion(AndroidConfig.targetSdkVersion)

    defaultConfig {
        minSdkVersion(AndroidConfig.minSdkVersion)
        targetSdkVersion(AndroidConfig.targetSdkVersion)
        testInstrumentationRunner = AndroidConfig.testInstrumentationRunner
        javaCompileOptions.annotationProcessorOptions.includeCompileClasspath = true
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
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

repositories { jcenter() }

dependencies {
    implementation(Dependencies.kotlinStdLib)
    implementation(Dependencies.appCompat)
    implementation(Dependencies.coreKtx)
    implementation(Dependencies.savedState)

    testImplementation(Dependencies.junit)

    androidTestImplementation(Dependencies.testCore)
    androidTestImplementation(Dependencies.espressoCore)
    androidTestImplementation(Dependencies.fragmentTesting)

    lintPublish(project(":state-container-lint"))
}