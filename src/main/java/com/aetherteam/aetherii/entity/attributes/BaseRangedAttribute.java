package com.aetherteam.aetherii.entity.attributes;

import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.item.equipment.weapons.TieredHammerItem;
import com.aetherteam.aetherii.item.equipment.weapons.TieredShortswordItem;
import com.aetherteam.aetherii.item.equipment.weapons.TieredSpearItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import org.jetbrains.annotations.Nullable;

public class BaseRangedAttribute extends RangedAttribute {
    public BaseRangedAttribute(String descriptionId, double defaultValue, double min, double max) {
        super(descriptionId, defaultValue, min, max);
    }

    @Override
    public @Nullable ResourceLocation getBaseId() {
        if (this == AetherIIAttributes.SLASH_DAMAGE.get()) {
            return AetherIIItems.BASE_SLASH_DAMAGE_ID;
        } else if (this == AetherIIAttributes.IMPACT_DAMAGE.get()) {
            return AetherIIItems.BASE_IMPACT_DAMAGE_ID;
        } else if (this == AetherIIAttributes.PIERCE_DAMAGE.get()) {
            return AetherIIItems.BASE_PIERCE_DAMAGE_ID;
        } else if (this == AetherIIAttributes.SWEEP_RANGE.get()) {
            return TieredShortswordItem.BASE_SWEEP_RANGE_ID;
        } else if (this == AetherIIAttributes.SHOCK_RANGE.get()) {
            return TieredHammerItem.BASE_SHOCK_RANGE_ID;
        } else if (this == AetherIIAttributes.STAB_RADIUS.get()) {
            return TieredSpearItem.BASE_STAB_RADIUS_ID;
        } else if (this == AetherIIAttributes.STAB_DISTANCE.get()) {
            return TieredSpearItem.BASE_STAB_DISTANCE_ID;
        } else {
            return super.getBaseId();
        }
    }
}
