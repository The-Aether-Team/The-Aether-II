package com.aetherteam.aetherii.block.fluid;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.attachment.level.AlkahestBreakageAttachment;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.block.AetherIIFluids;
import com.aetherteam.aetherii.client.particle.AetherIIParticleTypes;
import com.aetherteam.aetherii.client.sound.AetherIISoundEvents;
import com.aetherteam.aetherii.data.resources.registries.AetherIIDamageTypes;
import com.aetherteam.aetherii.integration.AccessoryUtil;
import com.aetherteam.aetherii.inventory.container.AccessoryContainer;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.aetherteam.aetherii.mixin.MixinHooks;
import com.aetherteam.aetherii.network.packet.clientbound.AlkahestFizzPacket;
import com.aetherteam.aetherii.network.packet.clientbound.AlkahestItemSmokePacket;
import com.aetherteam.aetherii.recipe.input.SingleRecipeInputWithRandom;
import com.aetherteam.aetherii.recipe.recipes.AetherIIRecipeTypes;
import com.aetherteam.aetherii.recipe.recipes.block.AlkahestCorrosionRecipe;
import com.aetherteam.aetherii.recipe.recipes.item.AlkahestPurificationRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.network.PacketDistributor;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public abstract class AlkahestFluid extends BaseFlowingFluid implements CanisterFluid {
    public AlkahestFluid(Properties properties) {
        super(properties);
    }

    @Override
    public void tick(ServerLevel level, BlockPos pos, BlockState blockState, FluidState fluidState) {
        super.tick(level, pos, blockState, fluidState);
        this.applyGravity(level, pos, fluidState);
        this.corrodeNeighbors(level, pos);
        this.damageFromStalactite(level, pos, pos.below(2), fluidState);
        this.damageFromSource(level, pos, pos.below(), fluidState);
    }

    @Override
    protected void randomTick(ServerLevel level, BlockPos pos, FluidState fluidState, RandomSource random) {
        super.randomTick(level, pos, fluidState, random);
        level.scheduleTick(pos, this, this.getTickDelay(level));
    }

    private void applyGravity(ServerLevel level, BlockPos pos, FluidState fluidState) {
        BlockState blockState = level.getBlockState(pos);
        if (fluidState.isSource()) {
            BlockPos belowPos = pos.below();
            BlockState belowState = level.getBlockState(belowPos);
            FluidState belowFluid = level.getFluidState(belowPos);
            if (belowState.isAir() || (belowState.is(this.createLegacyBlock(fluidState).getBlock()) && !belowFluid.isSource())) {
                level.setBlock(belowPos, blockState, 3);
                level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
            }
        }
    }

    private void corrodeNeighbors(ServerLevel level, BlockPos pos) {
        for (Direction direction : Direction.values()) {
            BlockPos offsetPos = pos.offset(direction.getUnitVec3i());
            BlockState offsetState = level.getBlockState(offsetPos);
            for (RecipeHolder<AlkahestCorrosionRecipe> recipe : level.recipeAccess().recipeMap().byType(AetherIIRecipeTypes.ALKAHEST_CORROSION.get())) {
                BlockState newState = recipe.value().getResultState(offsetState);
                if (recipe.value().matches(null, level, offsetPos, null, offsetState, newState, AetherIIRecipeTypes.ALKAHEST_CORROSION.get())) {
                    if (recipe.value().convert(level, offsetPos, newState, recipe.value().getFunction())) {
                        PacketDistributor.sendToPlayersInDimension(level, new AlkahestFizzPacket(pos, direction.getOpposite(), Vec3.ZERO));
                    }
                }
            }
        }
    }

    private void damageFromStalactite(ServerLevel level, BlockPos fluidPos, BlockPos blockPos, FluidState fluidState) {
        if (fluidState.isSource()) {
            BlockState belowState = level.getBlockState(blockPos);
            if (PointedDripstoneBlock.isStalactiteStartPos(belowState, level, blockPos)) {
                BlockPos stalactiteTipPos = PointedDripstoneBlock.findTip(belowState, level, blockPos, 11, false);
                if (stalactiteTipPos != null) {
                    BlockPos damageBlockPos = findBlockBelowStalactiteTip(level, stalactiteTipPos, fluidState);
                    if (damageBlockPos != null) {
                        level.levelEvent(1504, stalactiteTipPos, 0);
                        int fallDistance = stalactiteTipPos.getY() - damageBlockPos.getY();
                        int delay = 50 + fallDistance;
                        this.damageBlock(level, fluidState.getType(), fluidPos, damageBlockPos, 2, true, delay);
                    }
                }
            }
        }
    }

    private static BlockPos findBlockBelowStalactiteTip(ServerLevel level, BlockPos stalactiteTipPos, FluidState fluidState) {
        Predicate<BlockState> damagePredicate = state -> !PointedDripstoneBlock.isStalactite(state) && canDamageBlock(state, fluidState);
        BiPredicate<BlockPos, BlockState> pathPredicate = (pos, state) -> PointedDripstoneBlock.canDripThrough(level, pos, state);
        return PointedDripstoneBlock.findBlockVertical(level, stalactiteTipPos, Direction.DOWN.getAxisDirection(), pathPredicate, damagePredicate, 11).orElse(null);
    }

    private void damageFromSource(ServerLevel level, BlockPos fluidPos, BlockPos blockPos, FluidState fluidState) {
        if (fluidState.isSource()) {
            BlockState blockState = level.getBlockState(blockPos);
            if (canDamageBlock(blockState, fluidState)) {
                int destroySpeed = 0;
                if (blockState.is(AetherIITags.Blocks.ALKAHEST_INSTANTLY_DESTROYS)) {
                    destroySpeed = 9;
                } else if (blockState.is(AetherIITags.Blocks.ALKAHEST_QUICKLY_DESTROYS)) {
                    destroySpeed = 3;
                } else if (blockState.is(AetherIITags.Blocks.ALKAHEST_SLOWLY_DESTROYS)) {
                    destroySpeed = 1;
                }
                if (destroySpeed != 0) {
                    this.damageBlock(level, fluidState.getType(), fluidPos, blockPos, destroySpeed, false, 10);
                }
            }
        }
    }

    public void damageBlock(ServerLevel level, Fluid fluid, BlockPos fluidPos, BlockPos blockPos, int destroySpeed, boolean drop, int delay) {
        Map<BlockPos, AlkahestBreakageAttachment.BreakageData> damagedBlocks = level.getData(AetherIIDataAttachments.ALKAHEST_BREAKAGE).getBlockBreakage();
        if (!damagedBlocks.containsKey(blockPos)) {
            damagedBlocks.put(blockPos, new AlkahestBreakageAttachment.BreakageData(0, new AlkahestBreakageAttachment.StaticData(fluidPos, destroySpeed, drop, level.getServer().getTickCount(), delay)));
        }
        level.scheduleTick(fluidPos, fluid, fluid.getTickDelay(level) + 10);
    }

    public static boolean canDamageBlock(BlockState blockState, FluidState fluidState) {
        return !blockState.isAir() && !blockState.is(fluidState.createLegacyBlock().getBlock()) && !blockState.is(AetherIITags.Blocks.ALKAHEST_RESISTANT);
    }

    @Override
    public void animateTick(Level level, BlockPos pos, FluidState fluidState, RandomSource random) {
        level.addParticle(AetherIIParticleTypes.ALKAHEST.get(), (double) pos.getX() + random.nextDouble(), (double) pos.getY() + random.nextDouble(), (double) pos.getZ() + random.nextDouble(), 0.0, 0.15, 0.0);
        if (random.nextInt(50) == 0) {
            BlockPos belowPos = pos.below();
            BlockState belowState = level.getBlockState(belowPos);
            if (belowState.isSolid()) {
                ParticleUtils.spawnParticlesOnBlockFace(level, belowPos.above(), ParticleTypes.WHITE_SMOKE, ConstantInt.of(1), Direction.DOWN, () -> new Vec3(0, 0.5, 0), 0.5);
            }
        }
    }

    public void entityInside(BlockState state, ServerLevel level, BlockPos blockPos, Entity entity) {
        RandomSource random = level.getRandom();
        if (entity instanceof ItemEntity itemEntity) {
            ItemStack itemStack = itemEntity.getItem().copy();
            if (!itemStack.is(AetherIITags.Items.ALKAHEST_RESISTANT_ITEM) && !itemStack.has(AetherIIDataComponents.REINFORCEMENT_TIER)) {
                int newLifespanValue = itemEntity.lifespan - 15;

                PacketDistributor.sendToAllPlayers(new AlkahestItemSmokePacket(new Vec3(itemEntity.getX(), (itemEntity.getY() + itemEntity.getBoundingBox().getYsize()), itemEntity.getZ())));
                if (itemEntity.lifespan <= 500) {
                    if (itemStack.is(AetherIITags.Items.UNBREAKABLE_LOOT)) {
                        itemEntity.discard();
                        ItemStack brokenLootStack = MixinHooks.getBrokenLootStack(itemStack);
                        ItemEntity lootItemEntity = new ItemEntity(level, itemEntity.getX(), itemEntity.getY(), itemEntity.getZ(), brokenLootStack);
                        level.addFreshEntity(lootItemEntity);
                        return;
                    } else {
                        for (RecipeHolder<AlkahestPurificationRecipe> recipe : level.recipeAccess().recipeMap().byType(AetherIIRecipeTypes.ALKAHEST_PURIFICATION.get())) {
                            if (recipe != null) {
                                SingleRecipeInputWithRandom input = new SingleRecipeInputWithRandom(itemStack, level.getRandom());
                                if (recipe.value().matches(input, level)) {
                                    itemEntity.discard();
                                    ItemStack result = recipe.value().assemble(input);
                                    result.setDamageValue((result.getMaxDamage() / 3) + (random.nextInt(8) * (random.nextBoolean() ? 1 : -1)));
                                    ItemEntity cleansedItemEntity = new ItemEntity(level, itemEntity.getX(), itemEntity.getY(), itemEntity.getZ(), result);
                                    level.addFreshEntity(cleansedItemEntity);
                                    return;
                                }
                            }
                        }
                    }
                }
                itemEntity.lifespan = newLifespanValue;
            }
        } else if (entity instanceof LivingEntity livingEntity && !livingEntity.is(AetherIITags.EntityTypes.ALKAHEST_RESISTANT)) {
            if (entity.tickCount % 20 == 0) {
                livingEntity.hurt(AetherIIDamageTypes.damageSource(level, AetherIIDamageTypes.ALKAHEST), 3.0F);

                if (!livingEntity.level().isClientSide() && livingEntity.level() instanceof ServerLevel serverLevel) {
                    ItemStack mainhandItem = livingEntity.getMainHandItem();
                    if (!mainhandItem.is(AetherIITags.Items.ALKAHEST_RESISTANT_ITEM) && !mainhandItem.has(AetherIIDataComponents.REINFORCEMENT_TIER)) {
                        mainhandItem.hurtAndBreak(1, livingEntity, EquipmentSlot.MAINHAND);
                    }

                    ItemStack offhandItem = livingEntity.getOffhandItem();
                    if (!offhandItem.is(AetherIITags.Items.ALKAHEST_RESISTANT_ITEM) && !offhandItem.has(AetherIIDataComponents.REINFORCEMENT_TIER)) {
                        offhandItem.hurtAndBreak(1, livingEntity, EquipmentSlot.OFFHAND);
                    }

                    AccessoryUtil.getFirst(livingEntity, AccessoryContainer.SlotType.HANDWEAR).ifPresent((stack) -> {
                        if (!stack.is(AetherIITags.Items.ALKAHEST_RESISTANT_ITEM) && !stack.has(AetherIIDataComponents.REINFORCEMENT_TIER)) {
                            if (livingEntity instanceof ServerPlayer serverPlayer) {
                                stack.hurtAndBreak(1, serverPlayer, EquipmentSlot.BODY);
                            }
                        }
                    });
                }
            }
        }
    }

    @Override
    public boolean canBeReplacedWith(FluidState fluidState, BlockGetter level, BlockPos pos, Fluid fluid, Direction direction) {
        return direction == Direction.DOWN && !fluid.is(AetherIITags.Fluids.ALKAHEST);
    }

    @Override
    protected void beforeDestroyingBlock(LevelAccessor level, BlockPos pos, BlockState state) {
        BlockEntity blockentity = state.hasBlockEntity() ? level.getBlockEntity(pos) : null;
        Block.dropResources(state, level, pos, blockentity);
    }

    @Override
    public BlockState createLegacyBlock(FluidState fluidState) {
        return AetherIIBlocks.ALKAHEST.get().defaultBlockState().setValue(LiquidBlock.LEVEL, getLegacyLevel(fluidState));
    }

    @Override
    public boolean isSame(Fluid fluid) {
        return fluid == AetherIIFluids.ALKAHEST.get() || fluid == AetherIIFluids.FLOWING_ALKAHEST.get();
    }

    @Override
    public Fluid getFlowing() {
        return AetherIIFluids.FLOWING_ALKAHEST.get();
    }

    @Override
    public Fluid getSource() {
        return AetherIIFluids.ALKAHEST.get();
    }

    @Override
    public Item getBucket() {
        return ItemStack.EMPTY.getItem();
    }

    @Override
    public Item getCanister() {
        return AetherIIItems.ARKENIUM_ALKAHEST_CANISTER.get();
    }

    @Nullable
    @Override
    public ParticleOptions getDripParticle() {
        return AetherIIParticleTypes.DRIPPING_ALKAHEST.get();
    }

    @Override
    public Optional<SoundEvent> getPickupSound() {
        return Optional.of(AetherIISoundEvents.ITEM_ARKENIUM_CANISTER_FILL_ALKAHEST.get());
    }

    @Override
    protected boolean canConvertToSource(ServerLevel level) {
        return false;
    }

    @Override
    public int getSlopeFindDistance(LevelReader level) {
        return 4;
    }

    @Override
    public int getDropOff(LevelReader level) {
        return 3;
    }

    @Override
    public int getTickDelay(LevelReader level) {
        return 3;
    }

    @Override
    protected float getExplosionResistance() {
        return 100.0F;
    }

    @Override
    protected boolean isRandomlyTicking() {
        return true;
    }

    public static class Source extends AlkahestFluid {
        public Source(Properties properties) {
            super(properties);
        }

        public int getAmount(FluidState fluidState) {
            return 8;
        }

        public boolean isSource(FluidState fluidState) {
            return true;
        }
    }

    public static class Flowing extends AlkahestFluid {
        public Flowing(Properties properties) {
            super(properties);
        }

        protected void createFluidStateDefinition(StateDefinition.Builder<Fluid, FluidState> builder) {
            super.createFluidStateDefinition(builder);
            builder.add(LEVEL);
        }

        public int getAmount(FluidState fluidState) {
            return fluidState.getValue(LEVEL);
        }

        public boolean isSource(FluidState fluidState) {
            return false;
        }
    }
}