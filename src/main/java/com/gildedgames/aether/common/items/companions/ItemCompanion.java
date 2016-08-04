package com.gildedgames.aether.common.items.companions;

import com.gildedgames.aether.api.player.IPlayerAetherCapability;
import com.gildedgames.aether.common.AetherCreativeTabs;
import com.gildedgames.aether.common.entities.companions.EntityCompanion;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.util.List;

public class ItemCompanion extends Item
{
	private final Class<? extends EntityCompanion> companionClass;

	protected final DecimalFormat timeFormat = new DecimalFormat("0.0");

	public ItemCompanion(Class<? extends EntityCompanion> companionClass)
	{
		this.companionClass = companionClass;

		this.setMaxStackSize(1);

		this.setCreativeTab(AetherCreativeTabs.tabCompanions);
	}

	public static void setRespawnTimer(ItemStack stack, World world, int timer)
	{
		NBTTagCompound compound = stack.getTagCompound();

		if (compound == null)
		{
			stack.setTagCompound(compound = new NBTTagCompound());
		}

		compound.setLong("respawnTimer", world.getTotalWorldTime() + timer);
	}

	public static long getTicksUntilRespawn(ItemStack stack, World world)
	{
		NBTTagCompound compound = stack.getTagCompound();

		if (compound == null || !compound.hasKey("respawnTimer"))
		{
			return 0;
		}

		return compound.getLong("respawnTimer") - world.getTotalWorldTime();
	}

	public EntityCompanion createCompanionEntity(IPlayerAetherCapability aePlayer)
	{
		EntityCompanion companion = null;

		try
		{
			companion = this.companionClass.getConstructor(World.class).newInstance(aePlayer.getPlayer().worldObj);
		}
		catch (InstantiationException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e)
		{
			e.printStackTrace();
		}

		return companion;
	}

	public Class<? extends EntityCompanion> getCompanionEntityClass()
	{
		return this.companionClass;
	}

	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced)
	{
		long respawn = ItemCompanion.getTicksUntilRespawn(stack, player.worldObj);

		if (respawn > 0)
		{
			tooltip.add(TextFormatting.RED + "Respawns in " + this.parseTicks(respawn));
		}

		super.addInformation(stack, player, tooltip, advanced);
	}

	protected String parseTicks(long ticks)
	{
		boolean useMinutes = ticks > (20 * 60);

		return this.timeFormat.format(ticks / (useMinutes ? 20f * 60 : 20f)) + " " + (useMinutes ? "minutes" : "seconds");
	}
}
