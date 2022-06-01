package org.zerock.sample;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Component//root-context에 주입
@Data
@NoArgsConstructor //파라미터가 없는 기본 생성자 생성
@RequiredArgsConstructor //final이나 @NonNull 달려있는 파라미터값을 받는 생성자 생성
//@AllArgsConstructor //모든 필드값을 파라미터로 받는 생성자 생성
public class SampleHotel {
	/**
	 * @description : @NonNull 경우 내가 넣어줄 생성자를 정해주는것!
	 *                         여러개가 있을 경우 넣고싶은걸 선택할때 사용!!
	 *                         @RequiredArgsConstructor << 랑 세트!!
	 * */
	@NonNull
	private Chef chef;
	
	private NonNullCheckClass checkClass;
	
	//Autowired 를 사용 하지않고 생성자를 통해 주입
	/**
	 * @AllArgsConstructor  <<를 사용해서 생성자를 자동으로 만들기 가능!
	public SampleHotel(Chef chef) {
		super();
		this.chef = chef;
	}	
	*/
	
}
