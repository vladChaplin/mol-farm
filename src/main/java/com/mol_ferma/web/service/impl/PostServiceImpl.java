package com.mol_ferma.web.service.impl;

import com.mol_ferma.web.dto.PostDto;
import com.mol_ferma.web.models.Post;
import com.mol_ferma.web.repository.PostRepository;
import com.mol_ferma.web.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public List<Post> findAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts;
    }

    @Override
    public Post savePost(PostDto postDto, String photoUrl) {
        return postRepository.save(mapToPost(postDto, photoUrl));
    }

    @Override
    public PostDto findPostById(Long postId) {
        Post post = postRepository.findById(postId).get();
        return mapToPostDto(post);
    }

    @Override
    public void updatePost(PostDto postDto, String photoUrl) {
        Post post = mapToPost(postDto, photoUrl);
        postRepository.save(post);
    }

    private Post mapToPost(PostDto postDto, String photoUrl) {
        return Post.builder()
                .id(postDto.getId())
                .title(postDto.getTitle())
                .photoUrl(photoUrl)
                .content(postDto.getContent())
                .address(postDto.getAddress())
                .updatedOn(postDto.getUpdatedOn())
                .build();
    }


    private PostDto mapToPostDto(Post post) {
        PostDto postDto = PostDto.builder()
                .id(post.getId())
                .address(post.getAddress())
                .title(post.getTitle())
                .content(post.getContent())
                .createdOn(post.getCreatedOn())
                .updatedOn(post.getUpdatedOn())
                .build();
        return postDto;
    }
}
