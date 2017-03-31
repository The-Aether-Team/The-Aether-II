package com.gildedgames.aether.common.entities.living.npc;

import com.gildedgames.aether.common.entities.EntitiesAether;
import com.gildedgames.aether.common.entities.util.AetherSpawnEggInfo;
import com.gildedgames.aether.common.items.ItemsAether;
import com.gildedgames.aether.common.items.misc.ItemAetherSpawnEgg;
import net.minecraft.entity.EntityCreature;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class EntityNPC extends EntityCreature
{

	public EntityNPC(World worldIn)
	{
		super(worldIn);

		this.isImmuneToFire = true;
	}

	@Override
	public boolean isEntityInvulnerable(DamageSource source)
	{
		return source != DamageSource.outOfWorld || super.isEntityInvulnerable(source);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean canRenderOnFire()
	{
		return false;
	}

	@Override
	protected boolean canDespawn()
	{
		return false;
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
