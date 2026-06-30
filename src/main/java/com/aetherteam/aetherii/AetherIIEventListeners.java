package com.aetherteam.aetherii;

import com.aetherteam.aetherii.advancement.trigger.AetherIIAdvancementTriggers;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.client.event.hooks.BiomeHooks;
import com.aetherteam.aetherii.data.resources.registries.AetherIIDamageTypes;
import com.aetherteam.aetherii.effect.buildup.EffectBuildupPresets;
import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import com.aetherteam.aetherii.event.FreezeEvent;
import com.aetherteam.aetherii.event.hooks.BlockHooks;
import com.aetherteam.aetherii.event.hooks.PlayerHooks;
import com.aetherteam.aetherii.item.AetherIIConsumables;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.item.components.Consumable;
import com.aetherteam.aetherii.item.consumeeffect.ConsumeEffect;
import com.aetherteam.aetherii.recipe.recipes.AetherIIRecipeTypes;
import net.minecraft.advancements.Advancement;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.OnDatapackSyncEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.EntityMountEvent;
import net.minecraftforge.event.entity.EntityTravelToDimensionEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.*;
import net.minecraftforge.event.level.AlterGroundEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.event.level.ExplosionEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.TickEvent;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public class AetherIIEventListeners {
    public static void listen(IEventBus bus) {
        // Player
        bus.addListener(AetherIIEventListeners::onPlayerLogin);
        bus.addListener(AetherIIEventListeners::onPlayerLogout);
        bus.addListener(AetherIIEventListeners::onPlayerJoinLevel);
        bus.addListener(AetherIIEventListeners::onPlayerRespawn);
        bus.addListener(AetherIIEventListeners::onPlayerClone);
        bus.addListener(AetherIIEventListeners::onPlayerChangedDimension);
        bus.addListener(AetherIIEventListeners::onPlayerPostTick);
        bus.addListener(AetherIIEventListeners::onPlayerRightClickBlock);
        bus.addListener(AetherIIEventListeners::onPlayerEntityInteractSpecific);
        bus.addListener(AetherIIEventListeners::onPlayerCriticalHitAttack);
        bus.addListener(AetherIIEventListeners::onPlayerAdvancementProgression);
        bus.addListener(AetherIIEventListeners::onPlayerSetSpawn);
        bus.addListener(AetherIIEventListeners::canPlayerSleep);
        bus.addListener(AetherIIEventListeners::onPlayerWakeUp);
        bus.addListener(AetherIIEventListeners::onPlayerMount);
        bus.addListener(AetherIIEventListeners::onBreakSpeed);

        // Entity
        bus.addListener(EventPriority.HIGHEST, AetherIIEventListeners::onEntityPostTick);
        bus.addListener(AetherIIEventListeners::onEntitySpawn);
        bus.addListener(AetherIIEventListeners::onEntityTravelToDimension);
        bus.addListener(AetherIIEventListeners::onEntityCauseExplosion);
        bus.addListener(AetherIIEventListeners::onProjectileImpact);

        // Living
        bus.addListener(AetherIIEventListeners::onLivingPreDamaged);
        bus.addListener(AetherIIEventListeners::onLivingKnockBack);
        bus.addListener(AetherIIEventListeners::onLivingBlockAttack);
        bus.addListener(AetherIIEventListeners::onLivingItemUsed);
        bus.addListener(AetherIIEventListeners::onLivingDrops);
        bus.addListener(AetherIIEventListeners::onEffectRemove);

        // Block
        bus.addListener(AetherIIEventListeners::onBreakBlock);
        bus.addListener(AetherIIEventListeners::onBlockUpdateNeighbor);
        bus.addListener(AetherIIEventListeners::onModifyBlock);
        bus.addListener(AetherIIEventListeners::onAlterGround);
        bus.addListener(AetherIIEventListeners::onBlockFreeze);
        bus.addListener(AetherIIEventListeners::onBreatheInBlock);

        // Level
        bus.addListener(AetherIIEventListeners::onDatapackSync);
        bus.addListener(AetherIIEventListeners::onFurnaceFuelBurnTime);
    }

    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();

        AetherIIDataAttachments.get(player, AetherIIDataAttachments.PLAYER).login(player);
        AetherIIDataAttachments.get(player, AetherIIDataAttachments.AERBUNNY_MOUNT).login(player);
        AetherIIDataAttachments.get(player, AetherIIDataAttachments.ABILITY_BEHAVIOR).login(player);
        AetherIIDataAttachments.get(player, AetherIIDataAttachments.GUIDEBOOK_DISCOVERY).login(player);
        AetherIIDataAttachments.get(player, AetherIIDataAttachments.OUTPOST_TRACKER).login(player);
        BiomeHooks.sendColors(player);
    }

    public static void onPlayerLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        Player player = event.getEntity();

        AetherIIDataAttachments.get(player, AetherIIDataAttachments.PLAYER).logout(player);
        AetherIIDataAttachments.get(player, AetherIIDataAttachments.ABILITY_BEHAVIOR).logout(player);
    }

    public static void onPlayerJoinLevel(EntityJoinLevelEvent event) {
        Entity entity = event.getEntity();

        if (entity instanceof Player player) {
            AetherIIDataAttachments.get(player, AetherIIDataAttachments.PLAYER).onJoinLevel(player);
            AetherIIDataAttachments.get(player, AetherIIDataAttachments.DAMAGE_SYSTEM).onJoinLevel(player);
            AetherIIDataAttachments.get(player, AetherIIDataAttachments.ABILITY_BEHAVIOR).onJoinLevel(player);
        }
    }

    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        Player player = event.getEntity();

        AetherIIDataAttachments.get(player, AetherIIDataAttachments.OUTPOST_TRACKER).respawn(player);
    }

    public static void onPlayerClone(PlayerEvent.Clone event) {
        Player original = event.getOriginal();
        Player player = event.getEntity();
        boolean wasDeath = event.isWasDeath();

        AetherIIDataAttachments.get(player, AetherIIDataAttachments.GUIDEBOOK_DISCOVERY).clone(player);
    }

    public static void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        Player player = event.getEntity();
        ResourceKey<Level> to = event.getTo();

        AetherIIDataAttachments.get(player, AetherIIDataAttachments.PLAYER).changeDimension(player, to);
        AetherIIDataAttachments.get(player, AetherIIDataAttachments.ABILITY_BEHAVIOR).changeDimension(player);
        AetherIIDataAttachments.get(player, AetherIIDataAttachments.AERBUNNY_MOUNT).remountAerbunny(player);
    }

    public static void onBreakSpeed(PlayerEvent.BreakSpeed event) {
        Player player = event.getEntity();
        if (!AetherIIConfig.COMMON.allow_vanilla_equipment_in_aether.get()
                || player.level().dimension() != com.aetherteam.aetherii.data.resources.registries.AetherIIDimensions.AETHER_HOLY_ISLES_LEVEL
                || !BuiltInRegistries.BLOCK.getKey(event.getState().getBlock()).getNamespace().equals(AetherII.MODID)) {
            return;
        }
        float destroySpeed = player.getMainHandItem().getDestroySpeed(event.getState());
        if (destroySpeed > event.getNewSpeed()) {
            event.setNewSpeed(destroySpeed);
        }
    }

    public static void onPlayerPostTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }
        Player player = event.player;

        AetherIIDataAttachments.get(player, AetherIIDataAttachments.PLAYER).postTickUpdate(player);
        AetherIIDataAttachments.get(player, AetherIIDataAttachments.AERBUNNY_MOUNT).postTickUpdate(player);
        AetherIIDataAttachments.get(player, AetherIIDataAttachments.SWET_LATCH).postTickUpdate();
        AetherIIDataAttachments.get(player, AetherIIDataAttachments.ABILITY_BEHAVIOR).postTickUpdate(player);
        AetherIIDataAttachments.get(player, AetherIIDataAttachments.GUIDEBOOK_DISCOVERY).postTickUpdate(player);
        PlayerHooks.forceSpecialLoadingCrouch(player);
        PlayerHooks.mountAercloudEffects(player);
        PlayerHooks.skipAetherSleep(player);
    }

    public static void onPlayerRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        Player player = event.getEntity();
        Level level = event.getLevel();
        InteractionHand hand = event.getHand();
        ItemStack itemStack = event.getItemStack();
        BlockPos pos = event.getPos();
        Direction face = event.getFace();
        boolean cancelled = false;

        cancelled = PlayerHooks.playerActivatePortal(player, level, pos, face, itemStack, hand, cancelled);
        cancelled = PlayerHooks.cancelPlacementOnAercloud(player, level, pos, itemStack, cancelled);
        cancelled = PlayerHooks.snowlogBlock(player, level, pos, itemStack, hand, cancelled);
        cancelled = PlayerHooks.ferrositeMudBottleConversion(player, level, pos, itemStack, hand, face, cancelled);
        cancelled = PlayerHooks.interactWithMimicContainer(level, pos, cancelled);

        if (cancelled) {
            event.setCanceled(true);
        }
    }

    public static void onPlayerEntityInteractSpecific(PlayerInteractEvent.EntityInteractSpecific event) {
        Player player = event.getEntity();
        InteractionHand interactionHand = event.getHand();
        Entity targetEntity = event.getTarget();
        Optional<InteractionResult> result = Optional.empty();

        PlayerHooks.milkWithSkyrootBucket(targetEntity, player, interactionHand);
        PlayerHooks.feedCarrionSprout(event.getLevel(), targetEntity, player, interactionHand);
        PlayerHooks.useGoldenWyndberry(targetEntity, player, interactionHand);

        result = PlayerHooks.pickupBucketableTarget(targetEntity, player, interactionHand, result);

        if (result.isPresent()) {
            event.setCancellationResult(result.get());
            event.setCanceled(true);
        }
    }

    public static void onPlayerCriticalHitAttack(CriticalHitEvent event) {
        Player player = event.getEntity();
        float modifier = event.getDamageModifier();

        AetherIIDataAttachments.get(player, AetherIIDataAttachments.DAMAGE_SYSTEM).setCriticalDamageModifier(modifier);
    }

    public static void onPlayerAdvancementProgression(AdvancementEvent.AdvancementProgressEvent event) {
        Player player = event.getEntity();
        Advancement advancementHolder = event.getAdvancement();

        AetherIIDataAttachments.get(player, AetherIIDataAttachments.GUIDEBOOK_DISCOVERY).progressAdvancement(player, advancementHolder);
    }

    public static void onPlayerSetSpawn(PlayerSetSpawnEvent event) {
        Player player = event.getEntity();
        BlockPos pos = event.getNewSpawn();

        if (PlayerHooks.cancelBedrollSpawn(player, pos)) {
            event.setCanceled(true);
        }
    }

    public static void canPlayerSleep(PlayerSleepInBedEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) {
            return;
        }
        Level level = player.level();
        BlockPos pos = event.getPos();
        if (pos == null) {
            return;
        }
        BlockState state = level.getBlockState(pos);
        Player.BedSleepingProblem vanillaProblem = event.getResultStatus();

        event.setResult(PlayerHooks.handleBedrollSleeping(player, level, pos, state, vanillaProblem));
    }

    public static void onPlayerWakeUp(PlayerWakeUpEvent event) {
        Player player = event.getEntity();

        PlayerHooks.breakBedrollAfterSleeping(player);
    }

    public static void onPlayerMount(EntityMountEvent event) {
        Entity riderEntity = event.getEntityMounting();
        Entity mountEntity = event.getEntityBeingMounted();
        boolean isDismounting = event.isDismounting();
        event.setCanceled(PlayerHooks.dismountPrevention(riderEntity, mountEntity, isDismounting));
    }

    public static void onEntityPostTick(LivingEvent.LivingTickEvent event) {
        LivingEntity livingEntity = event.getEntity();

        AetherIIDataAttachments.get(livingEntity, AetherIIDataAttachments.DAMAGE_SYSTEM).postTickUpdate(livingEntity);
        AetherIIDataAttachments.get(livingEntity, AetherIIDataAttachments.EFFECTS_SYSTEM).postTickUpdate(livingEntity);
        AetherIIDataAttachments.get(livingEntity, AetherIIDataAttachments.ACCESSORIES).postTickUpdate(livingEntity);
    }

    public static void onEntitySpawn(MobSpawnEvent.SpawnPlacementCheck event) {
        EntityType<?> type = event.getEntityType();
        ServerLevelAccessor level = event.getLevel();
        BlockPos pos = event.getPos();
        ServerLevel serverLevel = level.getLevel();
        StructureManager structureManager = serverLevel.structureManager();
        var structureRegistry = serverLevel.registryAccess().lookupOrThrow(Registries.STRUCTURE);

        if (!type.builtInRegistryHolder().is(AetherIITags.EntityTypes.DUNGEON_MOBS)) {
            var dungeonStructures = structureRegistry.get(AetherIITags.Structures.DUNGEONS);
            if (dungeonStructures.isPresent()) {
                for (Holder<Structure> structure : dungeonStructures.get()) {
                    StructureStart structureStart = structureManager.getStructureAt(pos, structure.value());
                    if (structureStart.isValid() && structureManager.structureHasPieceAt(pos, structureStart)) {
                        event.setResult(MobSpawnEvent.SpawnPlacementCheck.Result.DENY);
                    }
                }
            }
        }
    }

    public static void onEntityTravelToDimension(EntityTravelToDimensionEvent event) {
        Entity entity = event.getEntity();
        ResourceKey<Level> dimension = event.getDimension();

        if (entity instanceof Player player && !player.level().dimension().equals(dimension)) {
            AetherIIDataAttachments.get(player, AetherIIDataAttachments.AERBUNNY_MOUNT).removeAerbunny();
        }
    }

    public static void onEntityCauseExplosion(ExplosionEvent.Detonate event) {
        Explosion explosion = event.getExplosion();
        Entity directSource = explosion.getDirectSourceEntity();
        Entity indirectSource = explosion.getIndirectSourceEntity();

        if (indirectSource != null && (indirectSource.getType() == AetherIIEntityTypes.DETONATION_SENTRY.get() || indirectSource.getType() == AetherIIEntityTypes.SENTRY_GOLEM.get())) {
            event.getAffectedEntities().removeIf((entity) -> entity instanceof ItemEntity);
            event.getAffectedEntities().forEach((entity) -> {
                if (entity instanceof LivingEntity livingEntity) {
                    if (!livingEntity.isBlocking()) {
                        AetherIIDataAttachments.get(livingEntity, AetherIIDataAttachments.EFFECTS_SYSTEM).addBuildup(livingEntity, indirectSource, directSource, EffectBuildupPresets.STUN, 150);
                    }
                }
            });
        }
    }

    public static void onProjectileImpact(ProjectileImpactEvent event) {
        HitResult hitResult = event.getRayTraceResult();
        Projectile projectile = event.getProjectile();

        if (hitResult instanceof EntityHitResult entityHitResult) {
            if (entityHitResult.getEntity() instanceof Player player) {
                AetherIIDataAttachments.get(player, AetherIIDataAttachments.PLAYER).stickProjectile(projectile, player);
            }
        }
    }

    public static void onLivingPreDamaged(LivingDamageEvent event) {
        LivingEntity target = event.getEntity();
        DamageSource source = event.getSource();
        float damage = event.getAmount();

        damage = AetherIIDataAttachments.get(target, AetherIIDataAttachments.DAMAGE_SYSTEM).getDamageTypeModifiedValue(target, source, damage);

        event.setAmount(damage);
    }

    public static void onLivingKnockBack(LivingKnockBackEvent event) {
        LivingEntity livingEntity = event.getEntity();

        if (!event.isCanceled() && AetherIIDataAttachments.get(livingEntity, AetherIIDataAttachments.DAMAGE_SYSTEM).cancelKnockback(livingEntity)) {
            event.setCanceled(true);
        }
    }

    public static void onLivingBlockAttack(ShieldBlockEvent event) {
        LivingEntity livingEntity = event.getEntity();
        DamageSource source = event.getDamageSource();
        double blockedDamage = event.getBlockedDamage();

        AetherIIDataAttachments.get(livingEntity, AetherIIDataAttachments.DAMAGE_SYSTEM).buildUpShieldStun(livingEntity, source.getEntity(), blockedDamage);
    }

    public static void onLivingItemUsed(LivingEntityUseItemEvent.Finish event) {
        ItemStack itemStack = event.getItem();
        Consumable consumable = AetherIIConsumables.get(itemStack);
        if (consumable != null) {
            for (Object effect : consumable.effects()) {
                if (effect instanceof ConsumeEffect consumeEffect) {
                    consumeEffect.apply(event.getEntity().level(), itemStack, event.getEntity());
                }
            }
        }

        if (event.getEntity() instanceof Player player) {
            PlayerHooks.valkyrieTeaAbility(player, itemStack);
        }
    }

    public static void onLivingDrops(LivingDropsEvent event) {
        LivingEntity entity = event.getEntity();
        Collection<ItemEntity> drops = event.getDrops();

        AetherIIDataAttachments.get(entity, AetherIIDataAttachments.ACCESSORIES).dropItems(entity, drops);
        if (entity instanceof Player player) {
            AetherIIDataAttachments.get(player, AetherIIDataAttachments.CURRENCY).dropAll(player, drops);
        }
    }

    public static void onEffectRemove(MobEffectEvent.Remove event) {
        LivingEntity livingEntity = event.getEntity();
        MobEffect effect = event.getEffect();
        ItemStack useItem = livingEntity.getUseItem();
        if (BuiltInRegistries.MOB_EFFECT.wrapAsHolder(effect).is(AetherIITags.MobEffects.MILK_DOESNT_CLEAR)
                && (useItem.is(Items.MILK_BUCKET) || useItem.is(AetherIIItems.SKYROOT_MILK_BUCKET.get()))) {
            event.setCanceled(true);
        }
    }

    public static void onBreakBlock(BlockEvent.BreakEvent event) {
        LevelAccessor level = event.getLevel();
        Player player = event.getPlayer();
        BlockPos pos = event.getPos();
        ItemStack stack = event.getPlayer().getMainHandItem();

        PlayerHooks.interactWithMimicContainer(level, pos, false);
        if (player instanceof ServerPlayer serverPlayer) {
            AetherIIAdvancementTriggers.ITEM_BREAK_BLOCK.get().trigger(serverPlayer, pos, stack);
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
        ToolAction toolAction = event.getToolAction();
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
        BlockHooks.modifyPodzolAlterGroundEvent(event);
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

    public static void onBreatheInBlock(LivingBreatheEvent event) {
        LivingEntity entity = event.getEntity();
        if (!BlockHooks.canBreathe(entity)) {
            event.setCanBreathe(false);
        }
    }

    public static void onDatapackSync(OnDatapackSyncEvent event) {
    }

    public static void onFurnaceFuelBurnTime(FurnaceFuelBurnTimeEvent event) {
        int burnTime = getAetherIIFuelBurnTime(event.getItemStack());
        if (burnTime > 0) {
            event.setBurnTime(burnTime);
        }
    }

    private static int getAetherIIFuelBurnTime(ItemStack stack) {
        if (stack.is(AetherIIItems.ARKENIUM_HESTVEIL_CANISTER.get())) {
            return 5000;
        } else if (stack.is(AetherIIItems.IRRADIATED_DUST.get())) {
            return 3500;
        } else if (stack.is(AetherIIItems.AMBROSIUM_SHARD.get())) {
            return 1600;
        } else if (stack.is(AetherIIBlocks.AMBROSIUM_BLOCK.get().asItem())) {
            return 16000;
        } else if (stack.is(AetherIIItems.SKYROOT_PINECONE.get())) {
            return 400;
        } else if (stack.is(AetherIIBlocks.AETHER_BUSH.get().asItem())) {
            return 100;
        } else if (stack.is(AetherIITags.Items.SKYROOT_LOGS)
                || stack.is(AetherIITags.Items.GREATROOT_LOGS)
                || stack.is(AetherIITags.Items.WISPROOT_LOGS)
                || stack.is(AetherIITags.Items.AMBEROOT_LOGS)) {
            return 300;
        } else if (stack.is(AetherIIBlocks.SKYROOT_PLANKS.get().asItem()) || stack.is(AetherIITags.Items.SKYROOT_DECORATIVE_BLOCKS)) {
            return 300;
        } else if (stack.is(AetherIIBlocks.GREATROOT_PLANKS.get().asItem()) || stack.is(AetherIITags.Items.GREATROOT_DECORATIVE_BLOCKS)) {
            return 300;
        } else if (stack.is(AetherIIBlocks.WISPROOT_PLANKS.get().asItem()) || stack.is(AetherIITags.Items.WISPROOT_DECORATIVE_BLOCKS)) {
            return 300;
        } else if (stack.is(AetherIIBlocks.AMBEROOT_PLANKS.get().asItem()) || stack.is(AetherIITags.Items.AMBEROOT_DECORATIVE_BLOCKS)) {
            return 300;
        } else if (stack.is(AetherIIBlocks.SKYROOT_BOOKSHELF.get().asItem())
                || stack.is(AetherIIBlocks.GREATROOT_BOOKSHELF.get().asItem())
                || stack.is(AetherIIBlocks.WISPROOT_BOOKSHELF.get().asItem())
                || stack.is(AetherIIBlocks.AMBEROOT_BOOKSHELF.get().asItem())) {
            return 300;
        } else if (stack.is(AetherIIItems.SKYROOT_SHORTSWORD.get())
                || stack.is(AetherIIItems.SKYROOT_HAMMER.get())
                || stack.is(AetherIIItems.SKYROOT_PIKE.get())
                || stack.is(AetherIIItems.SKYROOT_CROSSBOW.get())
                || stack.is(AetherIIItems.SKYROOT_PICKAXE.get())
                || stack.is(AetherIIItems.SKYROOT_AXE.get())
                || stack.is(AetherIIItems.SKYROOT_SHOVEL.get())
                || stack.is(AetherIIItems.SKYROOT_TROWEL.get())
                || stack.is(AetherIIBlocks.SKYROOT_TWIG.get().asItem())) {
            return 200;
        } else if (stack.is(AetherIIItems.SKYROOT_STICK.get())) {
            return 100;
        }
        return 0;
    }
}
