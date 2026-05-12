package com.aetherteam.aetherii.item.consumables;

import com.aetherteam.aetherii.client.particle.AetherIIParticleTypes;
import com.aetherteam.aetherii.client.sound.AetherIISoundEvents;
import net.minecraft.sounds.SoundSource;
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
        float scale = 7.5F;
        if (player.onGround()) {
            scale = 15.0F;
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
        level.playSound(null, player.getX(), player.getY(), player.getZ(), AetherIISoundEvents.ITEM_CRYSTAL_WING_USE, SoundSource.NEUTRAL, 1.0F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
        if (level.isClientSide()) {
            for (int i = 0; i < 4; i++) {
                for (int j = 1; j < 5; j++) {
                    level.addParticle(AetherIIParticleTypes.CRYSTAL_WING.get(), player.getX(), player.getY(i / 4.0F), player.getZ(), boost.x() / (j * 1.5F), 0.0F, boost.z() / (j * 1.5F));
                }
            }
        }
        if (!player.getAbilities().instabuild) {
            itemStack.shrink(1);
            player.getCooldowns().addCooldown(itemStack, 25);
        }
        return InteractionResult.SUCCESS;
    }
}