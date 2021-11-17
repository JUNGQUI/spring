package com.jk.spring.sort;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class JKSortTest {
	int[] needSort = new int[]{3, 5, 2, 6, 7, 0, 1, 5, 4};

	Bubble bubble = new Bubble();
	Select select = new Select();
	Merge merge = new Merge();

	@Test
	void BubbleTest() {
		int[] afterSort = bubble.sort(needSort);

		System.out.println("J Tag");
	}

	@Test
	void SelectTest() {
		int[] afterSort = select.sort(needSort);

		System.out.println("J Tag");
	}

	@Test
	void MergeTest() {
		int[] afterSort = merge.sort(needSort);

		System.out.println("J tag");
	}
}