package com.aetherteam.aetherii.event.listeners.attachment;

import com.aetherteam.aetherii.event.hooks.attachment.DimensionTeleportationHooks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

public class DimensionTeleportationListener {
    /**
     * @see com.aetherteam.aetherii.AetherII#eventSetup(IEventBus)
     */
    public static void listen(IEventBus bus) {
        bus.addListener(DimensionTeleportationListener::onPlayerLogin);
        bus.addListener(DimensionTeleportationListener::onInteractWithPortalFrame);
        bus.addListener(DimensionTeleportationListener::onWaterExistsInsidePortalFrame);
        bus.addListener(DimensionTeleportationListener::onPlayerUpdate);
    }

    /**
     * @see DimensionTeleportationHooks#startInAether(Player)
     */
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();
        DimensionTeleportationHooks.startInAether(player);
    }

    /**
     * @see DimensionTeleportationHooks#createPortal(Player, Level, BlockPos, Direction, ItemStack, InteractionHand)
     */
    public static void onInteractWithPortalFrame(PlayerInteractEvent.RightClickBlock event) {
        Player player = event.getEntity();
        Level level = event.getLevel();
        BlockPos blockPos = event.getPos();
        Direction direction = event.getFace();
        ItemStack itemStack = event.getItemStack();
        InteractionHand interactionHand = event.getHand();
        if (DimensionTeleportationHooks.createPortal(player, level, blockPos, direction, itemStack, interactionHand)) {
            event.setCanceled(true);
        }
    }

    /**
     * @see DimensionTeleportationHooks#detectWaterInFrame(LevelAccessor, BlockPos, BlockState, FluidState)
     */
    public static void onWaterExistsInsidePortalFrame(BlockEvent.NeighborNotifyEvent event) {
        LevelAccessor level = event.getLevel();
        BlockPos blockPos = event.getPos();
        BlockState blockState = level.getBlockState(blockPos);
        FluidState fluidState = level.getFluidState(blockPos);
        if (DimensionTeleportationHooks.detectWaterInFrame(level, blockPos, blockState, fluidState)) {
            event.setCanceled(true);
        }
    }

    // GENERIC ATTACHMENT METHODS

    /**
     * @see DimensionTeleportationHooks#update(LivingEntity)
     */
    public static void onPlayerUpdate(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        DimensionTeleportationHooks.update(player);
    }
}