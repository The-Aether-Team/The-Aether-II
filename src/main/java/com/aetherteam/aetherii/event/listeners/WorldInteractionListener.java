package com.aetherteam.aetherii.event.listeners;

import com.aetherteam.aetherii.AetherIITags;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.level.AlterGroundEvent;

import java.util.concurrent.atomic.AtomicReference;

public class WorldInteractionListener {
    /**
     * @see com.aetherteam.aetherii.AetherII#eventSetup(IEventBus)
     */
    public static void listen(IEventBus bus) {
        bus.addListener(WorldInteractionListener::onAlterGround);
    }

    /**
     * Prevents Aether Dirt from being replaced by Podzol.
     */
    public static void onAlterGround(AlterGroundEvent event) {
        TreeDecorator.Context context = event.getContext();
        AlterGroundEvent.StateProvider provider = event.getStateProvider();
        event.setStateProvider((rand, pos) -> {
            AtomicReference<BlockState> oldState = new AtomicReference<>(); // Ground to replace.
            BlockState attemptedState = provider.getState(rand, pos); // Ground to maybe replace with.
            if (context.level().isStateAtPosition(pos, state -> {
                if (state.is(AetherIITags.Blocks.AETHER_DIRT)) {
                    oldState.set(state);
                    return true;
                } else {
                    return false;
                }
            })) {
                return attemptedState.is(Blocks.PODZOL) ? oldState.get() : attemptedState; // Ground to actually replace with.
            } else {
                return attemptedState;
            }
        });
    }
}