package net.tapire_solutions.weirdoscheme;

import static org.junit.Assert.*;

import org.junit.Test;

public class ExpressionParserTest {

	@Test 
	public void tokenizeTest() {
		assertArrayEquals(new String[]{"(", ")"}, ExpressionParser.tokenize("()").toArray());
		assertArrayEquals(new String[]{"(", "bing", "(", ")"}, ExpressionParser.tokenize("(bing  ()").toArray());
	}
	
	@Test
	public void testInbalanedParentheses() {
		assertFalse(ExpressionParser.hasInbalancedParentheses(ExpressionParser.tokenize("(bing)")));
		assertFalse(ExpressionParser.hasInbalancedParentheses(ExpressionParser.tokenize("(bing)()")));
		assertFalse(ExpressionParser.hasInbalancedParentheses(ExpressionParser.tokenize("(bing(bong  (pang) peng))")));
		assertFalse(ExpressionParser.hasInbalancedParentheses(ExpressionParser.tokenize("( () ((())) ) (bing)")));
		assertFalse(ExpressionParser.hasInbalancedParentheses(ExpressionParser.tokenize("(   (   (   ())))")));
		
		assertTrue(ExpressionParser.hasInbalancedParentheses(ExpressionParser.tokenize("(bing()")));
		assertTrue(ExpressionParser.hasInbalancedParentheses(ExpressionParser.tokenize(")(bing)")));
		assertTrue(ExpressionParser.hasInbalancedParentheses(ExpressionParser.tokenize("(((bing)")));
		assertTrue(ExpressionParser.hasInbalancedParentheses(ExpressionParser.tokenize("(bing(())))")));
		assertTrue(ExpressionParser.hasInbalancedParentheses(ExpressionParser.tokenize("(bing))(")));
	}
	
	@Test
	public void testSplitExpressions() {
		assertEquals(0, ExpressionParser.splitExpressions(ExpressionParser.tokenize("bing bong)")).size());
		assertEquals(1, ExpressionParser.splitExpressions(ExpressionParser.tokenize("bing (bong)")).size());
		assertEquals(0, ExpressionParser.splitExpressions(ExpressionParser.tokenize("(bing bong")).size());
		assertEquals(1, ExpressionParser.splitExpressions(ExpressionParser.tokenize("(bing bong)")).size());
		assertEquals(2, ExpressionParser.splitExpressions(ExpressionParser.tokenize("(bing) (bong)")).size());
		assertEquals(0, ExpressionParser.splitExpressions(ExpressionParser.tokenize("bing bong")).size());
	}

}
