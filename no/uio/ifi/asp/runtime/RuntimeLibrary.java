package no.uio.ifi.asp.runtime;
import java.util.ArrayList;
import java.util.Scanner;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.RuntimeScope.*;
import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeLibrary extends RuntimeScope {
    private Scanner keyboard = new Scanner(System.in);

    public RuntimeLibrary() {
		//len
		assign("len", new RuntimeFuncValue("len") {
			@Override
			public RuntimeValue evalFuncCall(
						ArrayList<RuntimeValue> actualParams,
						AspSyntax where) {
					checkNumParams(actualParams, 1, "len", where);
					return actualParams.get(0).evalLen(where);
				}});

		assign("print", new RuntimeFuncValue("print") {
			@Override
			public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> actualParams,
						AspSyntax where) {
				for (int i = 0; i < actualParams.size(); ++i) {
					if (i > 0) System.out.print(" ");
					System.out.print(actualParams.get(i).toString());
				}
				System.out.println();
				return new RuntimeNoneValue();
		
		}});

		//range
		assign("range", new RuntimeFuncValue("range") {
			@Override
			public RuntimeValue evalFuncCall(
					ArrayList<RuntimeValue> actualParams,
					AspSyntax where) {
				checkNumParams(actualParams, 2, "range", where);
				ArrayList<RuntimeValue> tmp = new ArrayList<>();
				for(int i = (int) actualParams.get(0).getIntValue("", null); 
						i<actualParams.get(1).getIntValue("", null); i++){
					tmp.add(new RuntimeIntValue(i));
				}
				return new RuntimeListValue(tmp);
		}});
	


		//int
		assign("int", new RuntimeFuncValue("int") {
			@Override
			public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> actualParams,
						AspSyntax where) {
				checkNumParams(actualParams, 1, "int", where);
				return new RuntimeIntValue(actualParams.get(0).getIntValue("", null));
		}});

		//float
		assign("float", new RuntimeFuncValue("float") {
			@Override
			public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> actualParams,
						AspSyntax where) {
				checkNumParams(actualParams, 1, "float", where);
				return new RuntimeFloatValue(actualParams.get(0).getFloatValue("", null));
		}});


		//string
		assign("str", new RuntimeFuncValue("str") {
			@Override
			public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> actualParams,
						AspSyntax where) {
				checkNumParams(actualParams, 1, "str", where);
				RuntimeValue v = actualParams.get(0);
				if(v instanceof RuntimeFloatValue){
					return new RuntimeStringValue(String.valueOf(v.getFloatValue("", null)));
				}
				if(v instanceof RuntimeIntValue){
					return new RuntimeStringValue(String.valueOf(v.getIntValue("", null)));
				}
				if(v instanceof RuntimeStringValue){
					return v;
				}
				runtimeError("typeerror String() func", null);
				return null;
		}});


		//input
		assign("input", new RuntimeFuncValue("input") {
			@Override
			public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> actualParams,
						AspSyntax where) {
				checkNumParams(actualParams, 1, "input", where);
				System.out.println(actualParams.get(0).getStringValue("", null));
				String s = keyboard.nextLine();				
				return new RuntimeStringValue(s);
		}});


	}

    private void checkNumParams(ArrayList<RuntimeValue> actArgs, 
				int nCorrect, String id, AspSyntax where) {
	if (actArgs.size() != nCorrect)
	    RuntimeValue.runtimeError("Wrong number of parameters to "+id+"!",where);
    }
}
