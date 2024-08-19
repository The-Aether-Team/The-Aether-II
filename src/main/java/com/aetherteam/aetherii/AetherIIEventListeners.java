package com.aetherteam.aetherii;

import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.event.FreezeEvent;
import com.aetherteam.aetherii.event.hooks.BlockHooks;
import com.aetherteam.aetherii.event.hooks.PlayerHooks;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.portal.DimensionTransition;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.ItemAbility;
import net.neoforged.neoforge.event.entity.player.AdvancementEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.entity.player.PlayerRespawnPositionEvent;
import net.neoforged.neoforge.event.level.AlterGroundEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.level.SleepFinishedTimeEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.Optional;

public class AetherIIEventListeners {
    public static void listen(IEventBus bus) {
        // Player
        bus.addListener(AetherIIEventListeners::onPlayerLogin);
        bus.addListener(AetherIIEventListeners::onPlayerLogout);
        bus.addListener(AetherIIEventListeners::onPlayerRespawn);
        bus.addListener(AetherIIEventListeners::onPlayerPositionRespawn);
        bus.addListener(AetherIIEventListeners::onPlayerClone);
        bus.addListener(AetherIIEventListeners::onPlayerPostTick);
        bus.addListener(AetherIIEventListeners::onPlayerRightClickBlock);
        bus.addListener(AetherIIEventListeners::onPlayerEntityInteractSpecific);
        bus.addListener(AetherIIEventListeners::onPlayerAdvancementProgression);
        bus.addListener(AetherIIEventListeners::onPlayersFinishSleeping);

        // Entity
        bus.addListener(AetherIIEventListeners::onEntityPostTick);

        // Block
        bus.addListener(AetherIIEventListeners::onBlockUpdateNeighbor);
        bus.addListener(AetherIIEventListeners::onModifyBlock);
        bus.addListener(AetherIIEventListeners::onAlterGround);
        bus.addListener(AetherIIEventListeners::onBlockFreeze);
    }

    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();

        player.getData(AetherIIDataAttachments.PLAYER).login(player);
        player.getData(AetherIIDataAttachments.CURRENCY).login(player); //todo verify
        player.getData(AetherIIDataAttachments.GUIDEBOOK_DISCOVERY).login(player);
        player.getData(AetherIIDataAttachments.OUTPOST_TRACKER).login(player); //todo verify
    }

    public static void onPlayerLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        Player player = event.getEntity();

        player.getData(AetherIIDataAttachments.PLAYER).logout(player);
    }

    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        Player player = event.getEntity();

        player.getData(AetherIIDataAttachments.OUTPOST_TRACKER).respawn(player);
    }

    public static void onPlayerPositionRespawn(PlayerRespawnPositionEvent event) {
        Player player = event.getEntity();
        DimensionTransition transition;

        transition = player.getData(AetherIIDataAttachments.OUTPOST_TRACKER).findOutpostRespawnLocation(player);

        if (transition != null) {
            event.setDimensionTransition(transition);
        }
    }

    public static void onPlayerClone(PlayerEvent.Clone event) {
        Player player = event.getEntity();

        player.getData(AetherIIDataAttachments.GUIDEBOOK_DISCOVERY).clone(player);
    }

    public static void onPlayerPostTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();

        player.getData(AetherIIDataAttachments.PLAYER).postTickUpdate(player);
        player.getData(AetherIIDataAttachments.CURRENCY).postTickUpdate(player);
        player.getData(AetherIIDataAttachments.GUIDEBOOK_DISCOVERY).postTickUpdate(player);
        player.getData(AetherIIDataAttachments.OUTPOST_TRACKER).postTickUpdate(player);
        player.getData(AetherIIDataAttachments.SWET).postTickUpdate(player);
    }

    public static void onPlayerRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        Player player = event.getEntity();
        Level level = event.getLevel();
        InteractionHand interactionHand = event.getHand();
        ItemStack itemStack = event.getItemStack();
        BlockPos blockPos = event.getPos();
        Direction directionFace = event.getFace();
        boolean cancelled = false;

        cancelled = PlayerHooks.playerActivatePortal(player, level, blockPos, directionFace, itemStack, interactionHand, cancelled);
        cancelled = PlayerHooks.snowlogBlock(player, level, blockPos, itemStack, interactionHand, cancelled);

        if (cancelled) {
            event.setCanceled(true);
        }
    }

    public static void onPlayerEntityInteractSpecific(PlayerInteractEvent.EntityInteractSpecific event) {
        Player player = event.getEntity();
        Level level = event.getLevel();
        InteractionHand interactionHand = event.getHand();
        ItemStack itemStack = event.getItemStack();
        BlockPos localPos = event.getPos();
        Entity targetEntity = event.getTarget();
        Optional<InteractionResult> result = Optional.empty();

        PlayerHooks.milkWithSkyrootBucket(targetEntity, player, interactionHand);

        result = PlayerHooks.pickupBucketableTarget(targetEntity, player, interactionHand, result);

        if (result.isPresent()) {
            event.setCancellationResult(result.get());
            event.setCanceled(true);
        }
    }

    public static void onPlayerAdvancementProgression(AdvancementEvent.AdvancementProgressEvent event) {
        Player player = event.getEntity();
        AdvancementHolder advancementHolder = event.getAdvancement();

        player.getData(AetherIIDataAttachments.GUIDEBOOK_DISCOVERY).progressAdvancement(player, advancementHolder);
    }

    public static void onPlayersFinishSleeping(SleepFinishedTimeEvent event) {
        LevelAccessor level = event.getLevel();
        long newTime = event.getNewTime();

        PlayerHooks.resetAetherDayAndWeather(level, newTime);
    }

    public static void onEntityPostTick(EntityTickEvent.Post event) {
        Entity entity = event.getEntity();

        if (entity instanceof LivingEntity livingEntity) {
            livingEntity.getData(AetherIIDataAttachments.EFFECTS_SYSTEM).postTickUpdate(livingEntity);
        }
    }

    public static void onBlockUpdateNeighbor(BlockEvent.NeighborNotifyEvent event) {
        LevelAccessor levelAccessor = event.getLevel();
        BlockPos blockPos = event.getPos();
        boolean cancelled = false;

        BlockHooks.sendIcestoneFreezableUpdateEvent(levelAccessor, blockPos);

        cancelled = BlockHooks.activatePortalFromBlockUpdate(levelAccessor, blockPos, cancelled);

        if (cancelled) {
            event.setCanceled(true);
        }
    }

    public static void onModifyBlock(BlockEvent.BlockToolModificationEvent event) {
        LevelAccessor levelAccessor = event.getLevel();
        UseOnContext context = event.getContext();
        ItemAbility toolAction = event.getItemAbility();
        ItemStack itemStack = event.getHeldItemStack();
        BlockPos blockPos = event.getPos();
        BlockState oldState = event.getState();
        BlockState newState = oldState;

        if (!event.isSimulated() && !event.isCanceled()) {
            BlockHooks.stripMossyWisproot(levelAccessor, oldState, itemStack, toolAction, context);
            BlockHooks.stripAmberoot(levelAccessor, oldState, itemStack, toolAction, context);

            newState = AetherIIBlocks.registerBlockModifications(levelAccessor, toolAction, blockPos, oldState, newState);

            if (newState != oldState) {
                event.setFinalState(newState);
            }
        }
    }

    public static void onAlterGround(AlterGroundEvent event) {
        TreeDecorator.Context context = event.getContext();
        AlterGroundEvent.StateProvider provider = event.getStateProvider();

        event.setStateProvider(BlockHooks.modifyPodzolAlterGroundStateProvider(context, provider));
    }

    public static void onBlockFreeze(FreezeEvent.FreezeFromBlock event) {
        LevelAccessor level = event.getLevel();
        BlockPos sourcePos = event.getSourcePos();
        BlockPos pos = event.getPos();
        boolean cancelled = false;

        cancelled = BlockHooks.preventBlockFreezing(level, sourcePos, pos, cancelled);

        if (cancelled) {
            event.setCanceled(true);
        }
    }
}
