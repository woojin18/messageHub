package kr.co.uplus.cloud.project.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.uplus.cloud.project.service.ChannelService;
import kr.co.uplus.cloud.common.dto.RestResult;
import kr.co.uplus.cloud.utils.CommonUtils;

@RestController
@RequestMapping("/projectApi/channel")
public class ChannelController {

	@Autowired
	ChannelService channelService;

	@PostMapping("/selectRcsBrandList")
	public RestResult<?> selectRcsBrandList(@RequestBody Map<String, Object> params, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// commonutils에 집어 넣을것
		int paging = Integer.parseInt(CommonUtils.getString(params.get("paging")));
		int rows = Integer.parseInt(CommonUtils.getString(params.get("rows")));

		int rowsFront = (paging - 1) * rows;

		params.put("rowsFront", rowsFront);
		params.put("rowsEnd", rows);

		return channelService.selectRcsBrandList(params);
	}
}
