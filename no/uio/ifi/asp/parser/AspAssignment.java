package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
//import no.uio.ifi.asp.parser.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;


// name -> *subscription* ->> = - expr
public class AspAssignment extends AspSmallStmt{
	public AspName nam = null;

	AspExpr aEx;
	ArrayList<AspSubscription> subscriptions = new ArrayList<>();


    AspAssignment(int n){
        super(n);
	}


    public static AspAssignment parse(Scanner s){
		enterParser("assignment");
		AspAssignment aspAssignment = new AspAssignment(s.curLineNum());
		if(s.curToken().kind == nameToken){
			aspAssignment.nam = AspName.parse(s);
		}else{parserError("expected Name", s.curLineNum());
		}

		while(true){
			if(s.curToken().kind == equalToken) break;

			aspAssignment.subscriptions.add(AspSubscription.parse(s));

		}
		skip(s, equalToken);
		aspAssignment.aEx = AspExpr.parse(s);

		leaveParser("assignment");
		return aspAssignment;
    }

	public void prettyPrint(){
		int t = 0;
		nam.prettyPrint();
		for(AspSubscription sub: subscriptions){
			sub.prettyPrint();
		}
		prettyWrite(" = ");
		aEx.prettyPrint();
	}

	@Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue{
		RuntimeValue ex = aEx.eval(curScope);
		//Dette fungerer ikke. i hvert fall ikke foor
		if(subscriptions.size()==0) {
			curScope.assign(nam.name,ex);
		}else{
			RuntimeValue v = curScope.find(nam.name, this);
			for(int i=0; i<subscriptions.size()-1;i++){
				v=ex.evalSubscription(subscriptions.get(i).eval(curScope), this);
			}
			v.evalAssignElem(subscriptions.get(subscriptions.size()-1).eval(curScope), ex, this);
			
		}
		return null;
		}
}
