-dontwarn jakarta.**
-dontwarn org.graalvm.nativeimage.**
-dontwarn okhttp3.**
-dontwarn ch.qos.logback.classic.servlet.**
-dontwarn ch.qos.logback.core.net.**
-dontwarn org.codehaus.janino.**
-dontwarn org.codehaus.commons.compiler.**
-dontwarn org.tukaani.xz.**

# Keep Ktor serialization providers
#-keep class io.ktor.serialization.** { *; }
#-keep class io.ktor.serialization.kotlinx.** { *; }
#-keep class io.ktor.serialization.kotlinx.json.** { *; }

# Keep META-INF/services files
-keepdirectories META-INF/services/

# Prevent ProGuard from stripping ServiceLoader classes
#-keepclassmembers class ** {
#    @java.util.ServiceLoader$Provider *;
#}
