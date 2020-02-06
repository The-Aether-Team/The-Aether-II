package com.gildedgames.aether.common.capabilities.entity.player.modules;

import com.gildedgames.aether.api.player.IPlayerAetherModule;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAetherModule;
import com.gildedgames.aether.common.entities.blocks.EntityParachute;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class PlayerParachuteModule extends PlayerAetherModule implements IPlayerAetherModule.Serializable
{

	private boolean isParachuting;

	private boolean parachuteEquipped;

	public ItemStack parachuteItem;

	private EntityParachute.Type type = EntityParachute.Type.COLD;

	private int hitAmnt;

	private boolean prevAllowFlying;

	public PlayerParachuteModule(final PlayerAether playerAether)
	{
		super(playerAether);
	}

	@Override
	public void tickStart(TickEvent.PlayerTickEvent event)
	{
		if (this.isParachuting)
		{
			final int x = MathHelper.floor(this.getEntity().posX);
			final int y = MathHelper.floor(this.getEntity().posY);
			final int z = MathHelper.floor(this.getEntity().posZ);

			final Vec3d vec3 = this.getEntity().getLookVec();

			this.getEntity().fallDistance = 0F;

			if (!this.isParachuting)
			{
				this.hitAmnt = 0;
			}
			else if (this.getEntity().swingProgressInt >= 4 && this.isParachuting && vec3.y >= 1 && !this.getEntity().onGround)
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

	public void setParachuting(final boolean isParachuting, final EntityParachute.Type type)
	{
		this.isParachuting = isParachuting;
		this.type = type;
	}

	public boolean isUnderABlock(final int y)
	{
		final AxisAlignedBB boundingBox = this.getEntity().getEntityBoundingBox();

		final BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();

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

	public void parachuteEquipped(boolean equipped)
	{
		this.parachuteEquipped = equipped;
	}

	public boolean getParachuteEquipped()
	{
		return this.parachuteEquipped;
	}

	public void parachuteItem(ItemStack stack)
	{
		this.parachuteItem = stack;
	}

	public ItemStack getParachuteItem()
	{
		return this.parachuteItem;
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
	public void write(final NBTTagCompound output)
	{
		final NBTTagCompound root = new NBTTagCompound();
		output.setTag("Parachute", root);

		root.setBoolean("parachuting", this.isParachuting);
		root.setInteger("type", this.type.ordinal());
		root.setBoolean("prevAllowFlying", this.prevAllowFlying);
	}

	@Override
	public void read(final NBTTagCompound input)
	{
		final NBTTagCompound root = input.getCompoundTag("Parachute");

		this.isParachuting = input.getBoolean("parachuting");
		this.type = EntityParachute.Type.fromOrdinal(input.getInteger("type"));
		this.prevAllowFlying = input.getBoolean("prevAllowFlying");
	}

	@Override
	public ResourceLocation getIdentifier()
	{
		return AetherCore.getResource("parachute");
	}
}
