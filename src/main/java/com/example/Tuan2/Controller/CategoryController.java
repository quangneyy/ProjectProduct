package com.example.Tuan2.Controller;

import com.example.Tuan2.entity.Book;
import com.example.Tuan2.entity.Category;
import com.example.Tuan2.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public String showAllCategories(Model model) {
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        return "category/list";
    }

    @GetMapping("/add")
    public String addCategoryForm(Model model){
        model.addAttribute("category", new Category());
        return "category/add";
    }
    @PostMapping("/add")
    public String addCategory(@Valid @ModelAttribute("category") Category category, BindingResult result){
        if (result.hasErrors()) {
            return "/category/add";
        } else {
            categoryService.addCategory(category);
            return "redirect:/categories";
        }
    }

    @GetMapping("/edit/{id}")
    public String editCategoryForm(@PathVariable("id") Long id, Model model) {
        Optional<Category> editCategory = categoryService.getAllCategories().stream()
                .filter(category -> category.getId().equals(id))
                .findFirst();
        if (editCategory.isPresent()) {
            model.addAttribute("category", editCategory.get());
            return "category/edit";
        } else {
            return "not-found";
        }
    }

    @PostMapping("/edit")
    public String editCategory(Category category) {
        categoryService.updatedCategory(category);
        return "redirect:/categories";
    }

    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable("id")Long id){
        Iterator<Category> iterator = categoryService.getAllCategories().iterator();
        while (iterator.hasNext()){
            Category category = categoryService.getCategoryById(id);
            if(category.getId() == id){
                categoryService.deleteCategory(id);
                break;
            }
        }
        return "redirect:/categories";
    }
}
