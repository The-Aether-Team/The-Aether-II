package com.gildedgames.aether.common.asm.util;

import com.gildedgames.aether.common.asm.AetherASMTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class ASMUtil
{
	public static AbstractInsnNode getFirstNode(final MethodNode methodNode, final int opcode)
	{
		final Iterator<AbstractInsnNode> abstractInsnNodeIterator = methodNode.instructions.iterator();

		while (abstractInsnNodeIterator.hasNext())
		{
			final AbstractInsnNode abstractInsnNode = abstractInsnNodeIterator.next();

			if (abstractInsnNode.getOpcode() == opcode)
			{
				return abstractInsnNode;
			}
		}

		return null;
	}

	public static ClassNode readClass(final byte[] data)
	{
		final ClassNode classNode = new ClassNode();

		final ClassReader classReader = new ClassReader(data);
		classReader.accept(classNode, 0);

		return classNode;
	}

	public static byte[] writeClass(final ClassNode classNode)
	{
		final ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);

		classNode.accept(writer);

		return writer.toByteArray();
	}

	public static void transformClassMethods(ClassNode classNode, MethodTransformer[] methodTransformers)
	{
		if (methodTransformers.length == 0)
		{
			throw new IllegalArgumentException("Method transformer list is empty!");
		}

		// We would use a set, but it doesn't preserve order and the list should never be too large
		final ArrayList<MethodTransformer> pending = new ArrayList<>();

		Collections.addAll(pending, methodTransformers);

		for (final MethodNode methodNode : new ArrayList<>(classNode.methods))
		{
			if (pending.isEmpty())
			{
				AetherASMTransformer.LOGGER.info("Applied {} method transformer(s) to class: '{}'", methodTransformers.length, classNode.name);

				return;
			}

			Iterator<MethodTransformer> iterator = pending.iterator();

			while (iterator.hasNext())
			{
				MethodTransformer transformer = iterator.next();

				if (transformer.canTransformMethod(methodNode))
				{
					AetherASMTransformer.LOGGER.debug("Applying method transformer {} to class {}", transformer.getClass().getName(), classNode.name);

					if (!transformer.transformMethod(classNode, methodNode))
					{
						AetherASMTransformer.LOGGER.error("Failed to apply method transformer {} to class {}", transformer.getClass().getName(), classNode.name);

						throw new RuntimeException("MethodTransformer failed to apply");
					}

					iterator.remove();
				}
			}
		}

		for (MethodTransformer transformer : pending)
		{
			AetherASMTransformer.LOGGER.error("Method transformer {} did not apply patches for class {}", transformer.getClass().getName(), classNode.name);
		}

		throw new RuntimeException("Some MethodTransformers did not apply patches");
	}
}
