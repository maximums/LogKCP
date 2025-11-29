package com.maximums.fir

import org.jetbrains.kotlin.descriptors.ClassKind
import org.jetbrains.kotlin.descriptors.Visibilities
import org.jetbrains.kotlin.fir.FirSession
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin
import org.jetbrains.kotlin.fir.extensions.FirDeclarationGenerationExtension
import org.jetbrains.kotlin.fir.extensions.FirDeclarationPredicateRegistrar
import org.jetbrains.kotlin.fir.extensions.MemberGenerationContext
import org.jetbrains.kotlin.fir.extensions.NestedClassGenerationContext
import org.jetbrains.kotlin.fir.extensions.predicateBasedProvider
import org.jetbrains.kotlin.fir.plugin.createCompanionObject
import org.jetbrains.kotlin.fir.plugin.createConstructor
import org.jetbrains.kotlin.fir.plugin.createNestedClass
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol
import org.jetbrains.kotlin.fir.symbols.impl.FirClassSymbol
import org.jetbrains.kotlin.fir.symbols.impl.FirConstructorSymbol
import org.jetbrains.kotlin.name.Name
import org.jetbrains.kotlin.name.SpecialNames


class LogKCPObjectGenerator(session: FirSession) : FirDeclarationGenerationExtension(session) {

    override fun FirDeclarationPredicateRegistrar.registerPredicates() {
        register(FirLogKCPPredicates.logKcpObject)
    }

    override fun getNestedClassifiersNames(
        classSymbol: FirClassSymbol<*>,
        context: NestedClassGenerationContext
    ): Set<Name> {
        if (!session.predicateBasedProvider.matches(FirLogKCPPredicates.logKcpObject, classSymbol)) {
            return emptySet()
        }

        return setOf(LOGKCP_OBJECT_NAME)
    }

    override fun getCallableNamesForClass(classSymbol: FirClassSymbol<*>, context: MemberGenerationContext): Set<Name> {
        val key = (classSymbol.origin as? FirDeclarationOrigin.Plugin)?.key

        if (key !is FirLogKCPCompanionObjectKey) return emptySet()

        return setOf(SpecialNames.INIT)
    }

    override fun generateNestedClassLikeDeclaration(
        owner: FirClassSymbol<*>,
        name: Name,
        context: NestedClassGenerationContext
    ): FirClassLikeSymbol<*>? {
        if (name != LOGKCP_OBJECT_NAME) return null

        val companionObject = createNestedClass(
            owner = owner,
            name = name,
            key = FirLogKCPCompanionObjectKey,
            classKind = ClassKind.OBJECT
        ) {
            status { isCompanion = true }
        }

        return companionObject.symbol
    }

    override fun generateConstructors(context: MemberGenerationContext): List<FirConstructorSymbol> {
        val owner = context.owner
        val key = (owner.origin as? FirDeclarationOrigin.Plugin)?.key

        if (key !is FirLogKCPCompanionObjectKey) return emptyList()

        val constructor = createConstructor(
            owner = owner,
            key = FirLogKCPCompanionObjectKey,
            isPrimary = true,
            generateDelegatedNoArgConstructorCall = true
        )

        return listOf(constructor.symbol)
    }

    private companion object {
        val LOGKCP_OBJECT_NAME = Name.identifier("MISHA_FILIPESCU_II_CIORT_IBANII")
    }
}
