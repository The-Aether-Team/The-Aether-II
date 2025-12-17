package com.aetherteam.aetherii.block.dungeon;

import com.aetherteam.aetherii.blockentity.BossDoorwayBlockEntity;
import com.aetherteam.aetherii.blockentity.CopyBlockEntity;
import com.aetherteam.aetherii.client.particle.AetherIIParticleTypes;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
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
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.common.Tags;
import org.jetbrains.annotations.Nullable;

public class BossDoorwayBlock extends CopyBlock {
    public static final MapCodec<BossDoorwayBlock> CODEC = simpleCodec(BossDoorwayBlock::new);
    public static final BooleanProperty INVISIBLE = BooleanProperty.create("invisible");
    public static final VoxelShape INVISIBLE_SHAPE = Block.box(5.0, 5.0, 5.0, 11.0, 11.0, 11.0);

    @Override
    protected MapCodec<BossDoorwayBlock> codec() {
        return CODEC;
    }

    public BossDoorwayBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(CopyBlock.WATERLOGGED, false).setValue(CopyBlock.EMPTY, true).setValue(INVISIBLE, true));
//        LandPathNodeTypesRegistry.register(this, this); //todo
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new BossDoorwayBlockEntity(blockPos, blockState);
    }

    @Override
    protected void setCopyBlocksInfo(Level level, BlockPos pos, BlockState state, BlockState copyState, BlockState newState, CopyBlockEntity blockEntity) {
        newState = newState.setValue(INVISIBLE, false);
        super.setCopyBlocksInfo(level, pos, state, copyState, newState, blockEntity);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (player.isCreative() && !state.getValue(CopyBlock.EMPTY)) {
            BlockState newState = state.cycle(INVISIBLE);
            level.setBlock(pos, newState, 1 | 2);
            return InteractionResult.SUCCESS;
        }
        return super.useWithoutItem(state, level, pos, player, hitResult);
    }

    @Override
    public boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
        boolean flag = super.canBeReplaced(state, context);
        if (!flag) {
            Level level = context.getLevel();
            BlockPos pos = context.getClickedPos();
            for (int i = 0; i < 2; i++) {
//                EntityUtil.spawnRemovalParticles(level, pos); //todo
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
        if (!state.getValue(CopyBlock.EMPTY) && context instanceof EntityCollisionContext entity && entity.getEntity() != null && entity.getEntity().getType().is(Tags.EntityTypes.BOSSES)) {
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
    protected float getShadeBrightness(BlockState state, BlockGetter level, BlockPos pos) {
        if (!state.getValue(INVISIBLE)) {
            return super.getShadeBrightness(state, level, pos);
        }
        return 1.0F;
    }

    @Override
    protected VoxelShape getOcclusionShape(BlockState state) {
        if (!state.getValue(INVISIBLE)) {
            return super.getOcclusionShape(state);
        }
        return Shapes.empty();
    }

    @Override
    protected boolean propagatesSkylightDown(BlockState state) {
        if (!state.getValue(INVISIBLE)) {
            return super.propagatesSkylightDown(state);
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
            if (item instanceof BlockItem blockItem && ((blockEntity == null || !blockEntity.collectComponents().has(AetherIIDataComponents.BLOCK_STATE)) || state.getValue(INVISIBLE))) {
                if (blockItem.getBlock() == this) {
                    minecraft.level.addParticle(AetherIIParticleTypes.BOSS_DOORWAY_BLOCK.get(), pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 0.0, 0.0, 0.0);
                }
            }
        }
    }

//    @Override
//    public BlockPathTypes getPathNodeType(BlockState state, boolean neighbor) {
//        return AetherBlockPathTypes.BOSS_DOORWAY;
//    }
}
