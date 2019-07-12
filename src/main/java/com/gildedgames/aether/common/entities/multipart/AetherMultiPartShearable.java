package com.gildedgames.aether.common.entities.multipart;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.common.IShearable;

import java.util.List;
import java.util.Random;

public class AetherMultiPartShearable extends AetherMultiPartEntity
{

	public AetherMultiPartShearable(IEntityMultiPart parent, String partName, float width, float height)
	{
		super(parent, partName, width, height);
	}

	@Override
	public ActionResultType applyPlayerInteraction(PlayerEntity player, Vec3d vec, Hand hand)
	{
		ItemStack held = player.getHeldItem(hand);

		if (held.isEmpty() || !(held.getItem() instanceof ItemShears))
		{
			return ActionResultType.FAIL;
		}

		MobEntity entity = (MobEntity) this.parent;
		IShearable shearable = (IShearable) this.parent;

		if (shearable.isShearable(held, player.world, entity.getPosition()) && !this.world.isRemote)
		{
			List<ItemStack> drops = shearable.onSheared(held, entity.world, entity.getPosition(),
					EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, held));

			Random rand = player.world.rand;

			for (ItemStack stack : drops)
			{
				ItemEntity ent = this.entityDropItem(stack, 1.0F);

				ent.motionY += rand.nextFloat() * 0.05F;
				ent.motionX += (rand.nextFloat() - rand.nextFloat()) * 0.1F;
				ent.motionZ += (rand.nextFloat() - rand.nextFloat()) * 0.1F;
			}

			held.damageItem(1, entity);

			return ActionResultType.SUCCESS;
		}

		return super.applyPlayerInteraction(player, vec, hand);
	}

}
