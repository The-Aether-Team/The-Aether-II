package com.aetherteam.aetherii.item.equipment.weapons;

import com.aetherteam.aetherii.entity.projectile.AmberDart;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.aetherteam.aetherii.item.components.BuildupContents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class AmberDartsItem extends ArrowItem {
    public static final int FULL_AMOUNT = 32;

    public AmberDartsItem(Properties properties) {
        super(properties);
    }

    @Override
    public AbstractArrow createArrow(Level level, ItemStack stack, LivingEntity shooter, @Nullable ItemStack weaponStack) {
        return new AmberDart(level, shooter, stack.copyWithCount(1), weaponStack);
    }

    @Override
    public Component getName(ItemStack stack) {
        BuildupContents contents = stack.get(AetherIIDataComponents.BUILDUP_CONTENTS.get());
        return contents != null ? contents.getName(this.descriptionId + ".effect.") : super.getName(stack);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipDisplay, tooltipComponents, tooltipFlag);
        BuildupContents contents = stack.get(AetherIIDataComponents.BUILDUP_CONTENTS.get());
        if (contents != null) {
            contents.addToTooltip(context, tooltipComponents, tooltipFlag, stack.getComponents());
        }
    }
}
