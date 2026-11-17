# Bygg APK från mobilen (utan dator)

1. Logga in på https://github.com i mobilens webbläsare.
2. Skapa ett nytt privat repository, t.ex. `MifareNfcTool`, med en README.
3. Öppna repositoryt, välj **Add file > Upload files** (använd vid behov webbläsarens skrivbordsvy).
4. Packa upp denna ZIP på mobilen och ladda upp projektets filer **med mappstrukturen bevarad**, inklusive `.github/workflows/build-apk.yml`. GitHubs vanliga filuppladdning i mobilen kan vara begränsad; använd alternativt GitHub-webbredigeraren för att skapa mappar och filer.
5. Bekräfta ändringarna till grenen `main`.
6. Gå till **Actions > Build Android APK > Run workflow** (eller vänta på automatisk push-byggning).
7. När bygget är grönt, öppna körningen och hämta artefakten **MifareNfcTool-debug-apk**. ZIP-filen innehåller `app-debug.apk`.
8. Packa upp artefakten och installera APK på telefonen. Tillåt installation från webbläsaren/filhanteraren vid behov.

Om GitHub inte visar Actions: kontrollera att workflowfilen finns exakt i `.github/workflows/build-apk.yml` och att Actions är aktiverat för repositoryt.

Detta är en debug-APK avsedd för personlig testning. Appen läser NFC-metadata men kan inte emulera MIFARE Classic på Android HCE. Bygget har inte körts eller verifierats i denna miljö.
