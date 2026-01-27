package com.aetherteam.aetherii.item.equipment.weapons.zanite;

import com.aetherteam.aetherii.AetherIIStats;
import com.aetherteam.aetherii.entity.attributes.AetherIIAttributes;
import com.aetherteam.aetherii.item.equipment.AetherIIItemTiers;
import com.aetherteam.aetherii.item.equipment.weapons.TieredHammerItem;
import com.aetherteam.aetherii.item.equipment.weapons.abilities.ZaniteWeapon;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.attributes.Attribute;

public class ZaniteHammerItem extends TieredHammerItem implements ZaniteWeapon {
    public ZaniteHammerItem(Properties properties) {
        super(applyWeaponProperties(properties, AetherIIItemTiers.ZANITE, 3, -2.4F, AetherIIStats.ZANITE_HAMMER));
    }

    @Override
    public Holder<Attribute> getDamageType() {
        return AetherIIAttributes.IMPACT_DAMAGE;
    }
}
