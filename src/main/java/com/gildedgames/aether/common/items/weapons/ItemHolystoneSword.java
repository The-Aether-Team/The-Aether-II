package com.gildedgames.aether.common.items.weapons;

import com.gildedgames.aether.common.items.ItemsAether;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemHolystoneSword extends ItemAetherSword
{
	public ItemHolystoneSword()
	{
		super(ToolMaterial.STONE);
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
	{
		if (target.worldObj.rand.nextInt(100) <= 5)
		{
			EntityItem entityItem = new EntityItem(target.worldObj, target.posX, target.posY, target.posZ);
			entityItem.setEntityItemStack(new ItemStack(ItemsAether.ambrosium_shard, 1));

			target.worldObj.spawnEntityInWorld(entityItem);
		}

		return super.hitEntity(stack, target, attacker);
	}

	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings("unchecked")
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced)
	{
		tooltip.add(EnumChatFormatting.BLUE + "Ability: " + EnumChatFormatting.WHITE + "Chance to drop Ambrosium");
		tooltip.add(EnumChatFormatting.WHITE + "when attacking mobs");
	}
}
