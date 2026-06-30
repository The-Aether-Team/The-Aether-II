package com.aetherteam.aetherii.item;

import java.util.List;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.jetbrains.annotations.Contract;

import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.chat.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class UtilityBookBuilder {
    protected static final Style UNDERLINED = Style.EMPTY.withUnderlined(true);

    protected final ArrayList<Component> pages = new ArrayList<>();
    @Nullable
    protected final MinecraftServer server;
    protected String title = "missingno";
    protected String author = "";
    protected int generation = 0;

    public UtilityBookBuilder(MinecraftServer server) {
        this.server = Objects.requireNonNull(server);
    }

    UtilityBookBuilder() {
        this.server = null;
    }

    protected ParsedCommand parseCommand(String command) throws CommandSyntaxException {
        if (this.server != null) {
            ParseResults<CommandSourceStack> parseResults = this.server.getCommands().getDispatcher().parse(command, server.createCommandSourceStack());
            CommandSyntaxException exception = Commands.getParseException(parseResults);
            if (exception != null) {
                throw exception;
            }
        }
        return new ParsedCommand(command);
    }

    @Nullable
    @Contract("null->null;!null->!null")
    protected Component makeSectionNameComponent(@Nullable String sectionName) {
        if (sectionName == null) return null;
        return applySectionNameStyle(Component.literal(sectionName));
    }

    @Nullable
    @Contract("null->null;!null->!null;")
    protected MutableComponent applySectionNameStyle(@Nullable MutableComponent sectionName) {
        return sectionName == null ? null : sectionName.setStyle(UNDERLINED);
    }

    public Section section(@Nullable String sectionName) {
        return new Section(sectionName);
    }

    public Section section(@Nullable Component sectionName) {
        return new Section(sectionName);
    }

    public class Section {
        protected final Component initialPage;
        protected LineCounter lineCounter = new LineCounter();
        protected final int initialLineCount;
        @Nullable
        protected MutableComponent currentPage;
        protected int lineCount;

        public Section(@Nullable String sectionName) {
            this(makeSectionNameComponent(sectionName));
        }

        public Section(@Nullable Component sectionName) {
            if (sectionName == null) {
                initialPage = Component.empty();
                initialLineCount = 0;
            } else {
                initialPage = sectionName.copy();
                initialPage.visit(lineCounter);
                initialLineCount = lineCounter.lineCount;
            }
        }

        public Entry entry(String name) {
            return new Entry(name);
        }

        public Entry entry(Component name) {
            return new Entry(name.copy());
        }

        public Entry translatableEntry(String translationKey) {
            return new Entry(applySectionNameStyle(Component.translatable(translationKey)));
        }

        public Section entry(CommandEntry commandEntry) {
            return new Entry(commandEntry.name.copy()).command(commandEntry.command);
        }

        public class Entry {
            protected final MutableComponent name;

            public Entry(String name) {
                this(Component.literal(name));
            }

            public Entry(MutableComponent name) {
                this.name = name;
            }

            public Entry nameStyle(Style style) {
                name.setStyle(style);
                return this;
            }

            public Entry withNameStyle(Style style) {
                name.withStyle(style);
                return this;
            }

            public Section command(String command) throws CommandSyntaxException {
                return this.command(parseCommand(command));
            }

            public Section command(ParsedCommand command) {
                var line = Component.literal("[")
                    .append(name)
                    .append("]")
                    .withStyle(Style.EMPTY.withItalic(false).withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, command.commandString)));
                lineCounter.lineCount = 0;
                line.visit(lineCounter);
                if (lineCount == initialLineCount && lineCounter.lineCount + lineCount > 14) {
                    throw new IllegalArgumentException("A single entry took up more lines than there are available");
                }
                if (lineCount == 14) {
                    pages.add(currentPage);
                    currentPage = null;
                }
                if (currentPage == null) {
                    currentPage = initialPage.copy();
                    lineCount = initialLineCount;
                    if (lineCounter.lineCount + lineCount > 14) {
                        throw new IllegalArgumentException("A single entry took up more lines than there are available");
                    }
                }
                if (lineCount != 0) {
                    currentPage.append(CommonComponents.NEW_LINE);
                }
                currentPage.append(line);
                lineCount += lineCounter.lineCount;
                return Section.this;
            }
        }

        public <T> Section entries(Iterable<? extends T> values, Function<? super T, CommandEntry> toCommandEntry) {
            for (var value : values) {
                this.entry(toCommandEntry.apply(value));
            }
            return this;
        }

        public <T> Section entries(T[] values, Function<? super T, CommandEntry> toCommandEntry) {
            for (var value : values) {
                this.entry(toCommandEntry.apply(value));
            }
            return this;
        }

        public Section entries(Iterable<CommandEntry> commandEntries) {
            for (var commandEntry : commandEntries) {
                this.entry(commandEntry);
            }
            return this;
        }

        public Section entries(CommandEntry... commandEntries) {
            for (var commandEntry : commandEntries) {
                this.entry(commandEntry);
            }
            return this;
        }

        public Section entries(Stream<CommandEntry> commandEntries) {
            commandEntries.forEach(this::entry);
            return this;
        }

        public <T, X extends Throwable> Section entries(Iterable<? extends T> values, EntryCreator<? super T, ? extends X> biConsumer) throws X {
            Section section = this;
            for (var value : values) {
                section = biConsumer.accept(value, section);
            }
            return section;
        }

        public <T, X extends Throwable> Section entries(T[] values, EntryCreator<? super T, ? extends X> biConsumer) throws X {
            Section section = this;
            for (var value : values) {
                section = biConsumer.accept(value, section);
            }
            return section;
        }

        public <T, X extends Throwable> Section entries(Stream<? extends T> values, EntryCreator<? super T, ? extends X> biConsumer) throws X {
            Section section = this;
            var iter = values.iterator();
            while (iter.hasNext()) {
                section = biConsumer.accept(iter.next(), section);
            }
            return section;
        }

        public <X extends Throwable> Section entries(IntStream values, IntEntryCreator<? extends X> biConsumer) throws X {
            Section section = this;
            var iter = values.iterator();
            while (iter.hasNext()) {
                section = biConsumer.accept(iter.nextInt(), section);
            }
            return section;
        }

        public UtilityBookBuilder end() {
            if (currentPage != null) {
                if (lineCount > initialLineCount) {
                    pages.add(currentPage);
                }
                currentPage = null;
            }
            return UtilityBookBuilder.this;
        }

        public Section section(@Nullable String sectionName) {
            return this.end().section(sectionName);
        }

        public Section section(@Nullable Component sectionName) {
            return this.end().section(sectionName);
        }

        public ItemStack build() {
            return this.end().build();
        }

        @FunctionalInterface
        public interface EntryCreator<T, X extends Throwable> {
            Section accept(T t, Section section) throws X;
        }

        @FunctionalInterface
        public interface IntEntryCreator<X extends Throwable> {
            Section accept(int i, Section section) throws X;
        }
    }

    public static final class ParsedCommand {
        public final String commandString;

        private ParsedCommand(String commandString) {
            this.commandString = commandString;
        }
    }

    public record CommandEntry(Component name, ParsedCommand command) {
        public CommandEntry(String name, ParsedCommand command) {
            this(Component.literal(name), command);
        }

        public static CommandEntry translatable(String translationKey, ParsedCommand command) {
            return new CommandEntry(Component.translatable(translationKey), command);
        }
    }

    public UtilityBookBuilder title(String title) {
        this.title = title;
        return this;
    }

    public UtilityBookBuilder author(String author) {
        this.author = author;
        return this;
    }

    public UtilityBookBuilder generation(int generation) {
        if (generation >= 0 && generation <= 3) {
            this.generation = generation;
            return this;
        } else {
            throw new IllegalArgumentException("Generation was " + generation + ", but must be between 0 and 3");
        }
    }

    public ItemStack build() {
        ItemStack book = new ItemStack(Items.WRITTEN_BOOK);
        CompoundTag tag = book.getOrCreateTag();
        tag.putString("title", this.title);
        tag.putString("author", this.author);
        tag.putInt("generation", this.generation);
        ListTag pageTags = new ListTag();
        for (Component page : this.pages) {
            pageTags.add(StringTag.valueOf(Component.Serializer.toJson(page)));
        }
        tag.put("pages", pageTags);
        return book;
    }

    public static class LineCounter implements FormattedText.ContentConsumer<Void> {
        public int lineCount;

        @Override
        public Optional<Void> accept(String content) {
            if (content.isEmpty()) {
                return Optional.empty();
            }
            if (lineCount == 0) {   
                lineCount = 1;
            }
            for (int i = 0; i < content.length(); i++) {
                if (content.charAt(i) == '\n') {
                    lineCount++;
                }
            }
            return Optional.empty();
        }
    }
}
