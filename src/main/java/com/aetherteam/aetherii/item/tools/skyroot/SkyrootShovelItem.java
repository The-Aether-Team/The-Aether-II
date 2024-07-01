package com.aetherteam.aetherii.item.tools.skyroot;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.effect.buildup.EffectBuildupPresets;
import com.aetherteam.aetherii.item.AetherIIDataComponents;
import com.aetherteam.aetherii.item.AetherIIItemTiers;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.item.ReinforcementTier;
import com.aetherteam.aetherii.item.tools.abilities.SkyrootTool;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.context.UseOnContext;

public class SkyrootShovelItem extends ShovelItem implements SkyrootTool {
    public SkyrootShovelItem() {
        super(AetherIIItemTiers.SKYROOT, new Properties().attributes(ShovelItem.createAttributes(AetherIIItemTiers.SKYROOT, 1.5F, -3.0F)));
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        if (pContext.getPlayer() != null) {
            pContext.getPlayer().getData(AetherIIDataAttachments.EFFECTS_SYSTEM).addBuildup(EffectBuildupPresets.TOXIN, 500);
        }
        return super.useOn(pContext);
    }

    @Override
    public boolean overrideOtherStackedOnMe(ItemStack stack, ItemStack other, Slot slot, ClickAction action, Player player, SlotAccess access) {
        if (other.is(AetherIIItems.ARKENIUM_PLATES)) {
            stack.set(AetherIIDataComponents.REINFORCEMENT_TIER, ReinforcementTier.FIRST);
            stack.set(DataComponents.MAX_DAMAGE, stack.getMaxDamage() + stack.get(AetherIIDataComponents.REINFORCEMENT_TIER).getExtraDurability());
            other.shrink(1);
            return true;
        }
        AetherII.LOGGER.info(String.valueOf(stack.get(AetherIIDataComponents.REINFORCEMENT_TIER)));
        return super.overrideOtherStackedOnMe(stack, other, slot, action, player, access);
    }
}
