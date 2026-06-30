package com.aetherteam.aetherii.item.equipment.weapons.zanite;

import com.aetherteam.aetherii.AetherIIStats;
import com.aetherteam.aetherii.entity.attributes.AetherIIAttributes;
import com.aetherteam.aetherii.item.equipment.AetherIIToolMaterials;
import com.aetherteam.aetherii.item.equipment.weapons.TieredPikeItem;
import com.aetherteam.aetherii.item.equipment.weapons.abilities.ZaniteWeapon;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraftforge.registries.RegistryObject;

public class ZanitePikeItem extends TieredPikeItem implements ZaniteWeapon {
    public ZanitePikeItem(Properties properties) {
        super(AetherIIToolMaterials.ZANITE, 3, -2.4F, AetherIIStats.ZANITE_PIKE, properties);
    }

    @Override
    public RegistryObject<Attribute> getDamageType() {
        return AetherIIAttributes.PIERCE_DAMAGE;
    }
}
