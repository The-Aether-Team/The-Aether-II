package com.gildedgames.aether.common.items.consumables;

import com.gildedgames.aether.common.entities.projectiles.EntitySentryVault;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;

public class ItemSentryVault extends Item
{

	public ItemSentryVault()
	{
		super();
		
		this.maxStackSize = 1;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand)
	{
		if (!player.capabilities.isCreativeMode)
		{
            --stack.stackSize;
        }

		world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

		if (!world.isRemote)
        {
			EntitySentryVault sentry_vault = new EntitySentryVault(world, player);
			sentry_vault.setHeadingFromThrower(player, player.rotationPitch, player.rotationYaw, 0.0F, 1.5F, 1.0F);
            world.spawnEntityInWorld(sentry_vault);
        }

		player.addStat(StatList.getObjectUseStats(this));
		
		return new ActionResult<>(EnumActionResult.SUCCESS, stack);
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean par4)
	{
		tooltip.add(I18n.format(this.getUnlocalizedName() + ".ability.desc1"));
		tooltip.add(I18n.format(this.getUnlocalizedName() + ".ability.desc2"));
	}

}
