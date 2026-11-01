# Restore the stock launcher on the currently connected TV.
$adbPath = Join-Path $env:LOCALAPPDATA 'Android/Sdk/platform-tools/adb.exe'
& $adbPath shell pm enable --user 0 com.google.android.apps.tv.launcherx
& $adbPath shell cmd package set-home-activity --user 0 com.google.android.apps.tv.launcherx/.home.HomeActivity
& $adbPath shell input keyevent KEYCODE_HOME
