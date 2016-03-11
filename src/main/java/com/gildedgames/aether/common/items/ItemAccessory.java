package com.gildedgames.aether.common.items;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

import org.apache.commons.lang3.tuple.Pair;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.entities.effects.EffectInstance;
import com.gildedgames.aether.common.entities.effects.EffectProcessor;
import com.gildedgames.aether.common.entities.effects.EffectRule;
import com.gildedgames.aether.common.entities.effects.ItemEffects;
import com.gildedgames.aether.common.player.PlayerAether;

public class ItemAccessory extends Item
{
	
	private final AccessoryType type;
	
	@SuppressWarnings("rawtypes")
	private final List<Pair<EffectProcessor, EffectInstance>> effects = new ArrayList<Pair<EffectProcessor, EffectInstance>>();

	private final ItemRarity rarity;

	public ItemAccessory(AccessoryType type)
	{
		this(ItemRarity.COMMON, type);
	}

	public ItemAccessory(ItemRarity rarity, AccessoryType type)
	{
		this.rarity = rarity;
		
		this.type = type;

		this.setHasSubtypes(true);
		this.setMaxStackSize(1);
	}
	
	public <I extends EffectInstance> ItemAccessory effect(EffectProcessor<I> processor, I instanceFactory)
	{
		Pair<EffectProcessor, EffectInstance> effectPair = Pair.of((EffectProcessor)processor, (EffectInstance)instanceFactory);
		
		this.effects.add(effectPair);
		
		return this;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		PlayerAether aePlayer = PlayerAether.get(player);

		int nextEmptySlot = aePlayer.getInventoryAccessories().getNextEmptySlotForType(this.getType());

		if (nextEmptySlot != -1)
		{
			aePlayer.getInventoryAccessories().setInventorySlotContents(nextEmptySlot, stack.copy());

			stack.stackSize = 0;
		}

		return stack;
	}
	
	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int par4, boolean par5)
	{
		super.onUpdate(stack, world, entity, par4, par5);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> infoList, boolean advanced)
	{
		infoList.add(I18n.format(this.getRarity().getUnlocalizedName()));
		
		if (!stack.hasCapability(AetherCore.ITEM_EFFECTS_CAPABILITY, null))
		{
			return;
		}
		
		ItemEffects effects = stack.getCapability(AetherCore.ITEM_EFFECTS_CAPABILITY, null);
		
		if (effects.getEffectPairs().size() <= 0)
		{
			infoList.add(I18n.format("ability.cosmetic").replace("Format error: ", ""));
		}

		for (Pair<EffectProcessor, EffectInstance> effect : effects.getEffectPairs())
		{
			EffectProcessor processor = effect.getLeft();
			EffectInstance instance = effect.getRight();
			
			List<String> localizedDesc = new ArrayList<String>();

			for (String line : processor.getUnlocalizedDesc(player, instance))
			{
				localizedDesc.add(I18n.format(line).replace("Format error: ", ""));
			}
			
			processor.formatLocalizedDesc(localizedDesc, player, instance);
			
			infoList.addAll(localizedDesc);
			
			if (instance.getRules().length > 0)
			{
				//infoList.add(I18n.format("ability.active"));
			}

			for (EffectRule rule : instance.getRules())
			{
				for (String line : rule.getUnlocalizedDesc())
				{
					infoList.add(EnumChatFormatting.GRAY + "• " + I18n.format(line).replace("Format error: ", ""));
				}
			}
		}

		infoList.add("");
		infoList.add(EnumChatFormatting.DARK_GRAY + "" + EnumChatFormatting.ITALIC + I18n.format(this.getType().getUnlocalizedName()));
	}
	
	@Override
	public boolean getShareTag()
	{
		return true;
	}

	public AccessoryType getType()
	{
		return this.type;
	}
	
	public List<Pair<EffectProcessor, EffectInstance>> getEffects()
	{
		return this.effects;
	}
	
	public ItemRarity getRarity()
	{
		return this.rarity;
	}
	
}
