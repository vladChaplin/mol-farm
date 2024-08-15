package com.mol_ferma.web.service;

import com.mol_ferma.web.dto.PostDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PostService {
    List<PostDto> findAllPosts();
}
