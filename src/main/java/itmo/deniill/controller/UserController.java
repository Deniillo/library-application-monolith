package itmo.deniill.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import itmo.deniill.dao.model.User;
import itmo.deniill.dao.model.UserProfile;
import itmo.deniill.service.services.interfaces.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User Controller", description = "Operations related to users management")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @Operation(summary = "Create a new user",
            description = "Creates a new user and returns the created user",
            responses = {
                    @ApiResponse(responseCode = "201", description = "User created",
                            content = @Content(schema = @Schema(implementation = User.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input")
            })
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PostMapping("/with-profile")
    @Operation(summary = "Create user with profile",
            description = "Creates a new user with associated profile and returns the created user",
            responses = {
                    @ApiResponse(responseCode = "201", description = "User created with profile",
                            content = @Content(schema = @Schema(implementation = User.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input")
            })
    public ResponseEntity<User> createUserWithProfile(
            @RequestBody User user,
            @RequestParam @Parameter(description = "User profile data") UserProfile userProfile) {
        User createdUser = userService.saveUser(user, userProfile);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @GetMapping("/by-provider/{providerId}")
    @Operation(summary = "Get user by provider ID",
            description = "Returns a single user by their provider ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User found",
                            content = @Content(schema = @Schema(implementation = User.class))),
                    @ApiResponse(responseCode = "404", description = "User not found")
            })
    public ResponseEntity<User> getUserByProviderId(
            @Parameter(description = "Provider ID of the user", required = true)
            @PathVariable String providerId) {
        User user = userService.findByProviderId(providerId).orElseThrow(() -> new RuntimeException("cant find this user" + providerId));
        return ResponseEntity.ok(user);
    }

    @GetMapping("/by-email/{email}")
    @Operation(summary = "Get user by email",
            description = "Returns a single user by their email address",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User found",
                            content = @Content(schema = @Schema(implementation = User.class))),
                    @ApiResponse(responseCode = "404", description = "User not found")
            })
    public ResponseEntity<User> getUserByEmail(
            @Parameter(description = "Email address of the user", required = true)
            @PathVariable String email) {
        User user = userService.findByEmail(email).orElseThrow(() -> new RuntimeException("cant find this user" + email));
        return ResponseEntity.ok(user);
    }

    @PatchMapping("/update-last-login/{providerId}")
    @Operation(summary = "Update last login time",
            description = "Updates the last login timestamp for the user",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Last login updated"),
                    @ApiResponse(responseCode = "404", description = "User not found")
            })
    public ResponseEntity<Void> updateLastLogin(
            @Parameter(description = "Provider ID of the user", required = true)
            @PathVariable String providerId) {
        userService.updateLastLogin(providerId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{userId}")
    @Operation(summary = "Delete user by ID",
            description = "Deletes a user by their ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "User deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "User not found")
            })
    public ResponseEntity<Void> deleteUser(
            @Parameter(description = "ID of the user to delete", required = true)
            @PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleNotFoundException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
