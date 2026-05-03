package com.aetherteam.aetherii.item.consumables;

import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class CrystalWingItem extends Item {
    public CrystalWingItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        float scale = 5.0F;
        if (player.onGround()) {
            scale = 10.0F;
        }
        if (player.isSprinting()) {
            scale *= 0.75F;
        }
        Vec3 boost = new Vec3(
                Mth.clamp(player.getDeltaMovement().x() * scale, -5.0F, 5.0F),
                player.getDeltaMovement().y(),
                Mth.clamp(player.getDeltaMovement().z() * scale, -5.0F, 5.0F)
        );
        player.setDeltaMovement(boost);
        if (!player.getAbilities().instabuild) {
            itemStack.shrink(1);
            player.getCooldowns().addCooldown(itemStack, 25);
        }
        return InteractionResult.SUCCESS;
    }
}
