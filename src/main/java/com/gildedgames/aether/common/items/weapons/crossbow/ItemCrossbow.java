package com.gildedgames.aether.common.items.weapons.crossbow;

import com.gildedgames.aether.api.player.IPlayerAetherCapability;
import com.gildedgames.aether.common.entities.projectiles.EntityBolt;
import com.gildedgames.aether.common.items.ItemsAether;
import com.gildedgames.aether.common.player.PlayerAether;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemCrossbow extends Item
{

	public static final String[] crossbowIconNameArray = new String[] {"_fired", "_loaded", "_loading1", "_loading2"};

	private final int AMMOSLOT = 6;
	private final int DURATION = 180;

	public ItemCrossbow()
	{
		this.maxStackSize = 1;
	}

	// Should be adjusted to check for quiver accessory slot, currently checks the first space in players inventory for ammo.
	private int getBoltType(IPlayerAetherCapability player)
	{
		ItemStack boltStack = player.getEquipmentInventory().getStackInSlot(AMMOSLOT);

		if (hasAmmo(player))
			return boltStack.getMetadata();

		return -1;
	}

	private boolean hasAmmo(IPlayerAetherCapability player)
	{
		ItemStack boltStack = player.getEquipmentInventory().getStackInSlot(AMMOSLOT);

		if (boltStack != null && boltStack.getItem() == ItemsAether.bolt && boltStack.stackSize > 0)
		{
			return true;
		}

		return false;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand)
	{
		if (itemStackIn.getMetadata() < 3 || itemStackIn.getMetadata() == 4)
		{
			if (this.hasAmmo(PlayerAether.getPlayer(playerIn)))
			{
				playerIn.setActiveHand(EnumHand.MAIN_HAND);
			}
		}

		return new ActionResult(EnumActionResult.PASS, itemStackIn);
	}

	@Override
	public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack)
	{
		EntityBolt dart = new EntityBolt(entityLiving.getEntityWorld(), entityLiving, 2.0F);
		if (stack.getItemDamage() > 0)
		{
			entityLiving.getEntityWorld().spawnEntityInWorld(dart);
			stack.setItemDamage(stack.getItemDamage()-1);
			return true;
		}
		return false;
	}

	@Override
	public void onUsingTick(ItemStack stack, EntityLivingBase player, int count)
	{
		ItemStack boltStack = PlayerAether.getPlayer(player).getEquipmentInventory().getStackInSlot(AMMOSLOT);

		if (stack.getItemDamage() == 4)
		{
			stack.setItemDamage(0);
			player.stopActiveHand();
		}

		if (this.hasAmmo(PlayerAether.getPlayer(player)))
		{
			if (count == DURATION-10 && stack.getItemDamage() == 0)
			{
				stack.setItemDamage(1);
				boltStack.stackSize -= 1;
			}
			if (count == (DURATION - (DURATION / 3)) && stack.getItemDamage() == 1)
			{
				stack.setItemDamage(2);
				boltStack.stackSize -= 1;
			}
			if (count <= 20 && stack.getItemDamage() == 2)
			{
				stack.setItemDamage(3);
				boltStack.stackSize -=1;
			}
		}
		else
		{
			player.stopActiveHand();
		}
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase playerIn)
	{
		stack.setItemDamage(3);

		return stack;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack)
	{
		return DURATION; //80~4 seconds
	}
}
