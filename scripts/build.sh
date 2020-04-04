./clean.sh
find ../src -name "*.java" > source.txt
mkdir -p ../build
"$JDK_18/bin/javac" -g -d "../build" -cp "../lib/tools-1.8.0_241.jar;" @source.txt
rm source.txt