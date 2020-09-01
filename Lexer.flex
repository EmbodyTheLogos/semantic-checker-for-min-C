/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Copyright (C) 2000 Gerwin Klein <lsf@jflex.de>                          *
 * All rights reserved.                                                    *
 *                                                                         *
 * Thanks to Larry Bell and Bob Jamison for suggestions and comments.      *
 *                                                                         *
 * License: BSD                                                            *
 *                                                                         *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

%%

%class Lexer
%byaccj
%column
%line

%{

  public Parser   parser;
  public int      lineno;
  public int      column;

  public Lexer(java.io.Reader r, Parser parser) {
    this(r);
    this.parser = parser;
    this.lineno = 1;
    this.column = 1;
  }

%}

num        = [0-9]+("."[0-9]+)?(E[+-]?[0-9]+)?
identifier = [a-zA-Z_][a-zA-Z0-9_]*
newline    = \n
whitespace = [ \t\r]+
comment    = "//".*
blkcomment = "/*"(.|\n)*"*/"

%%

"print"                             { column = yycolumn + 1; parser.yylval = new ParserVal(new Object[]{lineno, column, yytext()}); return Parser.PRINT      ;}
"bool"                              { column = yycolumn + 1; parser.yylval = new ParserVal(new Object[]{lineno, column, yytext()}); return Parser.BOOL       ;}
"num"                               { column = yycolumn + 1; parser.yylval = new ParserVal(new Object[]{lineno, column, yytext()}); return Parser.NUM        ;}
"while"                             { column = yycolumn + 1; parser.yylval = new ParserVal(new Object[]{lineno, column, yytext()}); return Parser.WHILE      ;}
"if"                                { column = yycolumn + 1; parser.yylval = new ParserVal(new Object[]{lineno, column, yytext()}); return Parser.IF         ;}
"else"                              { column = yycolumn + 1; parser.yylval = new ParserVal(new Object[]{lineno, column, yytext()}); return Parser.ELSE       ;}
"return"                            { column = yycolumn + 1; parser.yylval = new ParserVal(new Object[]{lineno, column, yytext()}); return Parser.RETURN     ;}
"{"                                 { column = yycolumn + 1; parser.yylval = new ParserVal(new Object[]{lineno, column, yytext()}); return Parser.LBRACE     ;}
"}"                                 { column = yycolumn + 1; parser.yylval = new ParserVal(new Object[]{lineno, column, yytext()}); return Parser.RBRACE     ;}
"("                                 { column = yycolumn + 1; parser.yylval = new ParserVal(new Object[]{lineno, column, yytext()}); return Parser.LPAREN     ;}
")"                                 { column = yycolumn + 1; parser.yylval = new ParserVal(new Object[]{lineno, column, yytext()}); return Parser.RPAREN     ;}
"="                                 { column = yycolumn + 1; parser.yylval = new ParserVal(new Object[]{lineno, column, yytext()}); return Parser.ASSIGN     ;}
";"                                 { column = yycolumn + 1; parser.yylval = new ParserVal(new Object[]{lineno, column, yytext()}); return Parser.SEMI       ;}
","                                 { column = yycolumn + 1; parser.yylval = new ParserVal(new Object[]{lineno, column, yytext()}); return Parser.COMMA      ;}
"+"                                 { column = yycolumn + 1; parser.yylval = new ParserVal(new Object[]{lineno, column, yytext()}); return Parser.OP_ADD     ;}
"-"                                 { column = yycolumn + 1; parser.yylval = new ParserVal(new Object[]{lineno, column, yytext()}); return Parser.OP_SUB     ;}
"*"                                 { column = yycolumn + 1; parser.yylval = new ParserVal(new Object[]{lineno, column, yytext()}); return Parser.OP_MUL     ;}
"/"                                 { column = yycolumn + 1; parser.yylval = new ParserVal(new Object[]{lineno, column, yytext()}); return Parser.OP_DIV     ;}
"%" 								{ column = yycolumn + 1; parser.yylval = new ParserVal(new Object[]{lineno, column, yytext()}); return Parser.OP_MOD     ;}
"and"                               { column = yycolumn + 1; parser.yylval = new ParserVal(new Object[]{lineno, column, yytext()}); return Parser.OP_AND     ;}
"or"                                { column = yycolumn + 1; parser.yylval = new ParserVal(new Object[]{lineno, column, yytext()}); return Parser.OP_OR      ;}
"not"                               { column = yycolumn + 1; parser.yylval = new ParserVal(new Object[]{lineno, column, yytext()}); return Parser.OP_NOT     ;}
"<"                                 { column = yycolumn + 1; parser.yylval = new ParserVal(new Object[]{lineno, column, yytext()}); return Parser.RELOP_LT   ;}
">"                                 { column = yycolumn + 1; parser.yylval = new ParserVal(new Object[]{lineno, column, yytext()}); return Parser.RELOP_GT   ;}
"<="                                { column = yycolumn + 1; parser.yylval = new ParserVal(new Object[]{lineno, column, yytext()}); return Parser.RELOP_LE   ;}
">="                                { column = yycolumn + 1; parser.yylval = new ParserVal(new Object[]{lineno, column, yytext()}); return Parser.RELOP_GE   ;}
"=="                                { column = yycolumn + 1; parser.yylval = new ParserVal(new Object[]{lineno, column, yytext()}); return Parser.RELOP_EQ   ;}
"!="                                { column = yycolumn + 1; parser.yylval = new ParserVal(new Object[]{lineno, column, yytext()}); return Parser.RELOP_NE   ;}
"true"|"false"                      { column = yycolumn + 1; parser.yylval = new ParserVal(new Object[]{lineno, column, yytext()}); return Parser.BOOL_LIT   ; }
{num}                               { column = yycolumn + 1; parser.yylval = new ParserVal(new Object[]{lineno, column, yytext()}); return Parser.NUM_LIT    ; }
{identifier}                        { column = yycolumn + 1; parser.yylval = new ParserVal(new Object[]{lineno, column, yytext()}); return Parser.IDENT      ; }
{comment}                           { /* skip */ }
{whitespace}                        { /* skip */ }
{newline}                           { column = yycolumn; lineno++; /* skip */ }
{blkcomment} 		                { /* skip */
                                        for (int i = 0; i < yytext().length(); i++) {
                                            if (yytext().charAt(i) == '\n') {
                                                lineno++;
                                            }
										}
									}


\b     { System.err.println("Sorry, backspace doesn't work"); }

/* error fallback */
[^]    { System.err.println("Error: unexpected character '"+yytext()+"'"); return -1; }
