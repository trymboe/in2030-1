package no.uio.ifi.asp.parser;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspCompOpr extends AspSyntax {

	AspCompOpr(int n){
		super(n);
    }

	Token k = null;

	public static AspCompOpr parse(Scanner s){
		enterParser("comp opr");
		AspCompOpr a = new AspCompOpr(s.curLineNum());
		a.k = s.curToken();
		if(s.isCompOpr()){
			s.readNextToken();
		}
		leaveParser("comp opr");
        return a;


    }


    @Override
    public void prettyPrint() {
		prettyWrite(" ");
		prettyWrite(k.kind.image);	
		prettyWrite(" ");
    }



    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
	//-- Must be changed in part 4:
	return null;
    }

}
