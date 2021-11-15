package com.jk.spring.sort;

public class Select implements JKSort {

	@Override public int[] sort(int[] needSort) {
		for (int i = 0; i < needSort.length; i++) {
			int min = needSort[i];
			for (int j = i + 1; j < needSort.length; j++) {
				if (min > needSort[j]) {
					needSort[i] = needSort[j];
					needSort[j] = min;
					min = needSort[i];
				}
			}
		}
		return needSort;
	}
}
