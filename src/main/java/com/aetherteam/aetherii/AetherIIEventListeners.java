package com.aetherteam.aetherii;

import com.aetherteam.aetherii.advancement.trigger.AetherIIAdvancementTriggers;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.client.event.hooks.BiomeHooks;
import com.aetherteam.aetherii.client.event.hooks.RenderHooks;
import com.aetherteam.aetherii.data.resources.registries.AetherIIDamageTypes;
import com.aetherteam.aetherii.effect.buildup.EffectBuildupPresets;
import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import com.aetherteam.aetherii.event.FreezeEvent;
import com.aetherteam.aetherii.event.hooks.BlockHooks;
import com.aetherteam.aetherii.event.hooks.PlayerHooks;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.aetherteam.aetherii.recipe.recipes.AetherIIRecipeTypes;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.portal.TeleportTransition;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.ItemAbility;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.util.AttributeTooltipContext;
import net.neoforged.neoforge.event.AddAttributeTooltipsEvent;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.EntityMountEvent;
import net.neoforged.neoforge.event.entity.EntityTravelToDimensionEvent;
import net.neoforged.neoforge.event.entity.ProjectileImpactEvent;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.entity.player.*;
import net.neoforged.neoforge.event.level.AlterGroundEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.level.ExplosionEvent;
import net.neoforged.neoforge.event.level.block.BreakBlockEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.*;

public class AetherIIEventListeners {
    public static void listen(IEventBus bus) {
        // Player
        bus.addListener(AetherIIEventListeners::onPlayerLogin);
        bus.addListener(AetherIIEventListeners::onPlayerLogout);
        bus.addListener(AetherIIEventListeners::onPlayerJoinLevel);
        bus.addListener(AetherIIEventListeners::onPlayerRespawn);
        bus.addListener(AetherIIEventListeners::onPlayerPositionRespawn);
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
        bus.addListener(AetherIIEventListeners::onArmorDamaged);
        bus.addListener(AetherIIEventListeners::onPlayerMount);

        // Entity
        bus.addListener(AetherIIEventListeners::onEntityPostTick);
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

        // Item
        bus.addListener(EventPriority.LOWEST, AetherIIEventListeners::onAddTooltipsLowest);
        bus.addListener(AetherIIEventListeners::onAddAttributeTooltips);

        // Level
        bus.addListener(AetherIIEventListeners::onDatapackSync);
    }

    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();

        player.getData(AetherIIDataAttachments.PLAYER).login(player);
        player.getData(AetherIIDataAttachments.AERBUNNY_MOUNT).login(player);
        player.getData(AetherIIDataAttachments.ABILITY_BEHAVIOR).login(player);
        player.getData(AetherIIDataAttachments.GUIDEBOOK_DISCOVERY).login(player);
        player.getData(AetherIIDataAttachments.OUTPOST_TRACKER).login(player);
        BiomeHooks.sendColors(player);
    }

    public static void onPlayerLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        Player player = event.getEntity();

        player.getData(AetherIIDataAttachments.PLAYER).logout(player);
        player.getData(AetherIIDataAttachments.ABILITY_BEHAVIOR).logout(player);
    }

    public static void onPlayerJoinLevel(EntityJoinLevelEvent event) {
        Entity entity = event.getEntity();

        if (entity instanceof Player player) {
            player.getData(AetherIIDataAttachments.PLAYER).onJoinLevel(player);
            player.getData(AetherIIDataAttachments.DAMAGE_SYSTEM).onJoinLevel(player);
            player.getData(AetherIIDataAttachments.ABILITY_BEHAVIOR).onJoinLevel(player);
        }
    }

    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        Player player = event.getEntity();

        player.getData(AetherIIDataAttachments.OUTPOST_TRACKER).respawn(player);
    }

    public static void onPlayerPositionRespawn(PlayerRespawnPositionEvent event) {
        Player player = event.getEntity();
        TeleportTransition transition;

        transition = player.getData(AetherIIDataAttachments.OUTPOST_TRACKER).findOutpostRespawnLocation(player);

        if (transition != null) {
            event.setTeleportTransition(transition);
        }
    }

    public static void onPlayerClone(PlayerEvent.Clone event) {
        Player original = event.getOriginal();
        Player player = event.getEntity();
        boolean wasDeath = event.isWasDeath();

        player.getData(AetherIIDataAttachments.GUIDEBOOK_DISCOVERY).clone(player);
    }

    public static void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        Player player = event.getEntity();
        ResourceKey<Level> to = event.getTo();

        player.getData(AetherIIDataAttachments.PLAYER).changeDimension(player, to);
        player.getData(AetherIIDataAttachments.ABILITY_BEHAVIOR).changeDimension(player);
        player.getData(AetherIIDataAttachments.AERBUNNY_MOUNT.get()).remountAerbunny(player);
    }

    public static void onPlayerPostTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();

        player.getData(AetherIIDataAttachments.PLAYER).postTickUpdate(player);
        player.getData(AetherIIDataAttachments.AERBUNNY_MOUNT).postTickUpdate(player);
        player.getData(AetherIIDataAttachments.SWET_LATCH).postTickUpdate(player);
        player.getData(AetherIIDataAttachments.ABILITY_BEHAVIOR).postTickUpdate(player);
        player.getData(AetherIIDataAttachments.GUIDEBOOK_DISCOVERY).postTickUpdate(player);
        PlayerHooks.forceSpecialLoadingCrouch(player);
        PlayerHooks.mountAercloudEffects(player);
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
        float modifier = event.getDamageMultiplier();

        player.getData(AetherIIDataAttachments.DAMAGE_SYSTEM.get()).setCriticalDamageModifier(modifier);
    }

    public static void onPlayerAdvancementProgression(AdvancementEvent.AdvancementProgressEvent event) {
        Player player = event.getEntity();
        AdvancementHolder advancementHolder = event.getAdvancement();

        player.getData(AetherIIDataAttachments.GUIDEBOOK_DISCOVERY).progressAdvancement(player, advancementHolder);
    }

    public static void onPlayerSetSpawn(PlayerSetSpawnEvent event) {
        Player player = event.getEntity();
        BlockPos pos = event.getNewSpawn();

        if (PlayerHooks.cancelBedrollSpawn(player, pos)) {
            event.setCanceled(true);
        }
    }

    public static void canPlayerSleep(CanPlayerSleepEvent event) {
        ServerPlayer player = event.getEntity();
        Level level = event.getLevel();
        BlockPos pos = event.getPos();
        BlockState state = event.getState();
        Player.BedSleepingProblem vanillaProblem = event.getVanillaProblem();

        event.setProblem(PlayerHooks.handleBedrollSleeping(player, level, pos, state, vanillaProblem));
    }

    public static void onPlayerWakeUp(PlayerWakeUpEvent event) {
        Player player = event.getEntity();

        PlayerHooks.breakBedrollAfterSleeping(player);
    }

    public static void onArmorDamaged(ArmorHurtEvent event) {
        LivingEntity livingEntity = event.getEntity();
        Map<EquipmentSlot, ArmorHurtEvent.ArmorEntry> armorEntries = event.getArmorMap();

        if (livingEntity.getLastDamageSource() != null && livingEntity.getLastDamageSource().is(AetherIIDamageTypes.ALKAHEST)) {
            for (Map.Entry<EquipmentSlot, ArmorHurtEvent.ArmorEntry> entry : armorEntries.entrySet()) {
                ArmorHurtEvent.ArmorEntry armor = entry.getValue();
                if (armor.armorItemStack.has(AetherIIDataComponents.REINFORCEMENT_TIER)) {
                    event.setNewDamage(entry.getKey(), 0.0F);
                }
            }
        }
    }

    public static void onPlayerMount(EntityMountEvent event) {
        Entity riderEntity = event.getEntityMounting();
        Entity mountEntity = event.getEntityBeingMounted();
        boolean isDismounting = event.isDismounting();
        event.setCanceled(PlayerHooks.dismountPrevention(riderEntity, mountEntity, isDismounting));
    }

    public static void onEntityPostTick(EntityTickEvent.Post event) {
        Entity entity = event.getEntity();

        if (entity instanceof LivingEntity livingEntity) {
            livingEntity.getData(AetherIIDataAttachments.DAMAGE_SYSTEM).postTickUpdate(livingEntity);
            livingEntity.getData(AetherIIDataAttachments.EFFECTS_SYSTEM).postTickUpdate(livingEntity);
            livingEntity.getData(AetherIIDataAttachments.ACCESSORIES).postTickUpdate(livingEntity);
        }
    }

    public static void onEntitySpawn(MobSpawnEvent.SpawnPlacementCheck event) {
        EntityType<?> type = event.getEntityType();
        ServerLevelAccessor level = event.getLevel();
        BlockPos pos = event.getPos();
        ServerLevel serverLevel = level.getLevel();
        StructureManager structureManager = serverLevel.structureManager();
        Registry<Structure> structureRegistry = serverLevel.registryAccess().lookupOrThrow(Registries.STRUCTURE);

        if (!type.builtInRegistryHolder().is(AetherIITags.EntityTypes.DUNGEON_MOBS)) {
            for (Holder<Structure> structure : structureRegistry.getTagOrEmpty(AetherIITags.Structures.DUNGEONS)) {
                StructureStart structureStart = structureManager.getStructureAt(pos, structure.value());
                if (structureStart.isValid() && structureManager.structureHasPieceAt(pos, structureStart)) {
                    event.setResult(MobSpawnEvent.SpawnPlacementCheck.Result.FAIL);
                }
            }
        }
    }

    public static void onEntityTravelToDimension(EntityTravelToDimensionEvent event) {
        Entity entity = event.getEntity();
        ResourceKey<Level> dimension = event.getDimension();

        if (entity instanceof Player player && !player.level().dimension().equals(dimension)) {
            player.getData(AetherIIDataAttachments.AERBUNNY_MOUNT.get()).removeAerbunny();
        }
    }

    public static void onEntityCauseExplosion(ExplosionEvent.Detonate event) {
        ServerExplosion explosion = event.getExplosion();
        Entity directSource = explosion.getDirectSourceEntity();
        Entity indirectSource = explosion.getIndirectSourceEntity();

        if (indirectSource != null && (indirectSource.getType() == AetherIIEntityTypes.DETONATION_SENTRY.get() || indirectSource.getType() == AetherIIEntityTypes.SENTRY_GOLEM.get())) {
            event.getAffectedEntities().removeIf((entity) -> entity instanceof ItemEntity);
            event.getAffectedEntities().forEach((entity) -> {
                if (entity instanceof LivingEntity livingEntity) {
                    if (!livingEntity.isBlocking()) {
                        livingEntity.getData(AetherIIDataAttachments.EFFECTS_SYSTEM).addBuildup(livingEntity, indirectSource, directSource, EffectBuildupPresets.STUN, 150);
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
                player.getData(AetherIIDataAttachments.PLAYER.get()).stickProjectile(projectile, player);
            }
        }
    }

    public static void onLivingPreDamaged(LivingDamageEvent.Pre event) {
        LivingEntity target = event.getEntity();
        DamageSource source = event.getContainer().getSource();
        float damage = event.getContainer().getNewDamage();

        damage = target.getData(AetherIIDataAttachments.DAMAGE_SYSTEM).getDamageTypeModifiedValue(target, source, damage);

        event.getContainer().setNewDamage(damage);
    }

    public static void onLivingKnockBack(LivingKnockBackEvent event) {
        LivingEntity livingEntity = event.getEntity();

        if (!event.isCanceled() && livingEntity.getData(AetherIIDataAttachments.DAMAGE_SYSTEM).cancelKnockback(livingEntity)) {
            event.setCanceled(true);
        }
    }

    public static void onLivingBlockAttack(LivingShieldBlockEvent event) {
        LivingEntity livingEntity = event.getEntity();
        DamageSource source = event.getDamageSource();
        double blockedDamage = event.getBlockedDamage();

        livingEntity.getData(AetherIIDataAttachments.DAMAGE_SYSTEM).buildUpShieldStun(livingEntity, source.getEntity(), blockedDamage);
    }

    public static void onLivingItemUsed(LivingEntityUseItemEvent.Finish event) {
        ItemStack itemStack = event.getItem();

        if (event.getEntity() instanceof Player player) {
            PlayerHooks.valkyrieTeaAbility(player, itemStack);
        }
    }

    public static void onLivingDrops(LivingDropsEvent event) {
        LivingEntity entity = event.getEntity();
        Collection<ItemEntity> drops = event.getDrops();

        entity.getData(AetherIIDataAttachments.ACCESSORIES).dropItems(entity, drops);
        if (entity instanceof Player player) {
            player.getData(AetherIIDataAttachments.CURRENCY).dropAll(player, drops);
            PlayerHooks.trackDrops(drops);
        }
    }

    public static void onEffectRemove(MobEffectEvent.Remove event) {
        LivingEntity livingEntity = event.getEntity();
        Holder<MobEffect> effect = event.getEffect();
        if (effect.is(AetherIITags.MobEffects.MILK_DOESNT_CLEAR) && livingEntity.getUseItem().is(Tags.Items.BUCKETS_MILK)) {
            event.setCanceled(true);
        }
    }

    public static void onBreakBlock(BreakBlockEvent event) {
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

    public static void onBreatheInBlock(LivingBreatheEvent event) {
        LivingEntity entity = event.getEntity();
        if (!BlockHooks.canBreathe(entity)) {
            event.setCanBreathe(false);
        }
    }

    public static void onAddTooltipsLowest(ItemTooltipEvent event) {
        ItemStack itemStack = event.getItemStack();
        List<Component> itemTooltips = event.getToolTip();
        Item.TooltipContext context = event.getContext();
        TooltipFlag flag = event.getFlags();

        RenderHooks.addReinforcementTooltip(itemStack, itemTooltips, context, flag);
    }

    public static void onAddAttributeTooltips(AddAttributeTooltipsEvent event) {
        ItemStack itemStack = event.getStack();
        AttributeTooltipContext context = event.getContext();
        List<Component> tooltipLines = new ArrayList<>();

        RenderHooks.addAbilityAttributeTooltip(itemStack, tooltipLines, context);

        event.addTooltipLines(tooltipLines.toArray(Component[]::new));
    }

    public static void onDatapackSync(OnDatapackSyncEvent event) {
        event.sendRecipes(
                AetherIIRecipeTypes.ALKAHEST_PURIFICATION.get(),
                AetherIIRecipeTypes.ALKAHEST_CORROSION.get(),
                AetherIIRecipeTypes.ALTAR_ENCHANTING.get(),
                AetherIIRecipeTypes.AMBROSIUM_ENCHANTING.get(),
                AetherIIRecipeTypes.DUST_IRRADIATION.get(),
                AetherIIRecipeTypes.HOURGLASS_RESTORING.get(),
                AetherIIRecipeTypes.ICESTONE_FREEZABLE.get(),
                AetherIIRecipeTypes.SWET_GEL_CONVERSION.get());
    }
}
