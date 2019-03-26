package pl.myblog.springblog.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import pl.myblog.springblog.model.Post;
import pl.myblog.springblog.model.User;
import pl.myblog.springblog.repository.PostRepository;

import java.util.List;

@Service
public class PostService {


    PostRepository postRepository;
    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> getAllPosts(){
        return postRepository.findAll();
    }

    public Post getPostById(long id){
        return postRepository.getOne(id);
    }
    public void deletePostById(Long id){
        postRepository.deleteById(id);
    }
}
