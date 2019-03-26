package pl.myblog.springblog.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.myblog.springblog.model.Post;
import pl.myblog.springblog.repository.PostRepository;
import pl.myblog.springblog.repository.UserRepository;

import java.util.List;

@Service
public class PostService {
    PostRepository postRepository;
    UserRepository userRepository;
    @Autowired
    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
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
        return postRepository.save(updatePost);
    }

}