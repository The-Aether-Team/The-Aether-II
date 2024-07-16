package com.aetherteam.aetherii.event.hooks.attachment;

import com.aetherteam.aetherii.AetherIIConfig;
import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.attachment.player.DimensionTeleportationAttachment;
import com.aetherteam.aetherii.block.portal.AetherPortalShape;
import com.aetherteam.aetherii.event.listeners.WorldInteractionListener;
import com.aetherteam.aetherii.world.LevelUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.level.BlockEvent;

import javax.annotation.Nullable;
import java.util.Optional;

public class DimensionTeleportationHooks {
    public static boolean playerLeavingAether;
    public static boolean displayAetherTravel;
    public static int teleportationTimer;

    /**
     * Spawns the player in the Aether dimension if the {@link AetherIIConfig.Server#spawn_in_aether} config is enabled.
     *
     * @param player The {@link Player}.
     * @see WorldInteractionListener#onPlayerLogin(PlayerEvent.PlayerLoggedInEvent)
     */
    public static void startInAether(Player player) { //todo: port to new 1.21 portal system
//        var aetherIIPlayer = player.getData(AetherIIDataAttachments.PORTAL_TELEPORTATION);
//        if (AetherIIConfig.SERVER.spawn_in_aether.get()) {
//            if (aetherIIPlayer.canSpawnInAether()) { // Checks if the player has been set to spawn in the Aether.
//                if (player instanceof ServerPlayer serverPlayer) {
//                    MinecraftServer server = serverPlayer.level().getServer();
//                    if (server != null) {
//                        ServerLevel aetherLevel = server.getLevel(AetherIIDimensions.AETHER_HIGHLANDS_LEVEL);
//                        if (aetherLevel != null && serverPlayer.level().dimension() != AetherIIDimensions.AETHER_HIGHLANDS_LEVEL) {
//                            if (player.changeDimension(aetherLevel, new AetherPortalForcer(aetherLevel, false, true)) != null) {
//                                serverPlayer.setRespawnPosition(AetherIIDimensions.AETHER_HIGHLANDS_LEVEL, serverPlayer.blockPosition(), serverPlayer.getYRot(), true, false);
//                                aetherIIPlayer.setCanSpawnInAether(false); // Sets that the player has already spawned in the Aether.
//                            }
//                        }
//                    }
//                }
//            }
//        } else {
//            aetherIIPlayer.setCanSpawnInAether(false);
//        }
    }

    /**
     * Used to handle creating an Aether portal from a glowstone frame if the correct activation item is used.
     *
     * @param player    The {@link Player} creating the portal.
     * @param level     The {@link Level} to create the portal in.
     * @param pos       The {@link BlockPos} to create the portal at.
     * @param direction The {@link Direction} of where the portal is interacted at.
     * @param stack     The {@link ItemStack} used to attempt to activate the portal.
     * @param hand      The {@link InteractionHand} that the item is in.
     * @return Whether the portal should be created, as a {@link Boolean}.
     * @see WorldInteractionListener#onInteractWithPortalFrame(PlayerInteractEvent.RightClickBlock)
     */
    public static boolean createPortal(Player player, Level level, BlockPos pos, @Nullable Direction direction, ItemStack stack, InteractionHand hand) {
        if (direction != null) {
            BlockPos relativePos = pos.relative(direction);
            if (stack.is(AetherIITags.Items.AETHER_PORTAL_ACTIVATION_ITEMS)) { // Checks if the item can activate the portal.
                // Checks whether the dimension can have a portal created in it, and that the portal isn't disabled.
                if ((level.dimension() == LevelUtil.returnDimension() || level.dimension() == LevelUtil.destinationDimension())) {
                    Optional<AetherPortalShape> optional = AetherPortalShape.findEmptyAetherPortalShape(level, relativePos, Direction.Axis.X);
                    if (optional.isPresent()) {
                        optional.get().createPortalBlocks();
                        player.playSound(SoundEvents.BUCKET_EMPTY, 1.0F, 1.0F);
                        player.swing(hand);
                        if (!player.isCreative()) {
                            if (stack.getCount() > 1) {
                                stack.shrink(1);
                                player.addItem(stack.hasCraftingRemainingItem() ? stack.getCraftingRemainingItem() : ItemStack.EMPTY);
                            } else if (stack.isDamageableItem()) {
                                stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(hand));
                            } else {
                                player.setItemInHand(hand, stack.hasCraftingRemainingItem() ? stack.getCraftingRemainingItem() : ItemStack.EMPTY);
                            }
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Detects whether water is found in a glowstone frame.
     *
     * @param levelAccessor The {@link Level} to create the portal in.
     * @param pos           The {@link BlockPos} to create the portal at.
     * @param blockState    The water {@link BlockState}.
     * @param fluidState    The water {@link FluidState}.
     * @return Whether the portal should be created, as a {@link Boolean}.
     * @see WorldInteractionListener#onWaterExistsInsidePortalFrame(BlockEvent.NeighborNotifyEvent)
     */
    public static boolean detectWaterInFrame(LevelAccessor levelAccessor, BlockPos pos, BlockState blockState, FluidState fluidState) {
        if (levelAccessor instanceof Level level) {
            if (fluidState.is(Fluids.WATER) && fluidState.createLegacyBlock().getBlock() == blockState.getBlock()) {
                if ((level.dimension() == LevelUtil.returnDimension() || level.dimension() == LevelUtil.destinationDimension()) && !AetherIIConfig.SERVER.disable_aether_portal.get()) {
                    Optional<AetherPortalShape> optional = AetherPortalShape.findEmptyAetherPortalShape(level, pos, Direction.Axis.X);
                    if (optional.isPresent()) {
                        optional.get().createPortalBlocks();
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // GENERIC ATTACHMENT METHODS

    /**
     * @see DimensionTeleportationAttachment#onUpdate(Player)
     * @see DimensionTeleportationListener#onPlayerUpdate(LivingEvent.LivingTickEvent)
     */
    public static void update(LivingEntity entity) {
        if (entity instanceof Player player) {
            player.getData(AetherIIDataAttachments.DIMENSION_TELEPORTATION).onUpdate(player);
        }
    }
}