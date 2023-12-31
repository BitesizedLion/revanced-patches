package app.revanced.patches.imgur.ad.fingerprints

import app.revanced.patcher.fingerprint.MethodFingerprint

internal object GetShowFingerprint : MethodFingerprint(
    customFingerprint = { methodDef, _ ->
        methodDef.definingClass.endsWith("AdsEligibility;") && methodDef.name == "getShow"
    }
)
