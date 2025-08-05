package com.miaoyu.barc.api.work.controller;

import com.miaoyu.barc.annotation.ApiPath;
import com.miaoyu.barc.api.work.service.WorkClaimService;
import com.miaoyu.barc.utils.J;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/work/claim")
public class WorkClaimController {
    @Autowired
    private WorkClaimService workClaimService;
    /**
     * 获取全部作品认领信息
     * @return ListL类型中包含全部作品认领信息*/
    @GetMapping("/all_claims")
    public ResponseEntity<J> getAllWorkClaimsControl(HttpServletRequest request) {
        return workClaimService.getAllClaimsControl(request.getAttribute("uuid").toString());
    }
    @GetMapping("/claims_by_work")
    public ResponseEntity<J> getClaimsByWorkControl(HttpServletRequest request, @RequestParam("work_id") String workId) {
        return workClaimService.getClaimsByWorkControl(request.getAttribute("uuid").toString(), workId);
    }
}
