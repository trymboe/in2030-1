package no.uio.ifi.asp.parser;
import static no.uio.ifi.asp.scanner.TokenKind.*;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;

class AspInnerExpression extends AspAtom{
	protected AspExpr ae = null;
	AspInnerExpression(int n){
		super(n);
	}

	public static AspInnerExpression parse(Scanner s){
		enterParser("inner expr");
		AspInnerExpression t = new AspInnerExpression(s.curLineNum());
		skip(s, leftParToken);
		t.ae = AspExpr.parse(s);

		skip(s, rightParToken);	

		leaveParser("inner expr");

		return t;
	}
	public void prettyPrint(){
		prettyWrite("(");
		ae.prettyPrint();
		prettyWrite(")");
	}


	public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
		return ae.eval(curScope);
	}
}
