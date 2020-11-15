package com.jk.spring.jkenum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder
@NoArgsConstructor
@AllArgsConstructor
public class JKEnumObj {
	private String id;
	private JKEnum jkEnum;
}
