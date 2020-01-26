package no.uio.ifi.asp.parser;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspArgument extends AspPrimarySuffix {



	ArrayList<AspExpr> expressions = new ArrayList<>();

	AspArgument(int n) {
		super(n);
	}

	public static AspArgument parse(Scanner s)  {
		enterParser("arguments");
		AspArgument at = new AspArgument(s.curLineNum());
		skip(s, leftParToken);
		while (!(s.curToken().kind == TokenKind.rightParToken)) {
			at.expressions.add(AspExpr.parse(s));
			if (s.curToken().kind == commaToken){
				skip(s, commaToken);
			}
		}

		skip(s, rightParToken);
		leaveParser("arguments");
		return at;

	}

	@Override
	void prettyPrint() {
		prettyWrite("(");	
		for(int i = 0; i< expressions.size(); i++){
			expressions.get(i).prettyPrint();
			if(i!=expressions.size()-1){
				prettyWrite(", ");
			}
		}
		prettyWrite(")");	
	}

	@Override	
	public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
		ArrayList<RuntimeValue> eArgs = new ArrayList<>();
		for(AspExpr e: expressions){
			eArgs.add(e.eval(curScope));
		}
		return new RuntimeListValue(eArgs);
	}

}

