//package com.t795.zki.common.;

public class KeyGenerator {
    KeyGenerator(){};

    public String generateMagicSquareKey(int size)
    {
        int sideLength = (int)Math.ceil(Math.sqrt(size));
        byte[][] firstArray= new byte[sideLength][sideLength];
        byte[][] secondArray= new byte[sideLength][sideLength];
        String result = "";

        firstArray[0][0] = (byte)((sideLength-1)/2);
        for(int i = 1, j = 0; i < sideLength; i++, j++)
        {
            if(j == (sideLength-1)/2){j++;}
            firstArray[0][i] = (byte)j;
        }
        for(int i = 1; i < sideLength; i++)
        {
            for(int j = 0; j<sideLength; j++)
            {
                firstArray[i][j] = firstArray[0][(j+i)%sideLength];
            }
        }

        for(int i = 0 ; i < sideLength*sideLength; i++)
        {
            secondArray[i/sideLength][Math.abs((sideLength-1-i)%sideLength)] = firstArray[i/sideLength][i%sideLength];
        }

        for(int i = 0; i < sideLength*sideLength; i++)
        {
            result += (char) ((firstArray[i/sideLength][i%sideLength] * (byte)sideLength) + secondArray[i/sideLength][i%sideLength] +1);
        }
        return result;
    }
}
