package com.aetherteam.aetherii;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.gameevent.GameEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AetherIIGameEvents {
    public static final DeferredRegister<GameEvent> GAME_EVENTS = DeferredRegister.create(Registries.GAME_EVENT, AetherII.MODID);

    public static final DeferredHolder<GameEvent, GameEvent> ICESTONE_FREEZABLE_UPDATE = GAME_EVENTS.register("icestone_freezable_update", () -> new GameEvent(4));
}
