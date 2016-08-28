package com.codingmates.msgcodec.meta.loader;

import com.codingmates.msgcodec.meta.Codec;
import com.codingmates.msgcodec.meta.parser.Parser;
import com.codingmates.msgcodec.meta.parser.Tokenizer;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;

/**
 * A loader for {@link Codec}s.
 *
 * @author Gary Tierney
 */
public class CodecLoader {
    /**
     * Loads and returns a set of {@link Codec}s from one or more paths.
     *
     * @param paths One or more paths to parse {@link Codec}s from.
     * @return A {@code Set} of parsed {@code Codec}s.
     * @throws IOException If an error was encountered during reading.
     */
    public Set<Codec> load(Path... paths) throws IOException {
        Preconditions.checkArgument(paths.length > 0, "Must specify at least 1 path");

        ImmutableSet.Builder<Codec> codecs = ImmutableSet.builder();

        for (Path path : paths) {
            try (BufferedReader reader = Files.newBufferedReader(path)) {
                Tokenizer tokenizer = new Tokenizer(reader);
                Parser parser = new Parser(tokenizer);

                codecs.addAll(parser.parse());
            }
        }

        return codecs.build();
    }
}
