package com.gildedgames.aether.common.items;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.gildedgames.aether.common.entities.effects.Ability;
import com.gildedgames.aether.common.entities.effects.AbilityRule;
import com.gildedgames.aether.common.entities.effects.EntityEffect;
import com.gildedgames.aether.common.player.PlayerAether;
import com.mojang.realmsclient.gui.ChatFormatting;

public class ItemAccessory extends Item
{
	
	private final AccessoryType type;

	private final EntityEffect<EntityPlayer>[] effects;
	
	@SafeVarargs
	public ItemAccessory(AccessoryType type, EntityEffect<EntityPlayer>... effects)
	{
		this.type = type;

		this.setHasSubtypes(true);
		this.setMaxStackSize(1);

		this.effects = effects;
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
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> infoList, boolean advanced)
	{
		if (this.effects.length <= 0)
		{
			infoList.add(I18n.format("ability.cosmetic").replace("Format error: ", ""));
		}
		
		for (EntityEffect<EntityPlayer> effect : this.effects)
		{
			List<String> localizedDesc = new ArrayList<String>();
			
			for (Ability<EntityPlayer> ability : effect.getAbilities())
			{
				for (String line : ability.getUnlocalizedDesc(player, effect, effect.getAttributes()))
				{
					localizedDesc.add(I18n.format(line).replace("Format error: ", ""));
				}
				
				ability.formatLocalizedDesc(localizedDesc, player, effect, effect.getAttributes());
			}
			
			infoList.addAll(localizedDesc);
			
			if (effect.getRules().length > 0)
			{
				//infoList.add(I18n.format("ability.active"));
			}

			for (AbilityRule<EntityPlayer> rule : effect.getRules())
			{
				for (String line : rule.getUnlocalizedDesc())
				{
					infoList.add(ChatFormatting.GRAY + "• " + I18n.format(line).replace("Format error: ", ""));
				}
			}
		}
		
		infoList.add(ChatFormatting.DARK_GRAY + "" + ChatFormatting.ITALIC + I18n.format(this.getType().getUnlocalizedName()));
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
	
	public EntityEffect<EntityPlayer>[] getEffects()
	{
		return this.effects;
	}
	
}
