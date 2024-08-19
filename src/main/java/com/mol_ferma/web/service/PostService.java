package com.mol_ferma.web.service;

import com.mol_ferma.web.dto.PostDto;
import com.mol_ferma.web.models.Post;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PostService {
    List<PostDto> findAllPosts();
    Post savePost(PostDto postDto);
    PostDto findPostById(Long postId);
    void updatePost(PostDto post);
    void delete(Long postId);
    List<PostDto> searchPosts(String title);
}
