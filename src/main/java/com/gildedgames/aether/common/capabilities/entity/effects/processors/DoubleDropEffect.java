package com.gildedgames.aether.common.capabilities.entity.effects.processors;

import com.gildedgames.aether.api.capabilites.entity.effects.EntityEffectInstance;
import com.gildedgames.aether.api.capabilites.entity.effects.EntityEffectRule;
import com.gildedgames.aether.common.capabilities.entity.effects.AbstractEffectProcessor;
import com.gildedgames.aether.common.capabilities.entity.effects.processors.DoubleDropEffect.Instance;
import com.gildedgames.aether.common.items.weapons.swords.ItemSkyrootSword;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.living.LivingDropsEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Brandon Pearce
 */
public class DoubleDropEffect extends AbstractEffectProcessor<Instance>
{

	public static class Instance extends EntityEffectInstance
	{

		public Instance(double percentChance, EntityEffectRule... rules)
		{
			super(rules);

			this.getAttributes().setDouble("percentChance", percentChance);
		}

		@Override
		public EntityEffectInstance cloneInstance()
		{
			return new Instance(this.getAttributes().getDouble("percentChance"), this.getRules());
		}

	}

	public DoubleDropEffect()
	{
		super("ability.doubleDrops.localizedName", "ability.doubleDrops.desc1", "ability.doubleDrops.desc2");
	}

	@Override
	public String[] getFormatParameters(Entity source, Instance instance)
	{
		return new String[] { "\u2022" + TextFormatting.ITALIC + " +" + String.valueOf((int) (instance.getAttributes().getFloat("percentChance") * 10.0f)) };
	}

	@Override
	public void onKill(LivingDropsEvent event, Entity source, List<Instance> all)
	{
		EntityLivingBase target = event.getEntityLiving();

		if (target instanceof EntityPlayer)
		{
			return;
		}

		if (target instanceof EntityHorse)
		{
			if (((EntityHorse) target).isChested())
			{
				return;
			}
		}

		double totalPercent = 0.0D;

		for (Instance instance : all)
		{
			totalPercent += instance.getAttributes().getDouble("percentChance");
		}

		float chance = (float) target.getRNG().nextInt(10);

		if (chance < totalPercent)
		{
			List<ItemStack> stacks = new ArrayList<>();

			for (EntityItem item : event.getDrops())
			{
				if (!ItemSkyrootSword.blacklistedItems.contains(item.getEntityItem().getItem()))
				{
					stacks.add(item.getEntityItem());
				}
			}

			for (ItemStack droppedLoot : stacks)
			{
				EntityItem item = new EntityItem(target.worldObj, target.posX, target.posY, target.posZ, droppedLoot);

				target.worldObj.spawnEntityInWorld(item);
			}
		}
	}

}
