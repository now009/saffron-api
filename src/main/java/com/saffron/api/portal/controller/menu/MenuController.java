package com.saffron.api.portal.controller.menu;

import com.saffron.api.portal.dto.common.ApiResponse;
import com.saffron.api.portal.dto.menu.MenuDto;
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
            @RequestParam(required = false) String menuName,
            @RequestParam(required = false) String site) {
        return ResponseEntity.ok(menuService.getMenus(menuId, menuName, site));
    }

    @GetMapping("/tree")
    public ResponseEntity<List<MenuTreeDto>> tree(
            @RequestParam(required = false) String site) {
        return ResponseEntity.ok(menuService.getMenuTree(site));
    }

    @GetMapping("/{menuId}")
    public ResponseEntity<?> get(@PathVariable String menuId) {
        return ResponseEntity.ok(Map.of("menuId", menuId));
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody MenuDto menuDto) {
        ApiResponse result = menuService.saveMenu(menuDto);
        if ("fail".equals(result.getMessageCode())) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.ok(menuService.getMenus(null, null, null));
    }

    @PostMapping("/update")
    public ResponseEntity<?> update(@RequestBody MenuDto menuDto) {
        ApiResponse result = menuService.updateMenu(menuDto);
        if ("fail".equals(result.getMessageCode())) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.ok(menuService.getMenus(null, null, null));
    }

    @PostMapping("/delete/{menuId}")
    public ResponseEntity<ApiResponse> delete(@PathVariable String menuId) {
        return ResponseEntity.ok(menuService.deleteMenu(menuId));
    }

    @GetMapping("/parent-menus")
    public ResponseEntity<List<MenuListDto>> parentMenus(
            @RequestParam(required = false) String site) {
        return ResponseEntity.ok(menuService.getParentMenus(site));
    }

    @GetMapping("/next-id")
    public ResponseEntity<Map<String, String>> nextId() {
        return ResponseEntity.ok(Map.of("menuId", menuService.getNextMenuId()));
    }
}
