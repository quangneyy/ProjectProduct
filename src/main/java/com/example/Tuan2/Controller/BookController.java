package com.example.Tuan2.Controller;

import com.example.Tuan2.entity.Book;
import com.example.Tuan2.services.BookService;
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
@RequestMapping("/books")
public class BookController {
    @Autowired
    private BookService bookService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public String showAllBooks(Model model) {
        List<Book> books = bookService.getAllBook();
        model.addAttribute("books", books);
        return "book/list";
    }

    @GetMapping("/add")
    public String addBookForm(Model model){
        model.addAttribute("book", new Book());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "book/add";
    }
    @PostMapping("/add")
    public String addBook(@Valid @ModelAttribute("book") Book book, BindingResult result){
        if (result.hasErrors()) {
            return "/book/add";
        } else {
            bookService.addBook(book);
            return "redirect:/books";
        }
    }

    @GetMapping("/edit/{id}")
    public String editBookForm(@PathVariable("id") Long id, Model model) {
        Optional<Book> editBook = bookService.getAllBooks().stream()
                .filter(book -> book.getId().equals(id))
                .findFirst();
        if (editBook.isPresent()) {
            model.addAttribute("book", editBook.get());
            model.addAttribute("categories", categoryService.getAllCategories());
            return "book/edit";
        } else {
            return "not-found";
        }
    }

//    @PostMapping("/add")
//    public String addBook(@Valid @ModelAttribute("book") Book book, BindingResult result){
//        if (result.hasErrors()) {
//            return "/book/add";
//        } else {
//            bookService.addBook(book);
//            return "redirect:/books";
//        }
//    }

    @PostMapping("/edit")
    public String editBook(@Valid @ModelAttribute("book") Book book, BindingResult result) {
        if (result.hasErrors()) {
            return "/book/edit";
        } else {
            bookService.updatedBook(book);
            return "redirect:/books";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable("id")Long id){
        Iterator<Book> iterator = bookService.getAllBooks().iterator();
        while (iterator.hasNext()){
            Book book = bookService.getBookById(id);
            if(book.getId() == id){
                bookService.deleteBook(id);
                break;
            }
        }
        return "redirect:/books";
    }
}