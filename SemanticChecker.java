public class SemanticChecker {
	public static void main(String[] args) throws Exception
    {
//              java.io.Reader r = new java.io.StringReader
//              ("num main()\n"
//              +"{\n" +
//                      "int x;\n"
//              +"return 0;\n"
//              +"}\n"
//              );


          if(args.length == 0)
          args = new String[]
          {
              "C:\\Users\\robot\\Documents\\College Life\\Junior College\\Spring 2020\\CMPSC 470\\project 4\\minc\\test_06_func2_succ.minc",
          };

        if(args.length <= 0)
            return;
        String minicpath = args[0];
        java.io.Reader r = new java.io.FileReader(minicpath);

        Compiler compiler = new Compiler(r);
        compiler.Parse();


	}
}
