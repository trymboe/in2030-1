package no.uio.ifi.asp.parser;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public abstract class AspSmallStmt extends AspSmallStmtList {
	AspSmallStmt(int n){
		super(n);
    }


	public static AspSmallStmt parse(Scanner s){
		AspSmallStmt stmt = null;
		enterParser("small stmt");
		switch (s.curToken().kind) {
			case returnToken:
				stmt = AspReturn.parse(s);
				break;
			case passToken:
				stmt = AspPass.parse(s);
				break;
			case nameToken:
				if(s.anyEqualToken()){
					stmt = AspAssignment.parse(s);
				}else{stmt = AspExprStmt.parse(s);
				}
				break;
			default:
				parserError("AspSmallStmt default reached. curtoken:" + s.curToken().kind, s.curLineNum());
			System.out.println("AspSmallStmt default skal ikke skje homieee");
	   }
		 		leaveParser("small stmt");
        return stmt;


    }
}
