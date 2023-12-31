package app.revanced.patches.imgur.ad

import app.revanced.util.exception
import app.revanced.patcher.data.BytecodeContext
import app.revanced.patcher.extensions.InstructionExtensions.replaceInstructions
import app.revanced.patcher.patch.BytecodePatch
import app.revanced.patcher.patch.annotation.CompatiblePackage
import app.revanced.patcher.patch.annotation.Patch
import app.revanced.patches.imgur.ad.fingerprints.GetShowFingerprint

@Patch(
    name = "Disable ads",
    compatiblePackages = [CompatiblePackage("com.imgur.mobile")]
)
@Suppress("unused")
object DisableAdsPatch : BytecodePatch(
    setOf(GetShowFingerprint)
) {
    override fun execute(context: BytecodeContext) = GetShowFingerprint.result?.mutableMethod?.replaceInstructions(
        0,
        """
            const/4 v0, 0x0
            return v0
        """
    ) ?: throw GetShowFingerprint.exception
}
