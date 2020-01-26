package no.uio.ifi.asp.scanner;

import java.io.*;
import java.util.*;

import no.uio.ifi.asp.main.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class Scanner {
    private LineNumberReader sourceFile = null;
    private String curFileName;
    private ArrayList<Token> curLineTokens = new ArrayList<>();
    private Stack<Integer> indents = new Stack<>();
    private final int TABDIST = 4;

	// Farger for lettere feilsoking.  
	public static final String A_RESET = "\u001B[0m";
	public static final String A_BLACK = "\u001B[30m";
	public static final String A_RED = "\u001B[31m";
	public static final String A_GREEN = "\u001B[32m";




    public Scanner(String fileName) {
	curFileName = fileName;
	indents.push(0);

	try {
	    sourceFile = new LineNumberReader(
			    new InputStreamReader(
				new FileInputStream(fileName),
				"UTF-8"));

		//test
		System.out.println("Scanner blir opprettet med filnavn:" + curFileName);
	} catch (IOException e) {
	    scannerError("Cannot read " + fileName + "!");
	}
    }


    private void scannerError(String message) {
	String m = "Asp scanner error";
	if (curLineNum() > 0)
	    m += " on line " + curLineNum();
	m += ": " + message;

	Main.error(m);
    }


    public Token curToken() {
	while (curLineTokens.isEmpty()) {
	    readNextLine();
	}
	return curLineTokens.get(0);
    }


    public void readNextToken() {
	if (! curLineTokens.isEmpty())
	    curLineTokens.remove(0);
    }


    private void readNextLine() {
	curLineTokens.clear();

	// Read the next line:
	String line = null;
	try {
	    line = sourceFile.readLine();

		if(line == null){
			sourceFile.close();
			while(0<indents.peek()){
				indents.pop();
				Token tmp = new Token(dedentToken, curLineNum()); //Skal vare cur line num
				curLineTokens.add(tmp);
				Main.log.noteToken(tmp);
			}
			sourceFile = null;
			Token ferdig = new Token(eofToken, curLineNum());
			curLineTokens.add(ferdig);
			Main.log.noteToken(ferdig);
			return;
		}

		if (line.trim().isEmpty() || line.trim().startsWith("#")) {

			Main.log.noteSourceLine(curLineNum(), line);
            		return;
		}

		//2) Behandle indentering. FUNGERER
		line = expandLeadingTabs(line);
		int k = findIndent(line);
		if(k>indents.peek()){
			indents.push(k);
			curLineTokens.add(new Token(indentToken, curLineNum()));
		} else if (k<indents.peek()) {
			
			while(k<indents.peek()){
				indents.pop();
				curLineTokens.add(new Token(dedentToken, curLineNum()));
			}
		//------test indents------------------------
		if(k!= indents.peek()){
			scannerError("Feil med indents homie");
		}
	}

		
		

		

		line = line.split("#")[0]; //Fjerner comments

		int lLength=line.length();
		int pos = 0;		//possition
		String curWord = ""; 	//current word

		while(pos<lLength){
			if(line.charAt(pos) == ' '){pos++;}
			while(((pos<lLength)&& isLetterAZ(line.charAt(pos)))){
				curWord = curWord + line.charAt(pos);
				pos++;
				if(pos<lLength && isDigit(line.charAt(pos))){
					curWord = curWord + line.charAt(pos); pos++;
				}	
			}
			for(TokenKind t: TokenKind.values()){
				if(t.image.equals(curWord)){
					curLineTokens.add(new Token(t, curLineNum())); //mark curLineNum()
					curWord= "";
				}}
			if(curWord!=""){
				Token tmp = new Token(nameToken, curLineNum()); //mark curLineNum
				tmp.name = curWord;
				curLineTokens.add(tmp);
				curWord = "";
			}
			//Finner tall	
			boolean erFloat=false;
			while((pos<lLength) && (isDigit(line.charAt(pos)))){

				//her kan jeg sjekke om curword er "" og digit er 0
				if(curWord.isEmpty() && ('0' == line.charAt(pos))){	
					curWord = curWord + line.charAt(pos); pos++;
					break;
				}
				curWord = curWord + line.charAt(pos); pos++;
				if(pos<lLength){
					if(line.charAt(pos) == '.'){
						curWord = curWord + ".";
						pos++;	
						erFloat=true;	
					
				}
				
			}}
			//handling float and int tokens
			if((erFloat) && (curWord!="")){ 
				Token tmp = new Token(floatToken, curLineNum()); //mark curLineNum
				tmp.floatLit = Double.valueOf(curWord);
				curLineTokens.add(tmp);
				curWord = "";
				erFloat = false;
			}else if (curWord!=""){
				Token tmp = new Token(integerToken, curLineNum());//mark curLineNum
				tmp.integerLit = Integer.valueOf(curWord);
				curLineTokens.add(tmp);
				curWord = "";
				//pos++;
			}
		//Ser etter doble symboler
		for(TokenKind t: TokenKind.values()){
			if((pos<line.length()-1) && (t.image.equals("" +line.charAt(pos)+line.charAt(pos+1)))){
				curLineTokens.add(new Token(t, curLineNum()));
				pos = pos+2;

			}
		}
/*		//Ser etter doble symboler
		for(TokenKind t: TokenKind.values()){
			if((pos<line.length()-1) && (t.image.equals(line.charAt(pos)+line.charAt(pos+1)))){
				curLineTokens.add(new Token(t, curLineNum()));
				pos = pos+2;

			}
		}*/
		for(TokenKind t: TokenKind.values()){
			if(pos>=line.length()){	break;}
			if(t.image.equals("" + line.charAt(pos))){
				curLineTokens.add(new Token(t, curLineNum()));
			pos++;
			}
		}
		//Handling strings
		if((pos<line.length()) && (line.charAt(pos)=='"')){
			pos++; 
			while(line.charAt(pos)!='"'){
				curWord = curWord + line.charAt(pos); pos++;

				if(pos==line.length() && (line.charAt(pos)!='"')){
					scannerError("String not ended. Line: " + curLineNum());
				}
			}
			Token tmp = new Token(stringToken, curLineNum());
			tmp.stringLit = curWord; pos++;
			curLineTokens.add(tmp);
			curWord= "";


		
		}

		//handling string type 2
		if((pos<line.length()) && (line.charAt(pos)=='\'')){
			pos++; 
			while(line.charAt(pos)!='\''){
				curWord = curWord + line.charAt(pos); pos++;

				// error?
				if(pos==line.length() && (line.charAt(pos)!='\'')){
					scannerError("String not ended. Line: " + curLineNum());
				}
			}
			Token tmp = new Token(stringToken, curLineNum());
			tmp.stringLit = curWord; pos++;
			curLineTokens.add(tmp);
			curWord= "";
		
		}
		// System.out.println(curWord + "..." + line.charAt(pos) + ".." + pos);
		}
			
		//Avslutter.
	    if (line == null) {
		sourceFile.close();
		sourceFile = null;
		curLineTokens.add(new Token(eofToken, curLineNum()));


	    } else {
		Main.log.noteSourceLine(curLineNum(), line);
		for(Token t:curLineTokens){
			//Main.log.noteToken(t);
		}
	    }
	} catch (IOException e) {
	    sourceFile = null;
	    scannerError("Unspecified I/O error!");
	}


	// Terminate line:
	curLineTokens.add(new Token(newLineToken,curLineNum()));

	for (Token t: curLineTokens){
	    Main.log.noteToken(t);	
    	}
    }

	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++




    public int curLineNum() {
	return sourceFile!=null ? sourceFile.getLineNumber() : 0;
    }

    private int findIndent(String s) {
	int indent = 0;

	while (indent<s.length() && s.charAt(indent)==' ') indent++;
	return indent;
    }

    private String expandLeadingTabs(String s) {
	String newS = "";
	for (int i = 0;  i < s.length();  i++) {
	    char c = s.charAt(i);
	    if (c == '\t') {
		do {
		    newS += " ";
		} while (newS.length()%TABDIST > 0);
	    } else if (c == ' ') {
		newS += " ";
	    } else {
		newS += s.substring(i);
		break;
	    }
	}
	return newS;
    }


    private boolean isLetterAZ(char c) {
	return ('A'<=c && c<='Z') || ('a'<=c && c<='z') || (c=='_');
    }


    private boolean isDigit(char c) {
	return '0'<=c && c<='9';
    }


    public boolean isCompOpr() {
        switch (curToken().kind){
            case lessToken:
				return true;
            case greaterToken:
				return true;
            case doubleEqualToken:
				return true;
            case greaterEqualToken:
                return true;
            case lessEqualToken:
                return true;
            case notEqualToken:
                return true;
            default:
				return false;
	}
    }


    public boolean isFactorPrefix() {
	return plusMinus();
    }


    public boolean isFactorOpr() {
        switch (curToken().kind){
            case astToken:
				return true;
            case slashToken:
				return true;
            case percentToken:
				return true;
            case doubleSlashToken:
                return true;
            default:
				return false;
	}
    }
	

    public boolean isTermOpr() {
	return plusMinus();
    }

	
    public boolean anyEqualToken() {
	for (Token t: curLineTokens) {
	    if (t.kind == equalToken) return true;
	    if (t.kind == semicolonToken) return false;
	}
	return false;
    }

	public boolean plusMinus() {
		Token t = curLineTokens.get(0);
		if(t.kind == plusToken || t.kind == minusToken){
		   return true;
		}else{
			return false;}
	}
	
}
