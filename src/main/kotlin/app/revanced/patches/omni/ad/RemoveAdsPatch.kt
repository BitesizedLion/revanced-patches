package app.revanced.patches.omni.ad

import app.revanced.patcher.data.BytecodeContext
import app.revanced.patcher.extensions.InstructionExtensions.addInstruction
import app.revanced.patcher.extensions.InstructionExtensions.getInstruction
import app.revanced.patcher.extensions.InstructionExtensions.removeInstructions
import app.revanced.patcher.extensions.InstructionExtensions.replaceInstruction
import app.revanced.patcher.extensions.InstructionExtensions.replaceInstructions
import app.revanced.patcher.patch.annotation.CompatiblePackage
import app.revanced.patcher.patch.annotation.Patch
import app.revanced.patcher.patch.BytecodePatch
import app.revanced.patcher.patch.options.PatchOption.PatchExtensions.stringPatchOption
import app.revanced.patcher.patch.PatchException
import app.revanced.patches.omni.ad.fingerprints.LinkFingerprint
import app.revanced.util.exception
import app.revanced.util.getReference
import app.revanced.util.indexOfFirstInstruction
import com.android.tools.smali.dexlib2.builder.BuilderInstruction
import com.android.tools.smali.dexlib2.iface.instruction.Instruction
import com.android.tools.smali.dexlib2.iface.instruction.OneRegisterInstruction
import com.android.tools.smali.dexlib2.iface.instruction.TwoRegisterInstruction
import com.android.tools.smali.dexlib2.iface.Method
import com.android.tools.smali.dexlib2.iface.reference.MethodReference
import com.android.tools.smali.dexlib2.Opcode

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
