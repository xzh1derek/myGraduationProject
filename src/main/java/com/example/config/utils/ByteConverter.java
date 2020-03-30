package com.example.config.utils;

import java.util.ArrayList;
import java.util.List;

public class ByteConverter
{
    static byte[] getByteList(Integer[] pos) //把长整型转换成字节数组
    {
        byte[] bytes = new byte[8]; //64位数拆成8字节的字节数组
        for(int p : pos)
        {
            if(p<1||p>64) continue;
            int x=(p-1)/8,y=(p-1)%8;
            bytes[x] |= 1 << y; //将字节数组的第x位赋2的y次方
        }
        return bytes;
    }

    static long convertByteToLong(byte[] bytes) //把字节数组拼在一起成为一个长整形long
    {
        long l0 = bytes[0] & 0x00000000000000ffL;
        long l1 = ((long)bytes[1]<<8 ) & 0x000000000000ff00L;
        long l2 = ((long)bytes[2]<<16) & 0x0000000000ff0000L;
        long l3 = ((long)bytes[3]<<24) & 0x00000000ff000000L;
        long l4 = ((long)bytes[4]<<32) & 0x000000ff00000000L;
        long l5 = ((long)bytes[5]<<40) & 0x0000ff0000000000L;
        long l6 = ((long)bytes[6]<<48) & 0x00ff000000000000L;
        long l7 = ((long)bytes[7]<<56) & 0xff00000000000000L;
        return l0 | l1 | l2 | l3 | l4 | l5 | l6 | l7; //把8个长整型数的各个字节位取出来拼在一起
    }

    static byte[] convertLongToByte(long num) //把长整型拆成长度为8的字节数组
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

    static List<Integer> getIndexList(byte[] bytes) //提取字节数组中标志位1的下标
    {
        List<Integer> indexList = new ArrayList<>();
        for(int i=0;i<8;i++)
        {
            int b = bytes[i];
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
