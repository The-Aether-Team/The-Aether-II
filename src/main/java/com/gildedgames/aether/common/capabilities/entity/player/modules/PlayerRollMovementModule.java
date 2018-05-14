package com.gildedgames.aether.common.capabilities.entity.player.modules;

import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAetherModule;
import com.gildedgames.aether.common.network.packets.PacketSpecialMovement;
import com.gildedgames.aether.common.util.helpers.AetherHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class PlayerRollMovementModule extends PlayerAetherModule
{
	private static final int MAX_ROLLING_TICKS = 10, ROLL_COOLDOWN_TICKS = 10;

	private boolean isRolling;

	private int ticksRolling, rollCooldown;

	private float startRotationYaw, rollingRotationYaw;

	private float prevStepHeight, prevEyeHeight, prevHeight;

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

	public int getTicksRollingMax()
	{
		return this.MAX_ROLLING_TICKS;
	}

	private void setEntityHeight(float height)
	{
		EntityPlayer p = this.getEntity();

		float w = p.width / 2f;

		p.setEntityBoundingBox(new AxisAlignedBB(p.posX - w, p.posY, p.posZ - w, p.posX + w, p.posY + height, p.posZ + w));
		p.height = height;
		p.eyeHeight = (height / this.prevHeight) * this.prevEyeHeight;
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
		if (!AetherHelper.isEnabled(this.getWorld()) || rollCooldown > 0)
		{
			return;
		}

		this.startRotationYaw = this.getEntity().rotationYaw;

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

		this.prevStepHeight = this.getEntity().stepHeight;
		this.prevHeight = this.getEntity().height;
		this.prevEyeHeight = this.getEntity().getEyeHeight();
		this.isRolling = true;
		this.ticksRolling = 0;
		this.rollCooldown = this.ROLL_COOLDOWN_TICKS;
	}

	@Override
	public void tickStart(TickEvent.PlayerTickEvent event)
	{

	}

	@Override
	public void tickEnd(TickEvent.PlayerTickEvent event)
	{
		if (this.isRolling)
		{
			if (this.ticksRolling <= this.MAX_ROLLING_TICKS)
			{
				float newHeight = MathHelper.clamp(this.prevHeight / 4f + this.prevHeight * (Math.abs(this.ticksRolling / (float) this.MAX_ROLLING_TICKS - 0.5f)), this.prevHeight / 2f, this.prevHeight);

				setEntityHeight(newHeight);
			}
			else
			{
				this.setEntityHeight(prevHeight);
				this.getEntity().eyeHeight = this.prevEyeHeight;
			}
		}
	}

	@Override
	public void onUpdate()
	{
		if (this.isRolling)
		{
			if (this.ticksRolling <= this.MAX_ROLLING_TICKS)
			{
				this.getEntity().stepHeight = 1.0F;

				if (this.getEntity().onGround)
				{
					Vec3d vec = this.getVectorForRotation(0, (this.getEntity().rotationYaw - this.startRotationYaw) + this.rollingRotationYaw);

					double speed = Math.max(0.0, Math.pow(1.25, this.ticksRolling / 7.0) - 1.0) + 0.25;

					this.getEntity().motionX = (vec.x) * speed;
					this.getEntity().motionZ = (vec.z) * speed;
					this.getEntity().velocityChanged = true;
				}

				this.ticksRolling++;

				if (!this.getWorld().isRemote)
				{
					this.getEntity().setEntityInvulnerable(true);
				}
			}
			else
			{
				this.isRolling = false;
				this.ticksRolling = 0;

				if (!this.getWorld().isRemote)
				{
					this.getEntity().setEntityInvulnerable(false);
				}

				this.getEntity().stepHeight = this.prevStepHeight;
			}
		}
		else if (rollCooldown > 0)
		{
			rollCooldown--;
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
