package no.uio.ifi.asp.parser;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;

class AspBool extends AspAtom{
	boolean bval = false; 
	AspBool(int n){
		super(n);
	}

	static AspBool parse(Scanner s){
		enterParser("boolean literal");
		AspBool t = new AspBool(s.curLineNum());
		if(s.curToken().kind == TokenKind.trueToken){
			t.bval = true;
			skip(s, TokenKind.trueToken);
		}else if(t.bval == false){
			skip(s, TokenKind.falseToken);
		}
		leaveParser("boolean literal");
		return t;

	}


	public void prettyPrint(){
		if(bval){
		prettyWrite("True");
		}else{
			prettyWrite("False");}
	}

	@Override
	RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
		return new RuntimeBoolValue(bval);
	}
}
