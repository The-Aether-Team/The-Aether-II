package com.gildedgames.aether.common.entities.tiles.builder;

import com.gildedgames.aether.api.AetherAPI;
import com.gildedgames.aether.api.structure.IBakedStructure;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.entities.tiles.util.TileEntitySynced;
import com.gildedgames.aether.common.structure.BakedStructure;
import com.gildedgames.aether.common.structure.StructureBlockData;
import com.gildedgames.aether.common.structure.StructurePalette;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.network.ByteBufUtils;

import javax.annotation.Nullable;
import java.text.DecimalFormat;

public class TileEntityStructureBuilder extends TileEntitySynced
{
	private Data data = new Data();

	public TileEntityStructureBuilder()
	{

	}

	public void saveStructure(EntityPlayer player)
	{
		try
		{
			player.sendMessage(new TextComponentString(TextFormatting.ITALIC + "Saving structure... "));

			long startTime = System.nanoTime();

			IBakedStructure structure = this.bakeStructure();

			AetherAPI.content().structures().saveStructure(player.world, structure);

			double duration = (System.nanoTime() - startTime) / 1000D / 1000D / 1000D;

			player.sendMessage(new TextComponentString(TextFormatting.YELLOW + "Structure saved! (Took " +
					new DecimalFormat("#.###").format(duration) + "s)"));
		}
		catch (Exception e)
		{
			player.sendMessage(new TextComponentString(TextFormatting.RED + "Failed to save structure. Check the console for more information."));

			AetherCore.LOGGER.error("Failed to save structure", e);
		}
	}

	private IBakedStructure bakeStructure()
	{
		StructurePalette palette = new StructurePalette();
		StructureBlockData blocks = new StructureBlockData(this.data.size.getX(), this.data.size.getY(), this.data.size.getZ());

		String name = this.data.name;

		BlockPos origin = this.getPos();

		BlockPos offset = this.data.offset;
		BlockPos size = this.data.size;

		BlockPos from = origin.add(offset).add(1, 1, 1);
		BlockPos to = from.add(size).add(-1, -1, -1);

		for (BlockPos.MutableBlockPos pos : BlockPos.getAllInBoxMutable(from, to))
		{
			IBlockState state = this.world.getBlockState(pos);

			blocks.setBlock(pos.subtract(from), palette.add(state));
		}

		return new BakedStructure(name, size, palette, blocks);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);

		NBTTagCompound tag = new NBTTagCompound();
		compound.setTag("Info", tag);

		this.data.write(tag);

		return compound;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);

		if (compound.hasKey("Info"))
		{
			this.data.read(compound.getCompoundTag("Info"));
		}
	}

	@Override
	@Nullable
	public ITextComponent getDisplayName()
	{
		return new TextComponentString("Structure: " + TextFormatting.YELLOW + (this.data.name.length() <= 0 ? "(unnamed)" : this.data.name));
	}

	public Data getStructureData()
	{
		return this.data;
	}

	public void setStructureData(Data data)
	{
		this.data = data;
	}

	public static class Data
	{
		public String name = "";

		public BlockPos offset = new BlockPos(0, 0, 0), size = new BlockPos(3, 3, 3);

		public void write(NBTTagCompound tag)
		{
			tag.setString("Name", this.name);

			tag.setTag("Origin", NBTUtil.createPosTag(this.offset));
			tag.setTag("Size", NBTUtil.createPosTag(this.size));
		}

		public void read(NBTTagCompound tag)
		{
			this.name = tag.getString("Name");

			this.offset = NBTUtil.getPosFromTag(tag.getCompoundTag("Origin"));
			this.size = NBTUtil.getPosFromTag(tag.getCompoundTag("Size"));
		}

		public void write(ByteBuf buf)
		{
			ByteBufUtils.writeUTF8String(buf, this.name);

			buf.writeInt(this.offset.getX());
			buf.writeInt(this.offset.getY());
			buf.writeInt(this.offset.getZ());

			buf.writeInt(this.size.getX());
			buf.writeInt(this.size.getY());
			buf.writeInt(this.size.getZ());
		}

		public void read(ByteBuf buf)
		{
			this.name = ByteBufUtils.readUTF8String(buf);

			this.offset = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
			this.size = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
		}
	}
}
