package no.uio.ifi.asp.runtime;
import java.util.HashMap;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeDictValue extends RuntimeValue {
    boolean boolValue = true;
	HashMap<String, RuntimeValue> dictionary;

    public RuntimeDictValue(HashMap<String, RuntimeValue> dict) {
		dictionary = dict;
		if(dict.size()==0){ //setting bool value to false if empty
			boolValue = false;	
		}
    }

    @Override
    protected String typeName() {
	return "dictionary";
    }
	public String showInfo(){
		return this.toString();
	}

    @Override 
    public String toString() {
		//Brulletulle denne maa jeg se over <3<3 counterhelvete som vanlig
		String tmp = "{";
		int i = 0;
		for (String rv : dictionary.keySet()) {
			tmp = tmp+ "\'" + rv +"\'" +": " + dictionary.get(rv).toString();
			if(i != dictionary.size()-1){
				tmp = tmp + ", ";
			}else{
				tmp = tmp + "}";
			}
			i++;
		}
		return tmp;
    }

    @Override
    public boolean getBoolValue(String what, AspSyntax where) {
		return boolValue;
    }

    @Override
    public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where) {
	if (v instanceof RuntimeDictValue) { //Kan man bruke equals? haaper d
		if(v.equals(dictionary)){
			return new RuntimeBoolValue(true);
		}else{return new RuntimeBoolValue(false);}
	}
	runtimeError("Type error for ==.", where);
	return null;  // Required by the compiler
    }

    @Override
    public RuntimeValue evalNotEqual(RuntimeValue v,AspSyntax where) {
	if (v instanceof RuntimeDictValue) { //Kan man bruke equals? haaper d
		if(!v.equals(dictionary)){
			return new RuntimeBoolValue(true);
		}else{
			return new RuntimeBoolValue(false);}
	}
	runtimeError("Type error for ==.", where);
	return null;  // Required by the compiler
    }


    @Override
    public RuntimeValue evalNot(AspSyntax where) {
	return new RuntimeBoolValue(!boolValue);  // Required by the compiler
    }
	@Override
	public RuntimeValue evalSubscription(RuntimeValue index, AspSyntax where){
		return this.dictionary.get(index.getStringValue("", where));
	}
	@Override
    public void evalAssignElem(RuntimeValue inx, RuntimeValue val, AspSyntax where) {
		try{
			dictionary.put(inx.getStringValue("assignelem", where), val);
		}catch(Exception e){
			runtimeError("Assigning to an element not allowed for "+typeName()+"!", where);
		}
    }
}
