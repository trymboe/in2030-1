package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

abstract class AspPrimarySuffix extends AspPrimary{

	AspPrimarySuffix(int n){
		super(n);
	}

	public static AspPrimarySuffix parse(Scanner s){
		enterParser("primary suffix");
		AspPrimarySuffix aps = null;
		

		
		if(s.curToken().kind == TokenKind.leftParToken){
			aps = AspArgument.parse(s);
		}else if (s.curToken().kind == TokenKind.leftBracketToken){
			aps = AspSubscription.parse(s);
		}
	
		leaveParser("primary suffix");

		return aps;
	}
}



