package com.zcore.mabokeserver.studiomaker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpSession;

//@CrossOrigin
@RestController
@RequestMapping("/studiomaker")
public class StudioMakerController {
    @Autowired
    private StudioMakerService service;
    private Logger logger = LoggerFactory.getLogger(StudioMakerService.class);

    /*@RequestMapping("/code")
    public String callBackUrl(HttpServletRequest request, HttpSession httpSession) {
        String code = request.getParameter("code");
        String accessDenied = request.getParameter("access_denied") == null ? "" : request.getParameter("access_denied");
        //String[] scopes = request.getParameter("scope").split(" ");
        logger.info("Code : %s, %s", code, accessDenied);
        //String error = request.getParameter("error") == null ? "" : request.getParameter("error");
        //if(!accessDenied.isBlank()) throw new  // Authorization from google failed
        //if(!error.isBlank()) throw new  // Authorization from google failed
        //if(code.isBlank()) throw new  // Authorization from google failed

        return null;
        //return service.getToken(token.getCode(), token.getScope());
    }*/

    @PostMapping
    public ResponseEntity<String> add(@RequestBody GToken token) throws Exception {
        return service.getToken(token.getCode(), token.getScope());
    }
    
    @GetMapping(value = "/files")
    public ResponseEntity<String> getFiles(@RequestBody GToken token) {
        return service.getFiles(token.getAccessToken());
    }
}
