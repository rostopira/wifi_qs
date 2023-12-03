# WiFi QS

Google has removed *Wi-Fi* from QS in Android 12 replacing it with useless *Internet* button
This tiny project brings it back

Target API 28 is required to allow this app to turn Wi-Fi on and off.
Location permission is optional, if you want to show connected Wi-Fi SSID and currently must be granted manually.

I can't publish this in Google Play, since it requires minimum target API 29.
It's possible to use higher API if user grants this app a Device Admin role.
Not implemented currently, but I may do that in spare time.

# FAQ

## If you are still using Android 12
There is now an easier and better way to bring back those buttons: https://www.xda-developers.com/bring-back-wifi-mobile-data-quick-settings-tiles-android-12-adb/
Just execute in adb shell:
```
settings put global settings_provider_model false
settings put secure sysui_qs_tiles "wifi,cell,$(settings get secure sysui_qs_tiles)"
```

## Wi-Fi network name isn't displayed

You didn't gave app permission to use location. It's required to gather network info.

## I want internet toggle

Nope, not possible without root.

## I have root

Use [Better Internet Tiles](https://play.google.com/store/apps/details?id=be.casperverswijvelt.unifiedinternetqs)

## I don't have root, what about Shizuku

Use [Better Internet Tiles](https://play.google.com/store/apps/details?id=be.casperverswijvelt.unifiedinternetqs)

I'm not going to rewrite it using shizuku

## I have feature request

I don't have time for that. PR's are welcome
