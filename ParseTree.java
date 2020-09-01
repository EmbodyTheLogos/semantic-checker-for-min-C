import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class ParseTree
{
    public static class RunEnv
    {
        public Stack<ArrayList<Object>> stkFuncLocalVar;
        public HashMap<String, StmtFunc>  funcname_stmt;

        public RunEnv(ArrayList<ParseTree.StmtFunc> funcs)
        {
            stkFuncLocalVar = new Stack<ArrayList<Object>>();

            funcname_stmt = new HashMap<String, StmtFunc>();
            for(StmtFunc func : funcs)
            {
                funcname_stmt.put(func.name, func);
            }
        }
        public double Run()
        {
            StmtFunc main = funcname_stmt.get("main");
            return (double)main.Run(this, new ArrayList<Expr>());
        }

        public Object GetValue(int idxId)
        {
            var idx_val = stkFuncLocalVar.peek();
            while(idx_val.size() <= idxId)
                idx_val.add(null);
            var val = idx_val.get(idxId);
            return val;
        }
        public void SetValue(int idxId, Object val)
        {
            var idx_val = stkFuncLocalVar.peek();
            while(idx_val.size() <= idxId)
                idx_val.add(null);
            idx_val.set(idxId, val);
        }
        public void EnterFunc()
        {
            stkFuncLocalVar.push(new ArrayList<Object>());
        }
        public void LeaveFunc()
        {
            stkFuncLocalVar.pop();
        }
    }

    public static abstract class Expr
    {
        public Object info = null;
        abstract public Object Calc(RunEnv runenv);
        abstract public String ToString();
    }
    public static abstract class ExprUnary extends Expr
    {
        public Expr op1;
        public ExprUnary(Expr op1)
        {
            this.op1 = op1;
        }
    }
    public static abstract class ExprBinary extends Expr
    {
        public Expr op1;
        public Expr op2;
        public ExprBinary(Expr op1, Expr op2)
        {
            this.op1 = op1;
            this.op2 = op2;
        }
    }
    public static class ExprVar   extends Expr { int   idxId; public ExprVar  (int   idxId) { this.idxId = idxId; } public Object Calc(RunEnv runenv) { return runenv.GetValue(idxId); } public String ToString() { return "{idxId:" +idxId+"}"; } }
    public static class ExprBool  extends Expr { boolean val; public ExprBool (boolean val) { this.val   = val  ; } public Object Calc(RunEnv runenv) { return val;                    } public String ToString() { return "{"       +val  +"}"; } }
    public static class ExprNum   extends Expr { double  val; public ExprNum  (double  val) { this.val   = val  ; } public Object Calc(RunEnv runenv) { return val;                    } public String ToString() { return "{"       +val  +"}"; } }

    public static class ExprAdd extends ExprBinary { public ExprAdd(Expr op1, Expr op2) { super(op1, op2); } public Object Calc(RunEnv runenv) { return ( (double )op1.Calc(runenv) +  (double )op2.Calc(runenv) ); } public String ToString() { return "{"+op1.ToString()+"+" +op2.ToString()+"}"; } }
    public static class ExprSub extends ExprBinary { public ExprSub(Expr op1, Expr op2) { super(op1, op2); } public Object Calc(RunEnv runenv) { return ( (double )op1.Calc(runenv) -  (double )op2.Calc(runenv) ); } public String ToString() { return "{"+op1.ToString()+"-" +op2.ToString()+"}"; } }
    public static class ExprMul extends ExprBinary { public ExprMul(Expr op1, Expr op2) { super(op1, op2); } public Object Calc(RunEnv runenv) { return ( (double )op1.Calc(runenv) *  (double )op2.Calc(runenv) ); } public String ToString() { return "{"+op1.ToString()+"*" +op2.ToString()+"}"; } }
    public static class ExprDiv extends ExprBinary { public ExprDiv(Expr op1, Expr op2) { super(op1, op2); } public Object Calc(RunEnv runenv) { return ( (double )op1.Calc(runenv) /  (double )op2.Calc(runenv) ); } public String ToString() { return "{"+op1.ToString()+"/" +op2.ToString()+"}"; } }
    public static class ExprAnd extends ExprBinary { public ExprAnd(Expr op1, Expr op2) { super(op1, op2); } public Object Calc(RunEnv runenv) { return ( (Boolean)op1.Calc(runenv) && (Boolean)op2.Calc(runenv) ); } public String ToString() { return "{"+op1.ToString()+"&&"+op2.ToString()+"}"; } }
    public static class ExprOr  extends ExprBinary { public ExprOr (Expr op1, Expr op2) { super(op1, op2); } public Object Calc(RunEnv runenv) { return ( (Boolean)op1.Calc(runenv) || (Boolean)op2.Calc(runenv) ); } public String ToString() { return "{"+op1.ToString()+"||"+op2.ToString()+"}"; } }
    public static class ExprNot extends ExprUnary  { public ExprNot(Expr op1          ) { super(op1     ); } public Object Calc(RunEnv runenv) { return !((Boolean)op1.Calc(runenv)                              ); } public String ToString() { return "{not "                +op1.ToString()+"}"; } }
    public static class ExprEq  extends ExprBinary { public ExprEq (Expr op1, Expr op2) { super(op1, op2); } public Object Calc(RunEnv runenv) { return ( (        op1.Calc(runenv)).equals(    op2.Calc(runenv))); } public String ToString() { return "{"+op1.ToString()+"=="+op2.ToString()+"}"; } }
    public static class ExprNe  extends ExprBinary { public ExprNe (Expr op1, Expr op2) { super(op1, op2); } public Object Calc(RunEnv runenv) { return (!(        op1.Calc(runenv)).equals(    op2.Calc(runenv))); } public String ToString() { return "{"+op1.ToString()+"!="+op2.ToString()+"}"; } }
    public static class ExprLe  extends ExprBinary { public ExprLe (Expr op1, Expr op2) { super(op1, op2); } public Object Calc(RunEnv runenv) { return ( (double )op1.Calc(runenv) <= (double )op2.Calc(runenv) ); } public String ToString() { return "{"+op1.ToString()+"<="+op2.ToString()+"}"; } }
    public static class ExprLt  extends ExprBinary { public ExprLt (Expr op1, Expr op2) { super(op1, op2); } public Object Calc(RunEnv runenv) { return ( (double )op1.Calc(runenv) <  (double )op2.Calc(runenv) ); } public String ToString() { return "{"+op1.ToString()+"<" +op2.ToString()+"}"; } }
    public static class ExprGe  extends ExprBinary { public ExprGe (Expr op1, Expr op2) { super(op1, op2); } public Object Calc(RunEnv runenv) { return ( (double )op1.Calc(runenv) >= (double )op2.Calc(runenv) ); } public String ToString() { return "{"+op1.ToString()+">="+op2.ToString()+"}"; } }
    public static class ExprGt  extends ExprBinary { public ExprGt (Expr op1, Expr op2) { super(op1, op2); } public Object Calc(RunEnv runenv) { return ( (double )op1.Calc(runenv) >  (double )op2.Calc(runenv) ); } public String ToString() { return "{"+op1.ToString()+">" +op2.ToString()+"}"; } }

    public static class ExprParen extends ExprUnary{ public ExprParen(Expr op1        ) { super(op1     ); } public Object Calc(RunEnv runenv) { return (          op1.Calc(runenv)                              ); } public String ToString() { return "( "                  +op1.ToString()+" )"; } }

    public static class ExprCall extends Expr
    {
        public String           funcname;
        public ArrayList<Expr>  args;

        public ExprCall(String funcname, ArrayList<Expr> args)
        {
            this.funcname = funcname;
            this.args     = args    ;
        }
        public Object Calc(RunEnv runenv)
        {
            StmtFunc func = runenv.funcname_stmt.get(funcname);
            Object   ret  = func.Run(runenv, args);
            return   ret;
        }
        public String ToString()
        {
            String str = "{call ";
            str += funcname;
            for(Expr arg : args)
                str += "," + arg.ToString();
            str += "}";
            return str;
        }
    }

    public abstract static class Stmt
    {
        abstract public String Run(RunEnv runenv);
        abstract public ArrayList<String> ToStringList();
    }
    public static class StmtComment extends Stmt
    {
        public String comment;
        public StmtComment(String comment)
        {
            this.comment = comment;
        }
        public String Run(RunEnv runenv)
        {
            return "";
        }
        public ArrayList<String> ToStringList()
        {
            ArrayList<String> strs = new ArrayList<String>();
            strs.add("{comment: "+comment+" }");
            return strs;
        }
    }
    public static class StmtFunc extends Stmt
    {
        public String          name;
        public String          rettype;
        public ArrayList<Stmt> stmts;
        public String Run(RunEnv runenv)
        {
            Run(runenv, null);
            return "";
        }
        public Object Run(RunEnv runenv, ArrayList<Expr>  args)
        {
            // calculate argument values
            Object[] vals = new Object[args.size()];
            for(int i=0; i<vals.length; i++)
                vals[i] = args.get(i).Calc(runenv);

            // enter function environment
            runenv.EnterFunc();

            // pass argument values into the local variable stack of the function
            for(int i=0; i<vals.length; i++)
                runenv.SetValue(i+1, vals[i]);

            // call function's instructions
            for(Stmt stmt : stmts)
                stmt.Run(runenv);

            // get return value
            Object ret = runenv.GetValue(0);

            // leave function environment
            runenv.LeaveFunc();

            return ret;
        }
        public ArrayList<String> ToStringList()
        {
            ArrayList<String> strs = new ArrayList<String>();

            strs.add("{function");
            strs.add("  name: " + name);
            strs.add("  rettype: " + rettype);
            strs.add("  stmts: ");
            for(Stmt stmt : stmts)
                for(String str : stmt.ToStringList())
                    strs.add("    "+str);
            strs.add("}");

            return strs;
        }
    }
    public static class StmtAssign extends Stmt
    {
        public int idxId;
        public Expr expr;
        public String Run(RunEnv runenv)
        {
            Object exprval = expr.Calc(runenv);
            runenv.SetValue(idxId, exprval);
            return "";
        }
        public ArrayList<String> ToStringList()
        {
            String str = "{assign {idxID:" + idxId + "} <- " + expr.ToString() + " }";

            ArrayList<String> strs = new ArrayList<String>();
            strs.add(str);

            return strs;
        }
    }
    public static class StmtCompound extends Stmt
    {
        public ArrayList<Stmt> stmts;
        public StmtCompound()
        {
            stmts = new ArrayList<Stmt>();
        }
        public String Run(RunEnv runenv)
        {
            for(Stmt stmt : stmts)
            {
                String control = stmt.Run(runenv);
                if(control == "return")
                    return "return";
            }
            return "";
        }
        public ArrayList<String> ToStringList()
        {
            ArrayList<String> strs = new ArrayList<String>();

            strs.add("{compound stmts:");
            for(Stmt stmt : stmts)
                for(String str : stmt.ToStringList())
                    strs.add("    "+str);
            strs.add("}");

            return strs;
        }
    }
    public static class StmtIf extends Stmt
    {
        public Expr cond;
        public StmtCompound thenStmts;
        public StmtCompound elseStmts;
        public String Run(RunEnv runenv)
        {
            String control;

            boolean condval = (boolean)cond.Calc(runenv);
            if(condval)
            {
                control = thenStmts.Run(runenv);
            }
            else
            {
                control = elseStmts.Run(runenv);
            }

            if(control == "return")
                return "return";
            return "";
        }
        public ArrayList<String> ToStringList()
        {
            ArrayList<String> strs = new ArrayList<String>();

            strs.add("{if");
            strs.add("  cond: " + cond.ToString());
            strs.add("  then: ");
            for(String str : thenStmts.ToStringList())
                strs.add("    "+str);
            strs.add("  else: ");
            for(String str : elseStmts.ToStringList())
                strs.add("    "+str);
            strs.add("}");

            return strs;
        }
    }
    public static class StmtWhile extends Stmt
    {
        public Expr         cond;
        public StmtCompound stmts;
        public String Run(RunEnv runenv)
        {
            while(true)
            {
                boolean condval = (boolean)cond.Calc(runenv);
                if(condval == false)
                    break;

                String control = stmts.Run(runenv);
                if(control == "return")
                    return "return";
            }
            return "";
        }
        public ArrayList<String> ToStringList()
        {
            ArrayList<String> strs = new ArrayList<String>();

            strs.add("{while");
            strs.add("  cond: " + cond.ToString());
            strs.add("  stmts: ");
            for(String str : stmts.ToStringList())
                strs.add("    "+str);
            strs.add("}");

            return strs;
        }
    }
    public static class StmtReturn extends Stmt
    {
        public Expr expr;
        public String Run(RunEnv runenv)
        {
            Object val = expr.Calc(runenv);
            runenv.SetValue(0, val);
            return "return";
        }
        public ArrayList<String> ToStringList()
        {
            String str = "{return " + expr.ToString() + "}";

            ArrayList<String> strs = new ArrayList<String>();
            strs.add(str);

            return strs;
        }
    }
    public static class StmtPrint extends Stmt
    {
        public Expr expr;
        public String Run(RunEnv runenv)
        {
            System.out.println(expr.Calc(runenv));
            return "";
        }
        public ArrayList<String> ToStringList()
        {
            String str = "{print " + expr.ToString() + "}";

            ArrayList<String> strs = new ArrayList<String>();
            strs.add(str);

            return strs;
        }
    }
}
