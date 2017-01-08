package com.gildedgames.aether.common.items.consumables;

import com.gildedgames.aether.common.capabilities.player.PlayerAether;
import com.gildedgames.aether.common.entities.blocks.EntityParachute;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemCloudParachute extends Item
{
	public ItemCloudParachute()
	{
		this.maxStackSize = 1;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List<ItemStack> subItems)
	{
		for (EntityParachute.Type types : EntityParachute.Type.values())
		{
			subItems.add(new ItemStack(this, 1, types.ordinal()));
		}
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> infoList, boolean par4)
	{
		infoList.add(I18n.format("cloudParachute.ability") + "\247r" + I18n.format("cloudParachute.ability." + EntityParachute.Type.fromOrdinal(stack.getMetadata()).name));
		infoList.add(I18n.format("cloudParachute.ability.rightClick"));
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand)
	{
		EntityParachute parachute = new EntityParachute(world, player, EntityParachute.Type.fromOrdinal(stack.getMetadata()));

		PlayerAether playerAether = PlayerAether.getPlayer(player);

		playerAether.getParachuteModule().setParachuting(true, EntityParachute.Type.fromOrdinal(stack.getMetadata()));

		world.spawnEntityInWorld(parachute);

		if (!player.capabilities.isCreativeMode)
		{
			stack.stackSize--;
		}

		return super.onItemRightClick(stack, world, player, hand);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		return "item.aether." + EntityParachute.Type.fromOrdinal(stack.getMetadata()).name + "_cloud_parachute";
	}

}
