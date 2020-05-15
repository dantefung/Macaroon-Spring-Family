/*
 * Copyright (C), 2015-2018
 * FileName: CheckSignSampleTest
 * Author:   DANTE FUNG
 * Date:     2020/5/7 17:56
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2020/5/7 17:56   V1.0.0
 */
package com.dantefung.mvc.samples;

import com.dantefung.springbootmvc.constant.Constant;
import com.dantefung.springbootmvc.utils.JsonUtil;
import com.dantefung.springbootmvc.utils.RSAEncrypt;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @Title: CheckSignSampleTest
 * @Description: 测试请求签名校验以及响应报文验签
 * @author DANTE FUNG
 * @date 2020/5/7 17:56
 */
@Slf4j
public class CheckSignSampleTest {

	/** 接入方的私钥**/
	private static String privateKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCDpMiE1vS8EGXnj8aMOAd7QT+7BWZMI9Io8IBQe3F7x7vlc/VoT0puMPf8+Hi5F+MLWTByDGjDGi/zJTQDmsfdrlcrBSbTOkyvg4jNOuiT15vFXYXklgm9nlLHm3NBAxP3tnWjdMsSCjAAgMy4mIjBVcafhbxVmXXV54D1HLadC0zThyPu0eUqkdqnlS+XdbskODp7iNLVN70KWse0Xn2wdO/pjV/tsoAye2FraTlR0Wtsa/BIo/f+z/gPikJr0q4eohoVGgPA2pAMIQ0Wg0aQMBkkxXOwbpx4ZwLicVHTNTWUWKITQlw5SCODbmW7Zu1x+CT8ihSyUc+UB4Szb487AgMBAAECggEAKubS6mfbTkbRzwtOG3hPB94V1O9HjVzHKsxmJNR2AW7wTzDhM2NT0GFpECoxvbCJL7ObVC+zHJI2OjlPoDUbGaWthrmhE4mWYESvmqKuFTBY9ZMrBXnYJtGoDKEeiHtmUH5IDEMNww1K438WOLqNJuS7XFWLMSJYAqP1pOv8n2ULyoGLBVdvtXKQD9haUnLq8LBGadfgXyzMQ9byL29F6CG029F5H54aA37ImswQ67XUo5B9WNWE/3cN1okzVKMbRln6ZM/t74Ga2oIVhkKIK2bP0lAzdczjTFHdPt60kkU4bna3JX59ii15bx1JDWqZTmAsJTZycp9qk4+zjnsN8QKBgQDbk2wUcdDrJwpoV78xrRwF+5pcIKpYgkHM/dUhaBU0+v8D2bAj9k8yXDGtEKsLmOEPTkcYWqxl0paRJ7hWAsgJkiw0m/DWNyS5J0N2zd2UdAepP9r4JD+TFsWfodDqFnnd96fEAoB4ctI0ngn9tveVfVuqLJx7WAm/YYMFtE+aDQKBgQCZezSSmyfMk2Is7mI0J7Rgzg8Nb2LHPQ9a4r9yTCxC8EsSYBhvOuERKwU26ZwRxf6qxy7UONJ2wri6JGjUobsUbw41E2UHF9j0WsYkSpy/uh+f5a7LWe8Eef8NeIfQ/OJWM5UpABO3/mx+JbmmVjuH423rWHFzXiSFoDYeBsnkZwKBgQCXk3BDuMIo4p17pD83ErJKqwJG4MHXMawsz3kmg4xIM5CBXvAKE0lekWg1eVEqQ1Hx1+6aMFXcQIByGcJVlbvzZ3Wep5uctRpjumgHBlwU0/hJ7OvC6nr8lfa8mN74CaX9Ba5JUBTRkSns6sAo5fqJtqzlj8BCIWcxNyZUdMGSrQKBgDvJ7Q73dieRens6WfMrTG8xlleLfpVSyfqDvkSOO+fPp87+tEYQT8DaW2uq0WtzD+QDN9DgWcx9ymxo5pV+c1xgbs5qQb7joVZ/ThSxJCLkJJGrbc41uJCr3ZmnzHCzMpCWA5M3Pnc8m5MAqzOLACHNjPJTP87La7rKUIqd5mQdAoGAeD4FZe4ByqZs76Q1D/ouRoywXhrpa+/+wpJaIpNHfUaRCKc3ISSe228DnkwnYl2KosQC+W3r04wKwxcWdnGIFsz3RDKA18wIivRqZErqpNFpi1TeFw72+TUe/0m2E0ekU45hwl0yWphM74at4Pjt/kLtUoisp2/R5km22dTDnE0=";

	/** 接入方的公钥**/
	private static String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAg6TIhNb0vBBl54/GjDgHe0E/uwVmTCPSKPCAUHtxe8e75XP1aE9KbjD3/Ph4uRfjC1kwcgxowxov8yU0A5rH3a5XKwUm0zpMr4OIzTrok9ebxV2F5JYJvZ5Sx5tzQQMT97Z1o3TLEgowAIDMuJiIwVXGn4W8VZl11eeA9Ry2nQtM04cj7tHlKpHap5Uvl3W7JDg6e4jS1Te9ClrHtF59sHTv6Y1f7bKAMntha2k5UdFrbGvwSKP3/s/4D4pCa9KuHqIaFRoDwNqQDCENFoNGkDAZJMVzsG6ceGcC4nFR0zU1lFiiE0JcOUgjg25lu2btcfgk/IoUslHPlAeEs2+POwIDAQAB";

	/** 我方的私钥**/
	private static String selfPrivateKey = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDCZAC9dnZeAK8WCpqvFgjNJTEswSawWEKqU92XWz4M63jk7DWIF0iFE5abwptvWG+vuhlfjHIQkoz+cJmDUdLwKCCcZbJWA5hUW1ZCF4hninYNtK32wAv4yS1fCLZtui7Wtpbrg4nCijM7U4s0PYVMYOX8jfouxNg33/Qw8IMum5iSo02c/mTKk9PPDjPb8Jb1jG1Vm4QewMCqdRjH6g2K25zwc5r7Tg+sR1ANKt+1Q+rs2gYbW4XgFbrWKFCWCzsixeCB9wdyJUXTcriv7k1oZeopqLah1QdyPsrpLujwS4WKaHPPDy7n99fsO+NsVYGICdC4JZtse4h5ZWxvfNIpAgMBAAECggEAC5g1Zxeaxc6aouDtCwZwbfQK89VtflU1XL112SJLGDAvXNAt/TkOTUm20QGRjdmjh8K84rMY1p43JLIIpgmKYGK0Vz/FnFXIjzeHdJe8vhhHJFgjjDU2QZeWSjXV08OoKYxoghGtdNoTZjV8dxg4TC73aNH7GzEIiISmJwkhefUZKimW9TTHaaWEGlOC1hOSPHF3unocUxdJAb0+KqBQfgP6iQDsC7rbsFB8GPL1X8Wk1LUtoIH3dZEOT6abceCLSbXpMaInlUwpuZIWFu4AMxDEr3d4UrbdDDE8Gxbkx2zURBUyHzSWTj78CnXzZ8aXsWQBSDWm2J6P0h+cpbbIJQKBgQD3hJqDC604+solOSA5JQekV6ZsqmTP786zxH6JKUKYE0Monj+LrUIyZxXoSTc5MPM5f+m8nzwCnSnBvwssOP0nLD0gODH8TM82aT60XPV9dECHmOf5ZNx66/xnyKUOAkHd2VcqTbkmE+KeTK21Ef4DahTb1MnjUPvHHuJQEz4ZRwKBgQDJDVR9XlFTu45dUrJfdwfWptMQliLwWvrzWwrvPlo9Dwr1y7kAQ9YR2O1E1Laof8uT/1+ZE12qMWb4lehZ1kcLGu78sx7AIVugGXdtoX0HKEcxsdCqwgi9kp5QxMmFHgCBtpqbJRRyFOYeezWEOmIBfahKE5AsRMvkJjkOhUdxDwKBgDbyvC59aB4vJrMuxGWLP/AO+UZEaY7z6Mplw8WFYGBMXmtk/ixu8TQpSGbB4j2TDVvldqlGHFdkNoHpfHx7xk9s9cDyWL5u+s1UffQicuk/5pdzrYPjDUNIokcT9vhuLdsnRb+Bc6ntXjDQSvvYm7B8W1cWHR+Wud5Rj+JA81c/AoGBAMbXGCilj2lwpWxGzYL9BhOVCkntMsDkOi1UbYwP7s9UgVJ3GZDZhm5ATOk2NzCOs5i7iKCsNoaMNXrFR/cRpVGmYZYq0M7ULvo4NZWHkU/y1fagFe/L1zkmOM1I0sWE1ptzLZUjJwVtBeo/kL4lv6gZzsb0cjJWGBCcfPGPfwihAoGBANcc+DUc+GgBhLk8+FBW26Zme1L++C3aAUkDslHATaKZB+N5Lgnx5TiADByaZ435QC8hIPSFyZ4/XJflcc6a7Zh6KG/DMtdecIP+0uRvcsQiybqHgPiFsPnLGrlp65X/v4Gxr2tUZJ+6qld3abb1b7BIUj3dBzziw6NuB6tNo32E";

	/** 我方的公钥**/
	private static String selfPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAwmQAvXZ2XgCvFgqarxYIzSUxLMEmsFhCqlPdl1s+DOt45Ow1iBdIhROWm8Kbb1hvr7oZX4xyEJKM/nCZg1HS8CggnGWyVgOYVFtWQheIZ4p2DbSt9sAL+MktXwi2bbou1raW64OJwoozO1OLND2FTGDl/I36LsTYN9/0MPCDLpuYkqNNnP5kypPTzw4z2/CW9YxtVZuEHsDAqnUYx+oNituc8HOa+04PrEdQDSrftUPq7NoGG1uF4BW61ihQlgs7IsXggfcHciVF03K4r+5NaGXqKai2odUHcj7K6S7o8EuFimhzzw8u5/fX7DvjbFWBiAnQuCWbbHuIeWVsb3zSKQIDAQAB";

	/** 创科中心分配给第三方应用的id**/
	private static String appSysId = "fhltest2d887f6e6d54478c82c7c3701";
	//private static String appSysId = "0f8550708fe64ff597100bb73755795b";


	private static RestTemplate restTemplate;

	public static void setUp() {
		restTemplate = new RestTemplate();
		// 解决RestTemplate包含中文时乱码问题
		restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
	}

	public static void testGetToken() {
		getToken();
	}

	public static void testRequestBizApi()
			throws Exception {
		String bizUrl = "http://localhost:8080/test/ok";

		// First Step: get token
		String token = getToken();
		log.info("------------>token:{}", token);

		// Next Step: request biz api with sign
		Map<String, String> params = new HashMap<String, String>();
		params.put("a", "1");
		params.put("d", "4");
		params.put("b", "2");
		params.put("c", "3");
		List<String> keys = new ArrayList(params.keySet());
		// 字母升序排列
		Collections.sort(keys);
		log.info("===============>字母升序排列的结果:{}", keys);

		String body = "{\"username\":\"fhl\",\"gender\":\"male\"}";

		long timestamp = System.currentTimeMillis() / 1000;
		StringBuffer sb = new StringBuffer();
		for (String key : keys) {
			if(StringUtils.isNotBlank(params.get(key))) {
				sb.append("&").append(key).append("=").append(params.get(key));
			}
		}
		if (!keys.isEmpty()) {
			sb.deleteCharAt(0);
		}

		bizUrl = bizUrl + "?" + sb.toString();
		log.info("----------->bizUrl:{}", bizUrl);

		sb.append(body).append(appSysId).append(token).append(timestamp + "");

		log.info("---------->拼接后的字符串:{}", sb.toString());
		byte[] data = sb.toString().getBytes();
		String sign = RSAEncrypt.signByPrivateKey(data, privateKey);

		log.info("---------->sign:{}", sign);

		HttpHeaders headersMap = new HttpHeaders();
		headersMap.add(HttpHeaders.CONTENT_TYPE, "application/json");
		headersMap.add(Constant.APPSYSID_HTTP_HEADER_KEY, appSysId);
		headersMap.add(Constant.TOKEN_HTTP_HEADER_KEY, token);
		headersMap.add(Constant.TIMESTAMP_HTTP_HEADER_KEY, timestamp + "");
		headersMap.add(Constant.SIGN_HTTP_HEADER_KEY, sign);

		HttpEntity httpEntity = new HttpEntity<>(body, headersMap);

		ResponseEntity<Object> responseEntity = restTemplate
				.exchange(bizUrl, HttpMethod.POST, httpEntity, new ParameterizedTypeReference<Object>() {
				});

		String responseBody = JsonUtil.obj2Json(responseEntity.getBody());
		log.info("---------->response body:{}", responseBody);
		HttpHeaders resHeaders = responseEntity.getHeaders();
		log.info("---------->response headers:{}", resHeaders);
		List<String> resSignList = Optional.ofNullable(resHeaders.get(Constant.SIGN_HTTP_HEADER_KEY))
				.orElse(Collections.emptyList());
		String resSign = !resSignList.isEmpty() ? resSignList.get(0) : null;
		log.info("---------->resSign:{}", resSign);
		boolean result = RSAEncrypt.verifyByPublicKey(responseBody.getBytes(), selfPublicKey, resSign);
		log.info("----------->result:{}", result?"ok!":"fail!");
	}

	private static String getToken() {
		String token = "xxxx";
		return token;
	}

	/** sonar **/
	@Test
	public void testOk() {
		log.info("ok!");
		Assert.assertTrue(true);
	}



	public static void main(String[] args) throws Exception {

		// 让fiddler能抓到java程序发起的网络请求,如果要配合fiddler调试就打开注释
		/*System.setProperty("http.proxyHost", "localhost");
		System.setProperty("http.proxyPort", "8888");
		System.setProperty("https.proxyHost", "localhost");
		System.setProperty("https.proxyPort", "8888");*/

		setUp();
		//getToken();
		testRequestBizApi();
	}

}
