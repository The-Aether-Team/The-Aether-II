package com.gildedgames.aether.common.util.structure;

import com.gildedgames.util.core.io.MessageHandlerServer;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityStructure;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class StructureInjectionPacket implements IMessage
{

	private byte action;

	private BlockPos pos, relative, size;

	private TileEntityStructure.Mode mode;

	private String structureName;

	private Mirror mirror;

	private Rotation rotation;

	private String metadata;

	private boolean ignoresEntities, showsAir, showsBoundingBox, showMessages;

	private float integrity;

	private long seed;

	public StructureInjectionPacket()
	{

	}

	public StructureInjectionPacket(byte action, BlockPos pos, BlockPos relative, BlockPos size, TileEntityStructure.Mode mode, String structureName, Mirror mirror, Rotation rotation, String metadata, boolean ignoreEntities, boolean showsAir, boolean showsBoundingBox, float integrity, long seed, boolean showMessages)
	{
		this.action = action;

		this.pos = pos;
		this.relative = relative;
		this.size = size;

		this.mode = mode;
		this.structureName = structureName;
		this.mirror = mirror;
		this.rotation = rotation;
		this.metadata = metadata;
		this.ignoresEntities = ignoreEntities;
		this.showsAir = showsAir;
		this.showsBoundingBox = showsBoundingBox;
		this.integrity = integrity;
		this.seed = seed;

		this.showMessages = showMessages;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());

		this.action = buf.readByte();

		this.mode = TileEntityStructure.Mode.valueOf(ByteBufUtils.readUTF8String(buf));
		this.structureName = ByteBufUtils.readUTF8String(buf);

		this.relative = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
		this.size = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());

		this.mirror = Mirror.valueOf(ByteBufUtils.readUTF8String(buf));
		this.rotation = Rotation.valueOf(ByteBufUtils.readUTF8String(buf));

		this.metadata = ByteBufUtils.readUTF8String(buf);

		this.ignoresEntities = buf.readBoolean();
		this.showsAir = buf.readBoolean();
		this.showsBoundingBox = buf.readBoolean();

		this.integrity = MathHelper.clamp_float(buf.readFloat(), 0.0F, 1.0F);

		this.seed = buf.readLong();

		this.showMessages = buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(this.pos.getX());
		buf.writeInt(this.pos.getY());
		buf.writeInt(this.pos.getZ());

		buf.writeByte(this.action);

		ByteBufUtils.writeUTF8String(buf, this.mode.toString());
		ByteBufUtils.writeUTF8String(buf, this.structureName);

		buf.writeInt(this.relative.getX());
		buf.writeInt(this.relative.getY());
		buf.writeInt(this.relative.getZ());

		buf.writeInt(this.size.getX());
		buf.writeInt(this.size.getY());
		buf.writeInt(this.size.getZ());

		ByteBufUtils.writeUTF8String(buf, this.mirror.toString());
		ByteBufUtils.writeUTF8String(buf, this.rotation.toString());

		ByteBufUtils.writeUTF8String(buf, this.metadata);

		buf.writeBoolean(this.ignoresEntities);
		buf.writeBoolean(this.showsAir);
		buf.writeBoolean(this.showsBoundingBox);

		buf.writeFloat(this.integrity);

		buf.writeLong(this.seed);

		buf.writeBoolean(this.showMessages);
	}

	public static class Handler extends MessageHandlerServer<StructureInjectionPacket,StructureInjectionPacket>
	{
		@Override
		public StructureInjectionPacket onMessage(StructureInjectionPacket message, EntityPlayer player)
		{
			if (!player.canUseCommandBlock())
			{
				return null;
			}

			IBlockState iblockstate1 = player.worldObj.getBlockState(message.pos);
			TileEntity tileentity1 = player.worldObj.getTileEntity(message.pos);

			if (tileentity1 instanceof TileEntityStructure)
			{
				TileEntityStructure tileentitystructure = (TileEntityStructure)tileentity1;

				tileentitystructure.setMode(message.mode);

				tileentitystructure.setName(message.structureName);

				tileentitystructure.setPosition(message.relative);
				tileentitystructure.setSize(message.size);

				tileentitystructure.setMirror(message.mirror);
				tileentitystructure.setRotation(message.rotation);

				tileentitystructure.setMetadata(message.metadata);

				tileentitystructure.setIgnoresEntities(message.ignoresEntities);
				tileentitystructure.setShowAir(message.showsAir);
				tileentitystructure.setShowBoundingBox(message.showsBoundingBox);

				tileentitystructure.setIntegrity(message.integrity);

				tileentitystructure.setSeed(message.seed);

				String s4 = tileentitystructure.getName();

				if (message.action == 2)
				{
					if (tileentitystructure.save())
					{
						if (message.showMessages) player.addChatComponentMessage(new TextComponentTranslation("structure_block.save_success", s4));
					}
					else
					{
						if (message.showMessages) player.addChatComponentMessage(new TextComponentTranslation("structure_block.save_failure", s4));
					}
				}
				else if (message.action == 3)
				{
					if (!tileentitystructure.isStructureLoadable())
					{
						if (message.showMessages) player.addChatComponentMessage(new TextComponentTranslation("structure_block.load_not_found", s4));
					}
					else if (tileentitystructure.load())
					{
						if (message.showMessages) player.addChatComponentMessage(new TextComponentTranslation("structure_block.load_success", s4));
					}
					else
					{
						if (message.showMessages) player.addChatComponentMessage(new TextComponentTranslation("structure_block.load_prepare", s4));
					}
				}
				else if (message.action == 4)
				{
					if (StructureInjectionLogic.detectSize(tileentitystructure))
					{
						if (message.showMessages) player.addChatComponentMessage(new TextComponentTranslation("structure_block.size_success", s4));
					}
					else
					{
						if (message.showMessages) player.addChatComponentMessage(new TextComponentTranslation("structure_block.size_failure"));
					}
				}

				tileentitystructure.markDirty();
				player.worldObj.notifyBlockUpdate(message.pos, iblockstate1, iblockstate1, 3);
			}

			return null;
		}

	}

}
