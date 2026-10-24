# حروفي المرحة

## متطلبات بناء APK

- Java JDK 21.
- Android SDK Platform 36.1 وAndroid SDK Build-Tools 36.0.0.
- اتصال بالإنترنت في أول بناء لتنزيل Gradle والتبعيات.
- يحتوي المستودع على Gradle Wrapper؛ لا يلزم تثبيت Gradle منفصلًا.

## بناء نسخة Debug محليًا

ينشئ إعداد المشروع توقيع Debug من ملف `debug.keystore` في جذر المستودع. إذا لم يكن الملف موجودًا، أنشئ مفتاحًا محليًا غير مرفوع إلى Git:

```bash
keytool -genkeypair -v -noprompt \
  -keystore debug.keystore \
  -storepass android \
  -alias androiddebugkey \
  -keypass android \
  -keyalg RSA -keysize 2048 -validity 10000 \
  -dname "CN=Android Debug,O=Android,C=US"
```

ثم من جذر المستودع شغّل:

```bash
./gradlew :app:assembleDebug
```

على Windows:

```bat
gradlew.bat :app:assembleDebug
```

ملف APK الناتج:

```text
app/build/outputs/apk/debug/app-debug.apk
```

لتثبيته على جهاز موصول ومفعّل عليه Android Debug Bridge (ADB):

```bash
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

نسخة Debug موقعة بمفتاح تصحيح، وهي للتجربة والتوزيع الداخلي وليست للنشر في المتجر.

للحفاظ على التوقيع نفسه بين تشغيلات GitHub Actions، أضف سر المستودع `ANDROID_DEBUG_KEYSTORE_BASE64` بقيمة Base64 لمفتاح `debug.keystore`. إذا لم يُضف هذا السر، ينشئ CI مفتاحًا مؤقتًا لكل تشغيل، وقد يلزم حذف النسخة القديمة قبل تثبيت تحديث موقّع بمفتاح مختلف. حذف التطبيق القديم يزيل بياناته المحلية. محليًا، لا تستبدل `debug.keystore` إذا أردت تثبيت تحديث فوق نسخة سابقة.

## البناء على GitHub Actions

يشغّل سير العمل `.github/workflows/build-debug-apk.yml` بناء `:app:assembleDebug` عند الدفع أو فتح طلب دمج، ويمكن تشغيله يدويًا من تبويب **Actions**. بعد اكتماله نزّل artifact باسم `debug-apk` من صفحة تشغيل سير العمل؛ ويحتوي على ملف APK.

## توافق Android

الحد الأدنى للتثبيت هو **Android 11 (API 30)**، ويستهدف التطبيق Android API 36. لا يمكن تثبيته على إصدارات Android الأقدم من 11.
