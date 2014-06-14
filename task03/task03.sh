JAXB_HOME='./jaxb'

#$JAXB_HOME/bin/xjc.sh -d src cards.xsd

javac -d bin -cp $JAXB_HOME/lib/jaxb-api.jar:$JAXB_HOME/lib/jaxb-core.jar src/by/epam/cards/*.java
javac -d bin -cp bin src/by/epam/task/*.java

java -cp bin by.epam.task.Main

