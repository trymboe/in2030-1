package no.uio.ifi.asp.parser;
import static no.uio.ifi.asp.scanner.TokenKind.*;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import java.util.ArrayList;

class AspList extends AspAtom{

	AspList(int n){
		super(n);
	}
	ArrayList<AspExpr> aList = new ArrayList<>();

	public static AspList parse(Scanner s){
		enterParser("list display");
		AspList t = new AspList(s.curLineNum());
		skip(s, leftBracketToken);
		while(true){
			if(s.curToken().kind == rightBracketToken){
				skip(s, rightBracketToken);
				break;
			}
			t.aList.add(AspExpr.parse(s));
			if(s.curToken().kind == TokenKind.rightBracketToken){
				skip(s, rightBracketToken);
				break;
			}else if(s.curToken().kind == TokenKind.commaToken){
				skip(s, commaToken);
			}
		}
		leaveParser("list display");

		return t;
	}
	@Override
	void prettyPrint(){
		prettyWrite("[");
		for(int i = 0; i<aList.size(); i++){
			aList.get(i).prettyPrint();
			if(i!=aList.size()-1){
				prettyWrite(", ");
			}
		}
		prettyWrite("]");
	}
	@Override	
	public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
		ArrayList<RuntimeValue> rlist = new ArrayList<>();
		for(AspExpr i: aList){
			rlist.add(i.eval(curScope));	
		}
		return new RuntimeListValue(rlist);


	}
}
