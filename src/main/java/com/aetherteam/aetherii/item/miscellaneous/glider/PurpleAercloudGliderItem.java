package com.aetherteam.aetherii.item.miscellaneous.glider;

import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class PurpleAercloudGliderItem extends AercloudGliderItem {
    public PurpleAercloudGliderItem(Properties properties) {
        super(properties);
    }

    @Override
    protected void onParachuteOpen(Level level, Player player, InteractionHand hand, ItemStack stack) {
        if (player.getData(AetherIIDataAttachments.PLAYER).getCanRefuelAbilities().containsKey(stack.getItemHolder()) && player.getData(AetherIIDataAttachments.PLAYER).getCanRefuelAbilities().get(stack.getItemHolder())) {
            player.setDeltaMovement(player.getDeltaMovement().multiply(6.0, 1.0, 6.0));
            player.getData(AetherIIDataAttachments.PLAYER).getCanRefuelAbilities().put(stack.getItemHolder(), false);
        }
    }
}
