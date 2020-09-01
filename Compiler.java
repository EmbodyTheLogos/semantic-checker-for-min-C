import java.util.HashMap;

public class Compiler {
    Parser parser;
    public Compiler(java.io.Reader r)
    {

        parser = new Parser(r);

    }
    public void Parse()
    {
        ParserImpl._debug = false;


        try {
            if (parser.yyparse() == 0)
            {
                System.out.println("Success: no semantic error is found.");
                System.out.println();
            }
            else
            {
                System.out.println("Error: there is syntax error(s).");
                return;
            }
        }
        catch(Exception e)
        {
            System.out.println("Error: there is semantic error(s).");
            System.out.println(e.getMessage());
            return;
        }

        // run the program

        try
        {
            System.out.println("================================================================================");
            System.out.println("Parse Tree:");
            for(var func : parser.funcs)
            {
                for(String line : func.ToStringList())
                    System.out.println(line);
            }

            System.out.println("================================================================================");
            System.out.println("Execute:");
            ParseTree.RunEnv runenv = new ParseTree.RunEnv(parser.funcs);
            runenv.Run();
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }

    }
}
