package app.revanced.patches.irplus.ad.fingerprints

import app.revanced.patcher.extensions.or
import app.revanced.patcher.fingerprint.MethodFingerprint
import com.android.tools.smali.dexlib2.AccessFlags

object GetShowFingerprint : MethodFingerprint(
    returnType = "Z", // boolean return type
    access = AccessFlags.PUBLIC, // public method
    parameters = emptyList(), // no parameters
    opcodes = listOf(Opcode.IRETURN), // return instruction for int (boolean is represented as int in bytecode)
    customFingerprint = { it.name == "getShow" && it.definingClass == "Lcom/imgur/mobile/common/AdsEligibility;" }
)
