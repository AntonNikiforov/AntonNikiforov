#!/bin/bash

javac -d bin src/by/epam/regextest/text/*.java
javac -d bin -cp bin src/by/epam/regextest/parser/*.java
javac -d bin -cp bin src/by/epam/regextest/comparator/*.java
javac -d bin -cp bin src/by/epam/regextest/task/*.java

java -cp bin by.epam.regextest.task.Main

exit 0
