package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspPass extends AspSmallStmt {
	AspPass(int n){
		super(n);
    }

	/**
	 * Parser AspPass ees og fyller in arraylist exprs og suites
	 * 
	 * @param s Scanner
	 * @return AspPass stms
	 */
	public static AspPass parse(Scanner s){
		enterParser("pass stmt");
		AspPass pa = new AspPass(s.curLineNum());
		skip(s, passToken);	
		leaveParser("pass stmt");
		return pa;

    }

    
    @Override
    public void prettyPrint() {
		prettyWrite("pass");
    }


    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
		trace("pass");
		return null;
    }

}
