package app.revanced.patches.omni.ad

import app.revanced.util.exception
import app.revanced.patcher.data.BytecodeContext
import app.revanced.patcher.extensions.InstructionExtensions.replaceInstructions
import app.revanced.patcher.patch.BytecodePatch
import app.revanced.patcher.patch.annotation.CompatiblePackage
import app.revanced.patcher.patch.annotation.Patch
import app.revanced.patches.omni.ad.fingerprints.GetPremiumFingerprint
import app.revanced.patches.omni.ad.fingerprints.IsPremiumFingerprint
import app.revanced.patches.omni.ad.fingerprints.IsAdFreeFingerprint

@Patch(
    name = "Disable ads",
    compatiblePackages = [CompatiblePackage("se.omni")]
)
@Suppress("unused")
object DisableAdsPatch : BytecodePatch(
    setOf(GetPremiumFingerprint, IsPremiumFingerprint, IsAdFreeFingerprint)
) {
    override fun execute(context: BytecodeContext) {
        GetPremiumFingerprint.result?.mutableMethod?.replaceInstructions(
            0,
            """
                const/4 v0, 1
                return v0
            """
        ) ?: throw GetPremiumFingerprint.exception

        IsPremiumFingerprint.result?.mutableMethod?.replaceInstructions(
            0,
            """
                const/4 v0, 1
                return v0
            """
        ) ?: throw IsPremiumFingerprint.exception

        IsAdFreeFingerprint.result?.mutableMethod?.replaceInstructions(
            0,
            """
                const/4 v0, 1
                return v0
            """
        ) ?: throw IsAdFreeFingerprint.exception
    }
}
