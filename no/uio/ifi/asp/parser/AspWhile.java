package no.uio.ifi.asp.parser;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspWhile extends AspCompStmt {

    AspWhile(int n) {
	super(n);
    }
	AspSuite suite = null;
	AspExpr expr = null;

    public static AspWhile parse(Scanner s) {
		enterParser("while stmt");
		AspWhile aw = new AspWhile(s.curLineNum());
		skip(s, whileToken);
		aw.expr = AspExpr.parse(s);
		skip(s, colonToken);
		aw.suite = AspSuite.parse(s);
		leaveParser("while stmt");
		return aw;
    }


    @Override
    public void prettyPrint() {
		prettyWrite("while");
		expr.prettyPrint();
		prettyWrite(":");
		suite.prettyPrint();
    }

	//tatt fra forelesing i fjor. Ser paa dee for aa starte tidlig. 
    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
		while(true){
			RuntimeValue t = expr.eval(curScope);
			if(!t.getBoolValue("while loop test", this)) break;
			trace("While True: ...");
			suite.eval(curScope);
		}
		trace("while False:");
		return null;
    }
}
