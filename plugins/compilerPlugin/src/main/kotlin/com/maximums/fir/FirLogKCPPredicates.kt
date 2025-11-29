package com.maximums.fir

import com.maximums.annotations.LogKCPObject
import org.jetbrains.kotlin.fir.extensions.predicate.DeclarationPredicate
import org.jetbrains.kotlin.name.FqName

object FirLogKCPPredicates {
    internal val logKcpObject = DeclarationPredicate.create {
        val fqName = FqName(LogKCPObject::class.java.name)

        annotated(fqName) or metaAnnotated(fqName, includeItself = true)
    }
}
