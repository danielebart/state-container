package com.statecontainer.buildsrc

object Dependencies {

    const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
    const val savedState = "androidx.savedstate:savedstate:${Versions.savedState}"

    // testing
    const val testCore = "androidx.test:core:${Versions.testCore}"
    const val junit = "junit:junit:${Versions.junit}"
    const val fragmentTesting = "androidx.fragment:fragment-testing:${Versions.fragmentTesting}"
    const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espresso}"
}
