package com.aetherteam.aetherii.item.miscellaneous;

import com.aetherteam.aetherii.entity.passive.Moa;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.gameevent.GameEvent;

public class MoaSaddleItem extends Item {
    public MoaSaddleItem(Item.Properties properties) {
        super(properties);
    }

    /**
     * Try interacting with given entity. Return {@code InteractionResult.PASS} if nothing should happen.
     */
    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand hand) {
        if (target instanceof Moa moa && target.isAlive() && !moa.isSaddled() && moa.isSaddleable()) {
            if (!player.level().isClientSide()) {
                moa.equipSaddle(stack.split(1));
                target.level().gameEvent(target, GameEvent.EQUIP, target.position());
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }
}