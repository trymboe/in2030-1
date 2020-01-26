package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspIf extends AspCompStmt {
	AspIf(int n){
		super(n);
    }
	ArrayList<AspExpr> exprs = new ArrayList<>();
	ArrayList<AspSuite> suites = new ArrayList<>();
	AspSuite elseSuite = null;

	/**
	 * Parser AspIf ees og fyller in arraylist exprs og suites
	 * 
	 * @param s Scanner
	 * @return AspIf stms
	 */
	public static AspIf parse(Scanner s){
		enterParser("if stmt");
		AspIf iff = new AspIf(s.curLineNum());
		skip(s, ifToken);
		iff.exprs.add(AspExpr.parse(s));
		skip(s, colonToken);
		iff.suites.add(AspSuite.parse(s));
		while(s.curToken().kind == TokenKind.elifToken){
			skip(s, elifToken);
			iff.exprs.add(AspExpr.parse(s));
			skip(s, colonToken);
			iff.suites.add(AspSuite.parse(s));
		}
		if(s.curToken().kind ==TokenKind.elseToken){
			skip(s, elseToken);
			skip(s, colonToken);
			iff.elseSuite = AspSuite.parse(s);
		}
		leaveParser("if stmt");
		return iff;

    }

    
    @Override
    public void prettyPrint() {

		boolean first = true;
		for(int i = 0; i< exprs.size(); i++){
			if(first){
				prettyWrite("if ");
				first=false;
			}else{
				prettyWrite("(elif:");
			}
			exprs.get(i).prettyPrint();	
			prettyWrite(":");
			suites.get(i).prettyPrint();
			if(elseSuite!= null){
				prettyWrite("else:");
				elseSuite.prettyPrint();
			}
		}
		
    }




    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
		trace("if stmt");
		for(int i=0; i<exprs.size(); i++){
			if(exprs.get(i).eval(curScope).getBoolValue("if stmt", this)){
				return suites.get(i).eval(curScope); //slutter paa forste true
			}
		}
		if(elseSuite!=null) return elseSuite.eval(curScope);
		return null; //?????????? kanskje returnere pass?
    }

}
