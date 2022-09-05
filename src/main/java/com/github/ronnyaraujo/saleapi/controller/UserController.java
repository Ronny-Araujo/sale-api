package com.github.ronnyaraujo.saleapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.ronnyaraujo.saleapi.model.UserModel;
import com.github.ronnyaraujo.saleapi.service.UserService;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.servers.ServerVariable;
import io.swagger.v3.oas.annotations.tags.Tag;

@OpenAPIDefinition(info = @Info(title = "SALE API", 
                    description = "Sales and user registration", 
                    version = "1.0", 
                    contact = @Contact(name = "Ronny Araujo", 
                    url = "https://github.com/Ronny-Araujo", 
                    email = "https://github.com/Ronny-Araujo")), 
                    servers = @Server(url = "http://localhost:8081/", 
                    variables = {
                        @ServerVariable(name = "serverUrl", defaultValue = "localhost"),
                        @ServerVariable(name = "serverHttpPort", defaultValue = "8081")
}))
@RestController
@RequestMapping("sale-api/users")
@Tag(description = "Provide access to availabe user data", name = "User Controller")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "Get users", description = "Provides all available users list")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "${api.response-codes.ok}")})
    @GetMapping
    public ResponseEntity<List<UserModel>> getUsers() throws Exception{
        List<UserModel> users = userService.getUsers();
        return ResponseEntity.ok().body(users);
    }

    @Operation(summary = "Get user", description = "Provides available user")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "${api.response-codes.ok}")})
    @GetMapping(value = "/{id}")
    public ResponseEntity<UserModel> findById(@PathVariable Long id) throws Exception{
        UserModel obj = userService.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @Operation(summary = "Create user", description = "Create new user")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "${api.response-codes.created}")})
    @PostMapping
    public ResponseEntity<UserModel> createUser(@RequestBody UserModel userModel) {
        UserModel userCreated = userService.createUser(userModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
    }

    @Operation(summary = "Update user", description = "Update user")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "${api.response-codes.ok}")})
    @PutMapping(value = "/{id}")
    public ResponseEntity<UserModel> updateUser(@PathVariable Long id, @RequestBody UserModel userModel) {
        UserModel user = userService.updateUser(id, userModel);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @Operation(summary = "Delete user", description = "Delete user")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "${api.response-codes.noContent}")})
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) throws Exception{
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
