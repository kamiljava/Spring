package pl.myblog.springblog.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.myblog.springblog.model.Comment;
import pl.myblog.springblog.model.Post;
import pl.myblog.springblog.model.User;
import pl.myblog.springblog.model.dto.CommentDto;
import pl.myblog.springblog.model.dto.PostDto;
import pl.myblog.springblog.repository.CommentRepository;
import pl.myblog.springblog.repository.PostRepository;
import pl.myblog.springblog.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {
    PostRepository postRepository;
    UserRepository userRepository;
    CommentRepository commentRepository;

    @Autowired
    public PostService(PostRepository postRepository, UserRepository userRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
    }

    // metoda zwracająca wszystkie posty
    public List<Post> getAllPosts(){
        return postRepository.findAll();
    }
    // metoda zwracająca post po id
    public Post getPostById(long id){
        return postRepository.getOne(id);
    }
    // metoda usuwająca posta po id
    public void deletePostById(Long id){
        postRepository.deleteById(id);
    }
    // metoda aktualizująca post w bazie danych
    public Post updatePost(Long id, Post post){
        Post updatePost = postRepository.getOne(id);
        updatePost.setTitle(post.getTitle());
        updatePost.setContent(post.getContent());
        updatePost.setCategory(post.getCategory());
        updatePost.setComments(updatePost.getComments());
        return postRepository.save(updatePost);
    }
    // metoda zapisująca post zalogowanego użytkownika w DB
    public Post createPostByUser(PostDto postDto, String email){
        // wyszukaj użytkownika po adresie email
        User user = userRepository.findByEmail(email);
        // Towrzymy obiekt posta
        Post post = new Post(
                postDto.getTitle(),
                postDto.getContent(),
                postDto.getCategory(),
                user
        );
        return postRepository.save(post);
    }
    public List<Comment> getCommentByPostId(Long id_post){
        // zwracamy obiekt Post po id
        Post post = postRepository.getOne(id_post);
        // wyszukaj komenatze dla obiektu posta
        List<Comment> comments = commentRepository.findByPost(post);
        // zwracamy posotrowaną listę komentarzy po dacie DESC
        return comments
                .stream()
                .sorted(Comparator.comparing(Comment::getDate_added).reversed())
                .collect(Collectors.toList());
    }
    public Comment addCommnetToPost(Long id_post, CommentDto commentDto){
        // wyszukaj post po id
        Post post = postRepository.getOne(id_post);
        // dodaj komentarz do posta
        Comment comment = new Comment(
                commentDto.getMessage(),
                commentDto.getAuthor(),
                post
        );
        post.addComment(comment);
        // zapisz komentarz w DB
        return commentRepository.save(comment);
    }
    public Comment getCommentById(Long id){
        return commentRepository.getOne(id);
    }
    public void deleteCommentById(Long id){
        commentRepository.deleteById(id);
    }
}