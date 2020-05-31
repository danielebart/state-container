@file:Suppress("UnstableApiUsage")

package com.statecontainer.lint

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Detector.UastScanner
import com.android.tools.lint.detector.api.JavaContext
import org.jetbrains.uast.UDeclaration
import org.jetbrains.uast.UElement

class BundleArgumentDetector : Detector(), UastScanner {

    override fun getApplicableUastTypes(): List<Class<out UElement>> {
        return listOf(UDeclaration::class.java)
    }

    override fun createUastHandler(context: JavaContext): UElementHandler? {
        val stateContainerDeclarationVisitor = StateContainerDeclarationVisitor(context)
        return object : UElementHandler() {
            override fun visitDeclaration(node: UDeclaration) {
                stateContainerDeclarationVisitor.visit(node)
            }
        }
    }
}