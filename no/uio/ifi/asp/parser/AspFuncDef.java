package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
/**
 * Used to handle and parse funcdef
 */
public class AspFuncDef extends AspCompStmt {

    public AspName fnavn;
    public ArrayList<AspName> args = new ArrayList<>();
    private AspSuite aspSuite;

    AspFuncDef(int n) {
        super(n);
    }
	/**
	 * parsing funcdef
	 *
	 * @param Scanner s, Scanner object with tokens
	 * @return AspFuncDef object that is used later
	 *
	 */
	public static AspFuncDef parse(Scanner s){
    enterParser("func def");
		AspFuncDef afd = new AspFuncDef(s.curLineNum());
		skip(s, defToken);
		afd.fnavn = AspName.parse(s);
		skip(s, leftParToken);

		while(s.curToken().kind!=TokenKind.rightParToken){
			afd.args.add(AspName.parse(s));
			if(s.curToken().kind == TokenKind.commaToken){
				skip(s, commaToken);
			}
		}skip(s, rightParToken);
		skip(s, colonToken);
		afd.aspSuite = AspSuite.parse(s);

    leaveParser("func def");
		return afd;
	}
	public void prettyPrint(){
		prettyWrite("def " + fnavn.name + " (");
		String tmp = "";
		for(AspName n :args){
			tmp += n.name;
			tmp+=", ";
		}
		if(tmp!="") tmp = tmp.substring(0, tmp.length()-2);	//tmp = tmp - ", ";
		tmp = tmp + "):";
		prettyWrite(tmp);
		aspSuite.prettyPrint();
	}

	@Override	
	public RuntimeValue eval(RuntimeScope curScope){
		trace("func eval");
		AspFuncDef def = this;
		RuntimeFuncValue denne = new RuntimeFuncValue(fnavn.name, def, curScope);
		curScope.assign(fnavn.name,denne);
		return null;
	}

	
	public RuntimeValue evalFuncCall(RuntimeScope curScope){
		trace("evalFuncCall");
		RuntimeValue ve;
		try{
			aspSuite.eval(curScope);
		}catch(RuntimeReturnValue v){
			return v.hentValue();
		}

		return null;
		//return null; //null eller evnt none. SPiller d noe rolle?
	}
}
