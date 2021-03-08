package kr.co.uplus.cloud.sample.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PublicToken {
	private String accessToken;
	private String refreshToken;
}
