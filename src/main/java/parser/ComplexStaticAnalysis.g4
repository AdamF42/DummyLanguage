grammar ComplexStaticAnalysis;

// PARSER RULES

block		  : '{' statement* '}';

statement	  : assignment ';'
              | deletion ';'
              | print ';'
              | functioncall ';'
              | ifthenelse
              | declaration
              | block ;

assignment	  : ID '=' exp ;

deletion	  : 'delete' ID ;

print		  : 'print' exp ;

functioncall  : ID '(' (exp (',' exp)* )? ')' ;

ifthenelse 	  : 'if' '(' exp ')' 'then' block 'else' block ;

declaration	  : type ID '=' exp ';'                                                                 #varDec
		  	  | ID '(' ( parameter ( ',' parameter)* )? ')' block                                   #funDec;

type   		  : 'int'
        	  | 'bool'                                                                              ;

parameter  	  : ('var')? type ID ;

exp    		  :  ('-')? left=term (('+' | '-') right=exp)? ;

term   		  : left=factor (('*' | '/') right=term)? ;

factor 		  : left=value (op=ROP right=value)?
	          |   left=value (op=('&&' | '||') right=value)?                                        ;

value  		  : INTEGER                                                                             #intValue
     		  | ( 'true' | 'false' )                                                                #boolValue
      		  | '(' exp ')'                                                                         #expValue
		      | ID                                                                                  #idValue;
    
// LEXER RULES

ROP     : '==' | '>' | '<' | '<=' | '>=' | '!=' ;

//Numbers
fragment DIGIT : '0'..'9';    
INTEGER       : DIGIT+;

//IDs
fragment CHAR  : 'a'..'z' |'A'..'Z' ;
ID              : CHAR (CHAR | DIGIT)* ;

//ESCAPE SEQUENCES
WS              : (' '|'\t'|'\n'|'\r')-> skip;
LINECOMMENTS 	: '//' (~('\n'|'\r'))* -> skip;
BLOCKCOMMENTS   : '/*'( ~('/'|'*')|'/'~'*'|'*'~'/'|BLOCKCOMMENTS)* '*/' -> skip;

//LEXER ERRORS
ERR             : . -> channel(HIDDEN); 
