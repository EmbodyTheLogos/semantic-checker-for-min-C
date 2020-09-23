***This is a school project. Individual Project***
To compile Lexer.flex file:
java -jar jflex-1.6.1.jar Lexer.flex

To compile Parser.y file:
yacc -Jthrows="Exception" -Jextends=ParserImpl -Jnorun -J Parser.y

To compile all .java files (complied using Java 11):
javac *.java

To run program on provided test cases:
java SemanticChecker ../minc/test_01_main_fail1.minc      >   ../test_output/test_output_01_main_fail1.txt
java SemanticChecker ../minc/test_01_main_fail2.minc      >   ../test_output/test_output_01_main_fail2.txt
java SemanticChecker ../minc/test_01_main_succ.minc       >   ../test_output/test_output_01_main_succ.txt
java SemanticChecker ../minc/test_02_expr1_fail1.minc     >   ../test_output/test_output_02_expr1_fail1.txt
java SemanticChecker ../minc/test_02_expr1_fail2.minc     >   ../test_output/test_output_02_expr1_fail2.txt
java SemanticChecker ../minc/test_02_expr1_fail3.minc     >   ../test_output/test_output_02_expr1_fail3.txt
java SemanticChecker ../minc/test_02_expr1_fail4.minc     >   ../test_output/test_output_02_expr1_fail4.txt
java SemanticChecker ../minc/test_02_expr1_fail5.minc     >   ../test_output/test_output_02_expr1_fail5.txt
java SemanticChecker ../minc/test_02_expr1_fail6.minc     >   ../test_output/test_output_02_expr1_fail6.txt
java SemanticChecker ../minc/test_02_expr1_succ.minc      >   ../test_output/test_output_02_expr1_succ.txt
java SemanticChecker ../minc/test_03_expr2_fail1.minc     >   ../test_output/test_output_03_expr2_fail1.txt
java SemanticChecker ../minc/test_03_expr2_fail2.minc     >   ../test_output/test_output_03_expr2_fail2.txt
java SemanticChecker ../minc/test_03_expr2_fail3.minc     >   ../test_output/test_output_03_expr2_fail3.txt
java SemanticChecker ../minc/test_03_expr2_fail4.minc     >   ../test_output/test_output_03_expr2_fail4.txt
java SemanticChecker ../minc/test_03_expr2_succ.minc      >   ../test_output/test_output_03_expr2_succ.txt
java SemanticChecker ../minc/test_04_stmt_fail1.minc      >   ../test_output/test_output_04_stmt_fail1.txt
java SemanticChecker ../minc/test_04_stmt_fail2.minc      >   ../test_output/test_output_04_stmt_fail2.txt
java SemanticChecker ../minc/test_04_stmt_fail3.minc      >   ../test_output/test_output_04_stmt_fail3.txt
java SemanticChecker ../minc/test_04_stmt_fail4.minc      >   ../test_output/test_output_04_stmt_fail4.txt
java SemanticChecker ../minc/test_04_stmt_succ.minc       >   ../test_output/test_output_04_stmt_succ.txt
java SemanticChecker ../minc/test_05_func1_fail1.minc     >   ../test_output/test_output_05_func1_fail1.txt
java SemanticChecker ../minc/test_05_func1_succ.minc      >   ../test_output/test_output_05_func1_succ.txt
java SemanticChecker ../minc/test_06_func2_fail1.minc     >   ../test_output/test_output_06_func2_fail1.txt
java SemanticChecker ../minc/test_06_func2_fail2.minc     >   ../test_output/test_output_06_func2_fail2.txt
java SemanticChecker ../minc/test_06_func2_fail3.minc     >   ../test_output/test_output_06_func2_fail3.txt
java SemanticChecker ../minc/test_06_func2_fail4.minc     >   ../test_output/test_output_06_func2_fail4.txt
java SemanticChecker ../minc/test_06_func2_fail5.minc     >   ../test_output/test_output_06_func2_fail5.txt
java SemanticChecker ../minc/test_06_func2_succ.minc      >   ../test_output/test_output_06_func2_succ.txt
java SemanticChecker ../minc/test_07_func3_fail1.minc     >   ../test_output/test_output_07_func3_fail1.txt
java SemanticChecker ../minc/test_07_func3_succ.minc      >   ../test_output/test_output_07_func3_succ.txt
java SemanticChecker ../minc/test_08_advanced1_succ.minc  >   ../test_output/test_output_08_advanced1_succ.txt
java SemanticChecker ../minc/test_08_advanced2_succ.minc  >   ../test_output/test_output_08_advanced2_succ.txt
java SemanticChecker ../minc/test_09_advanced3_fail1.minc >   ../test_output/test_output_09_advanced3_fail1.txt
java SemanticChecker ../minc/test_09_advanced3_succ.minc  >   ../test_output/test_output_09_advanced3_succ.txt
java SemanticChecker ../minc/test_10_scope1_fail1.minc    >   ../test_output/test_output_10_scope1_fail1.txt
java SemanticChecker ../minc/test_10_scope1_fail2.minc    >   ../test_output/test_output_10_scope1_fail2.txt
java SemanticChecker ../minc/test_10_scope1_fail3.minc    >   ../test_output/test_output_10_scope1_fail3.txt
java SemanticChecker ../minc/test_10_scope1_succ.minc     >   ../test_output/test_output_10_scope1_succ.txt


