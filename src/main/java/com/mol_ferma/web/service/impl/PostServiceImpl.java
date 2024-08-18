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
                .map(this::mapToPostDto)
                .collect(Collectors.toList());
    }

    @Override
    public Post savePost(PostDto postDto) {
        return postRepository.save(mapToPost(postDto));
    }

    @Override
    public PostDto findPostById(Long postId) {
        Post post = postRepository.findById(postId).get();
        return mapToPostDto(post);
    }

    @Override
    public void updatePost(PostDto postDto) {
        Post post = mapToPost(postDto);
        postRepository.save(post);
    }

    @Override
    public void delete(Long postId) {
        postRepository.deleteById(postId);
    }

    private Post mapToPost(PostDto postDto) {
        return Post.builder()
                .id(postDto.getId())
                .title(postDto.getTitle())
                .photoUrl(postDto.getPhotoUrl())
                .content(postDto.getContent())
                .createdOn(postDto.getCreatedOn())
                .address(postDto.getAddress())
                .build();
    }


    private PostDto mapToPostDto(Post post) {
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
