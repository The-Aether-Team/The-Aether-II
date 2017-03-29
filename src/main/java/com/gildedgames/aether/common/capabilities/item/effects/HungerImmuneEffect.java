package com.gildedgames.aether.common.capabilities.item.effects;

import com.gildedgames.aether.api.capabilites.entity.IPlayerAether;
import com.gildedgames.aether.api.items.equipment.effects.EffectHelper;
import com.gildedgames.aether.api.items.equipment.effects.IEffect;
import com.gildedgames.aether.api.items.equipment.effects.IEffectInstance;
import com.gildedgames.aether.api.items.equipment.effects.IEffectProvider;
import com.gildedgames.aether.common.AetherCore;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

import java.util.Collection;


public class HungerImmuneEffect implements IEffect<HungerImmuneEffect.Provider>
{

	private static final ResourceLocation NAME = new ResourceLocation(AetherCore.MOD_ID, "hunger_immune");

	@Override
	public IEffectInstance createInstance(Collection<Provider> providers)
	{
		Instance state = new Instance();
		state.type = EffectHelper.combineInt(providers, instance -> instance.type);

		return state;
	}

	@Override
	public ResourceLocation getIdentifier()
	{
		return HungerImmuneEffect.NAME;
	}

	/*
 	*  The hunger immunity effect can be dependant on several factors, which should be provided when the effect is added.
 	*  type == 0 { effect is constant }
 	*  type == 1 { effect is direct sunlight }
 	*  type == 2 { effect is direct moonlight }
 	*/
	public static class Provider implements IEffectProvider
	{
		private final int type;

		public Provider(int type)
		{
			this.type = type;
		}
		@Override
		public ResourceLocation getFactory()
		{
			return HungerImmuneEffect.NAME;
		}
	}

	class Instance implements IEffectInstance
	{

		private int type;

		@Override
		public void onEntityUpdate(IPlayerAether player)
		{
			setPlayerFull(player.getEntity());
			/*
			if (type == 0) {
				setPlayerFull(player.getEntity());
				AetherCore.LOGGER.info("Constant");
			}
			else if (type == 1)
			{
				if (isInDirectSunlight(player.getEntity()))
				{
					setPlayerFull(player.getEntity());
					AetherCore.LOGGER.info("Sunlight");
				}
			}
			else if (type == 2)
			{
				if (isInDirectMoonlight(player.getEntity()))
				{
					setPlayerFull(player.getEntity());
					AetherCore.LOGGER.info("Moonlight");
				}
			}
			*/
		}

		@Override
		public void addItemInformation(Collection<String> label)
		{
			String ext;
			if (type == 0)
			{
				ext = "Constant";
			}
			else if (type == 1)
			{
				ext = "Sunlight";
			}
			else if (type == 2)
			{
				ext = "Moonlight";
			}
			else
			{
				ext = "NULL";
			}
			label.add(TextFormatting.YELLOW.toString() + TextFormatting.ITALIC.toString() + ext + " Hunger Immunity");
		}

		private void setPlayerFull(EntityPlayer player)
		{
			player.getFoodStats().setFoodLevel(30);
		}

		// currently not working, worldObj.isDaytime() seems to not be returning the proper values.
		private boolean isInDirectSunlight(EntityPlayer player)
		{
			return (player.world.getLight(player.getPosition()) >= 15 && player.world.isDaytime());
		}

		private boolean isInDirectMoonlight(EntityPlayer player)
		{
			return (player.world.getLight(player.getPosition()) >= 15 && !player.world.isDaytime());
		}
	}
}
