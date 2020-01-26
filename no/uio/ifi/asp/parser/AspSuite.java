package no.uio.ifi.asp.parser;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspSuite extends AspSyntax {
	AspSuite(int n){
		super(n);
    }
	AspStmt smallstmt  = null;
	ArrayList<AspStmt> stmtliste = new ArrayList<>();
	
	/**
	 * parses suite
	 * 
	 * @param scanner object
	 * @return suite object
	 */
	public static AspSuite parse(Scanner s){
		enterParser("suite");
		AspSuite suite = new AspSuite(s.curLineNum());
		if(s.curToken().kind!=TokenKind.newLineToken){
				suite.stmtliste.add(AspSmallStmtList.parse(s));
				leaveParser("suite");
				return suite;
			}
		else{
			skip(s, newLineToken);
			skip(s, indentToken);
			while(s.curToken().kind!=dedentToken){
				suite.stmtliste.add(AspStmt.parse(s));
				
			}
			skip(s, dedentToken);
		}

		//System.out.println(s.curToken().kind);
		leaveParser("suite");
		return suite;
	}
    @Override
    public void prettyPrint() {
		prettyWriteLn();
		prettyIndent();
		for(AspStmt s: stmtliste){
			s.prettyPrint();
		}
		prettyDedent();
    }


    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
		for (int i=0; i<stmtliste.size(); i++){
			stmtliste.get(i).eval(curScope);
		}
		return null;
    }

}
