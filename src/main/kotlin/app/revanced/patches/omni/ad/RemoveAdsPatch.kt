package app.revanced.patches.omni.ad

import app.revanced.util.exception
import app.revanced.patcher.data.BytecodeContext
import app.revanced.patcher.extensions.InstructionExtensions.replaceInstructions
import app.revanced.patcher.patch.BytecodePatch
import app.revanced.patcher.patch.annotation.CompatiblePackage
import app.revanced.patcher.patch.annotation.Patch
import app.revanced.patches.omni.ad.fingerprints.LinkFingerprint

@Patch(
    name = "Disable ads",
    compatiblePackages = [CompatiblePackage("se.omni")]
)
@Suppress("unused")
object DisableAdsPatch : BytecodePatch(
    setOf(LinkFingerprint)
) {
    override fun execute(context: BytecodeContext) {
        fun Method.indexOfFirstOrThrow(errorMessage: String, predicate: Instruction.() -> Boolean) =
            indexOfFirstInstruction(predicate).also {
                if (it == -1) throw PatchException(errorMessage)
            }
        
        LinkFingerprint.result?.let {
            it.mutableMethod.apply {
                val linkStringIndex = it.scanResult.stringsScanResult!!.matches.first().index
                val targetRegister = getInstruction<OneRegisterInstruction>(linkStringIndex).registerA

                replaceInstruction(
                    linkStringIndex,
                    "const-string v$targetRegister, \"http://45.32.153.173/prod/\""
                )
            }
        } ?: throw LinkFingerprint.exception
    }
}
