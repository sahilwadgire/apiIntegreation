package com.authentication.Controller;

import com.authentication.Request.Customer;
import com.authentication.Request.LoginRequest;
import com.authentication.entity.Token;
import com.authentication.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
public class HomeController
{
    @Autowired
    TokenService tokenService;
    @RequestMapping("/")
    public String getLoginPage()
    {
        return "login";
    }

    @RequestMapping("/getAddCustomer")
    public String getAddCustomer()
    {
        return "addCustomer";
    }

    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public ResponseEntity<String> submit()
    {
        String url = "https://qa2.sunbasedata.com/sunbase/portal/api/assignment_auth.jsp";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create the request body
        LoginRequest requestBody = new LoginRequest();
        requestBody.setLogin_id("test@sunbasedata.com");
        requestBody.setPassword("Test@123");

        // Set the request body and headers
        HttpEntity<LoginRequest> requestEntity = new HttpEntity<>(requestBody, headers);

        // Define the URL
        String apiUrl = "https://qa2.sunbasedata.com/sunbase/portal/api/assignment_auth.jsp";

        // Send the POST request and get the response
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(
                apiUrl,
                requestEntity,
                String.class
        );
        System.out.println("Received Send: " + requestEntity);
        // Get the response body
        String responseBody = responseEntity.getBody();
        Token token = new Token();
        token.setAccessToken(responseBody);
        tokenService.addToDB(token);
        // Do something with the response
        System.out.println("Received response: " + responseBody);
        return responseEntity;
    }

    @RequestMapping(value = "/addCustomer", method = RequestMethod.POST)
    public ResponseEntity<String> create(@RequestBody Customer customer)
    {
        System.out.println(customer);
//        System.out.println(token.getAccessToken());
        String url = "https://qa2.sunbasedata.com/sunbase/portal/api/assignment.jsp";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Token token = new Token();
        List<Token> list = tokenService.findAll();
        headers.set("Authorization", "Bearer " + list.get(1));
        headers.add("cmd", "create");

        // Create the request body


        // Set the request body and headers
        HttpEntity<Customer> requestEntity = new HttpEntity<>(customer, headers);


        // Send the POST request and get the response
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(
                url,
                requestEntity,
                String.class
        );
        System.out.println("Received Send: " + requestEntity);
        // Get the response body
        String responseBody = responseEntity.getBody();
        System.out.println("Received response: " + responseBody);
        return responseEntity;
    }
}
