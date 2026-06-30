package com.aetherteam.aetherii.item.equipment.weapons;

import java.util.List;

import com.aetherteam.aetherii.entity.projectile.AmberDart;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.aetherteam.aetherii.item.components.BuildupContents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import com.aetherteam.aetherii.item.components.TooltipDisplay;
import net.minecraft.world.level.Level;
public class AmberDartsItem extends ArrowItem {
    public static final int FULL_AMOUNT = 32;

    public AmberDartsItem(Properties properties) {
        super(properties);
    }

    @Override
    public AbstractArrow createArrow(Level level, ItemStack stack, LivingEntity shooter) {
        return new AmberDart(level, shooter, stack.copyWithCount(1), null);
    }

    @Override
    public Component getName(ItemStack stack) {
        BuildupContents contents = AetherIIDataComponents.get(stack, AetherIIDataComponents.BUILDUP_CONTENTS);
        return contents != null ? contents.getName(this.getDescriptionId() + ".effect.") : super.getName(stack);
    }

    @Override
    public void appendHoverText(ItemStack stack, net.minecraft.world.level.Level level, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, level, tooltipComponents, tooltipFlag);
        BuildupContents contents = AetherIIDataComponents.get(stack, AetherIIDataComponents.BUILDUP_CONTENTS);
        if (contents != null) {
            contents.addToTooltip(level, tooltipComponents::add, tooltipFlag, com.aetherteam.aetherii.item.components.DataComponentGetter.EMPTY);
        }
    }
}
