package com.aetherteam.aetherii.item.equipment.weapons.zanite;

import com.aetherteam.aetherii.AetherIIStats;
import com.aetherteam.aetherii.entity.attributes.AetherIIAttributes;
import com.aetherteam.aetherii.item.equipment.AetherIIItemTiers;
import com.aetherteam.aetherii.item.equipment.weapons.TieredShortswordItem;
import com.aetherteam.aetherii.item.equipment.weapons.abilities.ZaniteWeapon;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.attributes.Attribute;

public class ZaniteShortswordItem extends TieredShortswordItem implements ZaniteWeapon {
    public ZaniteShortswordItem(Properties properties) {
        super(applyWeaponProperties(properties, AetherIIItemTiers.ZANITE, 3, -2.4F, AetherIIStats.ZANITE_SHORTSWORD));
    }

    @Override
    public Holder<Attribute> getDamageType() {
        return AetherIIAttributes.SLASH_DAMAGE;
    }
}
