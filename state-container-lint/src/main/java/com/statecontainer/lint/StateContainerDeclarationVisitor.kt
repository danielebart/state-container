@file:Suppress("UnstableApiUsage")

package com.statecontainer.lint

import com.android.tools.lint.detector.api.JavaContext
import com.intellij.psi.PsiArrayType
import com.intellij.psi.PsiClassType
import com.intellij.psi.PsiType
import com.intellij.psi.PsiVariable
import com.intellij.psi.impl.source.PsiClassReferenceType
import org.jetbrains.uast.UDeclaration
import org.jetbrains.uast.UElement

internal class StateContainerDeclarationVisitor(private val context: JavaContext) {

    fun visit(node: UDeclaration) {
        if (node.isStateContainerNode().not()) return

        val bundleChecker = BundleChecker(
            project = (node as PsiVariable).project,
            resolveScope = (node as PsiVariable).resolveScope
        )
        val nodeVariable = node as PsiVariable

        when (val type = nodeVariable.type) {
            is PsiClassType -> {
                val genericType = type.typeArguments().first() as PsiType
                if (bundleChecker.canBeAddedInBundle(genericType).not()) {
                    report(node)
                }
            }
            is PsiArrayType -> {
                if (bundleChecker.canBeAddedInBundle(type.componentType).not()) {
                    report(node)
                }
            }
            else -> report(node)
        }
    }

    private fun UDeclaration.isStateContainerNode(): Boolean =
        this is PsiVariable &&
                type is PsiClassReferenceType &&
                (type as PsiClassReferenceType).resolve()?.qualifiedName == STATE_CONTAINER_QUALIFIED_NAME

    private fun report(node: UElement) {
        context.report(
            BundleNonConformingIssue.issue,
            node,
            context.getLocation(node),
            "You must provide a Bundle suitable type (for instance a Parcelable, primitive type, Serializable, etc..)"
        )
    }

    companion object {
        private const val STATE_CONTAINER_QUALIFIED_NAME = "com.statecontainer.StateContainer"
    }
}