# Pylerium Purge

A clean, fast Android TV / Google TV launcher. It replaces the ad-filled stock home screen with a
quiet app grid, input shortcuts, Wi-Fi status and a one-row quick launch bar.

Part of the Pylerium TV suite: [pyleriumTV](https://github.com/Pylerium/pyleriumTV) ·
[Pylerium Player](https://github.com/Pylerium/PyleriumPlayer).

## Features

- Remote-first home screen: quick launch row, full app library, TV inputs, Wi-Fi / Ethernet status
- Animated Pylerium boot logo and skeleton loading states
- Optional wallpaper (`wallpaper.jpg` in the app's external files directory), dimmed for readability
- Registers as a Home app, so it can be set as the default launcher
- No ads, no recommendations, no network access

## Build

```
./gradlew assembleDebug
```

The APK is written to `app/build/outputs/apk/debug/app-debug.apk`. Install with
`adb install -r app-debug.apk`. Requires the Android SDK (create `local.properties` with `sdk.dir=...`).

## Going back to the stock launcher

`RESTORE-GOOGLE-TV.ps1` re-enables the stock Google TV launcher on the connected TV over ADB:

```
powershell -ExecutionPolicy Bypass -File RESTORE-GOOGLE-TV.ps1
```

## Credits

Derived from [CleanTV](https://github.com/JohnWayneeee/cleantv) by JohnWayneeee (MIT). See
[NOTICE.md](NOTICE.md) and [LICENSE](LICENSE).
