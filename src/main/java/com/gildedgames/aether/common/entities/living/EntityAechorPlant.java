package com.gildedgames.aether.common.entities.living;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.entities.ai.AechorPlantAI;
import com.gildedgames.aether.common.items.ItemsAether;
import com.gildedgames.aether.common.util.PlayerUtil;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityAechorPlant extends EntityMob
{
	private static final int canSeePreyID = 16, plantSizeID = 17;

	@SideOnly(Side.CLIENT)
	public float sinage;

	private int poisonLeft;

	public EntityAechorPlant(World world)
	{
		super(world);

		this.tasks.addTask(0, new AechorPlantAI(this));
		this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true));

		this.setSize(0.75F + (this.getPlantSize() * 0.125F), 0.5F + (this.getPlantSize() * 0.075F));

		this.setPoisonLeft(2);

		if (world.isRemote)
		{
			this.sinage = this.rand.nextFloat() * 6F;
		}
	}

	protected void entityInit()
	{
		super.entityInit();

		this.dataWatcher.addObject(EntityAechorPlant.canSeePreyID, (byte) 0);
		this.dataWatcher.addObject(EntityAechorPlant.plantSizeID, 0);

		this.setPlantSize(this.rand.nextInt(3));
	}

	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();

		this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(3.5F);
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20);
	}

	@Override
	public void onUpdate()
	{
		super.onUpdate();

		this.motionX = 0.0D;
		this.motionZ = 0.0D;

		if (this.worldObj.isRemote)
		{
			this.tickAnimation();

			return;
		}

		boolean isTargeting = this.getAttackTarget() != null;

		if (this.canSeePrey() != isTargeting)
		{
			this.setCanSeePrey(isTargeting);
		}

		BlockPos beneathPos = new BlockPos(this.posX, this.getEntityBoundingBox().minY - 0.1D, this.posZ);
		Block blockBeneath = this.worldObj.getBlockState(beneathPos).getBlock();

		if (blockBeneath != BlocksAether.aether_grass && blockBeneath != BlocksAether.aether_dirt)
		{
			this.setHealth(0);
		}
	}

	@Override
	public void knockBack(Entity entity, float distance, double x, double y) { }

	@Override
	public void moveEntity(double x, double y, double z) { }

	@Override
	protected boolean interact(EntityPlayer player)
	{
		if (this.getPoisonLeft() <= 0)
		{
			return false;
		}

		ItemStack stack = player.getHeldItem();

		if (stack != null && stack.getItem() == ItemsAether.skyroot_bucket)
		{
			PlayerUtil.fillBucketInHand(player, new ItemStack(ItemsAether.skyroot_poison_bucket));

			this.setPoisonLeft(this.getPoisonLeft() - 1);
		}

		return false;
	}

	@SideOnly(Side.CLIENT)
	private void tickAnimation()
	{
		if (this.hurtTime > 0)
		{
			this.sinage += 0.5F;
		}
		else
		{
			this.sinage += this.canSeePrey() ? 0.3F : 0.1F;
		}

		float pie2 = 3.141593F * 2F;

		if (this.sinage > pie2)
		{
			this.sinage -= pie2;
		}
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound tagCompound)
	{
		super.writeEntityToNBT(tagCompound);

		tagCompound.setInteger("plantSize", this.getPlantSize());
		tagCompound.setInteger("poisonLeft", this.getPoisonLeft());
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound tagCompound)
	{
		super.readEntityFromNBT(tagCompound);

		this.setPlantSize(tagCompound.getInteger("plantSize"));
		this.setPoisonLeft(tagCompound.getInteger("poisonLeft"));
	}

	public boolean canSeePrey()
	{
		return this.dataWatcher.getWatchableObjectByte(EntityAechorPlant.canSeePreyID) == 1;
	}

	public void setCanSeePrey(boolean canSee)
	{
		this.dataWatcher.updateObject(EntityAechorPlant.canSeePreyID, (byte) (canSee ? 1 : 0));
	}

	public int getPlantSize()
	{
		return this.dataWatcher.getWatchableObjectInt(EntityAechorPlant.plantSizeID);
	}

	public void setPlantSize(int size)
	{
		this.dataWatcher.updateObject(EntityAechorPlant.plantSizeID, size);
	}

	public int getPoisonLeft()
	{
		return this.poisonLeft;
	}

	public void setPoisonLeft(int poisonLeft)
	{
		this.poisonLeft = poisonLeft;
	}
}
