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
    public List<PostDto> findAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream()
                .map(this::mapToPost)
                .collect(Collectors.toList());
    }

    @Override
    public Post savePost(Post post) {
        return postRepository.save(post);
    }

    private PostDto mapToPost(Post post) {
        PostDto postDto = PostDto.builder()
                .id(post.getId())
                .address(post.getAddress())
                .title(post.getTitle())
                .content(post.getContent())
                .photoUrl(post.getPhotoUrl())
                .createdOn(post.getCreatedOn())
                .updatedOn(post.getUpdatedOn())
                .build();
        return postDto;
    }
}
