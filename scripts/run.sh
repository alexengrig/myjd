./build.sh
cd ../build || exit #TODO: Add to classpath
"$JDK_18/bin/java" -cp "../lib/tools-1.8.0_241.jar;." dev/alexengrig/myjdi/DebugRunner --className dev.alexengrig.myjd.ExampleDebuggee
cd ../scripts || exit #TODO: Workaround to remove build
