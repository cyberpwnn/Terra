package com.volmit.terra.decor;

public class TypeColumn
{
	private final short[][] result;

	public TypeColumn()
	{
		result = new short[16][];
	}

	public void addBlocks(TypeColumn microColumn, int fx, int tx, int fz, int tz)
	{
		for(int h = 0; h < 256; h++)
		{
			if(!microColumn.hasSection(h >> 4))
			{
				continue;
			}

			for(int i = fx; i < tx; i++)
			{
				for(int j = fz; j < tz; j++)
				{
					setBlock(i, h, j, microColumn.getBlock(i, h, j));
				}
			}
		}
	}

	public boolean hasSection(int y)
	{
		return result[y >> 4] != null;
	}

	public short getBlock(int x, int y, int z)
	{
		if(result[y >> 4] == null)
		{
			return (short) 0;
		}

		return result[y >> 4][((y & 0xF) << 8) | (z << 4) | x];
	}

	public void setBlock(int x, int y, int z, short id)
	{
		if(result[y >> 4] == null)
		{
			result[y >> 4] = new short[4096];
		}

		result[y >> 4][((y & 0xF) << 8) | (z << 4) | x] = id;
	}

	public short[][] getResult()
	{
		return result;
	}
}
