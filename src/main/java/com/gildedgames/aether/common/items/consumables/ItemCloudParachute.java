package com.gildedgames.aether.common.items.consumables;

import com.gildedgames.aether.common.player.PlayerAether;
import com.gildedgames.aether.common.player.abilites.AbilityParachute;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemCloudParachute extends Item
{
	public enum ParachuteType
	{
		COLD("cold"), BLUE("blue"), GOLDEN("golden"), GREEN("green"), PURPLE("purple");

		private final String name;

		ParachuteType(String name)
		{
			this.name = name;
		}

		public static ParachuteType fromOrdinal(int ordinal)
		{
			ParachuteType[] darts = values();

			return darts[ordinal > darts.length || ordinal < 0 ? 0 : ordinal];
		}
	}

	public ItemCloudParachute()
	{
		this.setHasSubtypes(true);

		this.setMaxStackSize(1);
	}

	@Override
	@SuppressWarnings("unchecked")
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item itemIn, CreativeTabs tab, List subItems)
	{
		for (ParachuteType type : ParachuteType.values())
		{
			subItems.add(new ItemStack(itemIn, 1, type.ordinal()));
		}
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		if (world.isRemote)
		{
			PlayerAether playerAether = PlayerAether.getPlayer(player);
			AbilityParachute abilityParachute = playerAether.getAbilityParachute();

			ParachuteType type = ParachuteType.fromOrdinal(stack.getMetadata());

			if (abilityParachute.getParachuteType() == type && abilityParachute.isOpen())
			{
				abilityParachute.setOpen(false);
			}
			else
			{
				abilityParachute.openParachute(type);
			}
		}

		return stack;
	}

	public String getUnlocalizedName(ItemStack stack)
	{
		return super.getUnlocalizedName(stack) + "." + ParachuteType.fromOrdinal(stack.getMetadata()).name;
	}
}
