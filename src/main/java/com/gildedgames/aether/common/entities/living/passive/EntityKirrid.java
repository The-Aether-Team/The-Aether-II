package com.gildedgames.aether.common.entities.living.passive;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.entities.EntitiesAether;
import com.gildedgames.aether.common.entities.util.AetherSpawnEggInfo;
import com.gildedgames.aether.common.items.ItemsAether;
import com.gildedgames.aether.common.items.misc.ItemAetherSpawnEgg;
import com.gildedgames.aether.common.registry.content.LootTablesAether;
import com.google.common.collect.Sets;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class EntityKirrid extends EntitySheep
{

	private static final Set<Item> TEMPTATION_ITEMS = Sets.newHashSet(Items.WHEAT, ItemsAether.blueberries, ItemsAether.orange, ItemsAether.enchanted_blueberry, ItemsAether.enchanted_wyndberry, ItemsAether.wyndberry);

	public EntityKirrid(World world)
	{
		super(world);

		this.tasks.addTask(3, new EntityAITempt(this, 1.2D, false, TEMPTATION_ITEMS));

		this.setSize(1.0F, 1.5F);

		this.spawnableBlock = BlocksAether.aether_grass;
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();

		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(12.0D);
	}

	@Override
	@Nullable
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata)
	{
		livingdata = super.onInitialSpawn(difficulty, livingdata);

		this.setFleeceColor(EnumDyeColor.WHITE);

		return livingdata;
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

	@Nullable
	protected ResourceLocation getLootTable()
	{
		if (this.getSheared())
		{
			return LootTablesAether.ENTITY_KIRRID_SHEARED;
		}

		return LootTablesAether.ENTITY_KIRRID;
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

	@Override
	public List<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune)
	{
		this.setSheared(true);

		int count = 1 + this.rand.nextInt(3);

		List<ItemStack> ret = new ArrayList<>();

		for (int i = 0; i < count; i++)
		{
			ret.add(new ItemStack(ItemsAether.cloudwool));
		}

		this.playSound(SoundEvents.ENTITY_SHEEP_SHEAR, 1.0F, 1.0F);

		return ret;
	}

}
