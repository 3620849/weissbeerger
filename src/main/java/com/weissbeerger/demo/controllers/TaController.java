package com.weissbeerger.demo.controllers;

import com.weissbeerger.demo.model.taModel.Client;
import com.weissbeerger.demo.model.taModel.MyIp;
import com.weissbeerger.demo.model.taModel.Status;
import com.weissbeerger.demo.services.taServices.ClientService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;
@RestController
public class TaController {
    @Autowired
    private ClientService clientService;

    @PatchMapping("/clients/{clientId}/keepAlive")
    public ResponseEntity<HttpStatus> keepAlive(@PathVariable("clientId")String clientId){

        boolean isUpdated = clientService.setStatus(clientId, Status.ONLINE);
        if(!isUpdated){
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
        return new ResponseEntity(HttpStatus.OK);
    }
    @PostMapping("/clients")
    public ResponseEntity addNewClient(@RequestBody Client client, @RequestHeader HttpHeaders headers){

        clientService.addClient(client,headers);
        return new ResponseEntity(HttpStatus.OK);
    }
    @GetMapping("/clients")
    public ResponseEntity<List<Client>>getClientList(){

        List<Client> clients = clientService.getClients();
        return new ResponseEntity<List<Client>>(clients, HttpStatus.OK);
    }
    @GetMapping("/myIp")
    public ResponseEntity<MyIp>getIp(){

        MyIp ip = new MyIp();
        ip.setIp(getClientIpAddressIfServletRequestExist());
        return new ResponseEntity<MyIp>(ip, HttpStatus.OK);
    }

    private static final String[] IP_HEADER_CANDIDATES = {
            "X-Forwarded-For",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_X_FORWARDED_FOR",
            "HTTP_X_FORWARDED",
            "HTTP_X_CLUSTER_CLIENT_IP",
            "HTTP_CLIENT_IP",
            "HTTP_FORWARDED_FOR",
            "HTTP_FORWARDED",
            "HTTP_VIA",
            "REMOTE_ADDR"
    };

    public static String getClientIpAddressIfServletRequestExist() {

        if (RequestContextHolder.getRequestAttributes() == null) {
            return "0.0.0.0";
        }

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        for (String header: IP_HEADER_CANDIDATES) {
            String ipList = request.getHeader(header);
            if (ipList != null && ipList.length() != 0 && !"unknown".equalsIgnoreCase(ipList)) {
                String ip = ipList.split(",")[0];
                return ip;
            }
        }

        return request.getRemoteAddr();
    }
}
