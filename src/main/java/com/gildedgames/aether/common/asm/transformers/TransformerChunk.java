package com.gildedgames.aether.common.asm.transformers;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import java.util.ArrayList;
import java.util.Iterator;

public class TransformerChunk implements IClassTransformer
{
	@Override
	public byte[] transform(String name, String transformedName, byte[] basicClass)
	{
		ClassNode classNode = new ClassNode();

		ClassReader classReader = new ClassReader(basicClass);
		classReader.accept(classNode, 0);

		classNode.visitField(Opcodes.ACC_PUBLIC,
				"neighborLightChecks",
				"[S",
				null, null);

		classNode.visitField(Opcodes.ACC_PROTECTED + Opcodes.ACC_FINAL,
				"lightingEngine",
				"Lcom/gildedgames/aether/api/world/lighting/ILightingEngine;",
				null, null);

		ArrayList<MethodNode> newMethods = new ArrayList<>();

		for (MethodNode method : classNode.methods)
		{
			if (method.name.equals("<init>") && method.desc.equals("(Lnet/minecraft/world/World;II)V"))
			{
				this.transformConstructor(method);
			}
			else if (method.name.equals("getLightSubtracted") && method.desc.equals("(Lnet/minecraft/util/math/BlockPos;I)I"))
			{
				this.transformGetLightSubtracted(method);
			}
			else if (method.name.equals("getLightFor") && method.desc.equals("(Lnet/minecraft/world/EnumSkyBlock;Lnet/minecraft/util/math/BlockPos;)I"))
			{
				newMethods.add(this.transformGetLightFor(method));
			}
			else if (method.name.equals("relightBlock") && method.desc.equals("(III)V"))
			{
				this.transformRelightFor(method);
			}
			else if (method.name.equals("setBlockState") && method.desc.equals("(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/state/IBlockState;)Lnet/minecraft/block/state/IBlockState;"))
			{
				this.transformSetBlockState(method);
			}
			else if (method.name.equals("setLightFor") && method.desc.equals("(Lnet/minecraft/world/EnumSkyBlock;Lnet/minecraft/util/math/BlockPos;I)V"))
			{
				this.transformSetLightFor(method);
			}
			else if (method.name.equals("onLoad") && method.desc.equals("()V"))
			{
				this.transformOnLoad(method);
			}
		}

		classNode.methods.addAll(newMethods);

		ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
		classNode.accept(writer);

		return writer.toByteArray();
	}

	private void transformConstructor(MethodNode method)
	{
		Iterator<AbstractInsnNode> abstractInsnNodeIterator = method.instructions.iterator();

		while (abstractInsnNodeIterator.hasNext())
		{
			AbstractInsnNode abstractInsnNode = abstractInsnNodeIterator.next();

			if (abstractInsnNode.getOpcode() == Opcodes.RETURN)
			{
				InsnList injection = new InsnList();
				injection.add(new VarInsnNode(Opcodes.ALOAD, 0));
				injection.add(new InsnNode(Opcodes.ACONST_NULL));
				injection.add(new FieldInsnNode(Opcodes.PUTFIELD,
						"net/minecraft/world/chunk/Chunk",
						"neighborLightChecks",
						"[S"));
				injection.add(new VarInsnNode(Opcodes.ALOAD, 0));
				injection.add(new VarInsnNode(Opcodes.ALOAD, 0));
				injection.add(new FieldInsnNode(Opcodes.GETFIELD,
						"net/minecraft/world/chunk/Chunk",
						"world",
						"Lnet/minecraft/world/World;"));
				injection.add(new MethodInsnNode(Opcodes.INVOKESTATIC,
						"com/gildedgames/aether/common/world/lighting/LightingHooks",
						"getLightingEngine",
						"(Lnet/minecraft/world/World;)Lcom/gildedgames/aether/api/world/lighting/ILightingEngine;",
						false));
				injection.add(new FieldInsnNode(Opcodes.PUTFIELD,
						"net/minecraft/world/chunk/Chunk",
						"lightingEngine",
						"Lcom/gildedgames/aether/api/world/lighting/ILightingEngine;"));

				method.instructions.insertBefore(abstractInsnNode, injection);
			}
		}
	}

	private void transformSetLightFor(MethodNode methodNode)
	{
		InsnList instructions = new InsnList();

		Iterator<AbstractInsnNode> abstractInsnNodeIterator = methodNode.instructions.iterator();

		while (abstractInsnNodeIterator.hasNext())
		{
			AbstractInsnNode abstractInsnNode = abstractInsnNodeIterator.next();

			if (abstractInsnNode instanceof LineNumberNode)
			{
				LineNumberNode labelNode = (LineNumberNode) abstractInsnNode;

				// Replace line 719
				if (labelNode.line == 719)
				{
					InsnList insnList = new InsnList();
					insnList.add(new VarInsnNode(Opcodes.ALOAD, 0));
					insnList.add(new FieldInsnNode(Opcodes.GETFIELD,
							"net/minecraft/world/chunk/Chunk",
							"world",
							"Lnet/minecraft/world/World;"));
					insnList.add(new VarInsnNode(Opcodes.ALOAD, 0));
					insnList.add(new VarInsnNode(Opcodes.ALOAD, 7));
					insnList.add(new MethodInsnNode(Opcodes.INVOKESTATIC,
							"com/gildedgames/aether/common/world/lighting/LightingHooks",
							"initSkylightForSection",
							"(Lnet/minecraft/world/World;Lnet/minecraft/world/chunk/Chunk;Lnet/minecraft/world/chunk/storage/ExtendedBlockStorage;)V",
							false));

					instructions.add(insnList);

					while (abstractInsnNodeIterator.hasNext())
					{
						abstractInsnNode = abstractInsnNodeIterator.next();

						if (abstractInsnNode instanceof LineNumberNode)
						{
							instructions.add(((LineNumberNode) abstractInsnNode).start);
							instructions.add(abstractInsnNode);

							break;
						}
					}

					continue;
				}
			}

			instructions.add(abstractInsnNode);
		}

		methodNode.instructions = instructions;
	}

	private MethodNode createGetCachedLightFor(InsnList previous)
	{
		MethodNode methodNode = new MethodNode(Opcodes.ACC_PUBLIC,
				"getCachedLightFor",
				"(Lnet/minecraft/world/EnumSkyBlock;Lnet/minecraft/util/math/BlockPos;)I",
				null,
				null);

		methodNode.instructions.add(previous);
		methodNode.instructions.resetLabels(); // These are all miserably out of date now, just trash it

		return methodNode;
	}

	private MethodNode transformRelightFor(MethodNode methodNode)
	{
		Iterator<AbstractInsnNode> abstractInsnNodeIterator = methodNode.instructions.iterator();

		InsnList instructions = new InsnList();

		while (abstractInsnNodeIterator.hasNext())
		{
			AbstractInsnNode abstractInsnNode = abstractInsnNodeIterator.next();

			if (abstractInsnNode instanceof LineNumberNode)
			{
				LineNumberNode labelNode = (LineNumberNode) abstractInsnNode;

				// Delete line 411
				if (labelNode.line == 411)
				{
					// Safe to erase label here
					instructions.remove(labelNode.start);

					while (abstractInsnNodeIterator.hasNext())
					{
						abstractInsnNode = abstractInsnNodeIterator.next();

						if (abstractInsnNode instanceof LineNumberNode)
						{
							break;
						}
					}

					continue;
				}
				// Replace line 418
				else if (labelNode.line == 418)
				{
					// Safe to erase label here
					instructions.remove(labelNode.start);

					instructions.add(new VarInsnNode(Opcodes.ALOAD, 0));
					instructions.add(new VarInsnNode(Opcodes.ILOAD, 1));
					instructions.add(new VarInsnNode(Opcodes.ILOAD, 3));
					instructions.add(new VarInsnNode(Opcodes.ILOAD, 4));
					instructions.add(new VarInsnNode(Opcodes.ILOAD, 5));
					instructions.add(new MethodInsnNode(Opcodes.INVOKESTATIC,
							"com/gildedgames/aether/common/world/lighting/LightingHooks",
							"relightSkylightColumn",
							"(Lnet/minecraft/world/World;Lnet/minecraft/world/chunk/Chunk;IIII)V",
							false));

					// Delete lines 419-470 (next label is at 473)
					// Safe to erase all labels in this section
					while (abstractInsnNodeIterator.hasNext())
					{
						abstractInsnNode = abstractInsnNodeIterator.next();

						if (abstractInsnNode instanceof LineNumberNode)
						{
							labelNode = (LineNumberNode) abstractInsnNode;

							if (labelNode.line == 473)
							{
								break;
							}
						}
					}

					continue;
				}
				// Early exit on line 488
				else if (labelNode.line == 488)
				{
					// Keep the label line node, we're just returning instead
					instructions.add(abstractInsnNode);

					instructions.add(new FrameNode(Opcodes.F_SAME, 0, null, 0, null));
					instructions.add(new InsnNode(Opcodes.RETURN));

					continue;
				}
			}

			instructions.add(abstractInsnNode);
		}

		methodNode.instructions = instructions;

		return methodNode;
	}

	private void transformGetLightSubtracted(MethodNode methodNode)
	{
		// Insert hook before method executes
		InsnList insnList = new InsnList();
		insnList.add(new VarInsnNode(Opcodes.ALOAD, 0));
		insnList.add(new FieldInsnNode(Opcodes.GETFIELD,
				"net/minecraft/world/chunk/Chunk",
				"lightingEngine",
				"Lcom/gildedgames/aether/api/world/lighting/ILightingEngine;"));
		insnList.add(new MethodInsnNode(Opcodes.INVOKEINTERFACE,
				"com/gildedgames/aether/api/world/lighting/ILightingEngine",
				"procLightUpdates",
				"()V",
				true));

		methodNode.instructions.insertBefore(methodNode.instructions.getFirst(), insnList);
	}

	private MethodNode transformGetLightFor(MethodNode methodNode)
	{
		InsnList previous = new InsnList();
		previous.add(methodNode.instructions);

		InsnList insnList = new InsnList();
		insnList.add(new VarInsnNode(Opcodes.ALOAD, 0));
		insnList.add(new FieldInsnNode(Opcodes.GETFIELD,
				"net/minecraft/world/chunk/Chunk",
				"lightingEngine",
				"Lcom/gildedgames/aether/api/world/lighting/ILightingEngine;"));
		insnList.add(new MethodInsnNode(Opcodes.INVOKEINTERFACE,
				"com/gildedgames/aether/api/world/lighting/ILightingEngine",
				"procLightUpdates",
				"()V",
				true));

		insnList.add(new VarInsnNode(Opcodes.ALOAD, 0));
		insnList.add(new VarInsnNode(Opcodes.ALOAD, 1));
		insnList.add(new VarInsnNode(Opcodes.ALOAD, 2));
		insnList.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL,
				"net/minecraft/world/chunk/Chunk",
				"getCachedLightFor",
				"(Lnet/minecraft/world/EnumSkyBlock;Lnet/minecraft/util/math/BlockPos;)I",
				false));
		insnList.add(new InsnNode(Opcodes.IRETURN));

		methodNode.instructions.clear();
		methodNode.instructions = insnList;

		return this.createGetCachedLightFor(previous);
	}

	private void transformSetBlockState(MethodNode methodNode)
	{
		Iterator<AbstractInsnNode> abstractInsnNodeIterator = methodNode.instructions.iterator();

		LabelNode labelNode = null;

		// Find the label belonging to line 660
		while (abstractInsnNodeIterator.hasNext())
		{
			AbstractInsnNode abstractInsnNode = abstractInsnNodeIterator.next();

			if (abstractInsnNode instanceof LineNumberNode)
			{
				LineNumberNode lineNumberNode = (LineNumberNode) abstractInsnNode;

				if (lineNumberNode.line == 660)
				{
					labelNode = lineNumberNode.start;
					break;
				}
			}
		}

		abstractInsnNodeIterator = methodNode.instructions.iterator();

		// Find line 653 and insert a GOTO command to the label we found
		while (abstractInsnNodeIterator.hasNext())
		{
			AbstractInsnNode abstractInsnNode = abstractInsnNodeIterator.next();

			if (abstractInsnNode instanceof LineNumberNode)
			{
				LineNumberNode lineNumberNode = (LineNumberNode) abstractInsnNode;

				if (lineNumberNode.line == 653)
				{
					methodNode.instructions.insert(lineNumberNode, new JumpInsnNode(Opcodes.GOTO, labelNode));
					break;
				}
			}
		}
	}

	private void transformOnLoad(MethodNode methodNode)
	{
		Iterator<AbstractInsnNode> abstractInsnNodeIterator = methodNode.instructions.iterator();

		while (abstractInsnNodeIterator.hasNext())
		{
			AbstractInsnNode abstractInsnNode = abstractInsnNodeIterator.next();

			if (abstractInsnNode.getOpcode() == Opcodes.RETURN)
			{
				InsnList insnList = new InsnList();
				insnList.add(new VarInsnNode(Opcodes.ALOAD, 0));
				insnList.add(new FieldInsnNode(Opcodes.GETFIELD,
						"net/minecraft/world/chunk/Chunk",
						"world",
						"Lnet/minecraft/world/World;"));
				insnList.add(new VarInsnNode(Opcodes.ALOAD, 0));
				insnList.add(new MethodInsnNode(Opcodes.INVOKESTATIC,
						"com/gildedgames/aether/common/world/lighting/LightingHooks",
						"scheduleRelightChecksForChunkBoundaries",
						"(Lnet/minecraft/world/World;Lnet/minecraft/world/chunk/Chunk;)V",
						false));

				methodNode.instructions.insertBefore(abstractInsnNode, insnList);
			}
		}
	}
}
