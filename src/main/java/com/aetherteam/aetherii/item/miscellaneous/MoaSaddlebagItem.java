package com.aetherteam.aetherii.item.miscellaneous;

import com.aetherteam.aetherii.entity.passive.Moa;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.gameevent.GameEvent;

public class MoaSaddlebagItem extends Item {
    private final int size;

    public MoaSaddlebagItem(int size, Properties properties) {
        super(properties);
        this.size = size;
    }

    /**
     * Try interacting with given entity. Return {@code InteractionResult.PASS} if nothing should happen.
     */
    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand hand) {
        if (target instanceof Moa moa && target.isAlive() && moa.isSaddled() && moa.getSaddlebagSize() == 0) {
            if (!player.level().isClientSide()) {
                moa.equipSaddlebag(stack);
                target.level().gameEvent(target, GameEvent.EQUIP, target.position());
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    public int getSize() {
        return this.size;
    }
}
