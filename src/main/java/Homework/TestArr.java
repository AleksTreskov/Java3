package Homework;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestArr {
    static Methods methods;


    @BeforeEach
    public  void init(){
    methods=new Methods();}


    @Test
    public void testArr1() {
        int[] arr = new int[]{1,2,4,4,2,3,4,1,7};
        Assertions.assertArrayEquals(new int[]{1,7},methods.lastFourArray(arr));
    }
    @Test
    public void testArr2() {
        int[] arr = new int[]{1,2,4,4,2,3,4};
        Assertions.assertArrayEquals(new int[]{},methods.lastFourArray(arr));
    }
    @Test
    public void testArr3() {
        int[] arr = new int[]{1,2,44,2,34,1,2};
        Assertions.assertThrows(RuntimeException.class,()-> methods.lastFourArray(arr));
    }
    @Test
    public void testArr4() {
        int[] arr = new int[]{1,2,1,7};
        Assertions.assertThrows(RuntimeException.class,()-> methods.lastFourArray(arr));
    }
    @Test
    public void testArr5() {
        int[] arr = new int[]{1,1,1,4,4,1,4,4};
        Assertions.assertTrue(methods.oneOrFour(arr));

    }
    @Test
    public void testArr6() {
        int[] arr = new int[]{1,1,1,1,1,1};
        Assertions.assertFalse(methods.oneOrFour(arr));
    }
    @Test
    public void testArr7() {
        int[] arr = new int[]{4,4,4,4};
        Assertions.assertFalse(methods.oneOrFour(arr));
    }
    @Test
    public void testArr8() {
        int[] arr = new int[]{1,4,4,1,1,4,3};
        Assertions.assertFalse(methods.oneOrFour(arr));
    }
}