package pl.myblog.springblog.controller;

import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pl.myblog.springblog.model.Post;
import pl.myblog.springblog.model.PostCategory;
import pl.myblog.springblog.model.dto.PostDto;
import pl.myblog.springblog.service.PostService;
import pl.myblog.springblog.service.UserService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController {
    PostService postService;
    UserService userService;

    @Autowired
    public HomeController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @GetMapping("/")                                            // mapowany adres
    public String home(Model model, Authentication auth){                            // nazwa metody wywoływanej dla URL "/"
        List<Post> posts = postService.getAllPosts();
        List<Post> sortedPosts = posts
                .stream()                                                               // zamiana kolekcji na strumień
                .sorted((p1, p2) -> p2.getDate_added().compareTo(p1.getDate_added()))   // sortowanie po dacie DESC
                .collect(Collectors.toList());                                          // zapis do kolekcji posortowanych postów
        model.addAttribute("posts", sortedPosts);
        model.addAttribute("auth",auth);

        if (auth!=null){
            model.addAttribute("isAdmin", userService.isAdmin(auth));
            model.addAttribute("user", userService.getUserById(auth));
        }else {
            model.addAttribute("isAdmin",false);
        }
        return "index";         // nazwa zwracanego widoku HTML

    }
    @GetMapping("/allposts/{id}")
    public String getOnePost(
            @PathVariable("id") Long id,
            Model model,
            Authentication auth){
        Post post = postService.getPostById(id);
        model.addAttribute("post", post);
        model.addAttribute("auth",auth);
        return "post";
    }
    @GetMapping("/deletepost/{id}")
    public String deletePost(@PathVariable("id") Long id, Authentication auth, Model model){
        // usunięcie posta
        model.addAttribute("auth",auth);
        postService.deletePostById(id);
        return "redirect:/";
    }
    @GetMapping("/updatepost/{id}")
    public String updatePost(@PathVariable("id") Long id, Model model, Authentication auth){
        List<PostCategory> categories = new ArrayList<>(Arrays.asList(PostCategory.values()));
        Post post = postService.getPostById(id);
        model.addAttribute("post", post);
        model.addAttribute("categories", categories);
        model.addAttribute("auth",auth);
        return "updatePost";
    }
    @PostMapping("/allposts/{id}")
    public String updatedPost(@ModelAttribute @Valid Post post, Model model, Authentication auth){
        // zapis przez serwis
        Long id = post.getId();
        Post updatedPost = postService.updatePost(id, post);
        model.addAttribute("post", updatedPost);
        model.addAttribute("auth",auth);
        return "post";
    }

    @GetMapping("/addpost")
    public String addPost(Model model, Authentication auth){
        List<PostCategory> categories =
                new ArrayList<>(Arrays.asList(PostCategory.values()));
        model.addAttribute("post", new PostDto());
        model.addAttribute("categories", categories);
        model.addAttribute("auth",auth);
        return "addpostForm";
    }
    @PostMapping("/addpost")
    public String addPost(
            @ModelAttribute("post") @Valid PostDto postDto,
            BindingResult bindingResult,
            Authentication auth,
            Model model){
        if(bindingResult.hasErrors()){
            List<PostCategory> categories =
                    new ArrayList<>(Arrays.asList(PostCategory.values()));
            model.addAttribute("categories", categories);
            model.addAttribute("auth",auth);
            return "addpostForm";
        }
        // z obiekut auth -> spring framerork sprawdzam dane autoryzacji
        UserDetails principal = (UserDetails) auth.getPrincipal();
        String loggedEmail = principal.getUsername();
        // zapisz użytkownika do pola user z posta i utwórz posta
        System.out.println("Utworzono post: " +
                postService.createPostByUser(postDto,loggedEmail));
        model.addAttribute("auth",auth);
        return "redirect:/";
    }


    @GetMapping("/contact")
    public String contact(Model model, Authentication auth){
        model.addAttribute("auth",auth);
        return "contact";
    }
}
