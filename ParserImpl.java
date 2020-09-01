import javax.management.ObjectName;
import javax.naming.spi.ObjectFactoryBuilder;
import java.util.*;
import java.util.HashMap;

public class ParserImpl {
    Env env = new Env(null);
    int index = -1;

    public String functionName;
    public static Boolean _debug = false;

    void Debug(String message) {
        if (_debug)
            System.out.println(message);
    }

    // this stores list of all functions, which will be used to print parse tree and run the parse tree
    ArrayList<ParseTree.StmtFunc> funcs = null;

    Object program____decllist(Object p1) throws Exception {
        //check for main
        ArrayList<ParseTree.StmtFunc> decllist = (ArrayList<ParseTree.StmtFunc>) p1;
        funcs = decllist;

        //check for main
        int count = 0;

        for(int i = 0; i<funcs.size();i++)
        {
            ParseTree.StmtFunc funcInfo = funcs.get(i);
            if (funcInfo.name.equals("main")) {
                count++;
                break;
            }
        }

        if (count == 0) {
            throw new Exception("The program must have one main() function.");
        }

        return null;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    Object decllist____decllist_decl(Object p1, Object p2) throws Exception {
        ArrayList<ParseTree.StmtFunc> decllist = (ArrayList<ParseTree.StmtFunc>) p1;
        ParseTree.StmtFunc decl = (ParseTree.StmtFunc) p2;
        decllist.add((ParseTree.StmtFunc) decl);
        return decllist;
    }

    Object decllist____eps() throws Exception {
        return new ArrayList<ParseTree.StmtFunc>();
    }

    Object decl____fundecl(Object p1) throws Exception {
        return p1;
    }

    Object typespec____BOOL() throws Exception {
        return "bool";
    }

    Object typespec____NUM() throws Exception {
        return "num";
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    Object fundecl____LBRACE(Object p1, Object p2, Object p4) throws Exception {
        index = 0;
        Object[] ID = (Object[]) p2;
        if (env.table.containsKey(ID[2].toString())) {
            throw new Exception("Identifier " + ID[2] + " is already defined.\n" +
                    "Error location is " + ID[0] + ":" + ID[1] + ".");
        }
        // {name,{"function", args[], return type, "return", lineno, column, index}}
        Object[] name = (Object[]) p2;
        functionName = name[2].toString();

        env.Put(functionName, new Object[]{"function", p4, p1, "", name[0], name[1],index});

        create_local_env();

        ArrayList<Object[]> temp = (ArrayList<Object[]>) p4;

        for (Object a[] : temp) {
            index++;
            env.Put(a[0].toString(), new Object[] {a[1],a[2],a[3], index});
        }

//        ParseTree.StmtFunc function = new ParseTree.StmtFunc();
//        function.name = functionName;
//        function.rettype = p1.toString();
        return new Object[]{"function", p4, p1, "", name[0], name[1]};
        //return function;
    }

    //fun_decl       ->  type_spec  IDENT  LPAREN  params  RPAREN  LBRACE  local_decls  stmt_list  RBRACE
    Object fundecl____RBRACE(Object p1, Object p2, Object p4, Object p8, Object p9, Object p10) throws Exception {
        Object[] functionInfo = (Object[]) env.Get(functionName);

        var stmt = new ParseTree.StmtFunc();
        stmt.stmts = new ArrayList<ParseTree.Stmt>();
        stmt.stmts.add(new ParseTree.StmtComment("index "+functionInfo[6]+" of runenv.stkFuncLocalVar.top() : return variable"));

        stmt.name = ((Object[]) p2)[2].toString();
        stmt.rettype = (String) p1;

        //params
        ArrayList<Object[]> temp = (ArrayList<Object[]>) p4;
        ArrayList<ParseTree.StmtComment> param = new ArrayList<>();

        for (Object a[] : temp) {

            Object[] paramInfo = (Object[]) env.table.get(a[0]);
            param.add(new ParseTree.StmtComment("index "+ paramInfo[3] +" of runenv.stkFuncLocalVar.top() : parameter "+a[0]));
        }
        stmt.stmts.addAll(param);

        //local_delcls
        stmt.stmts.addAll((ArrayList<ParseTree.Stmt>) p8);
        stmt.stmts.addAll((ArrayList<ParseTree.Stmt>) p9);



        Object[] RBRACE = (Object[]) p10;
        if (!functionInfo[3].equals("return")) {
            throw new Exception("Function " + functionName + "() should return at least one " + functionInfo[2] + " value.\n" +
                    "Error location is " + RBRACE[0] + ":" + RBRACE[1] + ".");
        }

        delete_local_env();
        return stmt;
    }


    Object param_list____param_list_COMMA_param(Object p1, Object p2, Object p3) {
        ArrayList<Object> paramList = (ArrayList<Object>) p1; //This line here. That exception is telling you that p1 is actually an Arraylist but you are trying to cast it to an object[]
        paramList.add(p3);
        return paramList;
        //TODO
    }

    Object param_list____param(Object p1) {
        ArrayList<Object> o1 = new ArrayList<Object>(); //new ArrayList<>();
        o1.add(p1);
        return o1;
    }


    Object param____type_spec_IDENT(Object p1, Object p2) {
        Object[] ID = (Object[]) p2;
        return new Object[]{ID[2], p1, ID[0], ID[1]};
    }
    Object params____param_list(Object p1)
    {
        return p1;
    }


    Object params____eps() throws Exception {
        return new ArrayList<Object>();
    }

    Object stmtlist____stmtlist_stmt(Object p1, Object p2) throws Exception {
        // {name,{"function", args[], return type, "return", lineno, column}}
        ArrayList<ParseTree.Stmt> stmtlist = (ArrayList<ParseTree.Stmt>) p1;
        ParseTree.Stmt stmt = (ParseTree.Stmt) p2;
        stmtlist.add(stmt);

        return stmtlist;
    }

    Object stmtlist____eps() throws Exception {
        return new ArrayList<ParseTree.Stmt>();
    }

    Object stmt____exprstmt_SEMI(Object p1) throws Exception {

        return p1;
    }

    Object stmt____returnstmt_SEMI(Object p1) throws Exception {

        return p1;
    }

    Object stmt____SEMI() throws Exception {
        return null;
    }

    Object stmt____compound_stmt(Object p1)
    {
        return p1;
    }

    Object stmt____if_stmt(Object p1)
    {
        return p1;
    }

    Object stmt____while_stmt(Object p1)
    {
        return p1;
    }
    Object stmt____print_stmt_SEMI(Object p1)
    {
        return p1;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    Object exprstmt____IDENT_ASSIGN_expr(Object p1, Object p2, Object p3) throws Exception {
        //TODO
        // o1 is ID
        Object[] o1 = (Object[]) p1;
        Object[] o2 = (Object[]) p2;
        ParseTree.Expr o3 = (ParseTree.Expr) p3; //this is good
        Object[] ID_type = (Object[]) env.Get(o1[2].toString());
        Object[] expr_type = (Object[]) o3.info;


        if (ID_type == null) {
            throw new Exception("Undefine variable " + o1[2] +" is used.\n" +
                                "Error location is "+o1[0]+":"+o1[1]+".");
        }

        if (!ID_type[0].equals(expr_type[0])) {
            throw new Exception(expr_type[0] + " value cannot be assigned to " + ID_type[0] + " variable " + o1[2] + ".\n"
                    + "Error location is " + o2[0] + ":" + o2[1] + ".");
        }

        ParseTree.StmtAssign stmtAssign = new ParseTree.StmtAssign();
        Object[] ID = (Object[]) env.table.get(o1[2].toString());
        stmtAssign.idxId = (int)ID_type[3];
        stmtAssign.expr = o3;

        //you can set the expr and idx in here if you want. (Extra credit I believe)
        return stmtAssign;
    }

    // while_stmt     ->  WHILE  LPAREN  expr  RPAREN  compound_stmt
    Object while_statement(Object p1, Object p2) throws Exception{
        ParseTree.StmtWhile stmtWhile = new ParseTree.StmtWhile();
        ParseTree.StmtCompound stmtCompound = (ParseTree.StmtCompound)p2;
        ParseTree.Expr expr = (ParseTree.Expr)p1;
        Object[] info = (Object[]) expr.info;
        if(!info[0].equals("bool"))
        {
            throw new Exception("Use bool value to the check condition in while statement.\n" +
                    "Error location is " + info[1] + ":" + info[2]+".");
        }
        stmtWhile.cond = expr;
        stmtWhile.stmts = stmtCompound;
        return stmtWhile;
    }

    // if_stmt   ->    IF  LPAREN  expr  RPAREN  compound_stmt  ELSE  compound_stmt
    Object if_statement(Object p1, Object p2, Object p3) throws Exception{

        ParseTree.StmtIf stmtIf = new ParseTree.StmtIf();
        ParseTree.Expr expr = (ParseTree.Expr)p1;
        Object[] info = (Object[]) expr.info;
        if(!info[0].equals("bool"))
        {
            throw new Exception("Use bool value to the check condition in if statement.\n" +
                                "Error location is " + info[1] + ":" + info[2]+".");
        }
        stmtIf.cond = expr;
        stmtIf.thenStmts =(ParseTree.StmtCompound)p2;
        stmtIf.elseStmts = (ParseTree.StmtCompound)p3;

        return stmtIf;
    }

    Object local_decls____local_decls_local_decl(Object p1, Object p2) {
        ((ArrayList<ParseTree.Stmt>)p1).add((ParseTree.Stmt)p2);
        return p1;
    }


    Object localdecls____eps() throws Exception {
        return new ArrayList<ParseTree.Stmt>();
    }

    void create_local_env() throws Exception {
        if (env == null)
            env = new Env(null);
        else env = new Env(env);
    }

    void delete_local_env() throws Exception {
        if (env != null) {
            Env p = env.prev;
            env = p;
        }
    }


    Object localdecl____type_spec_IDENT_SEMI(Object p1, Object p2) throws Exception {

        //TODO
        index++;
        Object[] ID = (Object[]) p2;
        if (env.table.containsKey(ID[2].toString())) {
            throw new Exception("Identifier " + ID[2] + " is already defined.\n" +
                    "Error location is " + ID[0] + ":" + ID[1] + ".");
        }
        if (env == null) {
            env = new Env(null);
            env.Put(ID[2].toString(), new Object[]{p1, ID[0], ID[1], index});
        } else {
            env.Put(ID[2].toString(), new Object[]{p1, ID[0], ID[1], index});
        }
        ParseTree.StmtComment stmt = new ParseTree.StmtComment("index "+index+" of runenv.stkFuncLocalVar.top() : local variable "+ID[2]);
        return stmt;
    }

    Object returnstmt____RETURN_expr(Object p2) throws Exception {

        //// {name,{"function", args[], return type, "return", lineno, column, index}}
        var stmt = new ParseTree.StmtReturn();
        stmt.expr = (ParseTree.Expr) p2;
        Object[] location = (Object[]) stmt.expr.info;
        Object[] functionInfo = (Object[]) env.Get(functionName);

        if (!location[0].equals(functionInfo[2])) {
            throw new Exception("Function " + functionName + "() should return " + functionInfo[2] + " value\n"
                    + "Error location is " + location[1] + ":" + location[2] + ".");
        }
        functionInfo[3] = "return";
        ((HashMap<String, Object>) env.findBase()).replace(functionName, functionInfo);

        return stmt;
    }

    Object print_stmt____PRINT_expr(Object p2) throws Exception {
        var stmt = new ParseTree.StmtPrint();
        stmt.expr = (ParseTree.Expr) p2;
        return stmt;
    }

    Object compound_stmt____LBRACE(Object p1) throws Exception
    {
        create_local_env();
        return null;

    }

    // compound_stmt  ->  LBRACE  local_decls  stmt_list  RBRACE
    Object compound_stmt____RBRACE(Object p1, Object p2) throws Exception
    {
        ParseTree.StmtCompound stmtCompound = new ParseTree.StmtCompound();
        stmtCompound.stmts.addAll((ArrayList<ParseTree.Stmt>) p1);
        stmtCompound.stmts.addAll((ArrayList<ParseTree.Stmt>) p2);
        delete_local_env();
        return stmtCompound;
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    Object arg_list____arg_list_COMMA_expr(Object p1, Object p2, Object p3) {
        ((ArrayList<Object>) p1).add(p3);
        return p1;
    }

    Object args____eps() throws Exception {
        return new ArrayList<ParseTree.Expr>();
    }

    Object args____arg_list(Object p1) {
        return p1;
    }

    Object args_list____expr(Object p1) {
        //read the method. You are given an expr and you need to return a list. So create a list and add expr.
        ArrayList<Object> o1 = new ArrayList<>();
        o1.add(p1);
        return o1;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    Object expr____expr_RELOPEQ_expr(Object p1, Object p2, Object p3) throws Exception {
        ParseTree.Expr o1 = (ParseTree.Expr) p1;
        ParseTree.Expr o3 = (ParseTree.Expr) p3;

        Object[] expr1 = (Object[]) o1.info;
        Object[] op = (Object[]) p2;
        Object[] expr2 = (Object[]) o3.info;
        if (!expr1[0].equals(expr2[0])) {
            throw new Exception("Operation of " + expr1[0] + " " + op[2] + " " + expr2[0] + " is not allowed.\n"
                    + "Error location is " + op[0] + ":" + op[1] + ".");
        }

        ParseTree.ExprEq expr = new ParseTree.ExprEq(o1, o3);
        expr.info = new Object[]{"bool", op[0], op[1]};
        return expr;
    }

    Object expr____expr_RELOPLE_expr(Object p1, Object p2, Object p3) throws Exception {
        ParseTree.Expr o1 = (ParseTree.Expr) p1;
        ParseTree.Expr o3 = (ParseTree.Expr) p3;

        //TODO o1 is null. Need to fix expr -> Ident rule

        Object[] expr1 = (Object[]) o1.info;
        Object[] op = (Object[]) p2;
        Object[] expr2 = (Object[]) o3.info;
        if (!expr1[0].equals(expr2[0]) || (expr1[0].equals("bool") && expr2[0].equals("bool"))) {
            throw new Exception("Operation of " + expr1[0] + " " + op[2] + " " + expr2[0] + " is not allowed.\n"
                    + "Error location is " + op[0] + ":" + op[1] + ".");
        }

        ParseTree.ExprLe expr = new ParseTree.ExprLe(o1, o3);
        expr.info = new Object[]{"bool", op[0], op[1]};
        return expr;
    }

    Object expr____expr_RELOPLT_expr(Object p1, Object p2, Object p3) throws Exception {
        ParseTree.Expr o1 = (ParseTree.Expr) p1;
        ParseTree.Expr o3 = (ParseTree.Expr) p3;

        //TODO o1 is null. Need to fix expr -> Ident rule

        Object[] expr1 = (Object[]) o1.info;
        Object[] op = (Object[]) p2;
        Object[] expr2 = (Object[]) o3.info;
        if (!expr1[0].equals(expr2[0]) || (expr1[0].equals("bool") && expr2[0].equals("bool"))) {
            throw new Exception("Operation of " + expr1[0] + " " + op[2] + " " + expr2[0] + " is not allowed.\n"
                    + "Error location is " + op[0] + ":" + op[1] + ".");
        }

        ParseTree.ExprLt expr = new ParseTree.ExprLt(o1, o3);
        expr.info = new Object[]{"bool", op[0], op[1]};
        return expr;
    }

    Object expr____expr_RELOPGE_expr(Object p1, Object p2, Object p3) throws Exception {
        ParseTree.Expr o1 = (ParseTree.Expr) p1;
        ParseTree.Expr o3 = (ParseTree.Expr) p3;

        //TODO o1 is null. Need to fix expr -> Ident rule

        Object[] expr1 = (Object[]) o1.info;
        Object[] op = (Object[]) p2;
        Object[] expr2 = (Object[]) o3.info;
        if (!expr1[0].equals(expr2[0]) || (expr1[0].equals("bool") && expr2[0].equals("bool"))) {
            throw new Exception("Operation of " + expr1[0] + " " + op[2] + " " + expr2[0] + " is not allowed.\n"
                    + "Error location is " + op[0] + ":" + op[1] + ".");
        }

        ParseTree.ExprGe expr = new ParseTree.ExprGe(o1, o3);
        expr.info = new Object[]{"bool", op[0], op[1]};
        return expr;
    }

    Object expr____expr_RELOPGT_expr(Object p1, Object p2, Object p3) throws Exception {
        ParseTree.Expr o1 = (ParseTree.Expr) p1;
        ParseTree.Expr o3 = (ParseTree.Expr) p3;

        //TODO o1 is null. Need to fix expr -> Ident rule

        Object[] expr1 = (Object[]) o1.info;
        Object[] op = (Object[]) p2;
        Object[] expr2 = (Object[]) o3.info;
        if (!expr1[0].equals(expr2[0]) || (expr1[0].equals("bool") && expr2[0].equals("bool"))) {
            throw new Exception("Operation of " + expr1[0] + " " + op[2] + " " + expr2[0] + " is not allowed.\n"
                    + "Error location is " + op[0] + ":" + op[1] + ".");
        }

        ParseTree.ExprGt expr = new ParseTree.ExprGt(o1, o3);
        expr.info = new Object[]{"bool", op[0], op[1]};
        return expr;
    }

    Object expr____expr_RELOPNE_expr(Object p1, Object p2, Object p3) throws Exception {
        ParseTree.Expr o1 = (ParseTree.Expr) p1;
        ParseTree.Expr o3 = (ParseTree.Expr) p3;


        Object[] expr1 = (Object[]) o1.info;
        Object[] op = (Object[]) p2;
        Object[] expr2 = (Object[]) o3.info;
        if (!expr1[0].equals(expr2[0])) {
            throw new Exception("Operation of " + expr1[0] + " " + op[2] + " " + expr2[0] + " is not allowed.\n"
                    + "Error location is " + op[0] + ":" + op[1] + ".");
        }

        ParseTree.ExprNe expr = new ParseTree.ExprNe(o1, o3);
        expr.info = new Object[]{"bool", op[0], op[1]};
        return expr;
    }

    Object expr____OPNOT_expr(Object p1, Object p2) throws Exception {
        //p1 is parsetree
        Object[] expr = (Object[]) ((ParseTree.Expr) p2).info;
        Object[] opnot = (Object[]) p1;

        if(expr[0].equals("num"))
        {
            throw new Exception("Operation of not "+expr[0]+" is not allowed.\n" +
                                "Error location is " + opnot[0] + ":" + opnot[1]+".");
        }

        ParseTree.Expr o1 = (ParseTree.Expr) p2;
        ParseTree.ExprNot exprFinal = new ParseTree.ExprNot(o1);
        exprFinal.info = new Object[]{expr[0],opnot[0],opnot[1]};
        return exprFinal;
    }

    Object expr____expr_OPOR_expr(Object p1, Object p2, Object p3) throws Exception {
        ParseTree.Expr o1 = (ParseTree.Expr) p1;
        ParseTree.Expr o3 = (ParseTree.Expr) p3;

        Object[] expr1 = (Object[]) o1.info;
        Object[] op = (Object[]) p2;
        Object[] expr2 = (Object[]) o3.info;
        if (!expr1[0].equals(expr2[0]) || (expr1[0].equals("num") && expr2[0].equals("num"))) {
            throw new Exception("Operation of " + expr1[0] + " " + op[2] + " " + expr2[0] + " is not allowed.\n"
                    + "Error location is " + op[0] + ":" + op[1] + ".");
        }

        ParseTree.ExprOr expr = new ParseTree.ExprOr(o1, o3);
        expr.info = new Object[]{expr1[0],op[0],op[1]};
        return expr;
    }

    Object expr____expr_OPAND_expr(Object p1, Object p2, Object p3) throws Exception {
        ParseTree.Expr o1 = (ParseTree.Expr) p1;
        ParseTree.Expr o3 = (ParseTree.Expr) p3;


        Object[] expr1 = (Object[]) o1.info;
        Object[] op = (Object[]) p2;
        Object[] expr2 = (Object[]) o3.info;
        if (!expr1[0].equals(expr2[0]) || (expr1[0].equals("num") && expr2[0].equals("num"))) {
            throw new Exception("Operation of " + expr1[0] + " " + op[2] + " " + expr2[0] + " is not allowed.\n"
                    + "Error location is " + op[0] + ":" + op[1] + ".");
        }

        ParseTree.ExprAnd expr = new ParseTree.ExprAnd(o1, o3);
        expr.info = new Object[]{expr1[0],op[0],op[1]};
        return expr;
    }


    Object expr____expr_OPADD_expr(Object p1, Object p2, Object p3) throws Exception {
        //checking type of p1 and p3
        //if type of p1 != type of p3
        //  throw error message with information from p2 because include the location (line, column) of "+"
        //create new expr (p1 + p2)
        //and return the new expr

        //use line number and column
        ParseTree.Expr o1 = (ParseTree.Expr) p1;
        ParseTree.Expr o3 = (ParseTree.Expr) p3;

        Object[] expr1 = (Object[]) o1.info;
        Object[] op = (Object[]) p2;
        Object[] expr2 = (Object[]) o3.info;
        if (!expr1[0].equals(expr2[0]) || (expr1[0].equals("bool") && expr2[0].equals("bool"))) {
            throw new Exception("Operation of " + expr1[0] + " " + op[2] + " " + expr2[0] + " is not allowed.\n"
                    + "Error location is " + op[0] + ":" + op[1] + ".");
        }

        ParseTree.ExprAdd expr = new ParseTree.ExprAdd(o1, o3);
        expr.info = new Object[]{expr1[0],op[0],op[1]};
        return expr;
    }

    Object expr____expr_OPSUB_expr(Object p1, Object p2, Object p3) throws Exception {

        ParseTree.Expr o1 = (ParseTree.Expr) p1;
        ParseTree.Expr o3 = (ParseTree.Expr) p3;

        //TODO o1 is null. Need to fix expr -> Ident rule

        Object[] expr1 = (Object[]) o1.info;
        Object[] op = (Object[]) p2;
        Object[] expr2 = (Object[]) o3.info;
        if (!expr1[0].equals(expr2[0]) || (expr1[0].equals("bool") && expr2[0].equals("bool"))) {
            throw new Exception("Operation of " + expr1[0] + " " + op[2] + " " + expr2[0] + " is not allowed.\n"
                    + "Error location is " + op[0] + ":" + op[1] + ".");
        }

        ParseTree.ExprSub expr = new ParseTree.ExprSub(o1, o3);
        expr.info = new Object[]{expr1[0],op[0],op[1]};
        return expr;
    }

    Object expr____expr_OPMUL_expr(Object p1, Object p2, Object p3) throws Exception {

        ParseTree.Expr o1 = (ParseTree.Expr) p1;
        ParseTree.Expr o3 = (ParseTree.Expr) p3;

        //TODO o1 is null. Need to fix expr -> Ident rule

        Object[] expr1 = (Object[]) o1.info;
        Object[] op = (Object[]) p2;
        Object[] expr2 = (Object[]) o3.info;
        if (!expr1[0].equals(expr2[0]) || (expr1[0].equals("bool") && expr2[0].equals("bool"))) {
            throw new Exception("Operation of " + expr1[0] + " " + op[2] + " " + expr2[0] + " is not allowed.\n"
                    + "Error location is " + op[0] + ":" + op[1] + ".");
        }

        ParseTree.ExprMul expr = new ParseTree.ExprMul(o1, o3);
        expr.info = new Object[]{expr1[0],op[0],op[1]};
        return expr;
    }

    Object expr____expr_OPDIV_expr(Object p1, Object p2, Object p3) throws Exception {
        ParseTree.Expr o1 = (ParseTree.Expr) p1;
        ParseTree.Expr o3 = (ParseTree.Expr) p3;

        //TODO o1 is null. Need to fix expr -> Ident rule

        Object[] expr1 = (Object[]) o1.info;
        Object[] op = (Object[]) p2;
        Object[] expr2 = (Object[]) o3.info;
        if (!expr1[0].equals(expr2[0]) || (expr1[0].equals("bool") && expr2[0].equals("bool"))) {
            throw new Exception("Operation of " + expr1[0] + " " + op[2] + " " + expr2[0] + " is not allowed.\n"
                    + "Error location is " + op[0] + ":" + op[1] + ".");
        }

        ParseTree.ExprDiv expr = new ParseTree.ExprDiv(o1, o3);
        expr.info = new Object[]{expr1[0],op[0],op[1]};
        return expr;
    }




    Object expr____LPAREN_expr_RPAREN(Object p1, Object p2, Object p3) throws Exception {
        ParseTree.Expr expr = (ParseTree.Expr) p2;
        ParseTree.ExprParen exprParen = new ParseTree.ExprParen(expr);
        exprParen.info = ((ParseTree.Expr) p2).info;
        return  exprParen;
    }

    Object expr____IDENT(Object p1) throws Exception //p1 = (Object)yytext()
    {
        Object[] ID = (Object[]) p1;
        if (env.Get(ID[2].toString()) == null) {
            throw new Exception("Undefine variable " + ID[2] + " is used.\n" +
                    "Error location is " + ID[0] + ":" + ID[1] + ".");
        }
        Object[] isFunction = (Object[]) env.Get(ID[2].toString());
        if (!env.table.containsKey(ID[2]) && isFunction[0].equals("function")) {
            throw new Exception("Function " + ID[2] + "() is used as a variable.\n" +
                    "Error location is " + ID[0] + ":" + ID[1] + ".");
        }

          Object[] type = (Object[]) env.Get(ID[2].toString());
          ParseTree.Expr expr = new ParseTree.ExprVar((Integer) type[3]);
          expr.info = new Object[]{type[0],ID[0],ID[1]};
          return expr;


        //return new ParseTree.ExprVar(0); //the parameter you pass in here id (unique index for var, you might not need it unless you are doing the extra credit)
    }

    Object expr____IDENT_LPAREN_args_RPAREN(Object p1, Object p3) throws Exception {
        // TODO - this is called when you call another function (ex. func(1, 2, 3)). Look in 6 for examples. You need to check the number and type of arg that they match the paramters of the function you are calling.
        // {name,{"function", args[], return type, "return", lineno, column}}


        Object[] ID = (Object[]) p1;
        if (env.Get(ID[2].toString()) == null) {
            throw new Exception("Undefined function " + ID[2] + "() is used.\n" +
                    "Error location is " + ID[0] + ":" + ID[1] + ".");
        }

        Object[] variable = (Object[]) p1;
        if (env.table.containsKey(variable[2].toString())) {
            throw new Exception("Variable " + variable[2] + " is used as a function.\n" +
                    "Error location is " + variable[0] + ":" + variable[1] + ".");
        }

        ArrayList<Object> arguments = (ArrayList<Object>) p3;
        String name = ID[2].toString();
        Object[] functionInfo = (Object[]) env.Get(name);
        ArrayList<Object> parameters = (ArrayList<Object>) functionInfo[1];


        if (arguments.size() != parameters.size()) {
            throw new Exception("Incorrect number of arguments is passed to function " + name + "().\n" +
                    "Error location is " + ID[0] + ":" + ID[1] + ".");
        } else {
            //compare argument type and parameter types stored in function
            for (int i = 0; i < parameters.size(); i++) {
                //argument is a list of expression
                //parameters is a list of object
                ParseTree.Expr arg = (ParseTree.Expr) arguments.get(i);
                Object[] argInfo = (Object[]) arg.info;
                Object[] param = (Object[]) parameters.get(i);

                //argInfo[0] and param[1]: return type
                if (!argInfo[0].equals(param[1])) {
                    throw new Exception((i + 1) + "th argument of function " + name + "() must be " + param[1] + " type\n" +
                            "Error location is " + argInfo[1] + ":" + argInfo[2] + ".");
                }
            }
        }

        ArrayList<ParseTree.Expr> o2 = (ArrayList<ParseTree.Expr>) p3;
        ParseTree.ExprCall exprCall=  new ParseTree.ExprCall(ID[2].toString(), o2);
        exprCall.info = new Object[]{functionInfo[2], ID[0], ID[1]};
        return exprCall;

    }

    Object expr____BOOLLIT(Object p1) throws Exception {
        Object[] current = (Object[]) p1;
        boolean o1 = Boolean.parseBoolean((current[2].toString()));
        ParseTree.Expr expr = new ParseTree.ExprBool(o1);
        expr.info = new Object[]{"bool", current[0], current[1]};
        return expr;
    }

    Object expr____NUMLIT(Object p1) throws Exception {
        Object[] current = (Object[]) p1;
        double o1 = Double.parseDouble((current[2].toString()));
        ParseTree.Expr expr = new ParseTree.ExprNum(o1);
        expr.info = new Object[]{"num", current[0], current[1]};
        return expr;
    }
}
