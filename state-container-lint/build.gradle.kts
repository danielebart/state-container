import com.statecontainer.buildsrc.Dependencies

plugins {
    id("kotlin")
    id("com.android.lint")
}

dependencies {
    compileOnly(Dependencies.lintApi)
    compileOnly(Dependencies.lintChecks)
}
