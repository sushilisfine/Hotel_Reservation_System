package com.myhotel.feignservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.myhotel.feign.dto.response.GuestResponseDTO;

@FeignClient(name = "guest-service")
public interface GuestFeignService {

	@GetMapping("api/v1/guests/{guest_id}")
	GuestResponseDTO getGuestById(@PathVariable("guest_id") Long guestId);

}
