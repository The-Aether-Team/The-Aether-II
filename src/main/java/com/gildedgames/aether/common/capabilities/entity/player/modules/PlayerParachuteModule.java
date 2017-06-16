package com.gildedgames.aether.common.capabilities.entity.player.modules;

import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAetherModule;
import com.gildedgames.aether.common.entities.blocks.EntityParachute;
import com.gildedgames.aether.common.items.ItemsAether;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class PlayerParachuteModule extends PlayerAetherModule
{

	private boolean isParachuting;

	private EntityParachute.Type type = EntityParachute.Type.COLD;

	private int hitAmnt;

	private boolean prevAllowFlying;

	public PlayerParachuteModule(PlayerAether playerAether)
	{
		super(playerAether);
	}

	@Override
	public void onUpdate()
	{
		if (this.isParachuting)
		{
			int x = MathHelper.floor(this.getEntity().posX);
			int y = MathHelper.floor(this.getEntity().posY);
			int z = MathHelper.floor(this.getEntity().posZ);

			Vec3d vec3 = this.getEntity().getLookVec();

			if (this.getParachuteType() == EntityParachute.Type.BLUE)
			{
				if (this.getEntity().posY >= this.getEntity().world.getActualHeight() || this.isUnderABlock(y))
				{
					this.setParachuting(false, this.type);
				}
			}

			this.getEntity().fallDistance = 0F;

			if (!this.isParachuting)
			{
				this.hitAmnt = 0;
			}
			else if (this.getEntity().swingProgressInt >= 4 && this.isParachuting && vec3.yCoord >= 1 && !this.getEntity().onGround)
			{
				this.hitAmnt++;
			}

			if (this.getParachuteType() != EntityParachute.Type.BLUE && (!this.getEntity().world.isAirBlock(new BlockPos(x, y - 1, z))
					|| !this.isParachuting) || (this.isParachuting && this.hitAmnt >= 4))
			{
				this.setParachuting(false, this.type);
			}
		}
	}

	public void setParachuting(boolean isParachuting, EntityParachute.Type type)
	{
		this.isParachuting = isParachuting;
		this.type = type;

		if (isParachuting)
		{
			this.prevAllowFlying = this.getEntity().capabilities.allowFlying;
			this.getEntity().capabilities.allowFlying = true;
		}

		if (!isParachuting)
		{
			if (!this.getEntity().getEntityWorld().isRemote)
			{
				EntityItem block = new EntityItem(this.getEntity().getEntityWorld(), this.getEntity().posX, this.getEntity().posY, this.getEntity().posZ, new ItemStack(ItemsAether.cloud_parachute, 1, type.ordinal()));

				this.getEntity().getEntityWorld().spawnEntity(block);
			}

			this.getEntity().capabilities.allowFlying = this.prevAllowFlying;
		}
	}

	private boolean isUnderABlock(int y)
	{
		AxisAlignedBB boundingBox = this.getEntity().getEntityBoundingBox();

		BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();

		for (int x1 = (int) Math.floor(boundingBox.minX); x1 <= (int) Math.ceil(boundingBox.maxX); x1++)
		{
			for (int z1 = (int) Math.floor(boundingBox.minZ); z1 <= (int) Math.ceil(boundingBox.maxZ); z1++)
			{
				if (!this.getEntity().world.isAirBlock(pos.setPos(x1, y + 1, z1)) || !this.getEntity().world.isAirBlock(pos.setPos(x1, y + 2, z1)))
				{
					return true;
				}
			}
		}

		return false;
	}

	public EntityParachute.Type getParachuteType()
	{
		return this.type;
	}

	public boolean isParachuting()
	{
		return this.isParachuting;
	}

	@Override
	public void write(NBTTagCompound output)
	{
		NBTTagCompound root = new NBTTagCompound();
		output.setTag("Parachute", root);

		root.setBoolean("parachuting", this.isParachuting);
		root.setInteger("type", this.type.ordinal());
		root.setBoolean("prevAllowFlying", this.prevAllowFlying);
	}

	@Override
	public void read(NBTTagCompound input)
	{
		NBTTagCompound root = input.getCompoundTag("Parachute");

		this.isParachuting = input.getBoolean("parachuting");
		this.type = EntityParachute.Type.fromOrdinal(input.getInteger("type"));
		this.prevAllowFlying = input.getBoolean("prevAllowFlying");
	}

}
