
package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public abstract class AspExprStmt extends AspSmallStmt {

    AspExprStmt(int n) {
	super(n);
    }


    public static AspExprStmt parse(Scanner s) {
		enterParser("expr stmt");
		AspExpr ex = AspExpr.parse(s);
		leaveParser("expr stmt");
		return ex;
    }


    
}
