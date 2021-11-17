package com.jk.spring.sort;

import java.util.List;

public class Bubble implements JKSort {

	@Override public int[] sort(int[] needSort) {
		for (int i = needSort.length - 1; i > 0; i--) {
			for (int j = 0; j < i; j++) {
				if (needSort[j] > needSort[j+1]) {
					int temp = needSort[j];
					needSort[j] = needSort[j+1];
					needSort[j+1] = temp;
				}
			}
		}
		return needSort;
	}

}
