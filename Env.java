import java.util.ArrayList;
import java.util.HashMap;

public class Env
{
    public HashMap<String, Object> table = new HashMap<>();
    public Env prev;
    public Env(Env prev)
    {
        this.prev = prev;
    }
    public void Put(String name, Object value)
    {
        table.put(name, value);
    }

    public Object findBase()  //created to check return type with function type
    {
        Env p = this;
        while(p.prev!=null)
        {
            p = p.prev;
        }
        return p.table;
    }
    public Object Get(String name)
    {

        //search at the top
//        if(table.containsKey(name)) {
//            return table.get(name);
//        }

        //continue to search through the chain table
        Env p = this; //did you pass all the test cases. This looks wrong. You should start with the current.
        while(p!=null)
        {
            if(p.table.containsKey(name)) {
                return p.table.get(name);
            }
            p = p.prev;
        }
        return null;
        // this is a fake implementation
//        if(name.equals("a") == true) return "int";
//        if(name.equals("b") == true) return "bool";
//        if(name.equals("func") == true)
//        {
//            ArrayList<String> func_args = new ArrayList<>();
//            HashMap<String, Object> func_attr = new HashMap<String, Object>();
//            func_attr.put("return type", "int");
//            func_attr.put("arguments", func_args);
//            return func_attr;
//        }
//        return null;

    }

    public boolean contains(String name)
    {
        //search at the top
        if(table.containsKey(name)) {
            return true;
        }

        //continue to search through the chain table
        Env p = prev;
        while(p!=null)
        {
            if(p.table.containsKey(name)) {
                return true;
            }
            p = prev.prev;
        }
        return false;
    }
}
