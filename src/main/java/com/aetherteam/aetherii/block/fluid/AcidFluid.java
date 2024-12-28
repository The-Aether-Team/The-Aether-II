package com.aetherteam.aetherii.block.fluid;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.block.AetherIIFluids;
import com.aetherteam.aetherii.client.particle.AetherIIParticleTypes;
import com.aetherteam.aetherii.data.resources.registries.AetherIIDamageTypes;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.aetherteam.aetherii.item.equipment.armor.GlovesItem;
import com.aetherteam.aetherii.mixin.mixins.client.accessor.LevelRendererAccessor;
import com.aetherteam.aetherii.network.packet.clientbound.AcidDamageBlockPacket;
import com.aetherteam.aetherii.network.packet.clientbound.AcidFizzPacket;
import com.aetherteam.aetherii.network.packet.serverbound.AcidBreakBlockPacket;
import com.aetherteam.aetherii.recipe.input.SingleRecipeInputWithRandom;
import com.aetherteam.aetherii.recipe.recipes.AetherIIRecipeTypes;
import com.aetherteam.aetherii.recipe.recipes.block.AcidCorrosionRecipe;
import com.aetherteam.aetherii.recipe.recipes.item.IrradiationCleansingRecipe;
import com.aetherteam.nitrogen.recipe.input.BlockStateRecipeInput;
import io.wispforest.accessories.api.AccessoriesAPI;
import io.wispforest.accessories.api.AccessoriesCapability;
import io.wispforest.accessories.api.slot.SlotEntryReference;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.BlockDestructionProgress;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.*;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.network.PacketDistributor;

import javax.annotation.Nullable;
import java.util.Optional;

public abstract class AcidFluid extends BaseFlowingFluid implements CanisterFluid {
    public AcidFluid(Properties properties) {
        super(properties);
    }

    @Override
    protected void randomTick(ServerLevel level, BlockPos pos, FluidState state, RandomSource random) {
        super.randomTick(level, pos, state, random);
        if (level.getBlockState(pos.above()).isEmpty() && state.isSource()) {
            this.createGas(level, pos);
        }
    }

    @Override
    public void tick(ServerLevel level, BlockPos pos, BlockState blockState, FluidState fluidState) {
        super.tick(level, pos, blockState, fluidState);
        this.applyGravity(level, pos, fluidState);
        this.corrodeNeighbors(level, pos);
        this.destroyBelow(level, pos, fluidState);
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
            for (RecipeHolder<AcidCorrosionRecipe> recipe : level.recipeAccess().recipeMap().byType(AetherIIRecipeTypes.ACID_CORROSION.get())) {
                if (recipe != null) {
                    BlockState newState = recipe.value().getResultState(offsetState);
                    if (recipe.value().matches(null, level, offsetPos, null, offsetState, newState, AetherIIRecipeTypes.ACID_CORROSION.get())) {
                        if (recipe.value().convert(level, offsetPos, newState, recipe.value().getFunction())) {
                            PacketDistributor.sendToPlayersInDimension(level, new AcidFizzPacket(pos, direction.getOpposite()));
                        }
                    }
                }
            }
        }
    }

    private void destroyBelow(Level level, BlockPos pos, FluidState fluidState) {
        if (fluidState.isSource()) {
            BlockPos belowPos = pos.below();
            BlockState belowState = level.getBlockState(belowPos);
            if (!belowState.isAir() && !belowState.is(this.createLegacyBlock(fluidState).getBlock()) && !belowState.is(AetherIITags.Blocks.ACID_RESISTANT)) {
                int destroySpeed = 0;
                if (belowState.is(AetherIITags.Blocks.ACID_INSTANTLY_DESTROYS)) {
                    destroySpeed = 9;
                } else if (belowState.is(AetherIITags.Blocks.ACID_QUICKLY_DESTROYS)) {
                    destroySpeed = 3;
                } else if (belowState.is(AetherIITags.Blocks.ACID_SLOWLY_DESTROYS)) {
                    destroySpeed = 1;
                }
                if (destroySpeed != 0) {
                    PacketDistributor.sendToPlayersInDimension((ServerLevel) level, new AcidDamageBlockPacket(belowPos, destroySpeed, false));
                    level.scheduleTick(pos, this, this.getTickDelay(level) + 10);
                }
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static void progressivelyDestroyBlock(Level level, BlockPos belowPos, int speed, boolean drop) {
        int id = belowPos.hashCode();
        BlockDestructionProgress progress = ((LevelRendererAccessor) Minecraft.getInstance().levelRenderer).aether_ii$getDestroyingBlocks().get(id);
        if (progress != null) {
            int destroyProgress = progress.getProgress();
            level.destroyBlockProgress(belowPos.hashCode(), belowPos, destroyProgress + speed);
            if (destroyProgress >= 9) {
                PacketDistributor.sendToServer(new AcidBreakBlockPacket(belowPos, drop));
            }
        } else {
            level.destroyBlockProgress(belowPos.hashCode(), belowPos,  speed);
        }
        ParticleUtils.spawnParticlesOnBlockFace(level, belowPos.above(), ParticleTypes.WHITE_SMOKE, UniformInt.of(10, 20), Direction.DOWN, () -> new Vec3(0, 0.5, 0), 0.5);
    }

    public static void fullyDestroyBlock(Level level, BlockPos belowPos, boolean drop) {
        level.setBlock(belowPos.above(), Blocks.AIR.defaultBlockState(), 3);
        level.destroyBlock(belowPos, drop);
    }

    @Override
    public void animateTick(Level level, BlockPos pos, FluidState fluidState, RandomSource random) {
        level.addParticle(AetherIIParticleTypes.ACID.get(), (double) pos.getX() + random.nextDouble(), (double) pos.getY() + random.nextDouble(), (double) pos.getZ() + random.nextDouble(), 0.0, 0.15, 0.0);
        if (random.nextInt(50) == 0) {
            BlockPos belowPos = pos.below();
            BlockState belowState = level.getBlockState(belowPos);
            if (belowState.isSolid()) {
                ParticleUtils.spawnParticlesOnBlockFace(level, belowPos.above(), ParticleTypes.WHITE_SMOKE, ConstantInt.of(1), Direction.DOWN, () -> new Vec3(0, 0.5, 0), 0.5);
            }
        }
    }

    public void createGas(Level level, BlockPos pos) {
        BlockPos above = pos.above();
        if (level.getBlockState(above).isEmpty()) {
            level.setBlock(above, AetherIIBlocks.GAS.get().defaultBlockState(), 3);
        }
    }

    public void entityInside(BlockState state, ServerLevel level, BlockPos blockPos, Entity entity) {
        RandomSource random = level.getRandom();
        if (entity instanceof ItemEntity itemEntity) {
            ItemStack itemStack = itemEntity.getItem().copy();
            if (!itemStack.is(AetherIITags.Items.ACID_RESISTANT_ITEM) && !itemStack.has(AetherIIDataComponents.REINFORCEMENT_TIER)) {
                itemEntity.lifespan -= 15;
                if (entity.level().isClientSide()) {
                    for (int i = 0; i < 2; ++i) {
                        double d0 = random.nextGaussian() * 0.02;
                        double d1 = random.nextGaussian() * 0.02;
                        double d2 = random.nextGaussian() * 0.02;
                        level.addParticle(ParticleTypes.WHITE_SMOKE, itemEntity.getX(), (itemEntity.getY() + itemEntity.getBoundingBox().getYsize()), itemEntity.getZ(), d0, d1, d2);
                    }
                }
                if (itemEntity.lifespan <= 500) {
                    for (RecipeHolder<IrradiationCleansingRecipe> recipe : level.recipeAccess().recipeMap().byType(AetherIIRecipeTypes.IRRADIATION_CLEANSING.get())) {
                        if (recipe != null) {
                            SingleRecipeInputWithRandom input = new SingleRecipeInputWithRandom(itemStack, level.getRandom());
                            if (recipe.value().matches(input, level)) {
                                itemEntity.discard();
                                ItemStack result = recipe.value().assemble(input, level.registryAccess());
                                result.setDamageValue((result.getMaxDamage() / 3) + (random.nextInt(8) * (random.nextBoolean() ? 1 : -1)));
                                ItemEntity cleansedItemEntity = new ItemEntity(level, itemEntity.getX(), itemEntity.getY(), itemEntity.getZ(), result);
                                level.addFreshEntity(cleansedItemEntity);
                            }
                        }
                    }
                }
            }
        } else if (entity instanceof LivingEntity livingEntity) {
            if (entity.tickCount % 20 == 0) {
                livingEntity.hurt(AetherIIDamageTypes.damageSource(level, AetherIIDamageTypes.ACID), 3.0F);

                if (!livingEntity.level().isClientSide() && livingEntity.level() instanceof ServerLevel serverLevel) {
                    ItemStack mainhandItem = livingEntity.getMainHandItem();
                    if (!mainhandItem.is(AetherIITags.Items.ACID_RESISTANT_ITEM) && !mainhandItem.has(AetherIIDataComponents.REINFORCEMENT_TIER)) {
                        mainhandItem.hurtAndBreak(1, livingEntity, EquipmentSlot.MAINHAND);
                    }

                    ItemStack offhandItem = livingEntity.getOffhandItem();
                    if (!offhandItem.is(AetherIITags.Items.ACID_RESISTANT_ITEM) && !offhandItem.has(AetherIIDataComponents.REINFORCEMENT_TIER)) {
                        offhandItem.hurtAndBreak(1, livingEntity, EquipmentSlot.OFFHAND);
                    }

                    AccessoriesCapability accessories = AccessoriesCapability.get(livingEntity);
                    if (accessories != null) {
                        SlotEntryReference slotEntryReference = accessories.getFirstEquipped((itemStack) -> itemStack.getItem() instanceof GlovesItem);
                        if (slotEntryReference != null && slotEntryReference.stack().getItem() instanceof GlovesItem) {
                            ItemStack gloves = slotEntryReference.stack();
                            if (!gloves.is(AetherIITags.Items.ACID_RESISTANT_ITEM) && !gloves.has(AetherIIDataComponents.REINFORCEMENT_TIER)) {
                                if (livingEntity instanceof ServerPlayer serverPlayer) {
                                    gloves.hurtAndBreak(1, serverLevel, serverPlayer, (item) -> AccessoriesAPI.breakStack(slotEntryReference.reference()));
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean canBeReplacedWith(FluidState fluidState, BlockGetter level, BlockPos pos, Fluid fluid, Direction direction) {
        return direction == Direction.DOWN && !fluid.is(AetherIITags.Fluids.ACID) && !fluid.is(FluidTags.WATER); //todo water interaction
    }

    @Override
    protected void beforeDestroyingBlock(LevelAccessor level, BlockPos pos, BlockState state) {
        BlockEntity blockentity = state.hasBlockEntity() ? level.getBlockEntity(pos) : null;
        Block.dropResources(state, level, pos, blockentity);
    }

    @Override
    public BlockState createLegacyBlock(FluidState fluidState) {
        return AetherIIBlocks.ACID.get().defaultBlockState().setValue(LiquidBlock.LEVEL, getLegacyLevel(fluidState));
    }

    @Override
    public boolean isSame(Fluid fluid) {
        return fluid == AetherIIFluids.ACID.get() || fluid == AetherIIFluids.FLOWING_ACID.get();
    }

    @Override
    public Fluid getFlowing() {
        return AetherIIFluids.FLOWING_ACID.get();
    }

    @Override
    public Fluid getSource() {
        return AetherIIFluids.ACID.get();
    }

    @Override
    public Item getBucket() {
        return ItemStack.EMPTY.getItem();
    }

    @Override
    public Item getCanister() {
        return AetherIIItems.ARKENIUM_ACID_CANISTER.get();
    }

    @Nullable
    @Override
    public ParticleOptions getDripParticle() {
        return AetherIIParticleTypes.DRIPPING_ACID.get();
    }

    @Override
    public Optional<SoundEvent> getPickupSound() {
        return Optional.of(SoundEvents.BUCKET_FILL);
    } //todo

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

    public static class Source extends AcidFluid {
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

    public static class Flowing extends AcidFluid {
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
