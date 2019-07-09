# Partially From https://github.com/ACRA/acra/blob/master/acra-core/proguard.cfg
# Under Apache 2.0 (?)

#ACRA specifics
# Restore some Source file names and restore approximate line numbers in the stack traces,
# otherwise the stack traces are pretty useless
-keepattributes SourceFile,LineNumberTable

# ACRA needs "annotations" so add this...
# Note: This may already be defined in the default "proguard-android-optimize.txt"
# file in the SDK. If it is, then you don't need to duplicate it. See your
# "project.properties" file to get the path to the default "proguard-android-optimize.txt".
-keepattributes *Annotation*

# ACRA loads Plugins using reflection, so we need to keep all Plugin classes
-keep class * implements org.acra.plugins.Plugin {*;}

# ACRA uses enum fields in annotations, so we have to keep those
-keep enum org.acra.** {*;}

-keep class org.acra.collector.** {*;}
-keep class org.acra.config.** {*;}
-keep class org.acra.interaction.ToastInteraction {*;}

-keep class ryey.easer.ErrorSenderFactory { *; }
-keep class ryey.easer.ErrorSender { *; }