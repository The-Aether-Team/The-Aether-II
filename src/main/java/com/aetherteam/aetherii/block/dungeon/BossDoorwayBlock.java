package com.aetherteam.aetherii.block.dungeon;

import com.aetherteam.aetherii.blockentity.BossDoorwayBlockEntity;
import com.aetherteam.aetherii.blockentity.CopyBlockEntity;
import com.aetherteam.aetherii.client.particle.AetherIIParticleTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.Tags;
import org.jetbrains.annotations.Nullable;

public class BossDoorwayBlock extends CopyBlock {
    public static final BooleanProperty INVISIBLE = BooleanProperty.create("invisible");
    public static final VoxelShape INVISIBLE_SHAPE = Block.box(5.0, 5.0, 5.0, 11.0, 11.0, 11.0);
public BossDoorwayBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(CopyBlock.WATERLOGGED, false).setValue(CopyBlock.EMPTY, true).setValue(INVISIBLE, true));
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new BossDoorwayBlockEntity(blockPos, blockState);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState state = super.getStateForPlacement(context);
        return state != null && !state.getValue(CopyBlock.EMPTY) ? state.setValue(INVISIBLE, false) : state;
    }

    @Override
    protected void setCopyBlocksInfo(Level level, BlockPos pos, BlockState state, BlockState copyState, BlockState newState, CopyBlockEntity blockEntity) {
        newState = newState.setValue(INVISIBLE, false);
        super.setCopyBlocksInfo(level, pos, state, copyState, newState, blockEntity);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, net.minecraft.world.InteractionHand hand, BlockHitResult hitResult) {
        if (player.isCreative() && !state.getValue(CopyBlock.EMPTY)) {
            BlockState newState = state.cycle(INVISIBLE);
            level.setBlock(pos, newState, 1 | 2);
            return InteractionResult.SUCCESS;
        }
        return super.use(state, level, pos, player, hand, hitResult);
    }

    @Override
    public boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
        boolean flag = super.canBeReplaced(state, context);
        if (!flag) {
            Level level = context.getLevel();
            BlockPos pos = context.getClickedPos();
            for (int i = 0; i < 2; i++) {
                double a = pos.getX() + 0.5 + (double) (level.getRandom().nextFloat() - level.getRandom().nextFloat()) * 0.375;
                double b = pos.getY() + 0.5 + (double) (level.getRandom().nextFloat() - level.getRandom().nextFloat()) * 0.375;
                double c = pos.getZ() + 0.5 + (double) (level.getRandom().nextFloat() - level.getRandom().nextFloat()) * 0.375;
                if (level instanceof ServerLevel serverLevel) {
                    serverLevel.sendParticles(ParticleTypes.POOF, a, b, c, 1, 0.0, 0.0, 0.0, 0.0);
                }
            }
        }
        return flag;
    }

    @Override
    public boolean hidesNeighborFace(BlockGetter level, BlockPos pos, BlockState state, BlockState neighborState, Direction dir) {
        if (!state.getValue(INVISIBLE)) {
            return super.hidesNeighborFace(level, pos, state, neighborState, dir);
        }
        return false;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        if (state.getValue(INVISIBLE)) {
            if (context instanceof EntityCollisionContext entity && entity.getEntity() instanceof Player player && player.isCreative()) {
                return INVISIBLE_SHAPE;
            }
            return Shapes.empty();
        }
        return super.getShape(state, level, pos, context);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        if (!state.getValue(CopyBlock.EMPTY) && context instanceof EntityCollisionContext entity && entity.getEntity() != null && entity.getEntity().getType().builtInRegistryHolder().is(Tags.EntityTypes.BOSSES)) {
            return Shapes.block();
        } else {
            return state.getValue(INVISIBLE) ? Shapes.empty() : super.getCollisionShape(state, level, pos, context);
        }
    }

    @Override
    public int getLightEmission(BlockState state, BlockGetter level, BlockPos pos) {
        if (!state.getValue(INVISIBLE)) {
            return super.getLightEmission(state, level, pos);
        }
        return 0;
    }

    @Override
    public MapColor getMapColor(BlockState state, BlockGetter level, BlockPos pos, MapColor defaultColor) {
        if (!state.getValue(INVISIBLE)) {
            return super.getMapColor(state, level, pos, defaultColor);
        }
        return defaultColor;
    }

    @Override
    protected void spawnDestroyParticles(Level level, Player player, BlockPos pos, BlockState state) {
        if (!state.getValue(INVISIBLE)) {
            super.spawnDestroyParticles(level, player, pos, state);
        }
    }

    @Override
    public float getShadeBrightness(BlockState state, BlockGetter level, BlockPos pos) {
        if (!state.getValue(INVISIBLE)) {
            return super.getShadeBrightness(state, level, pos);
        }
        return 1.0F;
    }

    @Override
    public VoxelShape getOcclusionShape(BlockState state, BlockGetter level, BlockPos pos) {
        if (!state.getValue(INVISIBLE)) {
            return super.getOcclusionShape(state, level, pos);
        }
        return Shapes.empty();
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, net.minecraft.world.level.BlockGetter level, net.minecraft.core.BlockPos pos) {
        if (!state.getValue(INVISIBLE)) {
            return super.propagatesSkylightDown(state, level, pos);
        }
        return true;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        if (!state.getValue(INVISIBLE)) {
            return super.getRenderShape(state);
        }
        return RenderShape.INVISIBLE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(CopyBlock.WATERLOGGED, CopyBlock.EMPTY, INVISIBLE);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.gameMode != null && minecraft.gameMode.getPlayerMode() == GameType.CREATIVE && minecraft.player != null && minecraft.level != null) {
            ItemStack itemStack = minecraft.player.getMainHandItem();
            Item item = itemStack.getItem();
            BlockEntity blockEntity = level.getBlockEntity(pos);
            boolean hasCopyState = blockEntity instanceof CopyBlockEntity copyBlockEntity && copyBlockEntity.getCopyState() != null;
            if (item instanceof BlockItem blockItem && (!hasCopyState || state.getValue(INVISIBLE))) {
                if (blockItem.getBlock() == this) {
                    minecraft.level.addParticle(AetherIIParticleTypes.BOSS_DOORWAY_BLOCK.get(), pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 0.0, 0.0, 0.0);
                }
            }
        }
    }

    @Override
    public @Nullable BlockPathTypes getBlockPathType(BlockState state, BlockGetter level, BlockPos pos, @Nullable Mob mob) {
        if (mob != null && mob.getType().builtInRegistryHolder().is(Tags.EntityTypes.BOSSES)) {
            return BlockPathTypes.BLOCKED;
        }
        return super.getBlockPathType(state, level, pos, mob);
    }
}
