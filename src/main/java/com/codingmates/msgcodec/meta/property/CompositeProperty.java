package com.codingmates.msgcodec.meta.property;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

import java.util.List;

/**
 * A {@link Property} composed of other {@link Property}s.
 *
 * @author Toby Scrace
 */
public final class CompositeProperty extends Property {

	/**
	 * The {@link ImmutableList} of {@link Property}s this CompositeProperty consists of.
	 */
	private final ImmutableList<Property> contents;

	/**
	 * Creates the CompositeProperty.
	 *
	 * @param name The name of the CompositeProperty. Must not be {@code null}.
	 * @param contents The {@link List} of {@link Property}s the CompositeProperty contains. Must not be {@code null}.
	 */
	public CompositeProperty(String name, List<Property> contents) {
		super(name);
		this.contents = ImmutableList.copyOf(contents);
	}

	/**
	 * Gets the {@link ImmutableList} of {@link Property}s this CompositeProperty consists of.
	 *
	 * @return The {@link ImmutableList} of {@link Property}s. Will never be {@code null}. May be empty.
	 */
	public ImmutableList<Property> getContents() {
		return contents;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("name", name).add("contents", contents).toString();
	}
}
