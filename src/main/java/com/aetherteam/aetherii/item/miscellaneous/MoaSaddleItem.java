package com.aetherteam.aetherii.item.miscellaneous;

import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Saddleable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.gameevent.GameEvent;

//TODO
public class MoaSaddleItem extends Item {
    public MoaSaddleItem(Item.Properties properties) {
        super(properties);
    }

    /**
     * Try interacting with given entity. Return {@code InteractionResult.PASS} if nothing should happen.
     */
    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand hand) {
        if (target instanceof Saddleable saddleable && target.isAlive() && !saddleable.isSaddled() && saddleable.isSaddleable()) {
            if (!player.level().isClientSide) {
                saddleable.equipSaddle(stack.split(1), SoundSource.NEUTRAL);
                target.level().gameEvent(target, GameEvent.EQUIP, target.position());
            }

            return InteractionResult.sidedSuccess(player.level().isClientSide);
        }

        return InteractionResult.PASS;
    }
}