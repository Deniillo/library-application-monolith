package itmo.deniill.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import itmo.deniill.dao.model.Role;
import itmo.deniill.dao.model.enums.RoleType;
import itmo.deniill.service.services.interfaces.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@Tag(name = "Role Management", description = "APIs для управления ролями пользователей")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    @Operation(summary = "Создать новую роль", description = "Создает новую роль в системе")
    @ApiResponse(responseCode = "201", description = "Роль успешно создана")
    public ResponseEntity<Role> createRole(@RequestBody Role role) {
        Role createdRole = roleService.createRole(role);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRole);
    }

    @GetMapping
    @Operation(summary = "Получить все роли", description = "Возвращает список всех доступных ролей")
    public ResponseEntity<List<Role>> findAllRoles() {
        List<Role> roles = roleService.findAllRoles();
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Найти роль по ID", description = "Возвращает роль по указанному идентификатору")
    @ApiResponse(responseCode = "404", description = "Роль не найдена")
    public ResponseEntity<Role> findRoleById(
            @PathVariable @Parameter(description = "ID роли") int id) {
        Role role = roleService.findRoleById(id);
        return role != null ? ResponseEntity.ok(role) : ResponseEntity.notFound().build();
    }

    @GetMapping("/search")
    @Operation(summary = "Найти роль по имени", description = "Возвращает роль по указанному типу")
    @ApiResponse(responseCode = "404", description = "Роль не найдена")
    public ResponseEntity<Role> findRoleByName(
            @RequestParam @Parameter(description = "Тип роли") RoleType name) {
        Role role = roleService.findRoleByName(name);
        return role != null ? ResponseEntity.ok(role) : ResponseEntity.notFound().build();
    }

    @PutMapping
    @Operation(summary = "Обновить роль", description = "Обновляет информацию о существующей роли")
    @ApiResponse(responseCode = "404", description = "Роль не найдена")
    public ResponseEntity<Role> updateRole(@RequestBody Role role) {
        Role updatedRole = roleService.updateRole(role);
        return updatedRole != null ? ResponseEntity.ok(updatedRole) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить роль", description = "Удаляет роль по указанному идентификатору")
    @ApiResponse(responseCode = "204", description = "Роль успешно удалена")
    @ApiResponse(responseCode = "404", description = "Роль не найдена")
    public ResponseEntity<Void> deleteRole(
            @PathVariable @Parameter(description = "ID роли для удаления") int id) {
        if (roleService.findRoleById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        roleService.deleteRole(id);
        return ResponseEntity.noContent().build();
    }
}