if [ $1X = newX ]; then
echo "make new"
ant clean release
fi

echo "sign"
~/bin/signapk/signapk.sh bin/F-Droid-release-unsigned.apk
echo "uninstall"
adb uninstall org.fdroid.fdroid
echo "install"
adb install bin/F-Droid-release-unsigned.apk.signed
