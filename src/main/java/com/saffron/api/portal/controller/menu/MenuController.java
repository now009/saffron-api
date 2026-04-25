package com.saffron.api.portal.controller.menu;

import com.saffron.api.portal.dto.common.ApiResponse;
import com.saffron.api.portal.dto.menu.MenuListDto;
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
    public ResponseEntity<List<MenuListDto>> list(
            @RequestParam(required = false) String menuId,
            @RequestParam(required = false) String menuName) {
        return ResponseEntity.ok(menuService.getMenus(menuId, menuName));
    }

    @GetMapping("/tree")
    public ResponseEntity<List<MenuTreeDto>> tree() {
        return ResponseEntity.ok(menuService.getMenuTree());
    }

    @GetMapping("/{menuId}")
    public ResponseEntity<?> get(@PathVariable String menuId) {
        return ResponseEntity.ok(Map.of("menuId", menuId));
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody Map<String, Object> body) {
        return ResponseEntity.ok(Map.of("result", "ok"));
    }

    @PostMapping("/update/{menuId}")
    public ResponseEntity<?> update(@PathVariable String menuId,
                                    @RequestBody Map<String, Object> body) {
        return ResponseEntity.ok(Map.of("result", "ok"));
    }

    @PostMapping("/delete/{menuId}")
    public ResponseEntity<ApiResponse> delete(@PathVariable String menuId) {
        return ResponseEntity.ok(menuService.deleteMenu(menuId));
    }

    @GetMapping("/parent-menus")
    public ResponseEntity<List<MenuListDto>> parentMenus() {
        return ResponseEntity.ok(menuService.getParentMenus());
    }

    @GetMapping("/next-id")
    public ResponseEntity<Map<String, String>> nextId() {
        return ResponseEntity.ok(Map.of("menuId", menuService.getNextMenuId()));
    }
}
