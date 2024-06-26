package com.rxkj.controller;

import com.rxkj.common.R;
import com.rxkj.entity.ControlMessage;
import com.rxkj.entity.bo.MeiFenUser;
import com.rxkj.entity.bo.SamplerGroup;
import com.rxkj.message.StatusMessage;
import com.rxkj.server.handler.AlexForDTUHandler;
import com.rxkj.service.PlcService;
import com.rxkj.util.AlexUtil;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Slf4j
@EnableAsync
@RestController
@RequestMapping("api")
public class ApiController {

    @Autowired
    private PlcService plcService;

    @PostMapping("/commandToPlc")
    public R<StatusMessage> hello(@AuthenticationPrincipal MeiFenUser meiFenUser, HttpServletRequest request, @RequestBody ControlMessage controlMessage) {
        // 调用service将command转发给channel
        plcService.controller(controlMessage, meiFenUser);
        return R.success(new StatusMessage("01", "02", "02"));
    }
    @PostMapping("/batchSample")
    public R batchSample(@AuthenticationPrincipal MeiFenUser meiFenUser, HttpServletRequest request,@RequestBody List<SamplerGroup> groupList){
        CompletableFuture.runAsync(() -> {
            plcService.batchSample(groupList, meiFenUser);
        });
        return R.success();
    }
    @RequestMapping("/alex")
    public String helloAlex() {
        return "hello alex!";
    }

    @RequestMapping("/onOrOff")
    public Map<String, Object> onoroff(@RequestBody Map mapx) {
        String id = (String) mapx.get("id");
        String zhiling = (String) mapx.get("zhiling");
        Map<String, Object> map = new HashMap<>();
        System.out.println("id: " + id);
        System.out.println("zhiling: " + zhiling);
        if ("alex01".equals(id)) {
            ChannelHandlerContext ctx = AlexForDTUHandler.ctxMap.get(id);
            ctx.write(AlexUtil.hexStringToByteArray(zhiling));
            ctx.flush();
            map.put("res", 1);
        } else {
            map.put("res", 0);
        }
        return map;
    }

    @RequestMapping("/pcForControl")
    public Map<String, Object> pcForControl(@RequestParam("id") String id, @RequestParam("zhiling") String zhiling) {
        Map<String, Object> map = new HashMap<>();
        System.out.println("id: " + id);
        System.out.println("zhiling: " + zhiling);
        if ("alex01".equals(id)) {
            ChannelHandlerContext ctx = AlexForDTUHandler.ctxMap.get(id);
            ctx.write(AlexUtil.hexStringToByteArray(zhiling));
            ctx.flush();
            map.put("res", 1);
        } else {
            map.put("res", 0);
        }
        return map;
    }


}
