***This is a school project. Individual Project***
To compile Lexer.flex file:
java -jar jflex-1.6.1.jar Lexer.flex

To compile Parser.y file:
yacc -Jthrows="Exception" -Jextends=ParserImpl -Jnorun -J Parser.y

To compile all .java files:
javac *.java

Complied using Java 11
