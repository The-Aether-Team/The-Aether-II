package com.gildedgames.aether.common.entities.projectiles;

import com.gildedgames.aether.common.items.ItemsAether;
import com.gildedgames.aether.common.items.weapons.ItemDartType;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.S2BPacketChangeGameState;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class EntityDart extends Entity implements IProjectile
{
	private int ticksInAir, ticksInGround;

	private boolean inGround;

	private int xTile, yTile, zTile;

	private Block inBlock;

	private int inMeta;

	private Entity shootingEntity;

	private double damage = 2.0D;

	private int dartShake;

	private double knockbackStrength;

	private int canPickup;

	public EntityDart(World world)
	{
		super(world);

		this.renderDistanceWeight = 10.0D;

		this.setSize(0.5F, 0.5F);
	}


	public EntityDart(World world, EntityLivingBase shooter, EntityLivingBase prey, float fromHeight, float damage)
	{
		super(world);

		this.renderDistanceWeight = 10.0D;
		this.shootingEntity = shooter;

		if (shooter instanceof EntityPlayer)
		{
			this.canPickup = 1;
		}

		this.posY = shooter.posY + (double)shooter.getEyeHeight() - 0.1D;
		double d0 = prey.posX - shooter.posX;
		double d1 = prey.getEntityBoundingBox().minY + (double)(prey.height / 3.0F) - this.posY;
		double d2 = prey.posZ - shooter.posZ;
		double d3 = (double)MathHelper.sqrt_double(d0 * d0 + d2 * d2);

		if (d3 >= 1.0E-7D)
		{
			float f = (float)(MathHelper.func_181159_b(d2, d0) * 180.0D / Math.PI) - 90.0F;
			float f1 = (float)(-(MathHelper.func_181159_b(d1, d3) * 180.0D / Math.PI));

			double d4 = d0 / d3;
			double d5 = d2 / d3;

			float f2 = (float)(d3 * 0.2D);

			this.setLocationAndAngles(shooter.posX + d4, this.posY, shooter.posZ + d5, f, f1);
			this.setThrowableHeading(d0, d1 + (double)f2, d2, fromHeight, damage);
		}
	}


	public EntityDart(World worldIn, EntityLivingBase shooter, float velocity)
	{
		this(worldIn);

		this.shootingEntity = shooter;

		if (shooter instanceof EntityPlayer)
		{
			this.canPickup = 1;
		}

		this.setLocationAndAngles(shooter.posX, shooter.posY + (double) shooter.getEyeHeight(), shooter.posZ, shooter.rotationYaw, shooter.rotationPitch);

		this.posX -= (double) (MathHelper.cos(this.rotationYaw / 180.0F * (float) Math.PI) * 0.16F);
		this.posY -= 0.10000000149011612D;
		this.posZ -= (double) (MathHelper.sin(this.rotationYaw / 180.0F * (float) Math.PI) * 0.16F);

		this.setPosition(this.posX, this.posY, this.posZ);

		this.motionX = (double) (-MathHelper.sin(this.rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float) Math.PI));
		this.motionZ = (double) (MathHelper.cos(this.rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float) Math.PI));
		this.motionY = (double) (-MathHelper.sin(this.rotationPitch / 180.0F * (float) Math.PI));

		this.setThrowableHeading(this.motionX, this.motionY, this.motionZ, velocity * 1.5F, 1.0F);
	}

	@Override
	protected void entityInit()
	{
		this.dataWatcher.addObject(16, (byte) 0);
		this.dataWatcher.addObject(17, (byte) 0);
	}

	public void setThrowableHeading(double x, double y, double z, float velocity, float inaccuracy)
	{
		float f2 = MathHelper.sqrt_double(x * x + y * y + z * z);
		x /= (double) f2;
		y /= (double) f2;
		z /= (double) f2;
		x += this.rand.nextGaussian() * (double) (this.rand.nextBoolean() ? -1 : 1) * 0.007499999832361937D * (double) inaccuracy;
		y += this.rand.nextGaussian() * (double) (this.rand.nextBoolean() ? -1 : 1) * 0.007499999832361937D * (double) inaccuracy;
		z += this.rand.nextGaussian() * (double) (this.rand.nextBoolean() ? -1 : 1) * 0.007499999832361937D * (double) inaccuracy;
		x *= (double) velocity;
		y *= (double) velocity;
		z *= (double) velocity;
		this.motionX = x;
		this.motionY = y;
		this.motionZ = z;
		float sqrt = MathHelper.sqrt_double(x * x + z * z);
		this.prevRotationYaw = this.rotationYaw = (float) (Math.atan2(x, z) * 180.0D / Math.PI);
		this.prevRotationPitch = this.rotationPitch = (float) (Math.atan2(y, (double) sqrt) * 180.0D / Math.PI);
		this.ticksInGround = 0;
	}

	@SideOnly(Side.CLIENT)
	public void setPositionAndRotation2(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean p_180426_10_)
	{
		this.setPosition(x, y, z);
		this.setRotation(yaw, pitch);
	}

	@SideOnly(Side.CLIENT)
	public void setVelocity(double x, double y, double z)
	{
		this.motionX = x;
		this.motionY = y;
		this.motionZ = z;

		if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F)
		{
			float f = MathHelper.sqrt_double(x * x + z * z);
			this.prevRotationYaw = this.rotationYaw = (float) (Math.atan2(x, z) * 180.0D / Math.PI);
			this.prevRotationPitch = this.rotationPitch = (float) (Math.atan2(y, (double) f) * 180.0D / Math.PI);
			this.prevRotationPitch = this.rotationPitch;
			this.prevRotationYaw = this.rotationYaw;
			this.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
			this.ticksInGround = 0;
		}
	}

	public void onUpdate()
	{
		super.onUpdate();

		ItemDartType dartType = this.getDartType();

		if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F)
		{
			float sqrt = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);

			this.prevRotationYaw = this.rotationYaw = (float) (Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);
			this.prevRotationPitch = this.rotationPitch = (float) (Math.atan2(this.motionY, (double) sqrt) * 180.0D / Math.PI);
		}

		BlockPos pos = new BlockPos(this.xTile, this.yTile, this.zTile);

		IBlockState state = this.worldObj.getBlockState(pos);
		Block block = state.getBlock();

		if (block.getMaterial() != Material.air)
		{
			block.setBlockBoundsBasedOnState(this.worldObj, pos);

			AxisAlignedBB boundingBox = block.getCollisionBoundingBox(this.worldObj, pos, state);

			if (boundingBox != null && boundingBox.isVecInside(new Vec3(this.posX, this.posY, this.posZ)))
			{
				this.inGround = true;
			}
		}

		if (this.dartShake > 0)
		{
			--this.dartShake;
		}

		if (this.inGround)
		{
			int meta = block.getMetaFromState(state);

			if (block == this.inBlock && meta == this.inMeta)
			{
				++this.ticksInGround;

				if (this.ticksInGround >= 1200)
				{
					this.setDead();
				}
			}
			else
			{
				this.inGround = false;

				this.motionX *= (double) (this.rand.nextFloat() * 0.2F);
				this.motionY *= (double) (this.rand.nextFloat() * 0.2F);
				this.motionZ *= (double) (this.rand.nextFloat() * 0.2F);

				this.ticksInGround = 0;
				this.ticksInAir = 0;
			}
		}
		else
		{
			this.ticksInAir++;

			Vec3 posVec = new Vec3(this.posX, this.posY, this.posZ);
			Vec3 nextPosVec = new Vec3(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
			MovingObjectPosition raytrace = this.worldObj.rayTraceBlocks(posVec, nextPosVec, false, true, false);

			posVec = new Vec3(this.posX, this.posY, this.posZ);
			nextPosVec = new Vec3(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);

			if (raytrace != null)
			{
				nextPosVec = new Vec3(raytrace.hitVec.xCoord, raytrace.hitVec.yCoord, raytrace.hitVec.zCoord);
			}

			Entity closestEntity = null;
			List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0D, 1.0D, 1.0D));

			double closestDistance = 0.0D;
			float boundaries;

			for (Object obj : list)
			{
				Entity entity = (Entity) obj;

				if (entity.canBeCollidedWith() && (entity != this.shootingEntity || this.ticksInAir >= 5))
				{
					boundaries = 0.3F;

					AxisAlignedBB boundingBox = entity.getEntityBoundingBox().expand((double) boundaries, (double) boundaries, (double) boundaries);
					MovingObjectPosition intercept = boundingBox.calculateIntercept(posVec, nextPosVec);

					if (intercept != null)
					{
						double distance = posVec.distanceTo(intercept.hitVec);

						if (distance < closestDistance || closestDistance == 0.0D)
						{
							closestEntity = entity;
							closestDistance = distance;
						}
					}
				}
			}

			if (closestEntity != null)
			{
				raytrace = new MovingObjectPosition(closestEntity);
			}

			if (raytrace != null && raytrace.entityHit != null && raytrace.entityHit instanceof EntityPlayer)
			{
				EntityPlayer entityplayer = (EntityPlayer) raytrace.entityHit;

				if (entityplayer.capabilities.disableDamage || this.shootingEntity instanceof EntityPlayer && !((EntityPlayer) this.shootingEntity).canAttackPlayer(entityplayer))
				{
					raytrace = null;
				}
			}

			float distanceSqrt;
			float speed;
			float f4;

			if (raytrace != null)
			{
				if (raytrace.entityHit != null)
				{
					distanceSqrt = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
					int damage = MathHelper.ceiling_double_int((double) distanceSqrt * this.damage);

					if (this.isCritical())
					{
						damage += this.rand.nextInt(damage / 2 + 2);
					}

					DamageSource damageSource = new EntityDamageSourceIndirect("arrow", this, this.shootingEntity != null ? this.shootingEntity : this).setProjectile();

					if (this.isBurning() && !(raytrace.entityHit instanceof EntityEnderman))
					{
						raytrace.entityHit.setFire(5);
					}

					if (raytrace.entityHit.attackEntityFrom(damageSource, (float) damage))
					{
						if (this.getDartType() == ItemDartType.PHOENIX)
						{
							raytrace.entityHit.setFire(3);
						}

						if (raytrace.entityHit instanceof EntityLivingBase)
						{
							EntityLivingBase entity = (EntityLivingBase) raytrace.entityHit;

							if (!this.worldObj.isRemote)
							{
								entity.setArrowCountInEntity(entity.getArrowCountInEntity() + 1);
							}

							if (this.knockbackStrength > 0)
							{
								f4 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);

								if (f4 > 0.0F)
								{
									raytrace.entityHit.addVelocity(this.motionX * this.knockbackStrength * 0.6D / (double) f4, 0.1D, this.motionZ * this.knockbackStrength * 0.6D / (double) f4);
								}
							}

							if (this.shootingEntity instanceof EntityLivingBase)
							{
								EnchantmentHelper.applyThornEnchantments((EntityLivingBase) this.shootingEntity, entity);
								EnchantmentHelper.applyArthropodEnchantments(entity, this.shootingEntity);
							}

							if (this.shootingEntity != null && raytrace.entityHit != this.shootingEntity && raytrace.entityHit instanceof EntityPlayer && this.shootingEntity instanceof EntityPlayerMP)
							{
								((EntityPlayerMP) this.shootingEntity).playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(6, 0.0F));
							}
						}

						this.playSound("random.bowhit", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));

						if (!(raytrace.entityHit instanceof EntityEnderman))
						{
							this.setDead();
						}
					}
					else
					{
						this.motionX *= -0.1D;
						this.motionY *= -0.1D;
						this.motionZ *= -0.1D;

						this.rotationYaw += 180.0F;
						this.prevRotationYaw += 180.0F;

						this.ticksInAir = 0;
					}
				}
				else
				{
					BlockPos raytracePos = raytrace.getBlockPos();

					this.xTile = raytracePos.getX();
					this.yTile = raytracePos.getY();
					this.zTile = raytracePos.getZ();

					state = this.worldObj.getBlockState(raytracePos);

					this.inBlock = state.getBlock();
					this.inMeta = this.inBlock.getMetaFromState(state);

					this.motionX = (double) ((float) (raytrace.hitVec.xCoord - this.posX));
					this.motionY = (double) ((float) (raytrace.hitVec.yCoord - this.posY));
					this.motionZ = (double) ((float) (raytrace.hitVec.zCoord - this.posZ));

					speed = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);

					this.posX -= this.motionX / (double) speed * 0.05D;
					this.posY -= this.motionY / (double) speed * 0.05D;
					this.posZ -= this.motionZ / (double) speed * 0.05D;

					this.playSound("random.bowhit", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));

					this.inGround = true;
					this.dartShake = 7;

					this.setIsCritical(false);

					if (this.inBlock.getMaterial() != Material.air)
					{
						this.inBlock.onEntityCollidedWithBlock(this.worldObj, raytracePos, state, this);
					}
				}
			}

			if (this.isCritical())
			{
				for (int i = 0; i < 4; ++i)
				{
					this.worldObj.spawnParticle(EnumParticleTypes.CRIT, this.posX + this.motionX * (double) i / 4.0D, this.posY + this.motionY * (double) i / 4.0D, this.posZ + this.motionZ * (double) i / 4.0D, -this.motionX, -this.motionY + 0.2D, -this.motionZ);
				}
			}

			this.posX += this.motionX;
			this.posY += this.motionY;
			this.posZ += this.motionZ;

			distanceSqrt = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);

			this.rotationYaw = (float) (Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);

			this.rotationPitch = (float) (Math.atan2(this.motionY, (double) distanceSqrt) * 180.0D / Math.PI);

			while (this.rotationPitch - this.prevRotationPitch < -180.0F)
			{
				this.prevRotationPitch -= 360.0F;
			}

			while (this.rotationPitch - this.prevRotationPitch >= 180.0F)
			{
				this.prevRotationPitch += 360.0F;
			}

			while (this.rotationYaw - this.prevRotationYaw < -180.0F)
			{
				this.prevRotationYaw -= 360.0F;
			}

			while (this.rotationYaw - this.prevRotationYaw >= 180.0F)
			{
				this.prevRotationYaw += 360.0F;
			}

			this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
			this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;

			speed = 0.99F;
			boundaries = 0.05F;

			if (this.isInWater())
			{
				for (int count = 0; count < 4; ++count)
				{
					f4 = 0.25F;
					this.worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX - this.motionX * (double) f4, this.posY - this.motionY * (double) f4, this.posZ - this.motionZ * (double) f4, this.motionX, this.motionY, this.motionZ);
				}

				speed = 0.6F;
			}

			if (dartType == ItemDartType.PHOENIX)
			{
				f4 = 0.25F;
				this.worldObj.spawnParticle(EnumParticleTypes.FLAME, this.posX - this.motionX * (double) f4, this.posY - this.motionY * (double) f4, this.posZ - this.motionZ * (double) f4, 0, 0, 0);
			}

			if (this.isWet())
			{
				this.extinguish();
			}

			this.motionX *= (double) speed;
			this.motionY *= (double) speed;
			this.motionZ *= (double) speed;

			this.motionY -= (double) boundaries;

			this.setPosition(this.posX, this.posY, this.posZ);

			this.doBlockCollisions();
		}
	}

	@Override
	public void onCollideWithPlayer(EntityPlayer player)
	{
		if (!this.worldObj.isRemote && this.inGround && this.dartShake <= 0)
		{
			boolean canPickup = this.canPickup == 1 || this.canPickup == 2 && player.capabilities.isCreativeMode;

			if (this.canPickup == 1 && !player.inventory.addItemStackToInventory(new ItemStack(ItemsAether.dart, 1, this.getDartType().getAmmoItem().ordinal())))
			{
				canPickup = false;
			}

			if (canPickup)
			{
				this.playSound("random.pop", 0.2F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);

				player.onItemPickup(this, 1);

				this.setDead();
			}
		}
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound tagCompund)
	{
		this.xTile = tagCompund.getShort("xTile");
		this.yTile = tagCompund.getShort("yTile");
		this.zTile = tagCompund.getShort("zTile");
		this.ticksInGround = tagCompund.getShort("life");

		if (tagCompund.hasKey("inBlock", 8))
		{
			this.inBlock = Block.getBlockFromName(tagCompund.getString("inBlock"));
		}
		else
		{
			this.inBlock = Block.getBlockById(tagCompund.getByte("inBlock") & 255);
		}

		this.inMeta = tagCompund.getByte("inMeta") & 255;
		this.dartShake = tagCompund.getByte("shake") & 255;
		this.inGround = tagCompund.getBoolean("inGround");

		if (tagCompund.hasKey("damage", 99))
		{
			this.damage = tagCompund.getDouble("damage");
		}

		if (tagCompund.hasKey("pickup", 99))
		{
			this.canPickup = tagCompund.getByte("pickup");
		}
		else if (tagCompund.hasKey("player", 99))
		{
			this.canPickup = tagCompund.getByte("player");
		}
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound tagCompound)
	{
		ResourceLocation blockName = Block.blockRegistry.getNameForObject(this.inBlock);

		tagCompound.setShort("xTile", (short) this.xTile);
		tagCompound.setShort("yTile", (short) this.yTile);
		tagCompound.setShort("zTile", (short) this.zTile);
		tagCompound.setShort("life", (short) this.ticksInGround);
		tagCompound.setString("inTile", blockName == null ? "" : blockName.toString());
		tagCompound.setByte("inData", (byte) this.inMeta);
		tagCompound.setByte("shake", (byte) this.dartShake);
		tagCompound.setBoolean("inGround", this.inGround);
		tagCompound.setByte("pickup", (byte) this.canPickup);
		tagCompound.setDouble("damage", this.damage);
	}

	public int getDartShake()
	{
		return this.dartShake;
	}

	public void setKnockbackStrength(double strength)
	{
		this.knockbackStrength = strength;
	}

	public boolean isCritical()
	{
		return this.dataWatcher.getWatchableObjectByte(16) == 1;
	}

	public void setIsCritical(boolean isCritical)
	{
		this.dataWatcher.updateObject(16, (byte) (isCritical ? 1 : 0));
	}

	public ItemDartType getDartType()
	{
		return ItemDartType.fromOrdinal(this.dataWatcher.getWatchableObjectByte(17));
	}

	public void setDartType(ItemDartType type)
	{
		this.dataWatcher.updateObject(17, (byte) type.ordinal());
	}

	public void setDamage(double damage)
	{
		this.damage = damage;
	}

	public double getDamage()
	{
		return this.damage;
	}

	public void setCanPickup(int canPickup)
	{
		this.canPickup = canPickup;
	}
}
