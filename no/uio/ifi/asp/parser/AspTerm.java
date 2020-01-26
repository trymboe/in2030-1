package no.uio.ifi.asp.parser;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspTerm extends AspExpr{

	AspTerm(int n){
		super(n);
	}
	Token t;
	ArrayList<AspFactor> factors = new ArrayList<>();
	ArrayList<AspTermOpr> terms = new ArrayList<>();

	public static AspTerm parse(Scanner s){
		enterParser("term");
		AspTerm at= new AspTerm(s.curLineNum());

		
		while(true){
			at.factors.add(AspFactor.parse(s));
			if(s.isTermOpr()){	
				at.terms.add(AspTermOpr.parse(s));
			}else{
				at.terms.add(null);
				break;}
		}
		leaveParser("term");
		return at; 
	}

	//Hentet fra forelesingsnotater :) 
	@Override
	public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
		RuntimeValue v = factors.get(0).eval(curScope);
		for (int i = 1; i<factors.size(); i++) {
		TokenKind k = terms.get(i-1).t.kind;
				switch(k) {
					case minusToken:
						v = v.evalSubtract(factors.get(i).eval(curScope), this); break;
					case plusToken:
						v=v.evalAdd(factors.get(i).eval(curScope), this); break;
					default:
						Main.panic("Illigal term operator: " + k + "!");
					}
				}	
		return v;
	}

	public void prettyPrint(){
		for(int i=0; i<factors.size(); i++){
			factors.get(i).prettyPrint();
			if(terms.get(i)!=null){
				prettyWrite(" ");
				terms.get(i).prettyPrint();
				prettyWrite(" ");
			}
		}
	}
}
