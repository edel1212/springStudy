package com.yoo.domian;

import java.util.List;

import lombok.Data;

@Data

public class statusVo {
	String status;
	List<BoardVO> dataList;
	String msg;
}
