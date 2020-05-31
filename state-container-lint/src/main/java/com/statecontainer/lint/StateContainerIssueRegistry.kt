@file:Suppress("UnstableApiUsage")

package com.statecontainer.lint

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.detector.api.CURRENT_API
import com.android.tools.lint.detector.api.Issue

internal class StateContainerIssueRegistry : IssueRegistry() {

    override val issues: List<Issue>
        get() = listOf(BundleNonConformingIssue.issue)

    override val api: Int
        get() = CURRENT_API
}