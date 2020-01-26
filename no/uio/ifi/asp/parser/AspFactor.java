package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspFactor extends AspSyntax{

	AspFactor(int n){
		super(n);
	}
	ArrayList<AspFactorPrefix> facFix = new ArrayList<>();
	ArrayList<AspPrimary> primarys = new ArrayList<>();
	ArrayList<AspFactorOpr> facoprs = new ArrayList<>();


	public static AspFactor parse(Scanner s){
		enterParser("factor");
		AspFactor af = new AspFactor(s.curLineNum());
		
		for(int i=0; true; i++){
			if(s.isFactorPrefix()){
				af.facFix.add(AspFactorPrefix.parse(s));	
			}else{af.facFix.add(null);}
			af.primarys.add(AspPrimary.parse(s));
			if(!(s.isFactorOpr())){
				break;
			}else{af.facoprs.add(AspFactorOpr.parse(s));}
		}

		leaveParser("factor");
		return af;
	}

	@Override
	public void prettyPrint(){
		for(int i=0; i<primarys.size(); i++){
			if(facFix.get(i) != null){
			   	facFix.get(i).prettyPrint();
			}
			primarys.get(i).prettyPrint();
			if(i<facoprs.size()){ 
				facoprs.get(i).prettyPrint();
			}

		}
	}

	/**
	 * evaluates factorization. 
	 *
	 * Her burde jeg gaa gjennom counters.
	 */
	@Override
	public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
		RuntimeValue rV = primarys.get(0).eval(curScope); //elsker campingbiler. 
			if(facFix.get(0)!=null){
				//enten minus eller pluss
				if(facFix.get(0).t.kind == minusToken){
					rV = rV.evalNegate(this);
				}else{
					rV = rV.evalPositive(this);
				}
			}	
		for(int i=1; i<primarys.size(); i++){
				RuntimeValue nxt=primarys.get(i).eval(curScope);
				//enten minus eller pluss
				if(facFix.get(i)!=null){
					if(facFix.get(i).t.kind == minusToken){
						nxt = nxt.evalNegate(this);
					}else{
						nxt = nxt.evalPositive(this);
					}
				}
			//handle primary, kommer garantert
			TokenKind curToken = facoprs.get(i-1).t.kind;
			switch(curToken){
				case astToken:
					rV = rV.evalMultiply(nxt, this);break;
				case slashToken:
					rV = rV.evalDivide(nxt, this);break;
				case percentToken:
					rV = rV.evalModulo(nxt, this);break;
				case doubleSlashToken:
					rV = rV.evalIntDivide(nxt, this); break;
				default:
					Main.panic("Factor did not get expected token.");
			}
		}
		return rV;
	}

}
