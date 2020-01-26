package no.uio.ifi.asp.parser;

import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;



class AspInteger extends AspAtom {
    long value;


    AspInteger(int n){
        super(n);
    }

    static AspInteger parse(Scanner s) {
        enterParser("integer literal");
        AspInteger t = new AspInteger(s.curLineNum());
        t.value= s.curToken().integerLit;
		skip(s, integerToken);
        leaveParser("integer literal");
        return t;
    }

	public void prettyPrint(){
		prettyWrite(Long.toString(value));
	}
	
	@Override
	RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue{
		return new RuntimeIntValue(value);
	}

}
