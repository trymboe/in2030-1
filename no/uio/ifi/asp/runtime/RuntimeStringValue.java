
package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeStringValue extends RuntimeValue {
	boolean bval = true;
	public String value;



	public RuntimeStringValue(String s){
		value = s;
		if(s == ""){
			bval = false;
		}
	}

	public long getIntValue(String what, AspSyntax where){
		try {
			return Long.valueOf(value);
		}catch(Exception e){
			runtimeError("kan ikke konvertere string til long som ikke er long", where);
			return 0; //skjer ikke
		}
	}

	public double getFloatValue(String what, AspSyntax where){
		try {
			return Double.valueOf(value);
		}catch(Exception e){
			runtimeError("kan ikke konvertere string til float som ikke er long", where);
			return 0; //skjer ikke
		}
	}
		
    @Override
    protected String typeName() {
	return "string literal";
    }


    @Override 
    public String toString() {
	return "\'" + value + "\'";
    }
	@Override	
	public String getStringValue(String what, AspSyntax where){
			return value;	
	}



    @Override
    public boolean getBoolValue(String what, AspSyntax where) {
	return bval;
    }


    @Override
    public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where) {
		if(v instanceof RuntimeStringValue){
			return new RuntimeBoolValue(value.equals(v.getStringValue("evalequal string", where)));
		}
		runtimeError("Type error for evalequal string", where);
		return null;
    }


    @Override
    public RuntimeValue evalNot(AspSyntax where) {
		return new RuntimeBoolValue(!bval);
	}


    @Override
    public RuntimeValue evalNotEqual(RuntimeValue v, AspSyntax where) {
		if(v instanceof RuntimeStringValue){
			return new RuntimeBoolValue(value!= v.getStringValue("evalequal string", where));
		}
		runtimeError("Type error for evalequal string", where);
		return null;
    }

	public RuntimeValue evalGreater(RuntimeValue v, AspSyntax where){
		if(v instanceof RuntimeStringValue){
			int tmp = value.compareTo(v.getStringValue("evalgreater", where));
			if(tmp>0){
				return new RuntimeBoolValue(true);
			}else {return new RuntimeBoolValue(false);}
		
		}
		runtimeError("EvalGreater string", where);
		return null;
	}
	public RuntimeValue evalLess(RuntimeValue v, AspSyntax where){
		return v.evalGreater(this, where);
	}


    public RuntimeValue evalMultiply(RuntimeValue v, AspSyntax where) {
		if(v instanceof RuntimeIntValue){
			String tmp = "";
			long limit = v.getIntValue("evalmulti", where);
			if(limit<0){
				runtimeError("Ikke prov aa praank meg, stringmultiply", where);
			}
			for(int i=0; i<limit; i++){ //legger sammen limit ganger 
				tmp = tmp+value;
			}
			return new RuntimeStringValue(tmp);

		}
		runtimeError("'*' undefined for "+typeName()+"!", where);
		return null;  // Required by the compiler!
	}

	public RuntimeValue evalAdd(RuntimeValue v, AspSyntax where){
		if(v instanceof RuntimeStringValue){
			return new RuntimeStringValue(value+v.getStringValue("string add", where));
		
		}
		runtimeError("ingen typedefinert evaladd string", where);
		return null;
	}

}





