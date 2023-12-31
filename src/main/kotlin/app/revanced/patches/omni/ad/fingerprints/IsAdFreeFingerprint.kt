package app.revanced.patches.omni.ad.fingerprints

import app.revanced.patcher.fingerprint.MethodFingerprint

internal object IsAdFreeFingerprint : MethodFingerprint(
    customFingerprint = { methodDef, _ ->
        methodDef.definingClass.endsWith("PremiumStatus;") && methodDef.name == "isAdFree"
    }
)
