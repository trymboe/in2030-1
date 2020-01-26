package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspTermOpr extends AspSyntax{

	AspTermOpr(int n){
		super(n);
	}
	Token t;

	public static AspTermOpr parse(Scanner s){
		enterParser("term opr");
		AspTermOpr aTo= new AspTermOpr(s.curLineNum());

		aTo.t = s.curToken();
		//s.skip(s, factrortoken??); gidder ikke case
		if(s.isTermOpr()) s.readNextToken();

		leaveParser("term opr");
		return aTo; // skal returne aspnottest
	}


	@Override
	public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
		return null;
	}

	public void prettyPrint(){
		prettyWrite(t.toString());
	}
}
