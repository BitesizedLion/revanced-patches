package app.revanced.patches.omni.ad.fingerprints

import app.revanced.patcher.fingerprint.MethodFingerprint

object LinkFingerprint : MethodFingerprint(
    strings = listOf("https://omni-ads.omni.news/prod/"),
)
