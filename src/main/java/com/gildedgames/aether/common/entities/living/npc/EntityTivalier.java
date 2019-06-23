package com.gildedgames.aether.common.entities.living.npc;

import com.gildedgames.aether.api.entity.EntityNPC;
import com.gildedgames.aether.api.shop.IShopInstanceGroup;
import com.gildedgames.aether.common.entities.util.EntityBodyHelperNoRotation;
import com.gildedgames.orbis.lib.util.mc.NBTHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityBodyHelper;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityTivalier extends EntityNPC
{
	private BlockPos spawned;

	public EntityTivalier(final World worldIn)
	{
		super(worldIn);

		this.setSize(1.6F, 1.6F);
	}

	@Override
	public IShopInstanceGroup createShopInstanceGroup()
	{
		return null;
	}

	@Override
	protected EntityBodyHelper createBodyHelper()
	{
		return new EntityBodyHelperNoRotation(this);
	}

	@Override
	protected void initEntityAI()
	{
		this.tasks.addTask(2, new EntityAIWatchClosest(this, EntityPlayer.class, 10.0F));
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();

		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.4D);
		this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
	}

	@Override
	public void writeEntityToNBT(final NBTTagCompound compound)
	{
		super.writeEntityToNBT(compound);

		compound.setTag("spawned", NBTHelper.writeBlockPos(this.spawned));
	}

	@Override
	public void readEntityFromNBT(final NBTTagCompound compound)
	{
		super.readEntityFromNBT(compound);

		this.spawned = NBTHelper.readBlockPos(compound.getCompoundTag("spawned"));

		if (this.spawned != null)
		{
			this.setHomePosAndDistance(this.spawned, 3);
		}
	}

	@Override
	public void entityInit()
	{
		super.entityInit();
	}

	@Override
	protected void setRotation(final float yaw, final float pitch)
	{

	}

	@Override
	@SideOnly(Side.CLIENT)
	public void turn(final float yaw, final float pitch)
	{

	}

	@Override
	public void onUpdate()
	{
		this.posX = this.prevPosX;
		this.posZ = this.prevPosZ;

		if (this.spawned == null)
		{
			this.spawned = this.getPosition();
			this.setHomePosAndDistance(this.spawned, 3);
		}

		super.onUpdate();

		this.posX = this.prevPosX;
		this.posZ = this.prevPosZ;
	}

	@Override
	public boolean processInteract(final EntityPlayer player, final EnumHand hand)
	{
		return super.processInteract(player, hand);
	}

	@Override
	public boolean isOnLadder()
	{
		return false;
	}

	@Override
	protected void playStepSound(final BlockPos pos, final Block blockIn)
	{

	}

	@Override
	protected boolean canTriggerWalking()
	{
		return false;
	}

}
