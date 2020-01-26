package no.uio.ifi.asp.parser;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import java.util.ArrayList;
import java.util.HashMap;
import static no.uio.ifi.asp.scanner.TokenKind.*;
class AspDict extends AspAtom{

	AspDict(int n){
		super(n);
	}

	ArrayList<AspString> keys = new ArrayList<>();
	ArrayList<AspExpr> value = new ArrayList<>();

	public static AspDict parse(Scanner s){
		enterParser("dict display");
		AspDict t = new AspDict(s.curLineNum());
		skip(s, leftBraceToken);

		while(s.curToken().kind != TokenKind.rightBraceToken){
			t.keys.add(AspString.parse(s));	
			skip(s, colonToken);
			t.value.add(AspExpr.parse(s));
			if(s.curToken().kind == commaToken){
				skip(s, commaToken);
			}
		}
		skip(s, rightBraceToken);
		leaveParser("dict display");
		return t;
	}

	void prettyPrint() {
		int i= 0;
		prettyWrite("{");
		while(i<keys.size()){
			keys.get(i).prettyPrint(); 
			prettyWrite(":");
			value.get(i).prettyPrint();
			if(i!=keys.size()-1){
				prettyWrite(", ");
			}
			i++;
		} prettyWrite("}");
		
	}
	/**
	 * makes a dictionary with runtime values.
	 *
	 * All the other stuff will be handled later in the runtime dict value.
	 *
	 * @return RuntimeDictValue
	 */
	@Override
	public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
		HashMap<String, RuntimeValue> dictionary = new HashMap<>(); 
		//hashmaps er rartr.. Vil ha runtimeStringValue i 0, men funker ikke
		for(int i = 0; i<keys.size(); i++){
			dictionary.put(keys.get(i).eval(curScope).getStringValue("", this), value.get(i).eval(curScope));
		}
		return new RuntimeDictValue(dictionary);
	}
}


