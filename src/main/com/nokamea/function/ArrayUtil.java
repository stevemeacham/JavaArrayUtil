/*
 * Copyright © 2022 Steven D. Meacham
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

package com.nokamea.function;

import com.sun.istack.internal.NotNull;

import java.lang.reflect.Array;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * A namespace implemented as a Java class containing functions to manipulate
 * Java arrays. It is not intended to be instantiated, although the first-class
 * functions it contains can be.
 */
public class ArrayUtil {
    private ArrayUtil() {}

    /**
     * A high-order function that returns a {@link Supplier} configured to
     * concatenate the two provided arrays using {@link #concat}.
     *
     * <code>
     *     // Concatenate {1} and {2} into {1,2}.
     *     int[] a1 = new int[] {1};
     *     int[] a2 = new int[] {2};
     *     int[] combined = ArrayUtil.concatenator(a1, a2).get();
     * </code>
     *
     * @param a1 See {@link #concat}.
     * @param a2 See {@link #concat}.
     * @param <T> See {@link #concat}.
     * @return A Supplier that will return the concatenation of both arrays.
     */
    public static <T> Supplier<T> concatenator(T a1, T a2) {
        return () -> ArrayUtil.concat(a1, a2);
    }

    /**
     * A high-order function that returns a {@link Function} configured to
     * concatenate the provided array to others using {@link #concat}.
     *
     * <code>
     *     // Concatenate {1} and {2} into {1,2}.
     *     int[] a1 = new int[] {1};
     *     int[] a2 = new int[] {2};
     *     int[] combined = ArrayUtil.concatenator(a1).apply(a2);
     * </code>
     *
     * @param a1 See {@link #concat}.
     * @param <T> See {@link #concat}.
     * @return A Function that will return the concatenation of both arrays.
     */
    public static <T> Function<T,T> concatenator(T a1) {
        return a2 -> ArrayUtil.concat(a1, a2);
    }

    /**
     * A high-order function that returns a {@link BiFunction} version of
     * {@link #concat}.
     *
     * <code>
     *     // Concatenate {1} and {2} into {1,2}.
     *     int[] a1 = new int[] {1};
     *     int[] a2 = new int[] {2};
     *     int[] combined = ArrayUtil.concatenator().apply(a1, a2);
     * </code>
     *
     * @param <T> See {@link #concat}.
     * @return A BiFunction that will return the concatenation of both arrays.
     */
    public static <T> BiFunction<T,T,T> concatenator() {
        return ArrayUtil::concat;
    }

    /**
     * A type-safe first-class pure function that accepts two arrays of the
     * same type and concatenates their contents into the newly created
     * returned array. Type-safety and the ability to accept arrays of
     * primitive or non-primitive elements are accomplished through Java
     * reflection.
     *
     * The parameter preconditions are rigorously enforced. When Java
     * assertions are enabled, so will the return value.
     *
     * @param a1 a non-null array matching the type of <code>a2</code>.
     * @param a2 a non-null array matching the type of <code>a1</code>.
     * @param <T> the shared type of both parameters and the return value,
     *           which may be arrays of primitive or non-primitive elements.
     * @return a non-null array containing the elements of both arrays.
     */
    @SuppressWarnings({"unchecked", "SuspiciousSystemArraycopy"})
    public static <T> T concat(@NotNull T a1, @NotNull T a2) {
        final Class<?> type = enforcePreconditions(a1, a2);

        final int len1 = Array.getLength(a1);
        final int len2 = Array.getLength(a2);
        final T result = (T) Array.newInstance(type, len1 + len2);

        System.arraycopy(a1, 0, result, 0, len1);
        System.arraycopy(a2, 0, result, len1, len2);

        //noinspection ConstantConditions
        assert result != null;
        assert result.getClass().isArray();
        assert result.getClass().getComponentType().equals(type);
        return result;
    }

    private static <T> Class<?> enforcePreconditions(T a1, T a2) {
        final String P1NULL = "first parameter must not be null";
        final String P2NULL = "second parameter must not be null";
        final String P1ARRAY = "first parameter must be an array";
        final String P2ARRAY = "second parameter must be an array";
        final String P1P2MATCH = "both arrays must be of same type";

        final Class<?> c1 = Objects.requireNonNull(a1, P1NULL).getClass();
        final Class<?> c2 = Objects.requireNonNull(a2, P2NULL).getClass();
        if (!c1.isArray()) throw new IllegalArgumentException(P1ARRAY);
        if (!c2.isArray()) throw new IllegalArgumentException(P2ARRAY);
        final Class<?> type = c1.getComponentType();
        if (!c2.getComponentType().equals(type))
            throw new IllegalArgumentException(P1P2MATCH);

        assert type == a1.getClass().getComponentType();
        assert type == a2.getClass().getComponentType();
        return type;
    }
}
