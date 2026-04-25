package com.saffron.api.portal.controller.menu;

import com.saffron.api.portal.dto.menu.MenuGroupDto;
import com.saffron.api.portal.dto.menu.MenuTreeDto;
import com.saffron.api.portal.service.menu.MenuService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/portal/menus")
public class MenuController {

    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @PostMapping("/list")
    public ResponseEntity<List<MenuGroupDto>> list() {
        return ResponseEntity.ok(menuService.getMenus());
    }

    @GetMapping("/tree")
    public ResponseEntity<List<MenuTreeDto>> tree() {
        return ResponseEntity.ok(menuService.getMenuTree());
    }

    @GetMapping("/{menuId}")
    public ResponseEntity<?> get(@PathVariable String menuId) {
        return ResponseEntity.ok(Map.of("menuId", menuId));
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Map<String, Object> body) {
        return ResponseEntity.ok(Map.of("result", "ok"));
    }

    @PutMapping("/{menuId}")
    public ResponseEntity<?> update(@PathVariable String menuId,
                                    @RequestBody Map<String, Object> body) {
        return ResponseEntity.ok(Map.of("result", "ok"));
    }

    @DeleteMapping("/{menuId}")
    public ResponseEntity<?> delete(@PathVariable String menuId) {
        return ResponseEntity.ok(Map.of("result", "ok"));
    }
}
