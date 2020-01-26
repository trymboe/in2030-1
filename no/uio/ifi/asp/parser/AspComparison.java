package no.uio.ifi.asp.parser;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspComparison extends AspSyntax {

	AspComparison(int n){
		super(n);
    }
	ArrayList<AspTerm> terms = new ArrayList<>();
	ArrayList<AspCompOpr> cOprs = new ArrayList<>();

	public static AspComparison parse(Scanner s){
		enterParser("comparison");
		AspComparison comp = new AspComparison(s.curLineNum());
		while(true){
			comp.terms.add(AspTerm.parse(s));
			if(!s.isCompOpr()){ 
				comp.cOprs.add(null);
				break;}
			comp.cOprs.add(AspCompOpr.parse(s));
		}
		leaveParser("comparison");
        return comp;


    }


    @Override
    public void prettyPrint() {
		for(int i=0; i<terms.size(); i++){
			terms.get(i).prettyPrint();
			if(cOprs.get(i)!=null){
				cOprs.get(i).prettyPrint();
			}
		}
		
    }


	/**
	 * Handles all the comparing.
	 *
	 * In case there are no comparing tokens, the term will be returned alone.
	 *
	 * @return runtimebool or a runtime term value
	 */
    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
		
		RuntimeValue alene = terms.get(0).eval(curScope);	//Hvis compopr.size=0,returner alene
		for(int i=0; i<terms.size()-1; i++){

			TokenKind curToken = cOprs.get(i).k.kind;
			RuntimeValue rBV = null; //Maa initialize pga switch og case
			RuntimeValue t1 = terms.get(i).eval(curScope), t2 = terms.get(i+1).eval(curScope);
			
			switch(curToken){
				case doubleEqualToken:
					rBV = t1.evalEqual(t2, this); break;
				case greaterToken:
					rBV= t1.evalGreater(t2, this); break;
				case greaterEqualToken:
					rBV= t1.evalGreaterEqual(t2, this); break;
				case lessToken:
					rBV = t1.evalLess(t2, this); break;
				case lessEqualToken:
					rBV = t1.evalLessEqual(t2, this); break;
				case notEqualToken:
					rBV = t1.evalNotEqual(t2, this); break;
				default:
					Main.panic("Panikk i aspComparison eval. exp: comp token, got: " + curToken);
			}	

			if(rBV.getBoolValue("??", this)){
				if(i==terms.size()-2){ //Det at der er -2 her har gaatt meg hus forbi. Skjonner ikke helt hvorfor, men det fikset koden saa kjorrr daaaa
					// System.out.print("Siste comparison slo ut");
					return new RuntimeBoolValue(true); //kanskje rbv? :)
				}
			}else{return new RuntimeBoolValue(false);
			}
		}
		return alene;
    }

}
