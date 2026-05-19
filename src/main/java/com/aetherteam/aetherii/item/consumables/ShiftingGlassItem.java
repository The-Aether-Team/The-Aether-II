package com.aetherteam.aetherii.item.consumables;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.attachment.player.AetherIIPlayerAttachment;
import com.aetherteam.aetherii.client.particle.AetherIIParticleTypes;
import com.aetherteam.aetherii.client.sound.AetherIISoundEvents;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class ShiftingGlassItem extends Item {
    public ShiftingGlassItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        if (player.getData(AetherIIDataAttachments.PLAYER).isMovingHorizontally()) {
            ItemStack itemStack = player.getItemInHand(hand);
            float scale = 1.0F;
            if (player.onGround()) {
                scale = 1.35F;
            }
            if (player.isSprinting()) {
                scale *= 0.75F;
            }
            Vec3 boost = new Vec3(player.xxa * scale, 0.4, player.zza * scale).yRot(-player.getYRot() * Mth.DEG_TO_RAD);
            player.setDeltaMovement(boost);
            player.resetFallDistance();
            level.playSound(null, player.getX(), player.getY(), player.getZ(), AetherIISoundEvents.ITEM_SHIFTING_GLASS_USE, SoundSource.NEUTRAL, 1.0F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
            if (!level.isClientSide()) {
                player.getData(AetherIIDataAttachments.ABILITY_BEHAVIOR).setShiftingGlassBoostTime(6);
            }
            if (!player.getAbilities().instabuild) {
                itemStack.shrink(1);
                player.level().registryAccess().lookupOrThrow(Registries.ITEM).getTagOrEmpty(AetherIITags.Items.MOVEMENT_ALTERING_ITEMS).forEach((item) -> player.getCooldowns().addCooldown(item.value().getDefaultInstance(), 25));
            }
            return InteractionResult.SUCCESS;
        }
        return super.use(level, player, hand);
    }
}