package no.uio.ifi.asp.runtime;


import no.uio.ifi.asp.parser.*;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class RuntimeFuncValue extends RuntimeValue{
	public String name;
	public AspFuncDef func;
	ArrayList<AspName> args;
	public RuntimeScope defScope;

	public RuntimeFuncValue(String funcName, AspFuncDef func1, RuntimeScope skop){
		func = func1;
		name = funcName;
		defScope = skop;
		args = func.args;
	}

	public RuntimeFuncValue(String funcName){
		name = funcName;
	}
    @Override
    protected String typeName() {
		return "func definition";
    }


	
    public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> actualParams, AspSyntax where){
		if(args.size() == actualParams.size()){
			//fikse scope
			RuntimeScope innerScope = new RuntimeScope(defScope); //curscope??:a
			for(int i=0; i<args.size(); i++){
				innerScope.assign(args.get(i).name, actualParams.get(i));
			}
			return func.evalFuncCall(innerScope);
			
		}else{
			runtimeError("Not enough parameters for function", where);
		}
		runtimeError("runtimefuncvalue'Function call (...)' undefined for "+typeName()+"!", where);
		return null;  // Required by the compiler!
	}

}
