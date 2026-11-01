# Pylerium Purge 1.1

Installed on the ADB-connected AI PONT TV on 30 October 2026.

Graphite panels, orange focus outlines and cyan details follow Pylerium's gui_components.py and loadout-menu.py palette. Includes a custom vector icon/banner, readable persistent app labels, compact AM/PM, four installed shortcuts, a full application library, HDMI menu, and 140–180 ms focus/scroll animations. App discovery stays on a background executor. The ambient gradient updates once per selection rather than every animation frame. Layout recalculates after screen size or density changes, and column count adapts to available width.

Validation: assembleDebug, testDebugUnitTest and lintDebug passed (0 lint errors; existing dependency/deprecation and resource warnings remain). On-device checks covered app-library navigation, HDMI menu navigation, Settings launch, YouTube launch, and Home returning to fasterTV. Android's HOME resolver and HOME role holder both report com.cleantv.launcher. No runtime crash appeared in the inspected AndroidRuntime log. Navigation frame timing still depends on this TV's GPU and background load; no 60 fps guarantee is made.

The device's stock Google TV launcher has a higher-priority HOME activity. The HOME role alone did not redirect the Home button. Its protected activity could not be disabled individually, so com.google.android.apps.tv.launcherx was disabled for user 0 (not uninstalled). Run RESTORE-GOOGLE-TV.ps1 to re-enable it and restore its HOME association.

APK: app/build/outputs/apk/debug/app-debug.apk
Source backup before styling: polish-backup/before-polish.zip
Final home screenshot: polish-backup/home-final.png
