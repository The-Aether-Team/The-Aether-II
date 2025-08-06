package com.aetherteam.aetherii.client.renderer.block.model;

import com.google.common.base.Predicates;

import java.util.function.Predicate;

public class NamedModelProperty<T> extends net.neoforged.neoforge.model.data.ModelProperty<T> {
    private final String name;

    public NamedModelProperty(String name) {
        this(name, Predicates.alwaysTrue());
    }

    public NamedModelProperty(String name, Predicate<T> predicate) {
        super(predicate);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
