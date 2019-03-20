echo Apply Script: COPY
cd scripts
echo F|xcopy /y /s /f /q "C:\Users\cyberpwn\Documents\development\workspace\GlossAPI\target\GlossAPI-%4.jar" "libs\GlossAPI.jar"
echo F|xcopy /y /s /f /q "%1" "plugin.jar"
java -jar BileSigner-1.0.jar plugin.jar -encrypt -guard -repackage=com.volume.gloss.src -version=%4 -volume=com.volmit.gloss.volume