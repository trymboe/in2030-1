package no.uio.ifi.asp.parser;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;


class AspPrimary extends AspSyntax{

	AspAtom atom;
	ArrayList<AspPrimarySuffix> pslist = new ArrayList<>();
	boolean funksjon = false;

	AspPrimary(int n) {
		super(n);
	}

	static AspPrimary parse(Scanner s)  {
		enterParser("primary");
		AspPrimary aspP = new AspPrimary(s.curLineNum());
		aspP.atom = AspAtom.parse(s);
		if(s.curToken().kind == leftParToken){
			aspP.funksjon = true;
		}
		while(s.curToken().kind == leftParToken || s.curToken().kind == leftBracketToken) {
			aspP.pslist.add(AspPrimarySuffix.parse(s));
		}
		leaveParser("primary");
		return aspP;

	}

	@Override
	void prettyPrint() {
		atom.prettyPrint();
		for(AspPrimarySuffix aPS: pslist){
			aPS.prettyPrint();
		}
	}

	@Override
	public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
		RuntimeValue rV = atom.eval(curScope);
		ArrayList<RuntimeValue> eArgs = null;
		RuntimeValue tmperatur;

		if(pslist.size()>0){
			//ok saa hvis det er flere subscriptions kan den evaluere
			//subscription av seg self for aa finne ut hva den egt skal ha
			//Vet ikke om det er garra at det er sub paa neste, sa sjekker
			//uansett
			//maa lage for arguments ogsaa.
			
			for(int i=0; i<pslist.size();i++){
				if(pslist.get(i) instanceof AspSubscription){
					RuntimeValue tmp = null;
					if(rV instanceof RuntimeListValue || rV instanceof RuntimeDictValue){
						tmp = pslist.get(i).eval(curScope);
					}
					rV=rV.evalSubscription(tmp, this);
				}else{ //arguments returnerer en liste, i motsettning til subscription
					tmperatur = pslist.get(i).eval(curScope); //runtimeListValue
					//System.out.println(pslist.get(i).eval(curScope));
					eArgs = tmperatur.getList("", this);
					
					trace("Call function " + atom.navn() + "with params: " + eArgs);
					rV = rV.evalFuncCall(eArgs, this);
				}
			}
		}
		//trace("Call function " + atom.navn() + "with params: " + eArgs);
		return rV;	
	}
}
