package org.zerock.service;

import java.util.Map;

public interface SampleService {
	public Integer doAdd(String str1, String str2) throws Exception;
	public Integer doAddMapVer(Map<String, String> map) throws Exception;
}
