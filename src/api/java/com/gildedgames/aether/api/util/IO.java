package com.gildedgames.aether.api.util;

/**
 * Represents an object that can be serialized using the I and O types.
 * Implementing this interface allows this class to be used in various 
 * IOManager methods.
 *
 * @author Emile
 *
 * @param <I> The type used to read back the data inside of this class.
 * @param <O> The type used to write the data inside of this class.
 */
public interface IO<I, O>
{

	/**
	 * Write the data inside of this class to the output class.
	 */
	void write(O output);

	/**
	 * Read the data from the input class and assign the fields of this class.
	 */
	void read(I input);

}
