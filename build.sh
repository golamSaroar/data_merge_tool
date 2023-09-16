rm -rf build
mkdir -p build/main
mkdir -p build/test

# compile production code
javac -source 1.8 -target 1.8 -sourcepath src/main/java:src/main/resources -cp ".:lib/*" -d build/main src/main/java/com/veeva/core/RecordMerger.java

# compile test code
javac -source 1.8 -target 1.8 -sourcepath src/main/java:src/test/java:src/main/resources -cp ".:lib/*:build/main" -d build/test src/test/java/com/veeva/**/*.java

cp -r src/main/resources/* build/main

rm -f cantest.jar
jar cvf cantest.jar -C build/main .

rm -f veeva_solution.zip
find . -type f -not \( -path "./.idea/*" -o -path "./.git/*" \) -exec jar cvfM veeva_solution.zip {} +

