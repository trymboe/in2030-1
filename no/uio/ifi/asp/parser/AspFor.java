package no.uio.ifi.asp.parser;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspFor extends AspCompStmt {

    AspFor(int n) {
	super(n);
    }
	AspName nam = null;
	AspExpr expr = null;
	AspSuite suite = null;

	/**
	 * Parsing AspFor stmts
	 *
	 * @param s Scanner :w 
	 * @return AspFor object
	 */
    public static AspFor parse(Scanner s) {
		enterParser("for stmt");
		AspFor aF = new AspFor(s.curLineNum());
		skip(s, forToken);
		aF.nam = AspName.parse(s);
		skip(s, inToken);
		aF.expr = AspExpr.parse(s);
		skip(s, colonToken);
		aF.suite = AspSuite.parse(s);

		leaveParser("for stmt");
		return aF;
    }


    @Override
    public void prettyPrint() {	
		prettyWrite("for ");
		nam.prettyPrint();
		prettyWrite(" in ");
		expr.prettyPrint();
		prettyWrite(":");
		suite.prettyPrint();

    }


    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
		RuntimeValue eList = expr.eval(curScope); //1
		if(eList instanceof RuntimeListValue){
			for(RuntimeValue v: eList.getList("aspfor", this)){
				//Tilordne elist til variablen name
				curScope.assign(nam.name, v);
				//2) eval suite
				suite.eval(curScope);
				//hva boor bli liggende i name? Det siste elementet. Den overlever
			}
		}
	return null;
    }
}
