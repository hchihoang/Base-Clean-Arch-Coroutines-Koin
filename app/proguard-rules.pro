# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
-printmapping mapping.txt
-verbose
-dontoptimize
-dontpreverify
-dontshrink
-dontskipnonpubliclibraryclassmembers
-dontusemixedcaseclassnames
-keepparameternames
-renamesourcefileattribute SourceFile
-keepattributes *Annotation*
-keepattributes Exceptions,InnerClasses,Signature,Deprecated,SourceFile,LineNumberTable,*Annotation*,EnclosingMethod


-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** v(...);
}

-keep class *.R

-keepclasseswithmembers class **.R$* {
    public static <fields>;
}

-keep class retrofit2.** { *; }
-keep class okhttp3.** { *; }
-keep class jp.wasabeef.glide.** { *; }
-keep class com.google.** { *; }

-dontwarn retrofit2.**
-dontwarn okhttp3.**
-dontwarn jp.wasabeef.glide.**

-dontwarn coil.Coil
-dontwarn coil.ImageLoader
-dontwarn coil.request.LoadRequest$Companion
-dontwarn coil.request.LoadRequest
-dontwarn coil.request.LoadRequestBuilder
-dontwarn coil.request.RequestBuilder
-dontwarn coil.request.RequestDisposable
-dontwarn coil.size.Scale