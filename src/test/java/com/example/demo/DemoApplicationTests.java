package com.example.demo;

import com.example.domain.User;
import com.example.mapper.ModuleMapper;
import com.example.mapper.UserCourseMapper;
import jxl.Sheet;
import jxl.Workbook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@SpringBootTest
class DemoApplicationTests
{
    @Test
    void test()
    {
        int[] pos = {2,5,8,21,46};
        byte[] teacherBytes = getByteList(pos);
        System.out.println(Arrays.toString(teacherBytes));
        long teachers = convertByteToLong(teacherBytes);
        System.out.println(teachers);
        byte[] teacherBytesConverted = convertLongToByte(teachers);
        System.out.println(Arrays.toString(teacherBytesConverted));
        List<Integer> index = getIndexList(teacherBytesConverted);
        System.out.println(index);
    }

    public byte[] getByteList(int[] pos)
    {
        byte[] bytes = new byte[8];
        for(int p : pos)
        {
            int x=(p-1)/8,y=(p-1)%8;
            bytes[x] |= 1 << y;
        }
        return bytes;
    }

    public long convertByteToLong(byte[] bytes)
    {
//        long l0 = bytes[0];
//        long l1 = (bytes[1] & 0xFF) << 8;
//        long l2 = (bytes[2] & 0xFF) << 16;
//        long l3 = (bytes[3] & 0xFF) << 24;
//        long l4 = (bytes[4] & 0xFF) << 32;
//        long l5 = (bytes[5] & 0xFF) << 40;
//        long l6 = (bytes[6] & 0xFF) << 48;
//        long l7 = (bytes[7] & 0xFF) << 56;
//        return l0 | l1 | l2 | l3 | l4 | l5 | l6 | l7;
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

    public byte[] convertLongToByte(long num)
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

    public List<Integer> getIndexList(byte[] bytes)
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
}
