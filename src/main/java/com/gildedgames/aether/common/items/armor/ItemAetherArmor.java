package com.gildedgames.aether.common.items.armor;

import com.gildedgames.aether.api.ReflectionAether;
import com.gildedgames.aether.api.damage_system.DamageTypeAttributes;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.item.effects.stats.StatEffectFactory;
import com.gildedgames.aether.common.registry.content.CreativeTabsAether;
import com.google.common.collect.Multimap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.UUID;

public class ItemAetherArmor extends ItemArmor
{
	private static final UUID[] ARMOR_MODIFIERS = new UUID[] { UUID.fromString("a8896424-20ea-4d0a-b0d1-d961dbdcb309"),
			UUID.fromString("48712e68-2b7e-4c3e-95ae-dba24d7e9e84"), UUID.fromString("13552bc9-9a27-4b8f-879b-0d7e68417486"),
			UUID.fromString("fbaef69d-8d56-43e4-ac35-af3a25b28330") };

	private final String name;

	private int slashLevel, pierceLevel, impactLevel;

	public ItemAetherArmor(final ArmorMaterial material, final String name, final EntityEquipmentSlot armorType)
	{
		super(material, 0, armorType);

		this.name = name;

		this.setCreativeTab(CreativeTabsAether.ARMOR);

		Field field = ReflectionHelper.findField(ItemArmor.class, ReflectionAether.DAMAGE_REDUCE_AMOUNT.getMappings());

		try
		{
			Field modifiersField = Field.class.getDeclaredField("modifiers");
			modifiersField.setAccessible(true);
			modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

			field.set(this, 0);
		}
		catch (NoSuchFieldException | IllegalAccessException e)
		{
			AetherCore.LOGGER.error(e);
		}
	}

	public <T extends ItemAetherArmor> T slashDefense(int level)
	{
		this.slashLevel = level;

		return (T) this;
	}

	public <T extends ItemAetherArmor> T pierceDefense(int level)
	{
		this.pierceLevel = level;

		return (T) this;
	}

	public <T extends ItemAetherArmor> T impactDefense(int level)
	{
		this.impactLevel = level;

		return (T) this;
	}

	@Override
	public boolean getIsRepairable(final ItemStack target, final ItemStack stack)
	{
		return false;
	}

	protected boolean isAbilityPassive()
	{
		return true;
	}

	public float getExtraDamageReduction(final ItemStack stack)
	{
		return 0.0f;
	}

	@Override
	public String getArmorTexture(final ItemStack stack, final Entity entity, final EntityEquipmentSlot slot, final String type)
	{
		return AetherCore.getResourcePath("textures/armor/" + this.name + "_layer_" + (slot == EntityEquipmentSlot.LEGS ? 2 :
				1 + (AetherCore.CONFIG.hasCutoutHelmets() ? "b" : "")) + ".png");
	}

	@Override
	public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot)
	{
		Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);

		if (equipmentSlot == this.armorType)
		{
			multimap.put(
					DamageTypeAttributes.SLASH_DEFENSE_LEVEL.getName(),
					new AttributeModifier(ARMOR_MODIFIERS[equipmentSlot.getIndex()], "Slash defense level modifier", (double) this.slashLevel,
							StatEffectFactory.StatProvider.OP_ADD));
			multimap.put(DamageTypeAttributes.PIERCE_DEFENSE_LEVEL.getName(),
					new AttributeModifier(ARMOR_MODIFIERS[equipmentSlot.getIndex()], "Pierce defense level modifier", (double) this.pierceLevel,
							StatEffectFactory.StatProvider.OP_ADD));
			multimap.put(DamageTypeAttributes.IMPACT_DEFENSE_LEVEL.getName(),
					new AttributeModifier(ARMOR_MODIFIERS[equipmentSlot.getIndex()], "Impact defense level modifier", (double) this.impactLevel,
							StatEffectFactory.StatProvider.OP_ADD));
		}

		return multimap;
	}
}
