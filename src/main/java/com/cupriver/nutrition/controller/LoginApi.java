package com.cupriver.nutrition.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Dummy end-point to ensure the login and logout methods are documented in Swagger.
 * 
 * @author Chandra Prakash (www.cupriver.com)
 *
 */
@Api("Authentication")
@RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class LoginApi {
    /**
     * Implemented by Spring Security
     */
    @ApiOperation(value = "Login", notes = "Login with the given credentials.")
    @ApiResponses({@ApiResponse(code = 200, message = "")})
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    void login(
        @ApiParam @RequestParam("username") String username,
        @ApiParam @RequestParam("password") String password
    ) {
        throw new IllegalStateException("Add Spring Security to handle authentication");
    }

    /**
     * Implemented by Spring Security
     */
    @ApiOperation(value = "Logout", notes = "Logout the current user.")
    @ApiResponses({@ApiResponse(code = 200, message = "")})
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
     void logout() {
        throw new IllegalStateException("Add Spring Security to handle authentication");
    }
}