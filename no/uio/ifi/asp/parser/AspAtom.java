package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

abstract class AspAtom extends AspSyntax {


	AspAtom(int n) {
		super(n);
	}

	static AspAtom parse(Scanner s){
		enterParser("atom");

		AspAtom tmp = null;
		switch(s.curToken().kind){
			case nameToken:
				tmp = AspName.parse(s);
				break;
			case integerToken:
				tmp = AspInteger.parse(s);
				break;
			case floatToken:
				tmp = AspFloat.parse(s);
				break;
			case stringToken:
				tmp = AspString.parse(s);
				break;
			case trueToken:
				tmp = AspBool.parse(s);
				break;
			case falseToken:
				tmp = AspBool.parse(s);
				break;
			case noneToken:
				tmp = AspNone.parse(s);
				break;
			case leftParToken:
				tmp = AspInnerExpression.parse(s);
				break;
			case leftBracketToken:
				tmp = AspList.parse(s);
				break;
			case leftBraceToken:
				tmp = AspDict.parse(s);
				break;
			default:
				parserError(s.curToken().kind.toString() + "Expexted atom" + "got: " + s.curToken().kind, s.curLineNum());

		}
		leaveParser("atom");
		return tmp;
	}

	public String navn(){//hjelpemetode for aa hente navn i aspprimary
		return null;
	}
}


