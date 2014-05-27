
javac -d bin src/by/epam/gameroom/toy/characteristic/*.java
javac -d bin -cp bin src/by/epam/gameroom/toy/*.java
javac -d bin -cp bin src/by/epam/gameroom/toy/comparator/*.java
javac -d bin -cp bin src/by/epam/gameroom/creator/*.java
javac -d bin -cp bin src/by/epam/gameroom/toysroom/*.java
javac -d bin -cp bin src/by/epam/gameroom/main/*.java

java -cp bin by.epam.gameroom.main.Main
