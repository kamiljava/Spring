package pl.myblog.springblog.controller;

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
import pl.myblog.springblog.model.Comment;
import pl.myblog.springblog.model.Post;
import pl.myblog.springblog.model.PostCategory;
import pl.myblog.springblog.model.User;
import pl.myblog.springblog.model.dto.CommentDto;
import pl.myblog.springblog.model.dto.PostDto;
import pl.myblog.springblog.service.PostService;
import pl.myblog.springblog.service.UserService;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class PostController {
    PostService postService;
    UserService userService;
    @Autowired
    public PostController(PostService postService, UserService userService) {
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
        //-------------ZBIÓR KATEGORII---------------------
        Set<PostCategory> tags = new HashSet<>();
        for(Post p : sortedPosts){
            tags.add(p.getCategory());
        }
        model.addAttribute("tags",tags);
        //--------------------------------------------------
        model.addAttribute("auth",auth);
        if(auth != null) {
            model.addAttribute("isAdmin", userService.isAdmin(auth));
            model.addAttribute("user", userService.getUserById(auth));
        } else {
            model.addAttribute("isAdmin", false);
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
        User user = auth != null ? userService.getUserById(auth) : new User();
        String loggedName = user.getName();
        model.addAttribute("loggedName",loggedName);
        model.addAttribute("isAdmin", auth != null ? userService.isAdmin(auth) : false);
        // wypisz komentarze dla danego posta
        List<Comment> comments = postService.getCommentByPostId(id);
        System.out.println("Komentarze: "+ comments);
        model.addAttribute("comments", comments);
        // dla zalogowanych przypisane jest imię
        CommentDto commmentDto = new CommentDto();
        if(auth != null){
            String name = userService.getUserById(auth).getName();
            commmentDto.setAuthor(name);
        }
        // obiekt comment do formularza
        model.addAttribute("comment", commmentDto);
        return "post";
    }
    @PostMapping("/allposts/{id}")
    public String updatedPost(@ModelAttribute @Valid Post post, Model model, Authentication auth){
        // zapis przez serwis
        Long id = post.getId();
        Post updatedPost = postService.updatePost(id, post);
        model.addAttribute("post", updatedPost);
        model.addAttribute("auth",auth);
        User user = auth != null ? userService.getUserById(auth) : new User();
        String loggedName = user.getName();
        model.addAttribute("loggedName",loggedName);
        model.addAttribute("isAdmin", auth != null ? userService.isAdmin(auth) : false);
        // wypisz komentarze dla danego posta
        List<Comment> comments = postService.getCommentByPostId(id);
        System.out.println("Komentarze: "+ comments);
        model.addAttribute("comments", comments);
        // dla zalogowanych przypisane jest imię
        CommentDto commmentDto = new CommentDto();
        if(auth != null){
            String name = userService.getUserById(auth).getName();
            commmentDto.setAuthor(name);
        }
        // obiekt comment do formularza
        model.addAttribute("comment", commmentDto);

        return "post";
    }
    @GetMapping("/deletecomment/{id}")
    public String deleteComment(@PathVariable("id") Long id){
        // wyszukaj komentarz po id
        Comment deletedComment = postService.getCommentById(id);
        // zwróć obiekt posta dla usuwanego komntarza
        Post post = deletedComment.getPost();
        // usuwanie komentarza
        postService.deleteCommentById(id);
        return "forward:/allposts/"+post.getId();
    }

    @PostMapping("/addcomment/{id}")
    public String addComment(@PathVariable("id") Long id_post,
                             @ModelAttribute("comment") @Valid CommentDto commentDto,
                             BindingResult bindingResult,
                             Model model,
                             Authentication auth){
        if(bindingResult.hasErrors()){
            model.addAttribute("auth",auth);
            List<Comment> comments = postService.getCommentByPostId(id_post);
            System.out.println("Komentarze: "+ comments);
            model.addAttribute("comments", comments);
            model.addAttribute("post",postService.getPostById(id_post));
            return "post";
        }
        // zapis komentarza przez serwis
        postService.addCommnetToPost(id_post, commentDto);
        return "redirect:/allposts/"+id_post;
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
}