package com.aetherteam.aetherii.entity.variant;

import java.util.List;

public interface PriorityProvider<C, S> {
    List<Selector<C, S>> selectors();

    record Selector<C, S>(S condition, int priority) {
    }
}
