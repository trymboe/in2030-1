package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
class AspNone extends AspAtom{
	AspNone(int n){
		super(n);
	}

	static AspNone parse(Scanner s){
		enterParser("none literal");
		AspNone t = new AspNone(s.curLineNum());
		if(s.curToken().kind == TokenKind.trueToken){
			//Fin og enkel
		}else{parserError("token None expected, got: " + s.curToken().kind, s.curLineNum());
		}
		skip(s, noneToken);
		leaveParser("none literal");
		return t;

	}

	
	public void prettyPrint(){
		prettyWrite("none");
	}
}
