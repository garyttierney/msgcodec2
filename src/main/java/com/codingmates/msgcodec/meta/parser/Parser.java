package com.codingmates.msgcodec.meta.parser;

import com.codingmates.msgcodec.data.DataOrder;
import com.codingmates.msgcodec.data.DataTransformation;
import com.codingmates.msgcodec.data.DataType;
import com.codingmates.msgcodec.meta.Codec;
import com.codingmates.msgcodec.meta.attribute.UnresolvedAttribute;
import com.codingmates.msgcodec.meta.attribute.UnresolvedAttributeProperty;
import com.codingmates.msgcodec.meta.property.*;
import com.codingmates.msgcodec.util.LanguageUtil;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import java.io.Closeable;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

/**
 * Parses input from the {@link Tokenizer}.
 *
 * @author Toby Scrace
 */
public final class Parser implements Closeable {

    /**
     * The {@link Tokenizer} to read {@link Token}s from.
     */
    private final Tokenizer tokenizer;

    /**
     * The current {@link Token}.
     */
    private Token current;

    /**
     * Creates the Parser.
     *
     * @param tokenizer The {@link Tokenizer} to read {@link Token}s from.
     */
    public Parser(Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }

    @Override
    public void close() throws IOException {
        tokenizer.close();
    }

    /**
     * Completely parses the input from the {@link Tokenizer}.
     *
     * @return The {@link ImmutableSet} of parsed {@link Codec}s. Will never be {@code null}. May be empty.
     */
    public ImmutableSet<Codec> parse() {
        current = tokenizer.next();

        ImmutableSet.Builder<Codec> codecs = ImmutableSet.builder();
        while (current.getType() != Token.Type.END_OF_FILE) {
            codecs.add(parseCodec());
        }

        return codecs.build();
    }

    /**
     * Ensures the {@link #current} {@link Token} has the specified {@link Token.Type}..
     *
     * @param type The expected {@link Token.Type} of the {@link #current} {@link Token}. Must not be {@code null}.
     * @throws IllegalStateException If the {@link #current} {@link Token} is not of the expected type.
     */
    private void ensure(Token.Type type) {
        if (current.getType() != type) {
            String name = type.name().toLowerCase();
            String article = LanguageUtil.getIndefiniteArticle(name);

            throw new IllegalStateException("Expected " + article + " " + name + ", found " + current.getValue() + ".");
        }
    }

    /**
     * Ensures the {@link #current} {@link Token} has the specified {@link Token.Type} and one of the specified values.
     *
     * @param type   The expected {@link Token.Type} of the {@link #current} {@link Token}. Must not be {@code null}.
     * @param values The accepted values of the {@link #current} {@link Token}. Must not be {@code null}.
     * @throws IllegalStateException If the {@link #current} {@link Token} does not have the expected type or value.
     */
    private void ensure(Token.Type type, String... values) {
        if (current.getType() != type || !ImmutableSet.copyOf(values).contains(current.getValue())) {
            throw new IllegalStateException("Expected one of " + Arrays.toString(values) + ", found `" +
                    current.getValue() + "`.");
        }
    }

    /**
     * Skips past the {@link #current} {@link Token}, and ensures the next {@link Token} is of the specified
     * {@link Token.Type}.
     *
     * @param type The expected {@link Token.Type} of the next {@link Token}. Must not be {@code null}.
     * @throws IllegalStateException If the next {@link Token} does not have the expected {@link Token.Type}.
     */
    private void expect(Token.Type type) {
        next();
        ensure(type);
    }

    /**
     * Advances past the {@link #current} {@link Token}.
     */
    void next() {
        current = tokenizer.next();
    }

    /**
     * Parses an {@link UnresolvedAttribute}. Expects the {@link #current} {@link Token} to be the
     * {@link Token.Type#AT} symbol.
     *
     * @return The {@link UnresolvedAttribute}. Will never be {@code null}.
     */
    UnresolvedAttribute parseAttribute() {
        ensure(Token.Type.AT);
        next();

        String name = skip(Token.Type.IDENTIFIER);
        skip(Token.Type.OPEN_PAREN);

        ImmutableList.Builder<UnresolvedAttributeProperty> properties = ImmutableList.builder();

        while (current.getType() == Token.Type.IDENTIFIER) {
            UnresolvedAttributeProperty property = parseAttributeProperty();
            properties.add(property);

            if (current.getType() == Token.Type.CLOSE_PAREN) {
                break;
            }

            skip(Token.Type.COMMA);
        }

        skip(Token.Type.CLOSE_PAREN);

        return new UnresolvedAttribute(name, properties.build());
    }

    /**
     * Parses an {@link UnresolvedAttributeProperty}. Expects a key-value format, allowing values
     * to be either string literals or integers.
     *
     * @return The {@code UnresolvedAttribtueProperty}. Will never be {@code null}.
     * @throws IllegalStateException If an invalid value type was found.
     */
    private UnresolvedAttributeProperty parseAttributeProperty() {
        String name = skip(Token.Type.IDENTIFIER);
        skip(Token.Type.EQUALS);

        Token.Type type = current.getType();
        String value = current.getValue();
        next();

        switch (type) {
            case NUMBER:
                return new UnresolvedAttributeProperty(name, UnresolvedAttributeProperty.Type.INTEGER,
                        Long.valueOf(value));
            case STRING:
                return new UnresolvedAttributeProperty(name, UnresolvedAttributeProperty.Type.STRING,
                        value);
        }

        throw new IllegalStateException("Expected a number or string, found " + current.getType() + ".");
    }

    /**
     * Parses a {@link Codec}. Expects the {@link #current} {@link Token} to be the "codec"
     * {@link Token.Type#KEYWORD}, or the {@link Token.Type#AT} symbol representing
     * a codec attribute.
     *
     * @return The {@link Codec}. Will never be {@code null}.
     */
    private Codec parseCodec() {
        ImmutableList.Builder<UnresolvedAttribute> attributes = ImmutableList.builder();

        while (current.getType() == Token.Type.AT) {
            attributes.add(parseAttribute());
        }

        ensure(Token.Type.KEYWORD, "codec");
        next();

        String name = skip(Token.Type.IDENTIFIER);
        skip(Token.Type.OPEN_BRACE);

        ImmutableList.Builder<Property> values = ImmutableList.builder();

        while (current.getType() == Token.Type.IDENTIFIER) {
            Property property = parseValue();
            values.add(property);
        }

        skip(Token.Type.CLOSE_BRACE);
        return new Codec(name, attributes.build(), values.build());
    }

    /**
     * Attempts to parse a {@link DataOrder} from the specified input.
     *
     * @param input The input to attempt to parse as a {@link DataOrder}. Must not be {@code null}.
     * @return The {@link Optional} possibly containing the {@link DataOrder}. Will never be {@code null}.
     */
    private Optional<DataOrder> parseOrder(String input) {
        switch (input) {
            case "big":
                return Optional.of(DataOrder.BIG);
            case "little":
                return Optional.of(DataOrder.LITTLE);
            case "middle":
                return Optional.of(DataOrder.MIDDLE);
            case "inverse":
                return Optional.of(DataOrder.INVERSED_MIDDLE);
        }

        return Optional.empty();
    }

    /**
     * Attempts to parse a {@link DataTransformation} from the specified input.
     *
     * @param input The input to attempt to parse as a {@link DataTransformation}. Must not be {@code null}.
     * @return The {@link Optional} possibly containing the {@link DataTransformation}. Will never be {@code null}.
     */
    private Optional<DataTransformation> parseTransformation(String input) {
        switch (input) {
            case "added":
                return Optional.of(DataTransformation.ADD);
            case "subtracted":
                return Optional.of(DataTransformation.SUBTRACT);
            case "negated":
                return Optional.of(DataTransformation.NEGATE);
        }

        return Optional.empty();
    }

    /**
     * Parses a unit {@link Property}, which is a {@link IntegerProperty}, {@link StringProperty}, {@link Base37Property}, or
     * {@link DataProperty}.
     *
     * @param name The name of the {@link Property}. Must not be {@code null}.
     * @return The {@link Property}. Will never be {@code null}.
     */
    private Property parseUnit(String name) {
        switch (current.getType()) {
            case NUMBER: {
                long value = Long.parseLong(current.getValue());
                next();

                return new IntegerProperty(name, value);
            }
            case STRING: {
                String value = current.getValue();
                next();

                return new StringProperty(name, value);
            }
            case KEYWORD:
                String value = current.getValue();

                if (value.equals("base37")) {
                    skip(Token.Type.KEYWORD);
                    return new Base37Property(name);
                }

                Optional<DataOrder> order = parseOrder(value);
                if (order.isPresent()) {
                    expect(Token.Type.KEYWORD);
                    value = current.getValue();
                }

                Optional<DataTransformation> transformation = parseTransformation(value);
                if (transformation.isPresent()) {
                    expect(Token.Type.KEYWORD);
                    value = current.getValue();
                }

                ensure(Token.Type.KEYWORD, "i8", "i16", "i24", "i32", "i64", "u8", "u16", "u24", "u32", "u64");
                Signedness signedness = value.startsWith("i") ? Signedness.SIGNED : Signedness.UNSIGNED;

                int bytes = Integer.parseInt(value.substring(1)) / Byte.SIZE;
                DataType type = DataType.valueOf(bytes).get();

                skip(Token.Type.KEYWORD);
                return DataProperty.create(name, signedness, type, transformation, order);
        }

        throw new IllegalStateException("Expected a number, string, or type, found " + current.getType() + ".");
    }

    /**
     * Parses a {@link Property}. Expects the {@link #current} {@link Token} to be the name of the {@link Property}.
     *
     * @return The {@link Property}. Must not be {@code null}.
     */
    private Property parseValue() {
        String name = skip(Token.Type.IDENTIFIER);

        Token.Type type = current.getType();
        next();

        switch (type) {
            case COLON:
                return parseUnit(name);
            case OPEN_BRACE:
                ImmutableList.Builder<Property> contents = ImmutableList.builder();

                while (current.getType() == Token.Type.IDENTIFIER) {
                    Property property = parseValue();
                    contents.add(property);
                }

                skip(Token.Type.CLOSE_BRACE);
                return new CompositeProperty(name, contents.build());
        }

        throw new IllegalStateException("Expected a value (starting with `:` or `{`), found a " + type + ".");
    }

    /**
     * Skips the {@link #current} {@link Token}, if its {@link Token.Type} matches the specified {@link Token.Type}.
     *
     * @param type The expected {@link Token.Type} of the {@link #current} {@link Token}. Must not be {@code null}.
     * @return The value of the skipped {@link Token}. Will never be {@code null}.
     * @throws IllegalStateException If the current {@link Token} does not have the expected {@link Token.Type}.
     */
    private String skip(Token.Type type) {
        ensure(type);
        String value = current.getValue();
        next();
        return value;
    }

}
