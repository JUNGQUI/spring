package com.jk.spring.sort;

public class Merge implements JKSort {

	@Override
	public int[] sort(int[] needSort) {
		int half = needSort.length/2;
		int[] frontArray = new int[half];
		int[] backArray = new int[needSort.length - half];
		int[] afterArray = new int[needSort.length];
		int idx = 0;

		if (needSort.length > 2) {
			System.arraycopy(needSort, 0, frontArray, 0, half);
			System.arraycopy(needSort, half, backArray, 0, needSort.length - half);

			frontArray = sort(frontArray);
			backArray = sort(backArray);
		} else {
			System.arraycopy(needSort, 0, frontArray, 0, half);
			System.arraycopy(needSort, half, backArray, 0, needSort.length - half);
		}

		int i = 0, j = 0;
		while(true) {
			if (i >= frontArray.length) {
				for(; j < backArray.length; j++) {
					afterArray[idx] = backArray[j];
					idx++;
				}
				break;
			}

			if (j >= backArray.length) {
				for(; i < frontArray.length; i++) {
					afterArray[idx] = frontArray[i];
					idx++;
				}
				break;
			}

			if (frontArray[i] > backArray[j]) {
				afterArray[idx] = backArray[j];
				idx++;
				j++;
			} else {
				afterArray[idx] = frontArray[i];
				idx++;
				i++;
			}
		}

		return afterArray;
	}
}
