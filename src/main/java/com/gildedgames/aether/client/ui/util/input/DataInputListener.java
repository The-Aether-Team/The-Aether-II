package com.gildedgames.aether.client.ui.util.input;

public interface DataInputListener<T>
{

	void onInit();

	void onChange(T data);

}
