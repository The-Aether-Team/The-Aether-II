package com.gildedgames.aether.common.lighting.asm;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.*;

import java.util.Iterator;

import static org.objectweb.asm.Opcodes.*;

public class TLTransformer implements IClassTransformer
{

	private static final String MCP_NETCLIENTHANDLER = "net.minecraft.client.network.NetHandlerPlayClient";

	private static final String MCP_CHUNKPROVIDERCLIENT = "net.minecraft.client.multiplayer.ChunkProviderClient";

	private static final String MCP_CHUNK = "net.minecraft.world.chunk.Chunk";

	private static final String MCP_WORLDCLIENT = "net.minecraft.client.multiplayer.WorldClient";

	private static final String THREADED_WORLD = "com.gildedgames.aether.common.lighting.world.ThreadedWorld";

	private static final String THREADED_CHUNK = "com.gildedgames.aether.common.lighting.world.ThreadedChunk";

	@Override
	public byte[] transform(String name, String transformedName, byte[] bytes)
	{
		if (transformedName.equals(MCP_NETCLIENTHANDLER))
		{
			ClassReader classReader = new ClassReader(bytes);
			ClassNode classNode = new ClassNode();
			classReader.accept(classNode, 0);

			this.replaceInstance(classNode, MCP_WORLDCLIENT, THREADED_WORLD);

			ClassWriter cw = new ClassWriter(0);
			classNode.accept(cw);

			return cw.toByteArray();
		}

		if (transformedName.equals(MCP_CHUNKPROVIDERCLIENT))
		{
			ClassReader classReader = new ClassReader(bytes);
			ClassNode classNode = new ClassNode();
			classReader.accept(classNode, 0);

			this.replaceInstance(classNode, MCP_CHUNK, THREADED_CHUNK);

			ClassWriter cw = new ClassWriter(0);
			classNode.accept(cw);

			return cw.toByteArray();
		}

		if (transformedName.equals(MCP_CHUNK))
		{
			ClassReader classReader = new ClassReader(bytes);
			ClassNode classNode = new ClassNode();
			classReader.accept(classNode, 0);

			MethodNode relightBlock = this.findMethod(classNode, "func_76615_h", "(III)V");

			if (relightBlock == null)
			{
				// development environment
				relightBlock = this.findMethod(classNode, "relightBlock", "(III)V");
			}

			relightBlock.access = ACC_PUBLIC;

			MethodNode setBlockState = this.findMethod(classNode, "setBlockState", "(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/state/IBlockState;)Lnet/minecraft/block/state/IBlockState;");
			setBlockState.access = ACC_PUBLIC;

			Iterator<AbstractInsnNode> iter = setBlockState.instructions.iterator();

			while (iter.hasNext())
			{
				AbstractInsnNode insnNode = iter.next();

				if (insnNode instanceof MethodInsnNode)
				{
					MethodInsnNode methodInsnNode = (MethodInsnNode) insnNode;

					if (methodInsnNode.name.equals(relightBlock.name) && methodInsnNode.desc.equals(relightBlock.desc))
					{
						methodInsnNode.setOpcode(INVOKEVIRTUAL);
					}
				}
			}

			ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
			classNode.accept(cw);

			return cw.toByteArray();
		}

		if (name.equals(THREADED_CHUNK))
		{
			ClassReader classReader = new ClassReader(bytes);
			ClassNode classNode = new ClassNode();
			classReader.accept(classNode, 0);

			MethodInsnNode mn = (MethodInsnNode) classNode.methods.get(2).instructions.get(6);

			mn.owner = classNode.superName.replace(".", "/");
			mn.setOpcode(INVOKESPECIAL);

			ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
			classNode.accept(cw);

			return cw.toByteArray();
		}

		return bytes;
	}

	public MethodNode findMethod(ClassNode classNode, String name, String desc)
	{
		for (MethodNode methodNode : classNode.methods)
		{
			if (methodNode.name.equals(name) && methodNode.desc.equals(desc))
			{
				return methodNode;
			}
		}

		return null;
	}

	public void replaceInstance(ClassNode classNode, String oldInstance, String newInstance)
	{
		oldInstance = oldInstance.replace(".", "/");
		newInstance = newInstance.replace(".", "/");

		for (MethodNode methodNode : classNode.methods)
		{
			Iterator<AbstractInsnNode> iter = methodNode.instructions.iterator();
			TypeInsnNode previousTypeInsnNode = null;

			while (iter.hasNext())
			{
				AbstractInsnNode node = iter.next();

				if (node instanceof TypeInsnNode)
				{
					TypeInsnNode tn = (TypeInsnNode) node;

					if (tn.desc.equals(oldInstance))
					{
						previousTypeInsnNode = tn;
					}
				}

				if (node instanceof MethodInsnNode)
				{
					MethodInsnNode mn = (MethodInsnNode) node;
					if (mn.owner.equals(oldInstance) && mn.name.equals("<init>"))
					{
						mn.owner = newInstance;
						if(previousTypeInsnNode != null)
						{
							previousTypeInsnNode.desc = newInstance;
						}
					}
				}
			}
		}
	}

}
