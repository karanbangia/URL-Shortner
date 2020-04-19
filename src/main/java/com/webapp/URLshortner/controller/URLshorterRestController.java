package com.webapp.URLshortner.controller;

import com.webapp.URLshortner.model.URLs;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class URLshorterRestController {
    private Map<String, URLs> shortURLList = new HashMap<>();

    @PostMapping("/shortenurl")
    public ResponseEntity<Object> getShortenUrl(@RequestBody URLs shortenUrl) throws MalformedURLException {
        String randomChar = getRandomChars();
        setShortUrl(randomChar, shortenUrl);
        return new ResponseEntity<Object>(shortenUrl, HttpStatus.OK);
    }

    @GetMapping("/s/{randomstring}")
    public void getFullUrl(HttpServletResponse response, @PathVariable("randomstring") String randomString) throws IOException {
        response.sendRedirect(shortURLList.get(randomString).getFull_url());
    }

    private void setShortUrl(String randomChar, URLs shortenUrl) throws MalformedURLException {
        shortenUrl.setShort_url("http://localhost:8080/s/" + randomChar);
        shortURLList.put(randomChar, shortenUrl);
    }

    private String getRandomChars() {
        String randomStr = "";
        String possibleChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        for (int i = 0; i < 5; i++) {
            randomStr += possibleChars.charAt((int) Math.floor(Math.random() * possibleChars.length()));
        }
        return randomStr;
    }

}
