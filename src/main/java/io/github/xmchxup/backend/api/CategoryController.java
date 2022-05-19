package io.github.xmchxup.backend.api;

import io.github.xmchxup.backend.exception.http.ParameterException;
import io.github.xmchxup.backend.model.Category;
import io.github.xmchxup.backend.repository.CategoryRepository;
import io.github.xmchxup.backend.service.PinService;
import io.github.xmchxup.backend.vo.PinPureVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author huayang (sunhuayangak47@gmail.com)
 */
@Api(tags = "分类模块")
@RestController
@RequestMapping("/api/category")
public class CategoryController {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private PinService pinService;

    @GetMapping("/all")
    @PreAuthorize("hasRole('USER')")
    @ApiOperation("获取所有分类")
    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }

    @GetMapping("/{key}/pins")
    @PreAuthorize("hasRole('USER')")
    @ApiOperation("通过key获取Pins")
    public List<PinPureVo> getPinsByKey(@PathVariable String key) {
        Category category = categoryRepository.findByKey(key).orElseThrow(() -> new ParameterException(40003));
        return pinService.getPinsByCategoryId(category.getId());
    }

}
