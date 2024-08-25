package com.mol_ferma.web.service.impl;

import com.mol_ferma.web.dto.PostDto;
import com.mol_ferma.web.models.Post;
import com.mol_ferma.web.models.UserEntity;
import com.mol_ferma.web.repository.PostRepository;
import com.mol_ferma.web.repository.UserRepository;
import com.mol_ferma.web.security.SecurityUtil;
import com.mol_ferma.web.service.PostService;
import com.mol_ferma.web.utils.mapper.PostMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.mol_ferma.web.utils.mapper.PostMapper.mapToPost;
import static com.mol_ferma.web.utils.mapper.PostMapper.mapToPostDto;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<PostDto> findAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream()
                .map(PostMapper::mapToPostDto)
                .collect(Collectors.toList());
    }

    @Override
    public Post savePost(PostDto postDto) {
        String username = SecurityUtil.getSessionUser();
        UserEntity user = userRepository.findByEmail(username);
        Post post = mapToPost(postDto);
        post.setCreatedBy(user);

        return postRepository.save(post);
    }

    @Override
    public PostDto findPostById(Long postId) {
        Post post = postRepository.findById(postId).get();
        return mapToPostDto(post);
    }

    @Override
    public void updatePost(PostDto postDto) {
        String username = SecurityUtil.getSessionUser();
        UserEntity user = userRepository.findByEmail(username);
        Post post = mapToPost(postDto);
        post.setCreatedBy(user);

        postRepository.save(post);
    }

    @Override
    public void delete(Long postId) {
        postRepository.deleteById(postId);
    }

    @Override
    public List<PostDto> searchPosts(String title) {
        List<Post> posts = postRepository.searchPosts(title);
        return posts.stream().map(PostMapper::mapToPostDto).collect(Collectors.toList());
    }

}
