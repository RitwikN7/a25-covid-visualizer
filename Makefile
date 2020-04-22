.PHONY = make jar runjar test clean

# replace with path to your javac,java,jar,javafx installations
JC = /Library/Java/JavaVirtualMachines/adoptopenjdk-11.jdk/Contents/Home/bin/javac     # replace with path to javac or javac.exe
JAR = /Library/Java/JavaVirtualMachines/adoptopenjdk-11.jdk/Contents/Home/bin/jar      # replace with path to jar or jar.exe
JAVA = /Library/Java/JavaVirtualMachines/adoptopenjdk-11.jdk/Contents/Home/bin/java    # replace with path to java or javaw.exe
MP = --module-path "/Users/drewhalverson/OneDrive - UW-Madison/Github/a25-covid-visualizer/javafx-sdk-11.0.2/lib" --add-modules javafx.controls,javafx.fxml #-Dfile.encoding=UTF-8 
CP = -classpath ".:application" 
APP = application.Main_GUI

#CLASSPATH = .:junit-platform-console-standalone-1.5.2.jar:json-simple-1.1.1.jar

make: 
	$(JC) $(MP) $(CP) -d . application/*.java

run:
	$(JAVA) $(MP) $(CP) application.Main

fx: 
	$(JC) $(MP) $(CP) -d . application/*.java

fxrun:
	$(JAVA) $(MP) $(CP) $(APP)

jar: 
	$(JAR) cvmf manifest.txt executable.jar .

runjar:
	java $(MP) -jar executable.jar

zip:
	zip team.zip application/* *

test: 
	javac $(MP) -cp $(CLASSPATH) *.java
	java -jar junit-platform-console-standalone-1.5.2.jar --class-path $(CLASSPATH) -p ""

clean:
	\rm application/*.class
	\rm executable.jar
