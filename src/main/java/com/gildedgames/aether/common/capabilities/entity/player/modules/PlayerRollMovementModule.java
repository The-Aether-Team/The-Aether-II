package com.gildedgames.aether.common.capabilities.entity.player.modules;

import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAetherModule;
import com.gildedgames.aether.common.network.packets.PacketSpecialMovement;
import com.gildedgames.aether.common.util.helpers.AetherHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class PlayerRollMovementModule extends PlayerAetherModule
{
	private static final int MAX_ROLLING_TICKS = 12, ROLL_COOLDOWN_TICKS = 10;

	private boolean isRolling;

	private int ticksRolling, rollCooldown;

	private float startRotationYaw, rollingRotationYaw;

	private float prevStepHeight, prevEyeHeight, prevHeight;

	public PlayerRollMovementModule(final PlayerAether playerAether)
	{
		super(playerAether);
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
		return MAX_ROLLING_TICKS;
	}

	private void setEntityHeight(float height)
	{
		EntityPlayer p = this.getEntity();

		float w = p.width / 2f;

		p.setEntityBoundingBox(new AxisAlignedBB(p.posX - w, p.posY, p.posZ - w, p.posX + w, p.posY + height, p.posZ + w));
		p.height = height;
		p.eyeHeight = (height / this.prevHeight) * this.prevEyeHeight;
	}

	protected final Vec3d getVectorForRotation(float yaw)
	{
		float f = MathHelper.cos(-yaw * 0.017453292F - (float) Math.PI);
		float f1 = MathHelper.sin(-yaw * 0.017453292F - (float) Math.PI);
		return new Vec3d((double) (f1 * -1F), 0, (double) (f * -1F));
	}

	public boolean canRoll()
	{
		return AetherHelper.isEnabled(this.getWorld()) && this.rollCooldown <= 0 && !this.getEntity().isSneaking() && this.getEntity().onGround;
	}

	public float getDamageReduction(float inAmount)
	{
		return inAmount * (Math.abs(this.ticksRolling / (float) (MAX_ROLLING_TICKS) - 0.5f) * 2F);
	}

	public void startRolling(PacketSpecialMovement.Action action)
	{
		this.startRotationYaw = this.getEntity().rotationYaw;

		final float angle;

		if (action == PacketSpecialMovement.Action.ROLL_FORWARD)
		{
			angle = 0F;
		}
		else if (action == PacketSpecialMovement.Action.ROLL_BACK)
		{
			angle = 180F;
		}
		else if (action == PacketSpecialMovement.Action.ROLL_LEFT)
		{
			angle = -90F;
		}
		else if (action == PacketSpecialMovement.Action.ROLL_RIGHT)
		{
			angle = 90F;
		}
		else
		{
			return;
		}

		this.rollingRotationYaw = this.startRotationYaw + angle;

		if (!this.canRoll())
		{
			return;
		}

		this.prevStepHeight = this.getEntity().stepHeight;
		this.prevHeight = this.getEntity().height;
		this.prevEyeHeight = this.getEntity().getEyeHeight();
		this.isRolling = true;
		this.ticksRolling = 0;
		this.rollCooldown = ROLL_COOLDOWN_TICKS;
	}

	@Override
	public void tickStart(TickEvent.PlayerTickEvent event)
	{
		if (this.isRolling)
		{
			if (this.ticksRolling <= MAX_ROLLING_TICKS)
			{
				this.getEntity().stepHeight = 1.0F;

				if (this.getEntity().onGround)
				{
					Vec3d vec = this.getVectorForRotation(this.getEntity().rotationYaw - this.startRotationYaw + this.rollingRotationYaw);

					double speed = Math.max(0.0D, Math.pow(1.25D, this.ticksRolling / 8f) - 1) + 0.25D;

					if (this.ticksRolling > MAX_ROLLING_TICKS * .7F && !this.getEntity().isSprinting())
					{
						if (this.ticksRolling == MAX_ROLLING_TICKS)
						{
							speed = 0.05f;
						}
						else
						{
							speed /= (this.ticksRolling / (float) MAX_ROLLING_TICKS) * 5F;
						}
					}

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

				this.getEntity().stepHeight = this.prevStepHeight;
			}
		}
		else if (this.rollCooldown > 0)
		{
			this.rollCooldown--;
		}
	}

	@Override
	public void tickEnd(TickEvent.PlayerTickEvent event)
	{
		if (this.isRolling)
		{
			if (this.ticksRolling <= MAX_ROLLING_TICKS)
			{
				float newHeight = MathHelper
						.clamp(this.prevHeight / 4F + this.prevHeight * (Math.abs(this.ticksRolling / (float) (MAX_ROLLING_TICKS) - 0.5F)),
								this.prevHeight / 2F, this.prevHeight);

				this.setEntityHeight(newHeight);
			}
			else
			{
				this.setEntityHeight(this.prevHeight);
				this.getEntity().eyeHeight = this.prevEyeHeight;
			}
		}
	}

}
