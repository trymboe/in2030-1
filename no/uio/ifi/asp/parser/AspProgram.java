package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspProgram extends AspSyntax {
    //-- Must be changed in part 2:
    ArrayList<AspStmt> stmts = new ArrayList<>();

    AspProgram(int n) {
	super(n);
    }


    public static AspProgram parse(Scanner s) {
	enterParser("program");
	AspProgram ap = new AspProgram(s.curLineNum());
	int counter = 0;
	while (s.curToken().kind != eofToken) {
	    ap.stmts.add(AspStmt.parse(s));

//       //--errorstopper---------
//        if(counter>1000){
//		       System.out.println("Counter too high");
//			   break;
//		}
	}

	leaveParser("program");
	return ap;
    }


    @Override
    public void prettyPrint() {
		for(AspStmt s: stmts){
			s.prettyPrint();
		}
    }


    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
	for(AspStmt s: stmts){
		s.eval(curScope);
	}
	return null;
    }
}
