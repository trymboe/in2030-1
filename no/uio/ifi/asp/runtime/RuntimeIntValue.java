package no.uio.ifi.asp.runtime;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeIntValue extends RuntimeValue {
    long intValue;
	boolean bool=true;

    public RuntimeIntValue(long v) {
		intValue = v;
		if(intValue == 0){
			bool = false;}
    }

    @Override
    protected String typeName() {
	return "integer literal";
    }

	public RuntimeValue getStringValue(){
		return new RuntimeStringValue(String.valueOf(intValue));
	}

    @Override 
    public String toString() {
		return ""+ intValue;
    }

	@Override
	public boolean getBoolValue(String what, AspSyntax where){
		return bool;
	}
	public long getIntValue(String what, AspSyntax where){
		return intValue;
	}

	@Override
	public double getFloatValue(String what, AspSyntax where) {
		return (double) intValue;
	}
	

    @Override
    public RuntimeValue evalAdd(RuntimeValue v, AspSyntax where) {
		if (v instanceof RuntimeIntValue){
			return new RuntimeIntValue(intValue + v.getIntValue("+", where));
		} else if (v instanceof RuntimeFloatValue) {
			return new RuntimeFloatValue(intValue + v.getFloatValue("+", where));
		}
		runtimeError("Type error for 0.", where);
		return null; // Required by compiler.
	}

	/**
	 * Samme som evalAdd, bare med deleoperator.
	 *
	 * Tror det skal fungere
	 */
	@Override
	public RuntimeValue evalDivide(RuntimeValue v, AspSyntax where){
		if (v instanceof RuntimeIntValue){
			return new RuntimeFloatValue(intValue / v.getIntValue("/", where));
		} else if (v instanceof RuntimeFloatValue) {
			return new RuntimeFloatValue(intValue / v.getFloatValue("/", where));
		}
		runtimeError("Type error for 0.", where);
		return null; // Required by compiler.
	}
    
	/**
	 * gir det motsatte av boolvalue
	 * 
	 */
	@Override
	public RuntimeValue evalNot(AspSyntax where){
		return new RuntimeBoolValue(!bool);	
	}

	@Override	
	public RuntimeValue evalNegate(AspSyntax where){
		return new RuntimeIntValue(-intValue);	
	}

	@Override	
	public RuntimeValue evalPositive(AspSyntax where){
		return new RuntimeIntValue(intValue);	
	}


	@Override	
	public RuntimeValue evalMultiply(RuntimeValue v, AspSyntax where){
		if (v instanceof RuntimeIntValue){
			return new RuntimeIntValue(intValue * v.getIntValue("multiply", where));
		}else if(v instanceof RuntimeFloatValue){
			return new RuntimeFloatValue(intValue * v.getFloatValue("multiply", where));
		}
		runtimeError("Type error for intmultiply", where);
		return null;  // Required by the compiler
	
	}
	@Override
	public RuntimeValue evalSubtract(RuntimeValue v, AspSyntax where){
		if (v instanceof RuntimeIntValue){
			return new RuntimeIntValue(intValue - v.getIntValue("substract", where));
		}else if(v instanceof RuntimeFloatValue){
			return new RuntimeFloatValue(intValue - v.getFloatValue("substract", where));
		}
		runtimeError("Type error for !=.", where);
		return null;  // Required by the compiler
	
	}
    @Override
    public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where) {
		if (v instanceof RuntimeNoneValue) {
			return new RuntimeBoolValue(true);
		}
		else if (v instanceof RuntimeIntValue){
			return new RuntimeBoolValue(intValue == v.getIntValue("equal", where));
		}else if(v instanceof RuntimeFloatValue){
			return new RuntimeBoolValue((float) intValue == v.getFloatValue("equal", where));
		}
		runtimeError("Type error for !=.", where);
		return null;  // Required by the compiler
    }

    @Override
    public RuntimeValue evalNotEqual(RuntimeValue v, AspSyntax where) {
		if (v instanceof RuntimeNoneValue) {
			return new RuntimeBoolValue(true);
		}
		else if (v instanceof RuntimeIntValue){
			return new RuntimeBoolValue(intValue != v.getIntValue("not equal", where));
		}else if(v instanceof RuntimeFloatValue){
			return new RuntimeBoolValue((float) intValue != v.getFloatValue("Not equal", where));
	}
		runtimeError("Type error for !=", where);
		return null;  // Required by the compiler
    }

    @Override
    public RuntimeValue evalGreaterEqual(RuntimeValue v, AspSyntax where) {
		if (v instanceof RuntimeIntValue){
			return new RuntimeBoolValue(intValue >= v.getIntValue(">=", where));
		}else if(v instanceof RuntimeFloatValue){
			return new RuntimeBoolValue((float) intValue>=v.getFloatValue("greater equal", where));
		}
		runtimeError("Type error for >=", where);
		return null;  // Required by the compiler
    }


	@Override	
    public RuntimeValue evalLessEqual(RuntimeValue v, AspSyntax where) {
		if (v instanceof RuntimeIntValue){
			return new RuntimeBoolValue(intValue <= v.getIntValue("<=", where));
		}else if(v instanceof RuntimeFloatValue){
			return new RuntimeBoolValue((float) intValue<=v.getFloatValue("<=", where));
		}
		runtimeError("Type error for <=", where);
		return null;  // Required by the compiler
	}
	@Override
	public RuntimeValue evalGreater(RuntimeValue v, AspSyntax where){ //>
		if (v instanceof RuntimeIntValue){
			return new RuntimeBoolValue(intValue > v.getIntValue(">", where));
		}else if(v instanceof RuntimeFloatValue){
			return new RuntimeBoolValue((float)intValue > v.getFloatValue(">", where));
		}
		runtimeError("Type error for evalgreater.", where);
		return null;
	}


	@Override
	public RuntimeValue evalLess(RuntimeValue v, AspSyntax where){
		if (v instanceof RuntimeIntValue){
			return new RuntimeBoolValue(intValue < v.getIntValue("<", where));
		}else if(v instanceof RuntimeFloatValue){
			return new RuntimeBoolValue((float)intValue < v.getFloatValue("<", where));
		}
		runtimeError("Type error for evalgreater.", where);
		return null;
	}


	@Override
	public RuntimeValue evalModulo(RuntimeValue v, AspSyntax where){
		if (v instanceof RuntimeIntValue){
			long tmp = v.getIntValue("mod", where);
			return new RuntimeIntValue(Math.floorMod(intValue, tmp));
		}else if(v instanceof RuntimeFloatValue){
			double tmpf = v.getFloatValue("mod", where);
			return new RuntimeFloatValue(( intValue - tmpf* Math.floor(intValue / tmpf)));
		}
		runtimeError("Type error for mod", where);
		return null;  // Required by the compiler
	
	}

	@Override
	public RuntimeValue evalIntDivide(RuntimeValue v, AspSyntax where){
		if (v instanceof RuntimeIntValue){
			return new RuntimeIntValue(Math.floorDiv(intValue, v.getIntValue("intdivide", where)));
		}else if(v instanceof RuntimeFloatValue){
			return new RuntimeFloatValue(Math.floor(intValue / v.getFloatValue("intdivide", where)));
		}
		runtimeError("Type error for intdivide", where);
		return null;  // Required by the compiler
	
	}
}
