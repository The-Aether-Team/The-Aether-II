package com.gildedgames.aether.common.entities.effects.abilities.player;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import com.gildedgames.aether.common.entities.effects.Ability;
import com.gildedgames.aether.common.entities.effects.AbilityRule;
import com.gildedgames.aether.common.entities.effects.EntityEffect;
import com.gildedgames.aether.common.entities.effects.EntityEffectBuilder;
import com.gildedgames.aether.common.items.weapons.swords.ItemSkyrootSword;


/**
 * @author Brandon Pearce
 */
public class DoubleDropAbility<S extends Entity> implements Ability<S>
{

	private DoubleDropAbility()
	{
		
	}
	
	@SafeVarargs
	public static <S extends EntityLivingBase> EntityEffect<S> build(Class<S> cls, float percentChance, AbilityRule<S>... rules)
	{
		EntityEffect<S> effect = new EntityEffectBuilder<S>().abilities(new DoubleDropAbility<S>()).flush(rules);
		
		effect.getAttributes().setFloat("percentChance", percentChance);
		
		return effect;
	}

	@Override
	public String getUnlocalizedName(S source, EntityEffect<S> instance, NBTTagCompound attributes)
	{
		return "ability.doubleDrops.name";
	}

	@Override
	public String[] getUnlocalizedDesc(S source, EntityEffect<S> instance, NBTTagCompound attributes)
	{
		return new String[] { "ability.doubleDrops.desc1", "ability.doubleDrops.desc2" };
	}
	
	@Override
	public void formatLocalizedDesc(List<String> localizedDesc, S source, EntityEffect<S> instance, NBTTagCompound attributes)
	{
		localizedDesc.set(1, String.format(localizedDesc.get(1), (int)(attributes.getFloat("percentChance") * 10)));
	}
	
	@Override
	public void initAttributes(S source, NBTTagCompound attributes)
	{
		
	}

	@Override
	public void onKill(LivingDropsEvent event, S source, EntityEffect<S> holder, NBTTagCompound attributes)
	{
		EntityLivingBase target = event.entityLiving;
		
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

		if ((float)target.getRNG().nextInt(10) < ((float)attributes.getInteger("modifier") * attributes.getFloat("percentChance")))
		{
			List<ItemStack> stacks = new ArrayList<ItemStack>();

			for (EntityItem item : event.drops)
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

	@Override
	public void apply(S source, EntityEffect<S> holder, NBTTagCompound attributes) {}

	@Override
	public void tick(S source, EntityEffect<S> holder, NBTTagCompound attributes) {}

	@Override
	public void cancel(S source, EntityEffect<S> holder, NBTTagCompound attributes) {}

	@Override
	public void onAttack(LivingHurtEvent event, S source, EntityEffect<S> holder, NBTTagCompound attributes) {}

}
