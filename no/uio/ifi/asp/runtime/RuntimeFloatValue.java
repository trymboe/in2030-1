package no.uio.ifi.asp.runtime;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeFloatValue extends RuntimeValue {
    double value;
	boolean bool=true;

    public RuntimeFloatValue(double v) {
		value = v;
		if(value == 0.0){
			bool = false;}
    }

    @Override
    protected String typeName() {
	return "float";
    }

	public RuntimeValue getStringValue(){
		return new RuntimeStringValue(String.valueOf(value));
	}


    @Override 
    public String toString() {
	return "" + value;
    }

	@Override
	public boolean getBoolValue(String what, AspSyntax where){
		return bool;
	}

	@Override
	public double getFloatValue(String what, AspSyntax where) {
		return value;
	}
	

    @Override
    public RuntimeValue evalAdd(RuntimeValue v, AspSyntax where) {
		if (v instanceof RuntimeIntValue){
			return new RuntimeFloatValue(value + v.getFloatValue(" + operand", where));
		} else if (v instanceof RuntimeFloatValue) {
			return new RuntimeFloatValue(value + v.getFloatValue("+ operand", where));
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
			return new RuntimeFloatValue(value / v.getFloatValue(" + operand", where));
		} else if (v instanceof RuntimeFloatValue) {
			return new RuntimeFloatValue(value / v.getFloatValue("+ operand", where));
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
	public RuntimeValue evalPositive(AspSyntax where){
		return new RuntimeFloatValue(value);	
	}


	@Override	
	public RuntimeValue evalNegate(AspSyntax where){
		return new RuntimeFloatValue(-value);	
	}
	
	@Override	
	public RuntimeValue evalMultiply(RuntimeValue v, AspSyntax where){
		if (v instanceof RuntimeIntValue){
			return new RuntimeFloatValue(value * v.getFloatValue("multiply", where));
		}else if(v instanceof RuntimeFloatValue){
			return new RuntimeFloatValue(value * v.getFloatValue("multiply", where));
		}
		runtimeError("Type error for !=.", where);
		return null;  // Required by the compiler
	
	}

	@Override	
	public RuntimeValue evalSubtract(RuntimeValue v, AspSyntax where){
		if (v instanceof RuntimeIntValue){
			return new RuntimeFloatValue(value - v.getFloatValue("substract", where));
		}else if(v instanceof RuntimeFloatValue){
			return new RuntimeFloatValue(value - v.getFloatValue("substract", where));
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
			return new RuntimeBoolValue(value == v.getFloatValue("equal", where));
		}else if(v instanceof RuntimeFloatValue){
			return new RuntimeBoolValue((float) value == v.getFloatValue("equal", where));
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
			return new RuntimeBoolValue(value != v.getFloatValue("not equal", where));
		}else if(v instanceof RuntimeFloatValue){
			return new RuntimeBoolValue((float) value != v.getFloatValue("Not equal", where));
	}
		runtimeError("Type error for !=.", where);
		return null;  // Required by the compiler
    }

    @Override
    public RuntimeValue evalGreaterEqual(RuntimeValue v, AspSyntax where) {
		if (v instanceof RuntimeIntValue){
			return new RuntimeBoolValue(value >= v.getFloatValue(">=", where));
		}else if(v instanceof RuntimeFloatValue){
			return new RuntimeBoolValue((float) value>=v.getFloatValue("Not equal", where));
		}
		runtimeError("Type error for !=.", where);
		return null;  // Required by the compiler
    }


	@Override	
    public RuntimeValue evalLessEqual(RuntimeValue v, AspSyntax where) {
		if (v instanceof RuntimeIntValue){
			return new RuntimeBoolValue(value <= v.getFloatValue(">=", where));
		}else if(v instanceof RuntimeFloatValue){
			return new RuntimeBoolValue((float) value<=v.getFloatValue("Not equal", where));
		}
		runtimeError("Type error for !=.", where);
		return null;  // Required by the compiler
	}
	@Override
	public RuntimeValue evalGreater(RuntimeValue v, AspSyntax where){ //>
		if (v instanceof RuntimeIntValue){
			return new RuntimeBoolValue(value > v.getFloatValue(">", where));
		}else if(v instanceof RuntimeFloatValue){
			return new RuntimeBoolValue((float)value > v.getFloatValue(">", where));
		}
		runtimeError("typeerror evalgreater", where);	
		return null;
	}


	@Override
	public RuntimeValue evalLess(RuntimeValue v, AspSyntax where){
		if (v instanceof RuntimeIntValue){
			return new RuntimeBoolValue(value < v.getFloatValue("<", where));
		}else if(v instanceof RuntimeFloatValue){
			return new RuntimeBoolValue((float)value < v.getFloatValue("<", where));
		}
		runtimeError("typeerror evalgreater", where);	
		return null;
	}


	@Override
	public RuntimeValue evalModulo(RuntimeValue v, AspSyntax where){
		if (v instanceof RuntimeIntValue){
			long tmp = v.getIntValue("mod", where);
			return new RuntimeFloatValue(value-tmp*Math.floor(value/tmp));
		}else if(v instanceof RuntimeFloatValue){
			double tmpf = v.getFloatValue("mod", where);
			return new RuntimeFloatValue(value-tmpf*Math.floor(value/tmpf));
		}
		runtimeError("Type error for !=.", where);
		return null;  // Required by the compiler
	
	}

	@Override
	public RuntimeValue evalIntDivide(RuntimeValue v, AspSyntax where){
		if (v instanceof RuntimeIntValue){
			return new RuntimeFloatValue(Math.floor(value / v.getFloatValue("mod", where)));
		}else if(v instanceof RuntimeFloatValue){
			return new RuntimeFloatValue(Math.floor(value / v.getFloatValue("mod", where)));
		}
		runtimeError("Type error for !=.", where);
		return null;  // Required by the compiler
	
	}
}
