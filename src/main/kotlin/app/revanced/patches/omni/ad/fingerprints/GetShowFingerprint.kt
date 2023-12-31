package app.revanced.patches.omni.ad.fingerprints

import app.revanced.patcher.fingerprint.MethodFingerprint

internal object GetPremiumFingerprint : MethodFingerprint(
    customFingerprint = { methodDef, _ ->
        methodDef.definingClass.endsWith("OmniConfiguration;") && methodDef.name == "getPremiumApp"
    }
)
