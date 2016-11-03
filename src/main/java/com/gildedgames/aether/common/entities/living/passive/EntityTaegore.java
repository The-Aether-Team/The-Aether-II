package com.gildedgames.aether.common.entities.living.passive;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.items.ItemsAether;
import com.google.common.collect.Sets;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import us.ichun.mods.ichunutil.common.module.tabula.client.formats.ImportList;
import us.ichun.mods.ichunutil.common.module.tabula.client.model.IAnimatedEntity;
import us.ichun.mods.ichunutil.common.module.tabula.common.project.ProjectInfo;

import javax.annotation.Nullable;
import java.util.Set;

public class EntityTaegore extends EntityAetherAnimal implements IAnimatedEntity
{

	private static final Set<Item> TEMPTATION_ITEMS = Sets.newHashSet(Items.WHEAT, ItemsAether.blueberries, ItemsAether.orange, ItemsAether.enchanted_blueberry, ItemsAether.enchanted_wyndberry, ItemsAether.wyndberry);

	private static final ResourceLocation MODEL = new ResourceLocation(AetherCore.MOD_ID, "models/entities/taegore.tbl");

	private ProjectInfo projectInfo = ImportList.createProjectFromResource(MODEL);

	public EntityTaegore(World world)
	{
		super(world);

		this.setSize(1.5F, 1.5F);

		this.spawnableBlock = BlocksAether.aether_grass;
	}

	@Override
	protected void initEntityAI()
	{
		super.initEntityAI();

		this.tasks.addTask(3, new EntityAITempt(this, 1.2D, false, TEMPTATION_ITEMS));

		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(1, new EntityAIPanic(this, 2.0D));
		this.tasks.addTask(2, new EntityAIMate(this, 1.0D));
		this.tasks.addTask(3, new EntityAITempt(this, 1.2D, false, TEMPTATION_ITEMS));
		this.tasks.addTask(4, new EntityAIFollowParent(this, 1.25D));
		this.tasks.addTask(5, new EntityAIWander(this, 1.0D));
		this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
		this.tasks.addTask(7, new EntityAILookIdle(this));
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();

		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.2D);
	}

	@Override
	public EntityTaegore createChild(EntityAgeable ageable)
	{
		return new EntityTaegore(this.worldObj);
	}

	@Override
	public boolean isBreedingItem(@Nullable ItemStack stack)
	{
		return stack != null && TEMPTATION_ITEMS.contains(stack.getItem());
	}

	@Override
	public ProjectInfo getProjectInfo()
	{
		return this.projectInfo;
	}

	@Override
	public void onUpdate()
	{
		super.onUpdate();

		if (this.worldObj.isRemote)
		{
			this.projectInfo.getAnimation("Walk cycle").playSpeed = 1.0F;//Math.max(0.5F, 30.0F * Math.abs((float)(this.motionX + this.motionZ) / 2.0F));

			this.projectInfo.getAnimation("Walk cycle").loops = false;

			if (this.limbSwing * this.limbSwingAmount > 1.0F)
			{
				this.projectInfo.getAnimation("Walk cycle").play();
			}
		}
	}

}
