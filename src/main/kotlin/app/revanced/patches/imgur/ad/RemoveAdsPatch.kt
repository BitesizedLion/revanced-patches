package app.revanced.patches.imgur.ad

import app.revanced.patcher.data.BytecodeContext
import app.revanced.patcher.extensions.InstructionExtensions.addInstruction
import app.revanced.patcher.patch.BytecodePatch
import app.revanced.patcher.patch.annotation.CompatiblePackage
import app.revanced.patcher.patch.annotation.Patch
import app.revanced.patches.imgur.ad.fingerprints.GetShowFingerprint

@Patch(
    name = "Always False for getShow",
    description = "Patch to always return false for getShow",
    compatiblePackages = [CompatiblePackage("com.imgur.mobile")]
)
object AlwaysFalseForGetShowPatch : BytecodePatch(
    setOf(GetShowFingerprint)
) {
    override fun execute(context: BytecodeContext) {
        val result = GetShowFingerprint.result
            ?: throw PatchException("GetShowFingerprint not found")

        result.mutableMethod.replaceInstructions(
            0,
            """
                const/4 v0, 0x0 // Always return false
                return v0
            """
        )
    }
}
