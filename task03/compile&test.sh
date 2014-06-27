
javac -d bin -cp lib/junit-4.11.jar test/by/epam/multithreading/entity/StopTest.java

java -cp lib/junit-4.11.jar:lib/hamcrest-core-1.3.jar:bin org.junit.runner.JUnitCore by.epam.multithreading.entity.StopTest
