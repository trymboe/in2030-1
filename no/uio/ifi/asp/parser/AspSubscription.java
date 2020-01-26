package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;


class AspSubscription extends AspPrimarySuffix {



	AspExpr expr = null;

	AspSubscription(int n) {
		super(n);
	}

	public static AspSubscription parse(Scanner s)  {
		enterParser("subscription");

		AspSubscription aat = new AspSubscription(s.curLineNum());
		skip(s, leftBracketToken);
		aat.expr = AspExpr.parse(s);
		skip(s, rightBracketToken);


		leaveParser("subscription");
		return aat;

	}

	@Override
	public void prettyPrint() {
		prettyWrite("[");
		expr.prettyPrint();
		prettyWrite("]");
		
	}
	public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue{
		return expr.eval(curScope);
	}
}

