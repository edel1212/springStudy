package org.zerock.domain;

import lombok.Data;

/**
 * @Desripciton : 파일업로드 시 첨부파일의 정보를 저장하는 class
 * */
@Data
public class AttachFileDTO {
	private String fileName;
	private String uploadPath;
	private String uuid;
	private boolean image;
}
