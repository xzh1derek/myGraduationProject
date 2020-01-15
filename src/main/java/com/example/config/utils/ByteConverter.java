package com.example.config.utils;

import java.util.ArrayList;
import java.util.List;

public class ByteConverter
{
    static byte[] getByteList(Integer[] pos)
    {
        byte[] bytes = new byte[8];
        for(int p : pos)
        {
            if(p<1||p>64) continue;
            int x=(p-1)/8,y=(p-1)%8;
            bytes[x] |= 1 << y;
        }
        return bytes;
    }

    static long convertByteToLong(byte[] bytes)
    {
        long l0 = bytes[0] & 0x00000000000000ffL;
        long l1 = ((long)bytes[1]<<8 ) & 0x000000000000ff00L;
        long l2 = ((long)bytes[2]<<16) & 0x0000000000ff0000L;
        long l3 = ((long)bytes[3]<<24) & 0x00000000ff000000L;
        long l4 = ((long)bytes[4]<<32) & 0x000000ff00000000L;
        long l5 = ((long)bytes[5]<<40) & 0x0000ff0000000000L;
        long l6 = ((long)bytes[6]<<48) & 0x00ff000000000000L;
        long l7 = ((long)bytes[7]<<56) & 0xff00000000000000L;
        return l0 | l1 | l2 | l3 | l4 | l5 | l6 | l7;
    }

    static byte[] convertLongToByte(long num)
    {
        byte[] bytes = new byte[8];
        bytes[0] = (byte)num;
        bytes[1] = (byte)(num>>8);
        bytes[2] = (byte)(num>>16);
        bytes[3] = (byte)(num>>24);
        bytes[4] = (byte)(num>>32);
        bytes[5] = (byte)(num>>40);
        bytes[6] = (byte)(num>>48);
        bytes[7] = (byte)(num>>56);
        return bytes;
    }

    static List<Integer> getIndexList(byte[] bytes)
    {
        List<Integer> indexList = new ArrayList<>();
        for(int i=0;i<8;i++)
        {
            int b = (int)bytes[i];
            for(int j=0;j<8;j++)
            {
                if((b&0x01)==1) indexList.add(i*8+j+1);
                b = b>>1;
            }
        }
        return indexList;
    }

    public static long convertIndexToLong(Integer[] indexList)
    {
        return convertByteToLong(getByteList(indexList));
    }

    public static List<Integer> convertLongToIndex(long num)
    {
        return getIndexList(convertLongToByte(num));
    }
}
