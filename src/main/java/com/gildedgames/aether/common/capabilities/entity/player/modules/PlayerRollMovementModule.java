package com.gildedgames.aether.common.capabilities.entity.player.modules;

import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAetherModule;
import com.gildedgames.aether.common.network.packets.PacketSpecialMovement;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class PlayerRollMovementModule extends PlayerAetherModule
{
	private static float MAX_ROLLING_TICKS = 30;

	private boolean isRolling;

	private int ticksRolling;

	private float startRotationYaw, rollingRotationYaw;

	public PlayerRollMovementModule(final PlayerAether playerAether)
	{
		super(playerAether);
	}

	public static float getLookAtYaw(Vec3d vec)
	{
		double dx = vec.x;
		double dz = vec.z;

		double yaw = 0;

		if (dx != 0)
		{
			if (dx < 0)
			{
				yaw = 1.5 * Math.PI;
			}
			else
			{
				yaw = 0.5 * Math.PI;
			}
			yaw -= Math.atan(dz / dx);
		}
		else if (dz < 0)
		{
			yaw = Math.PI;
		}

		return (float) (-yaw * 180 / Math.PI - 90);
	}

	public boolean isRolling()
	{
		return this.isRolling;
	}

	public int getTicksRolling()
	{
		return this.ticksRolling;
	}

	protected final Vec3d getVectorForRotation(float pitch, float yaw)
	{
		float f = MathHelper.cos(-yaw * 0.017453292F - (float) Math.PI);
		float f1 = MathHelper.sin(-yaw * 0.017453292F - (float) Math.PI);
		float f2 = -MathHelper.cos(-pitch * 0.017453292F);
		float f3 = MathHelper.sin(-pitch * 0.017453292F);
		return new Vec3d((double) (f1 * f2), (double) f3, (double) (f * f2));
	}

	public void startRolling(PacketSpecialMovement.Action action)
	{
		//double xDif = this.getEntity().lastTickPosX - this.getEntity().posX;
		//double zDif = this.getEntity().posZ - this.getEntity().lastTickPosZ;

		this.startRotationYaw = this.getEntity().rotationYaw;

		//this.startVec = this.getVectorForRotation(this.getEntity().rotationPitch, (float) (MathHelper.atan2(xDif, zDif) * (180D / Math.PI)));

		if (action == PacketSpecialMovement.Action.ROLL_FORWARD)
		{
			this.rollingRotationYaw = this.startRotationYaw;
		}
		else if (action == PacketSpecialMovement.Action.ROLL_BACK)
		{
			this.rollingRotationYaw = this.getEntity().rotationYaw + 180.0F;
		}
		else if (action == PacketSpecialMovement.Action.ROLL_LEFT)
		{
			this.rollingRotationYaw = this.getEntity().rotationYaw - 90.0F;
		}
		else if (action == PacketSpecialMovement.Action.ROLL_RIGHT)
		{
			this.rollingRotationYaw = this.getEntity().rotationYaw + 90.0F;
		}

		this.isRolling = true;
		this.ticksRolling = 0;
	}

	@Override
	public void tickStart(TickEvent.PlayerTickEvent event)
	{

	}

	@Override
	public void tickEnd(TickEvent.PlayerTickEvent event)
	{

	}

	@Override
	public void onUpdate()
	{
		if (this.isRolling)
		{
			this.getEntity().stepHeight = 1.0F;

			if (this.ticksRolling <= 10)
			{
				if (this.getEntity().onGround)
				{
					Vec3d vec = this.getVectorForRotation(this.getEntity().rotationPitch, (this.getEntity().rotationYaw - this.startRotationYaw) + this.rollingRotationYaw);

					double speed = Math.max(0.0, Math.pow(1.25, this.ticksRolling / 7.0) - 1.0) + 0.25;

					this.getEntity().motionX = (vec.x) * speed;
					this.getEntity().motionZ = (vec.z) * speed;

					this.getEntity().velocityChanged = true;
				}

				this.ticksRolling++;
			}
			else
			{
				this.isRolling = false;
				this.ticksRolling = 0;
			}
		}
		else
		{
			this.getEntity().stepHeight = 0.5F;
		}
	}

	@Override
	public void write(final NBTTagCompound compound)
	{

	}

	@Override
	public void read(final NBTTagCompound compound)
	{

	}

	@Override
	public void onDeath(final LivingDeathEvent event)
	{

	}

}
