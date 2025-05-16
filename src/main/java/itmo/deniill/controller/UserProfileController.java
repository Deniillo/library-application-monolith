package itmo.deniill.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import itmo.deniill.dao.model.UserProfile;
import itmo.deniill.service.services.interfaces.UserProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user-profiles")
@Tag(name = "User Profile Controller", description = "Operations related to user profiles")
public class UserProfileController {

    private final UserProfileService userProfileService;

    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @PostMapping
    @Operation(summary = "Create a new user profile",
            description = "Creates a new user profile and returns the created profile",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Profile created",
                            content = @Content(schema = @Schema(implementation = UserProfile.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input")
            })
    public ResponseEntity<UserProfile> createProfile(@RequestBody UserProfile profile) {
        UserProfile createdProfile = userProfileService.saveProfile(profile);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProfile);
    }

    @PutMapping
    @Operation(summary = "Update a user profile",
            description = "Updates an existing user profile and returns the updated profile",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Profile updated",
                            content = @Content(schema = @Schema(implementation = UserProfile.class))),
                    @ApiResponse(responseCode = "404", description = "Profile not found"),
                    @ApiResponse(responseCode = "400", description = "Invalid input")
            })
    public ResponseEntity<UserProfile> updateProfile(@RequestBody UserProfile profile) {
        UserProfile updatedProfile = userProfileService.updateProfile(profile);
        return ResponseEntity.ok(updatedProfile);
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Get user profile by ID",
            description = "Returns a single user profile by user ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Profile found",
                            content = @Content(schema = @Schema(implementation = UserProfile.class))),
                    @ApiResponse(responseCode = "404", description = "Profile not found")
            })
    public ResponseEntity<UserProfile> getProfileById(
            @Parameter(description = "ID of the user to be obtained", required = true)
            @PathVariable Long userId) {
        try {
            UserProfile profile = userProfileService.findByUserId(userId);
            return ResponseEntity.ok(profile);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleNotFoundException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
