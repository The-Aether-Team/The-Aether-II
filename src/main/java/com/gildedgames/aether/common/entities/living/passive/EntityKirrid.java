package com.gildedgames.aether.common.entities.living.passive;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.entities.EntitiesAether;
import com.gildedgames.aether.common.entities.util.AetherSpawnEggInfo;
import com.gildedgames.aether.common.items.ItemsAether;
import com.gildedgames.aether.common.items.misc.ItemAetherSpawnEgg;
import com.google.common.collect.Sets;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Set;

public class EntityKirrid extends EntitySheep
{

	private static final Set<Item> TEMPTATION_ITEMS = Sets.newHashSet(Items.WHEAT, ItemsAether.blueberries, ItemsAether.orange, ItemsAether.enchanted_blueberry, ItemsAether.enchanted_wyndberry, ItemsAether.wyndberry);

	public EntityKirrid(World world)
	{
		super(world);

		this.tasks.addTask(3, new EntityAITempt(this, 1.2D, false, TEMPTATION_ITEMS));

		this.setSize(1.1F, 1.6F);

		this.spawnableBlock = BlocksAether.aether_grass;
	}

	@Override
	protected float getSoundPitch()
	{
		return (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 0.55F;
	}

	@Override
	public EntityKirrid createChild(EntityAgeable ageable)
	{
		return new EntityKirrid(this.worldObj);
	}

	@Override
	public boolean isBreedingItem(@Nullable ItemStack stack)
	{
		return stack != null && TEMPTATION_ITEMS.contains(stack.getItem());
	}

	@Override
	protected void dropFewItems(boolean var1, int var2)
	{
		super.dropFewItems(var1, var2);

		if (this.getRNG().nextBoolean())
		{
			this.dropItem(ItemsAether.bone_shard, this.getRNG().nextInt(5) + 1);
		}
	}

	@Override
	public ItemStack getPickedResult(RayTraceResult target)
	{
		String id = EntitiesAether.getStringFromClass(this.getClass());

		if (!EntitiesAether.entityEggs.containsKey(id))
		{
			return null;
		}

		AetherSpawnEggInfo info = EntitiesAether.entityEggs.get(id);

		ItemStack stack = new ItemStack(ItemsAether.aether_spawn_egg, 1);
		ItemAetherSpawnEgg.applyEntityIdToItemStack(stack, info.getSpawnedID());

		return stack;
	}

}
