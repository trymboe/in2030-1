package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspExpr extends AspExprStmt {
    //-- Must be changed in part 2:
    ArrayList<AspAndTest> andTests = new ArrayList<>();

    AspExpr(int n) {
		super(n);
    }


    public static AspExpr parse(Scanner s) {
		enterParser("expr");
		AspExpr expr = new AspExpr(s.curLineNum());
		while(true){
			expr.andTests.add(AspAndTest.parse(s));
			if(s.curToken().kind != orToken) break; //Sjekker out
      		skip(s, orToken);
		}
		//System.out.println(s.curToken().kind);
		leaveParser("expr");
		return expr;
    }


    @Override
    public void prettyPrint(){
		int n =0;
		for( AspAndTest aat : andTests){
			if(n>0){
				Main.log.prettyWrite(" or ");
			}
			aat.prettyPrint();
			n++;
       }
    }


    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
		//if alone, return val
		//her skal det kjores OR logikk. 
		RuntimeValue v = andTests.get(0).eval(curScope);
		if(andTests.size()==1) return v;
		//else return first false
		for(int i = 0; i<andTests.size(); i++){
			v = andTests.get(i).eval(curScope);
			if (v.getBoolValue("and test", this)){
				return v; 
			}
			v = andTests.get(i).eval(curScope);
		}
		//hvis alle er false, return siste;
    	return v;}
}
