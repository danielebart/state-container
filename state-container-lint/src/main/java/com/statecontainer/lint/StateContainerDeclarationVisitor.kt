@file:Suppress("UnstableApiUsage")

package com.statecontainer.lint

import com.android.tools.lint.detector.api.JavaContext
import com.intellij.psi.JavaPsiFacade
import com.intellij.psi.PsiArrayType
import com.intellij.psi.PsiClassType
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiParameter
import com.intellij.psi.impl.source.PsiClassReferenceType
import org.jetbrains.uast.UDeclaration

class StateContainerDeclarationVisitor(private val context: JavaContext) {

    fun visit(node: UDeclaration) {
        if (node.isStateContainerNode().not()) return

        val bundleChecker = BundleChecker(
            project = (node as PsiParameter).project,
            resolveScope = (node as PsiParameter).resolveScope
        )
        val psiClassReferenceType = node.type as PsiClassReferenceType

        when (psiClassReferenceType.typeArguments().firstOrNull()) {
            is PsiClassType -> {
                val generic =
                    psiClassReferenceType.typeArguments().first() as PsiClassType
                val psiClass = generic.resolve()!!
                val type = JavaPsiFacade.getElementFactory(psiClass.project)
                    .createType(psiClass)

                if (bundleChecker.canBeAddedInBundle(type).not()) {
                    report(node)
                }
            }
            is PsiArrayType -> {
                val generic =
                    psiClassReferenceType.typeArguments().first() as PsiArrayType
                if (bundleChecker.canBeAddedInBundle(generic.componentType).not()) {
                    report(node)
                }
            }
            else -> report(node)
        }
    }

    private fun UDeclaration.isStateContainerNode(): Boolean =
        this is PsiParameter &&
                type is PsiClassReferenceType &&
                (type as PsiClassReferenceType).resolve()?.qualifiedName == STATE_CONTAINER_QUALIFIED_NAME

    private fun report(node: PsiParameter) {
        context.report(
            BundleNonConformingIssue.issue,
            node as PsiElement,
            context.getLocation(node as PsiElement),
            "---------"
        )
    }

    companion object {
        private const val STATE_CONTAINER_QUALIFIED_NAME = "com.statecontainer.StateContainer"
    }
}