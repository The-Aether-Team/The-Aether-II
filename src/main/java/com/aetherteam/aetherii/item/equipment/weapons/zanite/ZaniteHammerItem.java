package com.aetherteam.aetherii.item.equipment.weapons.zanite;

import com.aetherteam.aetherii.AetherIIStats;
import com.aetherteam.aetherii.entity.attributes.AetherIIAttributes;
import com.aetherteam.aetherii.item.equipment.AetherIIToolMaterials;
import com.aetherteam.aetherii.item.equipment.weapons.TieredHammerItem;
import com.aetherteam.aetherii.item.equipment.weapons.abilities.ZaniteWeapon;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraftforge.registries.RegistryObject;

public class ZaniteHammerItem extends TieredHammerItem implements ZaniteWeapon {
    public ZaniteHammerItem(Properties properties) {
        super(AetherIIToolMaterials.ZANITE, 3, -2.4F, AetherIIStats.ZANITE_HAMMER, properties);
    }

    @Override
    public RegistryObject<Attribute> getDamageType() {
        return AetherIIAttributes.IMPACT_DAMAGE;
    }
}
