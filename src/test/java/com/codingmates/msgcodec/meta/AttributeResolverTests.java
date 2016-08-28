package com.codingmates.msgcodec.meta;

import com.codingmates.msgcodec.meta.attribute.Attribute;
import com.codingmates.msgcodec.meta.attribute.AttributeResolver;
import com.codingmates.msgcodec.meta.attribute.UnresolvedAttribute;
import com.codingmates.msgcodec.meta.attribute.UnresolvedAttributeProperty;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static com.codingmates.msgcodec.meta.attribute.UnresolvedAttributeProperty.Type.*;

public class AttributeResolverTests {
	public static class TestAttribute implements Attribute {
		private String testProperty;

		public void setTestProperty(String value) {
			this.testProperty = value;
		}

		public String getTestProperty() {
			return this.testProperty;
		}
	}

	@Test
	public void resolve() throws Exception {
		List<UnresolvedAttributeProperty> properties = ImmutableList.<UnresolvedAttributeProperty>builder()
			.add(new UnresolvedAttributeProperty("testProperty", STRING, "test"))
			.build();
		UnresolvedAttribute attribute = new UnresolvedAttribute("Test", properties);

		Map<String, Class<? extends Attribute>> attributeMap =
			ImmutableMap.<String, Class<? extends Attribute>>builder()
				.put("Test", TestAttribute.class)
				.build();

		AttributeResolver attributeResolver = new AttributeResolver(attributeMap);
		TestAttribute resolvedAttribute = (TestAttribute) attributeResolver.resolve(attribute);

		Assert.assertEquals("test", resolvedAttribute.getTestProperty());
	}
}