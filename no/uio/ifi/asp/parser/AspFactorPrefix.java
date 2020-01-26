

package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspFactorPrefix extends AspSyntax{

	AspFactorPrefix(int n){
		super(n);
	}
	Token t;

	public static AspFactorPrefix parse(Scanner s){
		enterParser("factor prefix");
		AspFactorPrefix  afp= new AspFactorPrefix(s.curLineNum());
		afp.t = s.curToken();
		if(s.isFactorPrefix()) s.readNextToken();

		leaveParser("factor prefix");
		return afp; // skal returne aspnottest
	}


	@Override
	public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
		return null;
	}

	public void prettyPrint(){
		prettyWrite(t.toString());
		prettyWrite(" ");
	}
}
