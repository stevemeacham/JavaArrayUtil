package com.nokamea.function;

import org.junit.jupiter.api.Test;

import java.util.function.BiFunction;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

class ArrayUtilTest {

    @Test
    void concat() {
        assertThrows(NullPointerException.class, () -> ArrayUtil.concat(null, new int[0]));
        assertThrows(NullPointerException.class, () -> ArrayUtil.concat(new int[0], null));
        assertThrows(NullPointerException.class, () -> ArrayUtil.concat(null, null));

        assertThrows(IllegalArgumentException.class, () -> ArrayUtil.concat(0, new int[0]));
        assertThrows(IllegalArgumentException.class, () -> ArrayUtil.concat(new int[0], 0));
        assertThrows(IllegalArgumentException.class, () -> ArrayUtil.concat(0, 0));

        assertThrows(IllegalArgumentException.class, () -> ArrayUtil.concat(new int[0], new long[0]));
        assertDoesNotThrow(() -> ArrayUtil.concat(new int[0], new int[0]));
        assertDoesNotThrow(() -> ArrayUtil.concat(new Object[0], new Object[0]));

        assertNotNull(ArrayUtil.concat(new int[0], new int[0]));
        assertInstanceOf(int[].class, ArrayUtil.concat(new int[0], new int[0]));
        assertInstanceOf(Object[].class, ArrayUtil.concat(new Object[0], new Object[0]));
        assertEquals(int.class, ArrayUtil.concat(new int[0], new int[0]).getClass().getComponentType());
        assertEquals(Object.class, ArrayUtil.concat(new Object[0], new Object[0]).getClass().getComponentType());

        assertArrayEquals(new byte[] {1,2,3,7,8,9}, ArrayUtil.concat(new byte[] {1,2,3}, new byte[] {7,8,9}));
        assertArrayEquals(new short[] {1,2,3,7,8,9}, ArrayUtil.concat(new short[] {1,2,3}, new short[] {7,8,9}));
        assertArrayEquals(new int[] {1,2,3,7,8,9}, ArrayUtil.concat(new int[] {1,2,3}, new int[] {7,8,9}));
        assertArrayEquals(new long[] {1,2,3,7,8,9}, ArrayUtil.concat(new long[] {1,2,3}, new long[] {7,8,9}));
        assertArrayEquals(new float[] {1,2,3,7,8,9}, ArrayUtil.concat(new float[] {1,2,3}, new float[] {7,8,9}));
        assertArrayEquals(new double[] {1,2,3,7,8,9}, ArrayUtil.concat(new double[] {1,2,3}, new double[] {7,8,9}));

        assertArrayEquals(new Byte[] {1,2,3,7,8,9}, ArrayUtil.concat(new Byte[] {1,2,3}, new Byte[] {7,8,9}));
        assertArrayEquals(new Short[] {1,2,3,7,8,9}, ArrayUtil.concat(new Short[] {1,2,3}, new Short[] {7,8,9}));
        assertArrayEquals(new Integer[] {1,2,3,7,8,9}, ArrayUtil.concat(new Integer[] {1,2,3}, new Integer[] {7,8,9}));
        assertArrayEquals(new Long[] {1L,2L,3L,7L,8L,9L}, ArrayUtil.concat(new Long[] {1L,2L,3L}, new Long[] {7L,8L,9L}));
        assertArrayEquals(new Float[] {1F,2F,3F,7F,8F,9F}, ArrayUtil.concat(new Float[] {1F,2F,3F}, new Float[] {7F,8F,9F}));
        assertArrayEquals(new Double[] {1D,2D,3D,7D,8D,9D}, ArrayUtil.concat(new Double[] {1D,2D,3D}, new Double[] {7D,8D,9D}));

        assertArrayEquals(new String[] {"a","b","c","x","y","z"},
                ArrayUtil.concat(new String[] {"a","b","c"}, new String[] {"x","y","z"}));
    }

    @Test
    void concatenator() {
        assertArrayEquals(new int[] {1,2}, ArrayUtil.concatenator(new int[] {1}, new int[] {2}).get());
        assertArrayEquals(new int[] {1,2}, ArrayUtil.concatenator(new int[] {1}).apply(new int[] {2}));
        assertArrayEquals(new int[] {1,2}, (int[]) ArrayUtil.concatenator().apply(new int[] {1}, new int[] {2}));
    }
}