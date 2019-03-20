package com.volmit.terra.decor;

public class TypePalette
{
	private short[] types;

	public TypePalette(short[] types)
	{
		this.types = types;
	}

	public TypePalette(int size)
	{
		this(new short[size]);
	}

	public short[] getTypes()
	{
		return types;
	}
}
