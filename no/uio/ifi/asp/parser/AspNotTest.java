package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspNotTest extends AspSyntax{

	AspNotTest(int n){
		super(n);
	}
	boolean not = false;
	AspComparison c = null;
	public static AspNotTest parse(Scanner s){
		enterParser("not test");
		AspNotTest aNT = new AspNotTest(s.curLineNum());
		if(s.curToken().kind == notToken){
			aNT.not = true;
			skip(s, notToken);
		}
		aNT.c = AspComparison.parse(s);
		leaveParser("not test");
		return aNT; // skal returne aspnottest
	}



	public void prettyPrint(){
		if(not) prettyWrite("not "); // Lol at not skal bli en true verdi er ganske rart, men saann faar det bare bli 
		c.prettyPrint();
	}


	@Override
	public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
		RuntimeValue v = c.eval(curScope);
		if (not){
			v = v.evalNot(this);
		}
		return v;
	}
}
