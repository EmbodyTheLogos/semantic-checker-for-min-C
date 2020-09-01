//### This file created by BYACC 1.8(/Java extension  1.15)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 15 "Parser.y"
import java.io.*;
//#line 19 "Parser.java"




public class Parser
             extends ParserImpl
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//public class ParserVal is defined in ParserVal.java


String   yytext;//user variable to return contextual strings
ParserVal yyval; //used to return semantic vals from action routines
ParserVal yylval;//the 'lval' (result) I got from yylex()
ParserVal valstk[];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
void val_init()
{
  valstk=new ParserVal[YYSTACKSIZE];
  yyval=new ParserVal();
  yylval=new ParserVal();
  valptr=-1;
}
void val_push(ParserVal val)
{
  if (valptr>=YYSTACKSIZE)
    return;
  valstk[++valptr]=val;
}
ParserVal val_pop()
{
  if (valptr<0)
    return new ParserVal();
  return valstk[valptr--];
}
void val_drop(int cnt)
{
int ptr;
  ptr=valptr-cnt;
  if (ptr<0)
    return;
  valptr = ptr;
}
ParserVal val_peek(int relative)
{
int ptr;
  ptr=valptr-relative;
  if (ptr<0)
    return new ParserVal();
  return valstk[ptr];
}
final ParserVal dup_yyval(ParserVal val)
{
  ParserVal dup = new ParserVal();
  dup.ival = val.ival;
  dup.dval = val.dval;
  dup.sval = val.sval;
  dup.obj = val.obj;
  return dup;
}
//#### end semantic value section ####
public final static short ASSIGN=257;
public final static short OP_OR=258;
public final static short OP_AND=259;
public final static short RELOP_EQ=260;
public final static short RELOP_NE=261;
public final static short RELOP_LE=262;
public final static short RELOP_LT=263;
public final static short RELOP_GE=264;
public final static short RELOP_GT=265;
public final static short OP_ADD=266;
public final static short OP_SUB=267;
public final static short OP_MUL=268;
public final static short OP_DIV=269;
public final static short OP_MOD=270;
public final static short OP_NOT=271;
public final static short IDENT=272;
public final static short NUM_LIT=273;
public final static short BOOL_LIT=274;
public final static short BOOL=275;
public final static short NUM=276;
public final static short IF=277;
public final static short ELSE=278;
public final static short PRINT=279;
public final static short WHILE=280;
public final static short RETURN=281;
public final static short LPAREN=282;
public final static short RPAREN=283;
public final static short LBRACE=284;
public final static short RBRACE=285;
public final static short SEMI=286;
public final static short COMMA=287;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    2,    5,    5,   21,    3,    6,    6,
    7,    7,    8,   10,   10,   11,   11,   11,   11,   11,
   11,   11,   13,   15,   22,   16,    4,    4,   17,   18,
   12,   19,   20,   20,    9,    9,   14,   14,   14,   14,
   14,   14,   14,   14,   14,   14,   14,   14,   14,   14,
   14,   14,   14,   14,
};
final static short yylen[] = {                            2,
    1,    2,    0,    1,    1,    1,    0,   10,    1,    0,
    3,    1,    2,    2,    0,    2,    1,    1,    1,    2,
    2,    1,    3,    5,    0,    5,    2,    0,    3,    7,
    2,    2,    3,    1,    1,    0,    3,    3,    3,    3,
    3,    2,    3,    3,    3,    3,    3,    3,    3,    3,
    1,    4,    1,    1,
};
final static short yydefred[] = {                         3,
    0,    0,    5,    6,    2,    4,    0,    0,    0,    0,
    0,    0,   12,   13,    0,    0,    7,   11,   28,    0,
    0,    0,   27,    0,    0,    0,    0,    0,    0,   25,
    8,   22,   14,    0,    0,   19,   17,   18,    0,   29,
    0,    0,    0,    0,   54,   53,    0,    0,    0,    0,
   28,   21,   16,   20,    0,    0,   42,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   50,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   39,   40,
    0,    0,    0,   52,    0,   24,   26,    0,    0,   30,
};
final static short yydgoto[] = {                          1,
    2,    5,    6,   20,   10,   11,   12,   13,   75,   22,
   33,   34,   35,   48,   36,   37,   23,   38,   39,   77,
   19,   51,
};
final static short yysindex[] = {                         0,
    0, -201,    0,    0,    0,    0, -270, -278, -201, -255,
 -260, -262,    0,    0, -253, -201,    0,    0,    0, -201,
 -243,  -90,    0, -220, -189, -212, -266, -204, -266,    0,
    0,    0,    0, -203, -181,    0,    0,    0, -180,    0,
 -266, -266, -266, -200,    0,    0, -266,   28, -266,   28,
    0,    0,    0,    0,   28,  -51,    0, -266,  -39, -266,
 -266, -266, -266, -266, -266, -266, -266, -266, -266, -266,
 -266,  -13, -201, -175, -173,   28, -174,    0,  -26,   38,
   46,   46, -257, -257, -257, -257, -192, -192,    0,    0,
 -175,  -12, -166,    0, -266,    0,    0, -175,   28,    0,
};
final static short yyrindex[] = {                         0,
    0,  116,    0,    0,    0,    0,    0,    0, -165,    0,
    0, -164,    0,    0,    0,    0,    0,    0,    0,   -1,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0, -214,    0,    0,    0, -169,    0, -163,
    0,    0,    0,    0, -136,    0,    0, -161,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   -1,    0,    0, -261, -131,    0, -102, -179,
  -86,  -81, -162, -132, -124, -116, -202, -172,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0, -259,    0,
};
final static short yygindex[] = {                         0,
    0,    0,    0,   69,   -2,    0,    0,  137,    0,   84,
    0,    0,    0,  -28,    0,  -71,    0,    0,    0,    0,
    0,    0,
};
final static int YYTABLESIZE=315;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                          7,
   50,    8,   93,    9,   43,   44,   45,   46,   68,   69,
   70,   71,   55,   56,   57,   47,   14,   21,   59,   96,
   72,   34,   15,   33,   16,   34,  100,   33,   24,   76,
   17,   79,   80,   81,   82,   83,   84,   85,   86,   87,
   88,   89,   90,   51,   51,   51,   51,   51,   51,   51,
   51,   51,   51,   51,   51,   49,   49,   49,   49,   49,
   49,   49,   49,   49,   49,   40,   99,   41,   51,   42,
   21,   51,   51,    3,    4,   70,   71,   49,   48,   48,
   49,   58,   52,   49,   49,   38,   38,   38,   38,   38,
   38,   38,   38,   38,   38,   44,   44,   44,   44,   44,
   44,   44,   44,   48,   53,   54,   48,   48,   30,   94,
   38,   98,   95,   38,   38,    1,   32,   10,    9,   73,
   44,   36,   31,   44,   44,   45,   45,   45,   45,   45,
   45,   45,   45,   46,   46,   46,   46,   46,   46,   46,
   46,   47,   47,   47,   47,   47,   47,   47,   47,   23,
   45,   35,   18,   45,   45,   41,   92,    0,   46,    0,
    0,   46,   46,    0,    0,    0,   47,    0,    0,   47,
   47,   37,   37,   37,   37,    0,   43,   43,   43,   43,
   41,   25,    0,   41,   41,    0,   26,    0,   27,   28,
   29,    0,    0,   30,   31,   32,   37,    0,    0,   37,
   37,   43,    0,    0,   43,   43,   60,   61,   62,   63,
   64,   65,   66,   67,   68,   69,   70,   71,   60,   61,
   62,   63,   64,   65,   66,   67,   68,   69,   70,   71,
    0,   74,   61,   62,   63,   64,   65,   66,   67,   68,
   69,   70,   71,   78,   60,   61,   62,   63,   64,   65,
   66,   67,   68,   69,   70,   71,    0,    0,    0,   25,
    0,    0,    0,    0,   26,    0,   27,   28,   29,   91,
   15,   30,   97,   32,    0,   15,    0,   15,   15,   15,
    0,    0,   15,   15,   15,   60,   61,   62,   63,   64,
   65,   66,   67,   68,   69,   70,   71,   62,   63,   64,
   65,   66,   67,   68,   69,   70,   71,   64,   65,   66,
   67,   68,   69,   70,   71,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          2,
   29,  272,   74,  282,  271,  272,  273,  274,  266,  267,
  268,  269,   41,   42,   43,  282,  272,   20,   47,   91,
   49,  283,  283,  283,  287,  287,   98,  287,  272,   58,
  284,   60,   61,   62,   63,   64,   65,   66,   67,   68,
   69,   70,   71,  258,  259,  260,  261,  262,  263,  264,
  265,  266,  267,  268,  269,  258,  259,  260,  261,  262,
  263,  264,  265,  266,  267,  286,   95,  257,  283,  282,
   73,  286,  287,  275,  276,  268,  269,  282,  258,  259,
  283,  282,  286,  286,  287,  258,  259,  260,  261,  262,
  263,  264,  265,  266,  267,  258,  259,  260,  261,  262,
  263,  264,  265,  283,  286,  286,  286,  287,  284,  283,
  283,  278,  287,  286,  287,    0,  286,  283,  283,   51,
  283,  283,  286,  286,  287,  258,  259,  260,  261,  262,
  263,  264,  265,  258,  259,  260,  261,  262,  263,  264,
  265,  258,  259,  260,  261,  262,  263,  264,  265,  286,
  283,  283,   16,  286,  287,  258,   73,   -1,  283,   -1,
   -1,  286,  287,   -1,   -1,   -1,  283,   -1,   -1,  286,
  287,  258,  259,  260,  261,   -1,  258,  259,  260,  261,
  283,  272,   -1,  286,  287,   -1,  277,   -1,  279,  280,
  281,   -1,   -1,  284,  285,  286,  283,   -1,   -1,  286,
  287,  283,   -1,   -1,  286,  287,  258,  259,  260,  261,
  262,  263,  264,  265,  266,  267,  268,  269,  258,  259,
  260,  261,  262,  263,  264,  265,  266,  267,  268,  269,
   -1,  283,  259,  260,  261,  262,  263,  264,  265,  266,
  267,  268,  269,  283,  258,  259,  260,  261,  262,  263,
  264,  265,  266,  267,  268,  269,   -1,   -1,   -1,  272,
   -1,   -1,   -1,   -1,  277,   -1,  279,  280,  281,  283,
  272,  284,  285,  286,   -1,  277,   -1,  279,  280,  281,
   -1,   -1,  284,  285,  286,  258,  259,  260,  261,  262,
  263,  264,  265,  266,  267,  268,  269,  260,  261,  262,
  263,  264,  265,  266,  267,  268,  269,  262,  263,  264,
  265,  266,  267,  268,  269,
};
}
final static short YYFINAL=1;
final static short YYMAXTOKEN=287;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,"ASSIGN","OP_OR","OP_AND","RELOP_EQ","RELOP_NE","RELOP_LE",
"RELOP_LT","RELOP_GE","RELOP_GT","OP_ADD","OP_SUB","OP_MUL","OP_DIV","OP_MOD",
"OP_NOT","IDENT","NUM_LIT","BOOL_LIT","BOOL","NUM","IF","ELSE","PRINT","WHILE",
"RETURN","LPAREN","RPAREN","LBRACE","RBRACE","SEMI","COMMA",
};
final static String yyrule[] = {
"$accept : program",
"program : decl_list",
"decl_list : decl_list decl",
"decl_list :",
"decl : fun_decl",
"type_spec : BOOL",
"type_spec : NUM",
"$$1 :",
"fun_decl : type_spec IDENT LPAREN params RPAREN LBRACE $$1 local_decls stmt_list RBRACE",
"params : param_list",
"params :",
"param_list : param_list COMMA param",
"param_list : param",
"param : type_spec IDENT",
"stmt_list : stmt_list stmt",
"stmt_list :",
"stmt : expr_stmt SEMI",
"stmt : compound_stmt",
"stmt : if_stmt",
"stmt : while_stmt",
"stmt : print_stmt SEMI",
"stmt : return_stmt SEMI",
"stmt : SEMI",
"expr_stmt : IDENT ASSIGN expr",
"while_stmt : WHILE LPAREN expr RPAREN compound_stmt",
"$$2 :",
"compound_stmt : LBRACE $$2 local_decls stmt_list RBRACE",
"local_decls : local_decls local_decl",
"local_decls :",
"local_decl : type_spec IDENT SEMI",
"if_stmt : IF LPAREN expr RPAREN compound_stmt ELSE compound_stmt",
"return_stmt : RETURN expr",
"print_stmt : PRINT expr",
"arg_list : arg_list COMMA expr",
"arg_list : expr",
"args : arg_list",
"args :",
"expr : expr RELOP_EQ expr",
"expr : expr OP_SUB expr",
"expr : expr OP_MUL expr",
"expr : expr OP_DIV expr",
"expr : expr OP_OR expr",
"expr : OP_NOT expr",
"expr : expr RELOP_NE expr",
"expr : expr RELOP_LE expr",
"expr : expr RELOP_LT expr",
"expr : expr RELOP_GE expr",
"expr : expr RELOP_GT expr",
"expr : expr OP_AND expr",
"expr : expr OP_ADD expr",
"expr : LPAREN expr RPAREN",
"expr : IDENT",
"expr : IDENT LPAREN args RPAREN",
"expr : BOOL_LIT",
"expr : NUM_LIT",
};

//#line 162 "Parser.y"
    private Lexer lexer;

    private int yylex () {
        int yyl_return = -1;
        try {
            yylval = new ParserVal(0);
            yyl_return = lexer.yylex();
        }
        catch (IOException e) {
            System.out.println("IO error :"+e);
        }
        return yyl_return;
    }

    public void yyerror (String error) {
        System.out.println ("Error at line " + lexer.lineno + ": " + error);
    }

    public Parser(Reader r) {
        lexer = new Lexer(r, this);
    }
//#line 363 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
throws Exception
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    yyval = dup_yyval(yyval); //duplicate yyval if ParserVal is used as semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 1:
//#line 49 "Parser.y"
{ Debug("program  ->  decl_list"                    ); yyval.obj = program____decllist(val_peek(0).obj); }
break;
case 2:
//#line 52 "Parser.y"
{ Debug("decl_list  ->  decl_list  decl"            ); yyval.obj = decllist____decllist_decl(val_peek(1).obj, val_peek(0).obj); }
break;
case 3:
//#line 53 "Parser.y"
{ Debug("decl_list  ->  eps"                        ); yyval.obj = decllist____eps          (      ); }
break;
case 4:
//#line 56 "Parser.y"
{ Debug("decl  ->  fun_decl"                        ); yyval.obj = decl____fundecl(val_peek(0).obj); }
break;
case 5:
//#line 60 "Parser.y"
{ Debug("type_spec  ->  BOOL"                       ); yyval.obj = typespec____BOOL(); }
break;
case 6:
//#line 61 "Parser.y"
{ Debug("type_spec  ->  NUM"                        ); yyval.obj = typespec____NUM (); }
break;
case 7:
//#line 64 "Parser.y"
{ Debug("fun_decl  ->  type_spec IDENT ( params ) {"); yyval.obj = fundecl____LBRACE(val_peek(5).obj, val_peek(4).obj, val_peek(2).obj ); }
break;
case 8:
//#line 65 "Parser.y"
{ Debug("                   local_decls stmt_list }"); yyval.obj      = fundecl____RBRACE(val_peek(9).obj, val_peek(8).obj, val_peek(6).obj, val_peek(2).obj, val_peek(1).obj, val_peek(0).obj); }
break;
case 9:
//#line 68 "Parser.y"
{ Debug("params -> param_list");    yyval.obj = params____param_list(val_peek(0).obj);}
break;
case 10:
//#line 69 "Parser.y"
{ Debug("params  ->  eps"                           ); yyval.obj = params____eps(); }
break;
case 11:
//#line 72 "Parser.y"
{ Debug("param_list -> param_list  COMMA  param"); yyval.obj = param_list____param_list_COMMA_param(val_peek(2).obj,val_peek(1).obj,val_peek(0).obj);}
break;
case 12:
//#line 73 "Parser.y"
{ Debug("param_list -> param");          yyval.obj = param_list____param(val_peek(0).obj);}
break;
case 13:
//#line 77 "Parser.y"
{ Debug("param -> type_spec_IDENT"); yyval.obj = param____type_spec_IDENT(val_peek(1).obj, val_peek(0).obj);}
break;
case 14:
//#line 80 "Parser.y"
{ Debug("stmt_list  ->  stmt_list  stmt"            ); yyval.obj = stmtlist____stmtlist_stmt(val_peek(1).obj, val_peek(0).obj); }
break;
case 15:
//#line 81 "Parser.y"
{ Debug("stmt_list  ->  eps"                        ); yyval.obj = stmtlist____eps          (      ); }
break;
case 16:
//#line 85 "Parser.y"
{ Debug("stmt  ->  expr_stmt "                     ); yyval.obj = stmt____exprstmt_SEMI  (val_peek(1).obj); }
break;
case 17:
//#line 86 "Parser.y"
{ Debug("stmt  ->  compound_stmt ");    yyval.obj = stmt____compound_stmt(val_peek(0).obj);}
break;
case 18:
//#line 87 "Parser.y"
{ Debug("stmt  ->  if_stmt ");          yyval.obj = stmt____if_stmt(val_peek(0).obj);}
break;
case 19:
//#line 88 "Parser.y"
{ Debug("stmt  ->  while_stmt ");       yyval.obj = stmt____while_stmt(val_peek(0).obj);}
break;
case 20:
//#line 89 "Parser.y"
{ Debug("stmt  ->  print_stmt ");       yyval.obj = stmt____print_stmt_SEMI(val_peek(1).obj);}
break;
case 21:
//#line 90 "Parser.y"
{ Debug("stmt  ->  return_stmt "          ); yyval.obj = stmt____returnstmt_SEMI(val_peek(1).obj); }
break;
case 22:
//#line 91 "Parser.y"
{ Debug("stmt  -> ;"                                ); yyval.obj = stmt____SEMI           (  ); }
break;
case 23:
//#line 94 "Parser.y"
{ Debug("expr_stmt  ->  IDENT  ASSIGN  expr"        ); 
																  yyval.obj = exprstmt____IDENT_ASSIGN_expr(val_peek(2).obj, val_peek(1).obj, val_peek(0).obj); }
break;
case 24:
//#line 98 "Parser.y"
{ Debug("while_stmt -> WHILE  LPAREN  expr  RPAREN  compound_stmt"); 
																	yyval.obj = while_statement(val_peek(2).obj,val_peek(0).obj);}
break;
case 25:
//#line 102 "Parser.y"
{ Debug("compound_stmt -> LBRACE"); yyval.obj = compound_stmt____LBRACE(val_peek(0).obj); }
break;
case 26:
//#line 103 "Parser.y"
{ Debug("compound_stmt -> RBRACE"); yyval.obj = compound_stmt____RBRACE(val_peek(2).obj, val_peek(1).obj);}
break;
case 27:
//#line 106 "Parser.y"
{ Debug("local_decls  ->  local_decls  local_decl"  ); yyval.obj = local_decls____local_decls_local_decl(val_peek(1).obj,val_peek(0).obj);}
break;
case 28:
//#line 107 "Parser.y"
{ Debug("local_decls  ->  eps"                      ); yyval.obj = localdecls____eps(); }
break;
case 29:
//#line 111 "Parser.y"
{ Debug("local_decl -> type_spec IDENT SEMI"		); yyval.obj = localdecl____type_spec_IDENT_SEMI(val_peek(2).obj, val_peek(1).obj); }
break;
case 30:
//#line 115 "Parser.y"
{ Debug("if_stmt -> IF  LPAREN  expr  RPAREN  compound_stmt  ELSE  compound_stmt");
																						yyval.obj = if_statement(val_peek(4).obj, val_peek(2).obj, val_peek(0).obj);}
break;
case 31:
//#line 120 "Parser.y"
{ Debug("return_stmt  ->  RETURN  expr"             ); yyval.obj = returnstmt____RETURN_expr(val_peek(0).obj); }
break;
case 32:
//#line 124 "Parser.y"
{ Debug("print_stmt  ->  PRINT  expr"				); yyval.obj = print_stmt____PRINT_expr(val_peek(0).obj); }
break;
case 33:
//#line 128 "Parser.y"
{ Debug("arg_list  ->  arg_list COMMA expr" ); 				yyval.obj = arg_list____arg_list_COMMA_expr(val_peek(2).obj,val_peek(1).obj,val_peek(0).obj);}
break;
case 34:
//#line 129 "Parser.y"
{ Debug("arg_list  ->  expr" ); yyval.obj = args_list____expr(val_peek(0).obj);}
break;
case 35:
//#line 133 "Parser.y"
{ Debug("args  ->  eps"); yyval.obj = args____arg_list(val_peek(0).obj);}
break;
case 36:
//#line 134 "Parser.y"
{ Debug("args  ->  eps"                             ); yyval.obj = args____eps(); }
break;
case 37:
//#line 140 "Parser.y"
{ Debug("expr  ->  expr == expr"                    ); yyval.obj = expr____expr_RELOPEQ_expr       (val_peek(2).obj, val_peek(1).obj, val_peek(0).obj); }
break;
case 38:
//#line 141 "Parser.y"
{ Debug("expr  ->  expr - expr"                     ); yyval.obj = expr____expr_OPSUB_expr         (val_peek(2).obj, val_peek(1).obj, val_peek(0).obj); }
break;
case 39:
//#line 142 "Parser.y"
{ Debug("expr  ->  expr * expr"                     ); yyval.obj = expr____expr_OPMUL_expr         (val_peek(2).obj, val_peek(1).obj, val_peek(0).obj); }
break;
case 40:
//#line 143 "Parser.y"
{ Debug("expr  ->  expr / expr"                     ); yyval.obj = expr____expr_OPDIV_expr         (val_peek(2).obj, val_peek(1).obj, val_peek(0).obj); }
break;
case 41:
//#line 144 "Parser.y"
{ Debug("expr  ->  expr OR expr"                     ); yyval.obj = expr____expr_OPOR_expr         (val_peek(2).obj, val_peek(1).obj, val_peek(0).obj); }
break;
case 42:
//#line 145 "Parser.y"
{ Debug("expr  ->  !expr"                     		); yyval.obj = expr____OPNOT_expr        		(val_peek(1).obj, val_peek(0).obj); }
break;
case 43:
//#line 146 "Parser.y"
{ Debug("expr  ->  expr != expr"                    ); yyval.obj = expr____expr_RELOPNE_expr       (val_peek(2).obj, val_peek(1).obj, val_peek(0).obj); }
break;
case 44:
//#line 147 "Parser.y"
{ Debug("expr  ->  expr <= expr"                    ); yyval.obj = expr____expr_RELOPLE_expr       (val_peek(2).obj, val_peek(1).obj, val_peek(0).obj); }
break;
case 45:
//#line 148 "Parser.y"
{ Debug("expr  ->  expr < expr"                    ); yyval.obj = expr____expr_RELOPLT_expr       (val_peek(2).obj, val_peek(1).obj, val_peek(0).obj); }
break;
case 46:
//#line 149 "Parser.y"
{ Debug("expr  ->  expr >= expr"                    ); yyval.obj = expr____expr_RELOPGE_expr       (val_peek(2).obj, val_peek(1).obj, val_peek(0).obj); }
break;
case 47:
//#line 150 "Parser.y"
{ Debug("expr  ->  expr > expr"                    ); yyval.obj = expr____expr_RELOPGT_expr       (val_peek(2).obj, val_peek(1).obj, val_peek(0).obj); }
break;
case 48:
//#line 152 "Parser.y"
{ Debug("expr  ->  expr AND expr"                   ); yyval.obj = expr____expr_OPAND_expr         (val_peek(2).obj, val_peek(1).obj, val_peek(0).obj); }
break;
case 49:
//#line 153 "Parser.y"
{ Debug("expr  ->  expr + expr"                     ); yyval.obj = expr____expr_OPADD_expr         (val_peek(2).obj, val_peek(1).obj, val_peek(0).obj); }
break;
case 50:
//#line 154 "Parser.y"
{ Debug("expr  ->  ( expr )"                        ); yyval.obj = expr____LPAREN_expr_RPAREN      (val_peek(2).obj, val_peek(1).obj, val_peek(0).obj); }
break;
case 51:
//#line 155 "Parser.y"
{ Debug("expr  ->  IDENT"                           ); yyval.obj = expr____IDENT                   (val_peek(0).obj        ); }
break;
case 52:
//#line 156 "Parser.y"
{ Debug("expr  ->  IDENT ( args )"                  ); yyval.obj = expr____IDENT_LPAREN_args_RPAREN(val_peek(3).obj,     val_peek(1).obj); }
break;
case 53:
//#line 157 "Parser.y"
{ Debug("expr  ->  BOOL_LIT"                        ); yyval.obj = expr____BOOLLIT                 (val_peek(0).obj); }
break;
case 54:
//#line 158 "Parser.y"
{ Debug("expr  ->  NUM_LIT"                         ); yyval.obj = expr____NUMLIT                  (val_peek(0).obj); }
break;
//#line 731 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
//## The -Jnorun option was used ##
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public Parser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public Parser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
