package com.aetherteam.aetherii.item.miscellaneous.bucket;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nullable;

public class SolidCanisterItem extends BlockItem implements DispensibleContainerItem {
    private final SoundEvent placeSound;

    public SolidCanisterItem(Block block, SoundEvent placeSound, Item.Properties properties) {
        super(block, properties);
        this.placeSound = placeSound;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        InteractionResult interactionresult = super.useOn(context);
        Player player = context.getPlayer();
        if (interactionresult.consumesAction() && player != null) {
            player.setItemInHand(context.getHand(), ArkeniumCanisterItem.getEmptySuccessItem(context.getItemInHand(), player));
        }

        return interactionresult;
    }

    @Override
    public String getDescriptionId() {
        return this.getOrCreateDescriptionId();
    }

    @Override
    protected SoundEvent getPlaceSound(BlockState state) {
        return this.placeSound;
    }

    @Override
    public boolean emptyContents(@Nullable Player player, Level level, BlockPos pos, @Nullable BlockHitResult result) {
        if (level.isInWorldBounds(pos) && level.isEmptyBlock(pos)) {
            if (!level.isClientSide) {
                level.setBlock(pos, this.getBlock().defaultBlockState(), 3);
            }

            level.gameEvent(player, GameEvent.FLUID_PLACE, pos);
            level.playSound(player, pos, this.placeSound, SoundSource.BLOCKS, 1.0F, 1.0F);
            return true;
        } else {
            return false;
        }
    }
}
