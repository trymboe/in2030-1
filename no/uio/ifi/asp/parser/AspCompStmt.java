package no.uio.ifi.asp.parser;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public abstract class AspCompStmt extends AspStmt {
	AspCompStmt(int n){
		super(n);
    }


	public static AspCompStmt parse(Scanner s){
		AspCompStmt stmt = null;
		enterParser("compound stmt");
		switch (s.curToken().kind) {
			case defToken:
				stmt = AspFuncDef.parse(s);
				break;
			case ifToken:
				stmt = AspIf.parse(s);
				break;
			case whileToken:
				stmt = AspWhile.parse(s);
				break;
			case forToken:
				stmt = AspFor.parse(s);
				break;
			default:
				System.out.println("AspComp default skal ikke skje homieee");
	   }
		 		leaveParser("compound stmt");
        return stmt;


    }

}
