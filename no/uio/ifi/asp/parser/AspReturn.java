package no.uio.ifi.asp.parser;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspReturn extends AspSmallStmt {

    AspReturn(int n) {
		super(n);
    }
	AspExpr expr;
	int curLineNum;

    public static AspReturn parse(Scanner s) {
		enterParser("return stmt");
		AspReturn r = new AspReturn(s.curLineNum());
		r.curLineNum = s.curLineNum();
		skip(s, returnToken);
		r.expr = AspExpr.parse(s);
		leaveParser("return stmt");
		return r;
    }


    @Override
    public void prettyPrint() {
		//prettyIndent();
		prettyWrite("return ");
		expr.prettyPrint();
		//prettyDedent();
		prettyWriteLn();
    }


    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
		RuntimeValue v = expr.eval(curScope);
		//3.5.4.3 return-setningen - Regne ut og throwe returnval
		trace("Asp return ");
		throw new RuntimeReturnValue(v, curLineNum);
    }
}
