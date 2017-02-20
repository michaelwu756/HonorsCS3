javac -sourcepath ./ *.java
jar -cvfm MissileCommand.jar MANIFEST.MF *.class engine\*.class ui\*.class members\*.class