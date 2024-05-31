package com.aetherteam.aetherii.block.natural;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.client.particle.AetherIIParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class HighlandsBushBlock extends AetherBushBlock {
    public HighlandsBushBlock(Properties properties) {
        super(properties);
    }

    @SuppressWarnings("deprecation")
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (entity instanceof LivingEntity && entity.getType() != EntityType.FOX && entity.getType() != EntityType.BEE) {
            entity.makeStuckInBlock(state, new Vec3(0.8F, 0.75F, 0.8F));
            if (entity.getX() != entity.xOld && entity.getZ() != entity.zOld) {
                if (level.getRandom().nextInt(10) == 0) {
                    level.playSound(null, pos, SoundEvents.CAVE_VINES_STEP, SoundSource.BLOCKS, 1.0F, 0.8F + level.getRandom().nextFloat() * 0.4F);
                }
                Vec3 vec3 = Vec3.atCenterOf(pos);
                for (int j = 0; j < 15; ++j) {
                    double d0 = vec3.x + Mth.nextDouble(level.getRandom(), -0.6, 0.6);
                    double d1 = vec3.y + Mth.nextDouble(level.getRandom(), -0.6, 0.6);
                    double d2 = vec3.z + Mth.nextDouble(level.getRandom(), -0.6, 0.6);
                    double d3 = Mth.nextDouble(level.getRandom(), -0.6, 0.6);
                    double d4 = Mth.nextDouble(level.getRandom(), -0.6, 0.6);
                    double d5 = Mth.nextDouble(level.getRandom(), -0.6, 0.6);
                    level.addParticle(AetherIIParticleTypes.SKYROOT_LEAVES.get(), d0, d1, d2, d3, d4, d5);
                }
            }
        }
    }

    @Override
    public void playerDestroy(Level level, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack tool) {
        super.playerDestroy(level, player, pos, state, blockEntity, tool);
        if (tool.getEnchantmentLevel(Enchantments.SILK_TOUCH) <= 0) {
            level.setBlock(pos, AetherIIBlocks.HIGHLANDS_BUSH_STEM.get().defaultBlockState(), 1 | 2);
        }
    }

    @Override
    public void onBlockExploded(BlockState state, Level level, BlockPos pos, Explosion explosion) {
        super.onBlockExploded(state, level, pos, explosion);
        level.setBlock(pos, AetherIIBlocks.HIGHLANDS_BUSH_STEM.get().defaultBlockState(), 1 | 2);
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }
}
