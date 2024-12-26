package com.aetherteam.aetherii.block.miscellaneous;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.blockentity.AetherIIBlockEntityTypes;
import com.aetherteam.aetherii.blockentity.MoaEggBlockEntity;
import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import com.aetherteam.aetherii.entity.passive.Moa;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.aetherteam.aetherii.item.components.MoaEggType;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class MoaEggBlock extends BaseEntityBlock {
    public static final MapCodec<MoaEggBlock> CODEC = simpleCodec(MoaEggBlock::new);

    public static final EnumProperty<Moa.KeratinColor> KERATIN = EnumProperty.create("keratin", Moa.KeratinColor.class);
    public static final EnumProperty<Moa.EyeColor> EYES = EnumProperty.create("eyes", Moa.EyeColor.class);
    public static final EnumProperty<Moa.FeatherColor> FEATHERS = EnumProperty.create("feathers", Moa.FeatherColor.class);
    public static final EnumProperty<Moa.FeatherShape> FEATHER_SHAPE = EnumProperty.create("feather_shape", Moa.FeatherShape.class);
    public static final IntegerProperty HATCH = BlockStateProperties.HATCH;
    public static final BooleanProperty WILD = BooleanProperty.create("wild");
    public static final int MAX_HATCH_LEVEL = 2;
    private static final int REGULAR_HATCH_TIME_TICKS = 24000;
    private static final int BOOSTED_HATCH_TIME_TICKS = 12000;
    private static final int RANDOM_HATCH_OFFSET_TICKS = 300;
    private static final VoxelShape SHAPE = Block.box(4.0, 0.0, 4.0, 12.0, 12.0, 12.0);

    public MoaEggBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(HATCH, 0).setValue(KERATIN, Moa.KeratinColor.GRAY).setValue(EYES, Moa.EyeColor.BLUE).setValue(FEATHERS, Moa.FeatherColor.LIGHT_BLUE).setValue(FEATHER_SHAPE, Moa.FeatherShape.CURVED).setValue(WILD, false));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HATCH, KERATIN, EYES, FEATHERS, FEATHER_SHAPE, WILD);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    public int getHatchLevel(BlockState pState) {
        return pState.getValue(HATCH);
    }

    private boolean isReadyToHatch(BlockState pState) {
        return this.getHatchLevel(pState) == 2;
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!this.isReadyToHatch(state)) {
            level.playSound(null, pos, SoundEvents.SNIFFER_EGG_CRACK, SoundSource.BLOCKS, 0.7F, 0.9F + random.nextFloat() * 0.2F);
            level.setBlock(pos, state.setValue(HATCH, this.getHatchLevel(state) + 1), 2);
        } else {
            level.playSound(null, pos, SoundEvents.SNIFFER_EGG_HATCH, SoundSource.BLOCKS, 0.7F, 0.9F + random.nextFloat() * 0.2F);
            level.destroyBlock(pos, false);
            Moa moa = AetherIIEntityTypes.MOA.get().create(level, EntitySpawnReason.BREEDING);
            if (moa != null) {
                Vec3 vec3 = pos.getCenter();
                moa.setBaby(true);
                if (!state.getValue(WILD)) {
                    moa.setPlayerGrown(true);
                }
                moa.setKeratinColor(state.getValue(KERATIN).getSerializedName());
                moa.setEyeColor(state.getValue(EYES).getSerializedName());
                moa.setFeatherColor(state.getValue(FEATHERS).getSerializedName());
                moa.setFeatherShape(state.getValue(FEATHER_SHAPE).getSerializedName());

                //moa.setMoaTypeByKey(this.moaType);
                moa.moveTo(vec3.x(), vec3.y(), vec3.z(), Mth.wrapDegrees(level.random.nextFloat() * 360.0F), 0.0F);
                level.addFreshEntity(moa);
            }
        }
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        if (level.getBlockState(pos.below()).is(AetherIITags.Blocks.MOA_HATCH_BLOCK)) {
            if (!level.isClientSide()) {
                level.levelEvent(3009, pos, 0);
            }
            int i = 12000;
            int j = i / 3;
            level.gameEvent(GameEvent.BLOCK_PLACE, pos, GameEvent.Context.of(state));
            level.scheduleTick(pos, this, j + level.random.nextInt(300));
        }
    }

    @Override
    public boolean isPathfindable(BlockState state, PathComputationType type) {
        return false;
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return canSupportCenter(level, pos.below(), Direction.UP);
    }

    @Override
    public ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state, boolean includeData, Player player) {
        ItemStack moaEggItem = super.getCloneItemStack(level, pos, state, includeData, player);
        Moa.KeratinColor keratinColor = state.getValue(KERATIN);
        Moa.EyeColor eyeColor = state.getValue(EYES);
        Moa.FeatherColor featherColor = state.getValue(FEATHERS);
        Moa.FeatherShape featherShape = state.getValue(FEATHER_SHAPE);
        moaEggItem.set(AetherIIDataComponents.MOA_EGG_TYPE, new MoaEggType(keratinColor, eyeColor, featherColor, featherShape));
        return moaEggItem;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new MoaEggBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntity) {
        return createTickerHelper(blockEntity, AetherIIBlockEntityTypes.MOA_EGG.get(), MoaEggBlockEntity::tick);
    }
}