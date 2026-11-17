# MIFARE NFC Tool

Android Studio-projekt för Android 8+ (testmål Sony Xperia med Android 14).

## Funktioner
- Läser NFC-taggar med Android Reader Mode.
- Visar UID, tekniker och MIFARE Classic-metadata när hårdvaran stöder tekniken.
- Sparar senast avlästa metadata lokalt och låter dig radera dem.
- Visar NFC- och HCE-status.
- Läser **inte** skyddade sektorer eller nycklar och emulerar **inte** MIFARE Classic.

## Bygg APK
1. Installera Android Studio med Android SDK 35 och JDK 17.
2. Öppna projektmappen `MifareNfcTool` i Android Studio.
3. Vänta på Gradle-synkronisering (internet krävs för beroenden).
4. Välj Build > Build Bundle(s) / APK(s) > Build APK(s).
5. APK hamnar normalt i `app/build/outputs/apk/debug/app-debug.apk`.

## Begränsning
Vanlig Android HCE emulerar ISO-DEP/APDU, inte MIFARE Classic-protokollet. Appen kan därför inte ersätta ett fysiskt MIFARE Classic-kort mot en oförändrad Classic-läsare. Det finns ingen kompilerad APK i denna leverans eftersom Android SDK/Gradle inte finns i byggmiljön.
