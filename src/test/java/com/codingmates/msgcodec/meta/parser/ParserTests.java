package com.codingmates.msgcodec.meta.parser;

import com.codingmates.msgcodec.data.DataOrder;
import com.codingmates.msgcodec.data.DataTransformation;
import com.codingmates.msgcodec.data.DataType;
import com.codingmates.msgcodec.meta.attribute.UnresolvedAttribute;
import com.codingmates.msgcodec.meta.attribute.UnresolvedAttributeProperty;
import com.codingmates.msgcodec.meta.property.DataProperty;
import com.codingmates.msgcodec.meta.property.IntegerProperty;
import com.codingmates.msgcodec.meta.property.Signedness;
import com.codingmates.msgcodec.meta.property.StringProperty;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class ParserTests {

	private static Parser newParser(String input) {
		BufferedReader reader = new BufferedReader(new StringReader(input));
		Tokenizer tokenizer = new Tokenizer(reader);
		Parser parser = new Parser(tokenizer);
		parser.next(); // advance parser to first token

		return parser;
	}

	@Test
	public void attribute() throws Exception {
		Parser parser = newParser("@Test(key1=\"test\",key2=5)");
		UnresolvedAttribute attribute = parser.parseAttribute();

		Map<String, Object> propertyValues = attribute.properties().stream()
			.collect(Collectors.toMap(
				UnresolvedAttributeProperty::name, UnresolvedAttributeProperty::value));

		assertEquals("Test", attribute.name());
		assertEquals("test", propertyValues.get("key1"));
		assertEquals(5L, propertyValues.get("key2"));
	}

	@Test
	public void integerProperty() throws Exception {
		Parser parser = newParser("test: 10");
		IntegerProperty property = (IntegerProperty) parser.parseValue();

		assertEquals("test", property.getName());
		assertEquals(10L, property.getValue());
	}

	@Test
	public void stringProperty() throws Exception {
		Parser parser = newParser("test: \"val\"");
		StringProperty property = (StringProperty) parser.parseValue();

		assertEquals("test", property.getName());
		assertEquals("val", property.getValue());
	}

	@Test
	public void dataProperty() throws Exception {
		Parser parser = newParser("test: little negated u8");
		DataProperty property = (DataProperty) parser.parseValue();

		assertEquals("test", property.getName());
		assertEquals(DataOrder.LITTLE, property.getOrder());
		assertEquals(DataTransformation.NEGATE, property.getTransformation());
		assertEquals(DataType.BYTE, property.getType());
		assertEquals(Signedness.UNSIGNED, property.getSignedness());
	}
}