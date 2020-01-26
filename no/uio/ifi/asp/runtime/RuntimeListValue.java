package no.uio.ifi.asp.runtime;
import java.util.HashMap;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.parser.AspSyntax;
import java.util.ArrayList;
import java.util.Arrays;

public class RuntimeListValue extends RuntimeValue {
    boolean boolValue = true;
	public ArrayList<RuntimeValue> liste;
	public int size;

    public RuntimeListValue(ArrayList<RuntimeValue> v) {
		liste = v;
		if(liste.size()==0){ //setting bool value to false if empty
			boolValue = false;	
		}
		size = liste.size();
    }

    @Override
    protected String typeName() {
	return "list";
    }



    public RuntimeValue evalLen(AspSyntax where) {
	return new RuntimeIntValue(size);  // Required by the compiler!
	}
	
	public String showInfo(){
		return Arrays.toString(liste.toArray());
 
	}
	//Denne er basicly prettyprint liste. 
    @Override 
    public String toString() {
		String tmp = "[";
		for(int i = 0; i<liste.size(); i++){
			tmp+= liste.get(i).toString();
			if(i!=liste.size()-1){
				tmp+= ", ";
			}
		}
		tmp+="]";
		return tmp;
    }
	
	public ArrayList<RuntimeValue> getList(String what, AspSyntax where){
		return liste;
	}

    @Override
    public boolean getBoolValue(String what, AspSyntax where) {
		return boolValue;
    }

    @Override
    public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where) {
	if (v instanceof RuntimeListValue) { //Kan man bruke equals? haaper d
		if(v.equals(liste)){
			return new RuntimeBoolValue(true);
		}else{return new RuntimeBoolValue(false);}
	}
	runtimeError("Type error for ==.", where);
	return null;  // Required by the compiler
    }

    @Override
    public RuntimeValue evalNotEqual(RuntimeValue v,AspSyntax where) {
	if (v instanceof RuntimeListValue) { //Kan man bruke equals? haaper d
		if(!v.equals(liste)){
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
	public RuntimeValue evalMultiply(RuntimeValue v, AspSyntax where){
		if(v instanceof RuntimeIntValue){
			ArrayList<RuntimeValue> tmp = new ArrayList<>();
			int counter = 0;
			while(counter<v.getIntValue("test", where)){
				for(RuntimeValue rv: liste){
					tmp.add(rv);
				}
				counter++;	
			}	
			return new RuntimeListValue(tmp);
		}	
	
		runtimeError("Type error for dict multiply", where);
		return null;  // Required by the compiler
	}
	@Override
	public RuntimeValue evalSubscription(RuntimeValue index, AspSyntax where){
		long tmp = index.getIntValue("hei", where);
		return liste.get((int) tmp);
	}
	@Override
    public void evalAssignElem(RuntimeValue inx, RuntimeValue val, AspSyntax where) {
		try{
			liste.set((int) inx.getIntValue("assignelem", where), val);
			return;
		}catch(Exception e){
			runtimeError("Assigning to an element not allowed for "+typeName()+"!", where);}
    }
}
