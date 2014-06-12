#!/bin/bash

javac -d bin src/by/epam/regextest/text/*.java
javac -d bin -cp bin:./lib/log4j-api-2.0-rc1.jar:./lib/og4j-core-2.0-rc1.jar src/by/epam/regextest/parser/*.java
javac -d bin -cp bin src/by/epam/regextest/comparator/*.java
javac -d bin -cp bin:./lib/log4j-api-2.0-rc1.jar:./lib/og4j-core-2.0-rc1.jar src/by/epam/regextest/task/*.java

java -cp ./lib/log4j-api-2.0-rc1.jar:./lib/og4j-core-2.0-rc1.jar:./bin by.epam.regextest.task.Main

exit 0
