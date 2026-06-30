package com.aetherteam.aetherii.block.natural;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.block.AetherIIBlockStateProperties;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.client.particle.AetherIIParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class AetherLeavesBlock extends LeavesBlock {
    public static final BooleanProperty SNOWY = BlockStateProperties.SNOWY;
    public static final EnumProperty<AetherIIBlockStateProperties.Mossy> MOSSY = AetherIIBlockStateProperties.MOSSY;
    private final Supplier<? extends ParticleOptions> leavesParticle;
    private final Supplier<Block> leavesPile;
    private float leafParticleChance = 0.005F;

    public AetherLeavesBlock(Properties properties, ParticleOptions leavesParticle, Holder<Block> leavesPile) {
        this(properties, () -> leavesParticle, leavesPile::value);
    }

    public AetherLeavesBlock(Properties properties, Supplier<? extends ParticleOptions> leavesParticle, Holder<Block> leavesPile) {
        this(properties, leavesParticle, leavesPile::value);
    }

    public AetherLeavesBlock(Properties properties, ParticleOptions leavesParticle, RegistryObject<Block> leavesPile) {
        this(properties, () -> leavesParticle, leavesPile::get);
    }

    public AetherLeavesBlock(Properties properties, Supplier<? extends ParticleOptions> leavesParticle, RegistryObject<Block> leavesPile) {
        this(properties, leavesParticle, leavesPile::get);
    }

    private AetherLeavesBlock(Properties properties, Supplier<? extends ParticleOptions> leavesParticle, Supplier<Block> leavesPile) {
        super(properties);
        this.leavesParticle = leavesParticle;
        this.leavesPile = leavesPile;
        this.registerDefaultState(this.stateDefinition.any().setValue(DISTANCE, 7).setValue(PERSISTENT, Boolean.FALSE).setValue(WATERLOGGED, Boolean.FALSE).setValue(SNOWY, Boolean.FALSE).setValue(MOSSY, AetherIIBlockStateProperties.Mossy.NONE));
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return !state.getValue(PERSISTENT);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        ItemStack stack = player.getItemInHand(hand);
        if (stack.canPerformAction(ToolActions.SHEARS_HARVEST) && state.getValue(MOSSY) != AetherIIBlockStateProperties.Mossy.NONE) {
            level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.SHEEP_SHEAR, SoundSource.BLOCKS, 1.0F, 1.0F);
            level.setBlock(pos, state.setValue(MOSSY, AetherIIBlockStateProperties.Mossy.NONE), 3);
            stack.hurtAndBreak(1, player, (owner) -> owner.broadcastBreakEvent(hand));
            if (!level.isClientSide()) {
                player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
            }
            return InteractionResult.SUCCESS;
        }
        return super.use(state, level, pos, player, hand, hitResult);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (level.isRaining() && random.nextInt(40) == 0) {
            BlockPos.MutableBlockPos mutablePos = pos.mutable();
            for (int i = 1; i < 20; ++i) {
                mutablePos.move(Direction.DOWN);
                BlockState mutableState = level.getBlockState(mutablePos);
                BlockPos abovePos = mutablePos.above();
                BlockState aboveState = level.getBlockState(abovePos);
                BlockState pileState = this.leavesPile.get().defaultBlockState();
                if (Block.canSupportCenter(level, mutablePos, Direction.UP) && aboveState.isAir() && pileState.canSurvive(level, abovePos)) {
                    level.setBlock(mutablePos.above(), pileState, 2);
                    break;
                }
                if (level.isOutsideBuildHeight(mutablePos.getY()) || mutableState.isSolidRender(level, mutablePos) || !mutableState.getFluidState().isEmpty() || Shapes.joinIsNotEmpty(Block.box(6.0, 0.0, 6.0, 10.0, 16.0, 10.0), mutableState.getCollisionShape(level, mutablePos), BooleanOp.AND)) {
                    break;
                }
            }
        }
        super.randomTick(state, level, pos, random);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor levelReader, BlockPos pos, BlockPos neighborPos) {
        LevelAccessor scheduledTickAccess = levelReader;
        BlockState returnState = super.updateShape(state, direction, neighborState, levelReader, pos, neighborPos);
        if (direction == Direction.UP) {
            if (neighborState.is(AetherIIBlocks.BRYALINN_MOSS_CARPET.get()) || neighborState.is(AetherIIBlocks.BRYALINN_MOSS_BLOCK.get())) {
                returnState = returnState.setValue(MOSSY, AetherIIBlockStateProperties.Mossy.BRYALINN);
            } else if (neighborState.is(AetherIIBlocks.SHAYELINN_MOSS_CARPET.get()) || neighborState.is(AetherIIBlocks.SHAYELINN_MOSS_BLOCK.get())) {
                returnState = returnState.setValue(MOSSY, AetherIIBlockStateProperties.Mossy.SHAYELINN);
            } else if (neighborState.is(AetherIIBlocks.AMBRELINN_MOSS_CARPET.get()) || neighborState.is(AetherIIBlocks.AMBRELINN_MOSS_BLOCK.get())) {
                returnState = returnState.setValue(MOSSY, AetherIIBlockStateProperties.Mossy.AMBRELINN);
            }
        }
        return direction == Direction.UP
                ? returnState.setValue(SNOWY, neighborState.is(AetherIIBlocks.ARCTIC_SNOW.get()) || neighborState.is(AetherIIBlocks.ARCTIC_SNOW_BLOCK.get()))
                : returnState;
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        this.leafParticleChance = level.isRaining()? 0.01f : 0.005f;
        if (level.getBiome(pos).is(AetherIITags.Biomes.THE_AETHER)) {
            BlockPos belowPos = pos.below();
            BlockState belowState = level.getBlockState(belowPos);
            makeAetherDrippingWaterParticles(level, pos, random, belowState, belowPos);
            this.makeFallingLeavesParticles(level, pos, random, belowState, belowPos);
        } else {
            super.animateTick(state, level, pos, random);
        }
    }

    protected void spawnFallingLeavesParticle(Level level, BlockPos pos, RandomSource random) {
        BlockPos belowPos = pos.below();
        BlockState belowState = level.getBlockState(belowPos);
        if (!isFaceFull(belowState.getCollisionShape(level, belowPos), Direction.UP)) {
            ParticleUtils.spawnParticleBelow(level, pos, random, this.leavesParticle.get());
        }
    }

    private static void makeAetherDrippingWaterParticles(Level level, BlockPos pos, RandomSource random, BlockState blockBelow, BlockPos belowPos) {
        if (level.isRainingAt(pos.above()) && random.nextInt(15) == 1 && (!blockBelow.canOcclude() || !blockBelow.isFaceSturdy(level, belowPos, Direction.UP))) {
            ParticleUtils.spawnParticleBelow(level, pos, random, AetherIIParticleTypes.DRIPPING_WATER.get());
        }
   }

    private void makeFallingLeavesParticles(Level level, BlockPos pos, RandomSource random, BlockState blockBelow, BlockPos belowPos) {
        if (!(random.nextFloat() >= this.leafParticleChance) && !isFaceFull(blockBelow.getCollisionShape(level, belowPos), Direction.UP)) {
            this.spawnFallingLeavesParticle(level, pos, random);
        }
    }

    @Override
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
        super.stepOn(level, pos, state, entity);
        if (!entity.isCrouching() && entity.getX() != entity.xOld && entity.getZ() != entity.zOld) {
            if (level.getRandom().nextInt(10) == 0) {
                this.spawnFallingLeavesParticle(level, pos, level.getRandom());
            }
        }
    }

    @Override
    public boolean canSustainPlant(BlockState state, BlockGetter level, BlockPos soilPosition, Direction facing, IPlantable plantable) {
        if (state.getValue(MOSSY) != AetherIIBlockStateProperties.Mossy.NONE && plantable instanceof Block block && block.defaultBlockState().is(AetherIITags.Blocks.GROWS_ON_MOSSY_LEAVES)) {
            return true;
        } else {
            return super.canSustainPlant(state, level, soilPosition, facing, plantable);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(DISTANCE, PERSISTENT, WATERLOGGED, SNOWY, MOSSY);
    }
}
