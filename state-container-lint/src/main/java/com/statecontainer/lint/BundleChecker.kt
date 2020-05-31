package com.statecontainer.lint

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiType
import com.intellij.psi.search.GlobalSearchScope
import java.io.Serializable

internal class BundleChecker(
    private val project: Project,
    private val resolveScope: GlobalSearchScope
) {

    fun canBeAddedInBundle(psiType: PsiType): Boolean {
        val supportedBundleClasses = listOf(
            Boolean::class,
            Byte::class,
            Char::class,
            Double::class,
            Float::class,
            Int::class,
            Long::class,
            Short::class,
            CharSequence::class,
            BooleanArray::class,
            ByteArray::class,
            CharArray::class,
            DoubleArray::class,
            FloatArray::class,
            IntArray::class,
            LongArray::class,
            ShortArray::class,
            Serializable::class
        )
            .map { it.qualifiedName }
            .toMutableList()
            .apply { add("android.os.Parcelable") }

        return supportedBundleClasses.any { classQualifiedName ->
            val otherType = PsiType.getTypeByName(classQualifiedName!!, project, resolveScope)
            otherType.isAssignableFrom(psiType)
        }
    }
}

