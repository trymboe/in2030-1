package no.uio.ifi.asp.parser;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public abstract class AspStmt extends AspSyntax {
	AspStmt(int n){
		super(n);
    }

	/**
	 * parser AspStmt
	 * 
	 * @param scanner obeject
	 * @return stmt object
	 */
	public static AspStmt parse(Scanner s){
		AspStmt stmt = null;
		enterParser("stmt");
		switch (s.curToken().kind) {
			case defToken:
				stmt = AspCompStmt.parse(s);
				break;
			case ifToken:
				stmt = AspCompStmt.parse(s);
				break;
			case forToken:
				stmt = AspCompStmt.parse(s);
				break;
			case whileToken:
				stmt = AspCompStmt.parse(s);
				break;
			case returnToken:
				stmt = AspSmallStmtList.parse(s);
				break;
			case passToken:
				stmt = AspSmallStmtList.parse(s);
				break;
			case nameToken:
				stmt = AspSmallStmtList.parse(s);
				break;
			default:
				parserError("Aspstmt got: " + s.curToken().kind + " But expected something " +  
						"that fits with the trainrails ", s.curLineNum());
	   }
		 leaveParser("stmt");
		//System.out.println("curtoken: " + s.curToken().kind + " at stmt, line: " + s.curLineNum());
        return stmt;


    }

}
