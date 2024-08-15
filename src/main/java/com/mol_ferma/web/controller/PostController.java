package com.mol_ferma.web.controller;

import com.mol_ferma.web.dto.PostDto;
import com.mol_ferma.web.models.Post;
import com.mol_ferma.web.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/posts")
    public String listPosts(Model model) {
        List<PostDto> posts = postService.findAllPosts();
        model.addAttribute("posts", posts);
        return "posts-list";
    }

    @GetMapping("/posts/new")
    public String createPostForm(Model model) {
        Post post = new Post();
        model.addAttribute("post", post);
        return "posts-create";
    }

    @PostMapping("/posts/new")
    public String saveNewPost(@ModelAttribute("post") Post post)  {
        postService.savePost(post);
        return "redirect:/posts";
    }
}
