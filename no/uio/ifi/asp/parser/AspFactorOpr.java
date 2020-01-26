
package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspFactorOpr extends AspSyntax{

	AspFactorOpr(int n){
		super(n);
	}
	Token t;

	public static AspFactorOpr parse(Scanner s){
		enterParser("factor opr");
		AspFactorOpr  afo= new AspFactorOpr(s.curLineNum());

		afo.t = s.curToken();

		if(s.isFactorOpr()){
			s.readNextToken();
		}

		leaveParser("factor opr");
		return afo; // skal returne aspnottest
	}


	@Override
	public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
		return null;
	}

	public void prettyPrint(){
		prettyWrite(" ");
		prettyWrite(t.toString());
		prettyWrite(" ");
	}
}
