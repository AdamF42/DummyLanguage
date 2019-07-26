// Generated from /home/adamf42/Projects/Esercizio1/src/main/java/parser/CVM.g4 by ANTLR 4.7.2
package interpreter.parser;

import java.util.HashMap;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class CVMLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.7.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, PUSH=5, POP=6, ADD=7, ADDI=8, SUB=9, SUBI=10, 
		MULT=11, DIV=12, STOREW=13, LOADW=14, BRANCH=15, BRANCHEQ=16, BRANCHLESS=17, 
		BRANCHLESSEQ=18, BRANCHGREATER=19, BRANCHGREATEREQ=20, JR=21, JAL=22, 
		TOP=23, PRINT=24, HALT=25, LOADI=26, MOVE=27, REGISTER=28, LABEL=29, NUMBER=30, 
		WHITESP=31, ERR=32;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "T__2", "T__3", "PUSH", "POP", "ADD", "ADDI", "SUB", 
			"SUBI", "MULT", "DIV", "STOREW", "LOADW", "BRANCH", "BRANCHEQ", "BRANCHLESS", 
			"BRANCHLESSEQ", "BRANCHGREATER", "BRANCHGREATEREQ", "JR", "JAL", "TOP", 
			"PRINT", "HALT", "LOADI", "MOVE", "REGISTER", "LABEL", "NUMBER", "WHITESP", 
			"ERR"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'('", "')'", "':'", "'<-'", "'push'", "'pop'", "'add'", "'addi'", 
			"'sub'", "'subi'", "'mult'", "'div'", "'sw'", "'lw'", "'b'", "'beq'", 
			"'blr'", "'blre'", "'bgr'", "'bgre'", "'jr'", "'jal'", "'top'", "'print'", 
			"'halt'", "'li'", "'move'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, "PUSH", "POP", "ADD", "ADDI", "SUB", "SUBI", 
			"MULT", "DIV", "STOREW", "LOADW", "BRANCH", "BRANCHEQ", "BRANCHLESS", 
			"BRANCHLESSEQ", "BRANCHGREATER", "BRANCHGREATEREQ", "JR", "JAL", "TOP", 
			"PRINT", "HALT", "LOADI", "MOVE", "REGISTER", "LABEL", "NUMBER", "WHITESP", 
			"ERR"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	public int lexicalErrors=0;


	public CVMLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "CVM.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	@Override
	public void action(RuleContext _localctx, int ruleIndex, int actionIndex) {
		switch (ruleIndex) {
		case 31:
			ERR_action((RuleContext)_localctx, actionIndex);
			break;
		}
	}
	private void ERR_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 0:
			 System.err.println("Invalid char: "+ getText()); lexicalErrors++;  
			break;
		}
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\"\u00e0\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\7\3\7\3"+
		"\7\3\7\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3\13\3\13\3"+
		"\13\3\13\3\13\3\f\3\f\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\16\3\16\3\16\3\17"+
		"\3\17\3\17\3\20\3\20\3\21\3\21\3\21\3\21\3\22\3\22\3\22\3\22\3\23\3\23"+
		"\3\23\3\23\3\23\3\24\3\24\3\24\3\24\3\25\3\25\3\25\3\25\3\25\3\26\3\26"+
		"\3\26\3\27\3\27\3\27\3\27\3\30\3\30\3\30\3\30\3\31\3\31\3\31\3\31\3\31"+
		"\3\31\3\32\3\32\3\32\3\32\3\32\3\33\3\33\3\33\3\34\3\34\3\34\3\34\3\34"+
		"\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35"+
		"\3\35\3\35\3\35\3\35\5\35\u00bf\n\35\3\36\3\36\7\36\u00c3\n\36\f\36\16"+
		"\36\u00c6\13\36\3\37\3\37\5\37\u00ca\n\37\3\37\3\37\7\37\u00ce\n\37\f"+
		"\37\16\37\u00d1\13\37\5\37\u00d3\n\37\3 \6 \u00d6\n \r \16 \u00d7\3 \3"+
		" \3!\3!\3!\3!\3!\2\2\"\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27"+
		"\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31\61\32\63\33"+
		"\65\34\67\359\36;\37= ?!A\"\3\2\5\4\2C\\c|\5\2\62;C\\c|\5\2\13\f\17\17"+
		"\"\"\2\u00e9\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2"+
		"\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2"+
		"\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2"+
		"\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2"+
		"\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3"+
		"\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\3C\3\2\2\2\5E\3\2\2"+
		"\2\7G\3\2\2\2\tI\3\2\2\2\13L\3\2\2\2\rQ\3\2\2\2\17U\3\2\2\2\21Y\3\2\2"+
		"\2\23^\3\2\2\2\25b\3\2\2\2\27g\3\2\2\2\31l\3\2\2\2\33p\3\2\2\2\35s\3\2"+
		"\2\2\37v\3\2\2\2!x\3\2\2\2#|\3\2\2\2%\u0080\3\2\2\2\'\u0085\3\2\2\2)\u0089"+
		"\3\2\2\2+\u008e\3\2\2\2-\u0091\3\2\2\2/\u0095\3\2\2\2\61\u0099\3\2\2\2"+
		"\63\u009f\3\2\2\2\65\u00a4\3\2\2\2\67\u00a7\3\2\2\29\u00be\3\2\2\2;\u00c0"+
		"\3\2\2\2=\u00d2\3\2\2\2?\u00d5\3\2\2\2A\u00db\3\2\2\2CD\7*\2\2D\4\3\2"+
		"\2\2EF\7+\2\2F\6\3\2\2\2GH\7<\2\2H\b\3\2\2\2IJ\7>\2\2JK\7/\2\2K\n\3\2"+
		"\2\2LM\7r\2\2MN\7w\2\2NO\7u\2\2OP\7j\2\2P\f\3\2\2\2QR\7r\2\2RS\7q\2\2"+
		"ST\7r\2\2T\16\3\2\2\2UV\7c\2\2VW\7f\2\2WX\7f\2\2X\20\3\2\2\2YZ\7c\2\2"+
		"Z[\7f\2\2[\\\7f\2\2\\]\7k\2\2]\22\3\2\2\2^_\7u\2\2_`\7w\2\2`a\7d\2\2a"+
		"\24\3\2\2\2bc\7u\2\2cd\7w\2\2de\7d\2\2ef\7k\2\2f\26\3\2\2\2gh\7o\2\2h"+
		"i\7w\2\2ij\7n\2\2jk\7v\2\2k\30\3\2\2\2lm\7f\2\2mn\7k\2\2no\7x\2\2o\32"+
		"\3\2\2\2pq\7u\2\2qr\7y\2\2r\34\3\2\2\2st\7n\2\2tu\7y\2\2u\36\3\2\2\2v"+
		"w\7d\2\2w \3\2\2\2xy\7d\2\2yz\7g\2\2z{\7s\2\2{\"\3\2\2\2|}\7d\2\2}~\7"+
		"n\2\2~\177\7t\2\2\177$\3\2\2\2\u0080\u0081\7d\2\2\u0081\u0082\7n\2\2\u0082"+
		"\u0083\7t\2\2\u0083\u0084\7g\2\2\u0084&\3\2\2\2\u0085\u0086\7d\2\2\u0086"+
		"\u0087\7i\2\2\u0087\u0088\7t\2\2\u0088(\3\2\2\2\u0089\u008a\7d\2\2\u008a"+
		"\u008b\7i\2\2\u008b\u008c\7t\2\2\u008c\u008d\7g\2\2\u008d*\3\2\2\2\u008e"+
		"\u008f\7l\2\2\u008f\u0090\7t\2\2\u0090,\3\2\2\2\u0091\u0092\7l\2\2\u0092"+
		"\u0093\7c\2\2\u0093\u0094\7n\2\2\u0094.\3\2\2\2\u0095\u0096\7v\2\2\u0096"+
		"\u0097\7q\2\2\u0097\u0098\7r\2\2\u0098\60\3\2\2\2\u0099\u009a\7r\2\2\u009a"+
		"\u009b\7t\2\2\u009b\u009c\7k\2\2\u009c\u009d\7p\2\2\u009d\u009e\7v\2\2"+
		"\u009e\62\3\2\2\2\u009f\u00a0\7j\2\2\u00a0\u00a1\7c\2\2\u00a1\u00a2\7"+
		"n\2\2\u00a2\u00a3\7v\2\2\u00a3\64\3\2\2\2\u00a4\u00a5\7n\2\2\u00a5\u00a6"+
		"\7k\2\2\u00a6\66\3\2\2\2\u00a7\u00a8\7o\2\2\u00a8\u00a9\7q\2\2\u00a9\u00aa"+
		"\7x\2\2\u00aa\u00ab\7g\2\2\u00ab8\3\2\2\2\u00ac\u00ad\7&\2\2\u00ad\u00ae"+
		"\7c\2\2\u00ae\u00bf\7\62\2\2\u00af\u00b0\7&\2\2\u00b0\u00b1\7v\2\2\u00b1"+
		"\u00bf\7\63\2\2\u00b2\u00b3\7&\2\2\u00b3\u00b4\7u\2\2\u00b4\u00bf\7r\2"+
		"\2\u00b5\u00b6\7&\2\2\u00b6\u00b7\7h\2\2\u00b7\u00bf\7r\2\2\u00b8\u00b9"+
		"\7&\2\2\u00b9\u00ba\7c\2\2\u00ba\u00bf\7n\2\2\u00bb\u00bc\7&\2\2\u00bc"+
		"\u00bd\7t\2\2\u00bd\u00bf\7c\2\2\u00be\u00ac\3\2\2\2\u00be\u00af\3\2\2"+
		"\2\u00be\u00b2\3\2\2\2\u00be\u00b5\3\2\2\2\u00be\u00b8\3\2\2\2\u00be\u00bb"+
		"\3\2\2\2\u00bf:\3\2\2\2\u00c0\u00c4\t\2\2\2\u00c1\u00c3\t\3\2\2\u00c2"+
		"\u00c1\3\2\2\2\u00c3\u00c6\3\2\2\2\u00c4\u00c2\3\2\2\2\u00c4\u00c5\3\2"+
		"\2\2\u00c5<\3\2\2\2\u00c6\u00c4\3\2\2\2\u00c7\u00d3\7\62\2\2\u00c8\u00ca"+
		"\7/\2\2\u00c9\u00c8\3\2\2\2\u00c9\u00ca\3\2\2\2\u00ca\u00cb\3\2\2\2\u00cb"+
		"\u00cf\4\63;\2\u00cc\u00ce\4\62;\2\u00cd\u00cc\3\2\2\2\u00ce\u00d1\3\2"+
		"\2\2\u00cf\u00cd\3\2\2\2\u00cf\u00d0\3\2\2\2\u00d0\u00d3\3\2\2\2\u00d1"+
		"\u00cf\3\2\2\2\u00d2\u00c7\3\2\2\2\u00d2\u00c9\3\2\2\2\u00d3>\3\2\2\2"+
		"\u00d4\u00d6\t\4\2\2\u00d5\u00d4\3\2\2\2\u00d6\u00d7\3\2\2\2\u00d7\u00d5"+
		"\3\2\2\2\u00d7\u00d8\3\2\2\2\u00d8\u00d9\3\2\2\2\u00d9\u00da\b \2\2\u00da"+
		"@\3\2\2\2\u00db\u00dc\13\2\2\2\u00dc\u00dd\b!\3\2\u00dd\u00de\3\2\2\2"+
		"\u00de\u00df\b!\2\2\u00dfB\3\2\2\2\t\2\u00be\u00c4\u00c9\u00cf\u00d2\u00d7"+
		"\4\2\3\2\3!\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}