package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;

class AspString extends AspAtom{
	public String streng = null;
	AspString(int n){
		super(n);
	}

	static AspString parse(Scanner s){
		enterParser("string literal");
		AspString t = new AspString(s.curLineNum());
		if(s.curToken().kind == TokenKind.stringToken){
			t.streng = s.curToken().stringLit;
			skip(s, TokenKind.stringToken);
			leaveParser("string literal");
			return t;
		}else{
		parserError(("class AspString expected type string, got: " + s.curToken().kind), s.curLineNum());
		}
		return t;
	}

	public void prettyPrint(){
		prettyWrite('"' + streng + '"');
	}


	public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
		return new RuntimeStringValue(streng);
	}
}
