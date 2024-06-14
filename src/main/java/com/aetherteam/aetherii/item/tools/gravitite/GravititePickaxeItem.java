package com.aetherteam.aetherii.item.tools.gravitite;

import com.aetherteam.aetherii.entity.block.HoveringBlockEntity;
import com.aetherteam.aetherii.item.AetherIIItemTiers;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class GravititePickaxeItem extends PickaxeItem {
    public GravititePickaxeItem() {
        super(AetherIIItemTiers.GRAVITITE, 1, -2.8F, new Properties());
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = level.getBlockState(pos);
        Player player = context.getPlayer();
        if (player != null) {
            HoveringBlockEntity floatingBlockEntity = new HoveringBlockEntity(level, (double) pos.getX() + 0.5, pos.getY(), (double) pos.getZ() + 0.5, state);
            floatingBlockEntity.setHoldingPlayer(context.getPlayer());
            level.addFreshEntity(floatingBlockEntity);
            level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
        }
        return super.useOn(context);
    }
}
