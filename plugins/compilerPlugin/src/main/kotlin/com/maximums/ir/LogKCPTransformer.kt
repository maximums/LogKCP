package com.maximums.ir

import com.maximums.annotations.LogKCP
import org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
import org.jetbrains.kotlin.backend.common.lower.DeclarationIrBuilder
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity
import org.jetbrains.kotlin.cli.common.messages.MessageCollector
import org.jetbrains.kotlin.ir.IrStatement
import org.jetbrains.kotlin.ir.declarations.IrFunction
import org.jetbrains.kotlin.ir.util.hasAnnotation
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
import org.jetbrains.kotlin.name.CallableId
import org.jetbrains.kotlin.ir.builders.irCall
import org.jetbrains.kotlin.ir.builders.irString
import org.jetbrains.kotlin.ir.expressions.IrBlockBody
import org.jetbrains.kotlin.ir.util.DumpIrTreeOptions
import org.jetbrains.kotlin.ir.util.dump
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.name.Name

class LogKCPTransformer(
    private val context: IrPluginContext,
    private val messageCollector: MessageCollector
) : IrElementTransformerVoid() {

    override fun visitFunction(declaration: IrFunction): IrStatement {
        val name = LogKCP::class.java.name
        val fqName = FqName(fqName = name)

        if (!declaration.hasAnnotation(fqName)) return super.visitFunction(declaration)

        val body = declaration.body as? IrBlockBody ?: return super.visitFunction(declaration)
        val ioPackage = FqName(fqName = "kotlin.io")
        val printlnId = CallableId(packageName = ioPackage, callableName = Name.identifier("println"))
        val printlnSymbol = context.referenceFunctions(printlnId).firstOrNull { funSymbol ->
                funSymbol.owner.parameters.size == 1 &&
                funSymbol.owner.parameters.first().type == context.irBuiltIns.anyNType
                        || funSymbol.owner.parameters.first().type == context.irBuiltIns.stringType
            } ?: return super.visitFunction(declaration)

        val builder = DeclarationIrBuilder(generatorContext = context, symbol = declaration.symbol)
        val firstPrint = builder.irCall(callee = printlnSymbol, type = context.irBuiltIns.unitType).apply {
            arguments.addFirst(builder.irString("First Println from IR"))
        }
        val secondePrint = builder.irCall(callee = printlnSymbol, type = context.irBuiltIns.unitType).apply {
            arguments.addFirst(builder.irString("Last Println from IR"))
        }

        body.statements.addFirst(firstPrint)
        body.statements.addLast(secondePrint)

        val declarationIrDump = declaration.dump(
            DumpIrTreeOptions(
                normalizeNames = true,
                stableOrder = true,
                printSignatures = true,
                printSourceOffsets = true
            )
        )
        messageCollector.report(severity = CompilerMessageSeverity.INFO, message = declarationIrDump)

        return super.visitFunction(declaration)
    }
}