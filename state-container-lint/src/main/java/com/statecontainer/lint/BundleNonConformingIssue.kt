@file:Suppress("UnstableApiUsage")

package com.statecontainer.lint

import com.android.tools.lint.detector.api.Category
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity

object BundleNonConformingIssue {

    val issue = Issue.create(
        "NonConformingToBundle",
        "Not conforming to Android Bundle argument",
        """
            You need to pass a type conforming to an Android Bundle (Parcelable, String,
            Int, etc..)
        """.trimIndent(),
        Category.CORRECTNESS,
        6, Severity.ERROR,
        Implementation(BundleArgumentDetector::class.java, Scope.JAVA_FILE_SCOPE)
    )
}