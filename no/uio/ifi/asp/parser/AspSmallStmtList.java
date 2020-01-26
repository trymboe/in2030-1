package no.uio.ifi.asp.parser;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspSmallStmtList extends AspStmt {
	AspSmallStmtList(int n){
		super(n);
    }

	ArrayList<AspSmallStmt> smallStmts = new ArrayList<>();

	public static AspSmallStmtList parse(Scanner s){
		AspSmallStmtList stmts = new AspSmallStmtList(s.curLineNum());
		enterParser("small stmt list");
		while(s.curToken().kind != newLineToken){
			if(s.curToken().kind == semicolonToken){
				skip(s, semicolonToken);} 
			stmts.smallStmts.add(AspSmallStmt.parse(s));
			if(s.curToken().kind == semicolonToken){
				skip(s, semicolonToken);} 
		}
	  	 skip(s, newLineToken);
		 leaveParser("small stmt list");
        return stmts;


    }


    @Override
    public void prettyPrint() {
		int counter = 0;
		for(AspSmallStmt s: smallStmts){
			s.prettyPrint();
			if(counter != smallStmts.size()-1) prettyWrite("; ");
			counter++;
		}prettyWriteLn();
    }



    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
	for(AspSmallStmt s: smallStmts){
		s.eval(curScope);
	}
	return null;
    }

}
