BGMI Device Spoof (LSPatch module)

BUILD
1. Open this folder in Android Studio and let Gradle sync.
2. Build > Build APK(s). Output: app/build/outputs/apk/debug/app-debug.apk
3. Install app-debug.apk on your phone.

USE WITH LSPATCH
1. In LSPatch, choose "Manage" > "New patch", select the BGMI APK
   (install all split APKs if it is a bundle).
2. Choose "Local" mode (embeds module) and select "BGMI Device Spoof".
3. Patch, uninstall the original BGMI, install the patched one.

EDIT THE FAKE DEVICE
Change FAKE_MODEL / FAKE_DEVICE / FAKE_PRODUCT in
app/src/main/java/com/example/bgmispoof/MainHook.java

WARNING: repackaged BGMI may trigger anti-tamper and get the account banned.
Use a throwaway account.
