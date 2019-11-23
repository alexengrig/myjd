./clean.sh
find ../src -name "*.java" > source.txt
javac -g -d "../build" -cp "$JDK_18/lib/tools.jar;" @source.txt
rm source.txt