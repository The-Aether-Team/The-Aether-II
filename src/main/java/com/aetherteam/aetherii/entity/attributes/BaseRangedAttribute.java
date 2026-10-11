package com.aetherteam.aetherii.entity.attributes;

import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.item.equipment.weapons.TieredHammerItem;
import com.aetherteam.aetherii.item.equipment.weapons.TieredPikeItem;
import com.aetherteam.aetherii.item.equipment.weapons.TieredShortswordItem;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import org.jetbrains.annotations.Nullable;

public class BaseRangedAttribute extends RangedAttribute {
    public BaseRangedAttribute(String descriptionId, double defaultValue, double min, double max) {
        super(descriptionId, defaultValue, min, max);
    }

    @Override
    public @Nullable Identifier getBaseId() {
        if (this == AetherIIAttributes.SLASH_DAMAGE.get()) {
            return AetherIIItems.BASE_SLASH_DAMAGE_ID;
        } else if (this == AetherIIAttributes.SLASH_RANGED_DAMAGE.get()) {
            return AetherIIItems.BASE_SLASH_RANGED_DAMAGE_ID;
        } else if (this == AetherIIAttributes.IMPACT_DAMAGE.get()) {
            return AetherIIItems.BASE_IMPACT_DAMAGE_ID;
        } else if (this == AetherIIAttributes.IMPACT_RANGED_DAMAGE.get()) {
            return AetherIIItems.BASE_IMPACT_RANGED_DAMAGE_ID;
        } else if (this == AetherIIAttributes.PIERCE_DAMAGE.get()) {
            return AetherIIItems.BASE_PIERCE_DAMAGE_ID;
        } else if (this == AetherIIAttributes.PIERCE_RANGED_DAMAGE.get()) {
            return AetherIIItems.BASE_PIERCE_RANGED_DAMAGE_ID;
        } else if (this == AetherIIAttributes.SWEEP_RANGE.get()) {
            return TieredShortswordItem.BASE_SWEEP_RANGE_ID;
        } else if (this == AetherIIAttributes.SHOCK_RANGE.get()) {
            return TieredHammerItem.BASE_SHOCK_RANGE_ID;
        } else if (this == AetherIIAttributes.STAB_RADIUS.get()) {
            return TieredPikeItem.BASE_STAB_RADIUS_ID;
        } else if (this == AetherIIAttributes.STAB_DISTANCE.get()) {
            return TieredPikeItem.BASE_STAB_DISTANCE_ID;
        } else {
            return super.getBaseId();
        }
    }
}
