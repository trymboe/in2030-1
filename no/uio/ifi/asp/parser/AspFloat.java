package no.uio.ifi.asp.parser;

import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;



class AspFloat extends AspAtom {
    double value;


    AspFloat(int n){
        super(n);
    }

    static AspFloat parse(Scanner s) {
        enterParser("float literal");
        AspFloat t = new AspFloat(s.curLineNum());
        t.value= s.curToken().floatLit;
		skip(s, floatToken);
        leaveParser("float literal");
        return t;
    }

	public void prettyPrint(){
		//https://stackoverflow.com/questions/9602444/force-a-double-to-write-the-whole-number
		String valuef = String.format("%.6f", value);
		prettyWrite(valuef);	

	}


	public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
		return new RuntimeFloatValue(value);
	}
}
