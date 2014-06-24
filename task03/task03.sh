
javac -d bin -cp bin:lib/log4j-1.2.17.jar src/by/epam/multithreading/entity/*.java
javac -d bin -cp bin:lib/log4j-1.2.17.jar src/by/epam/multithreading/task/*.java

java -cp bin:lib/log4j-1.2.17.jar by.epam.multithreading.task.Main

