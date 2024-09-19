package com.aetherteam.aetherii.item.equipment.tools.skyroot;

import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.effect.buildup.EffectBuildupPresets;
import com.aetherteam.aetherii.item.equipment.AetherIIItemTiers;
import com.aetherteam.aetherii.item.equipment.tools.abilities.SkyrootTool;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.level.Level;

public class SkyrootShovelItem extends ShovelItem implements SkyrootTool {
    public SkyrootShovelItem() {
        super(AetherIIItemTiers.SKYROOT, new Properties().attributes(ShovelItem.createAttributes(AetherIIItemTiers.SKYROOT, 1.5F, -3.0F)));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        player.getData(AetherIIDataAttachments.EFFECTS_SYSTEM).addBuildup(EffectBuildupPresets.WOUND, 400);
        player.getData(AetherIIDataAttachments.EFFECTS_SYSTEM).addBuildup(EffectBuildupPresets.FRACTURE, 400);
        return super.use(level, player, usedHand);
    }
}
