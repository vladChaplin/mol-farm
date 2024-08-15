package com.mol_ferma.web.controller;

import com.mol_ferma.web.dto.PostDto;
import com.mol_ferma.web.models.Post;
import com.mol_ferma.web.service.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
    public String saveNewPost(@Valid @ModelAttribute("post") PostDto postDto,
                              BindingResult result,
                              Model model) {
        if(result.hasErrors()) {
            model.addAttribute("post", postDto);
            return "posts-create";
        }

        postService.savePost(postDto);
        return "redirect:/posts";
    }

    @GetMapping("/posts/{postId}/edit")
    public String editPostForm(@PathVariable("postId") Long postId, Model model) {
        PostDto post = postService.findPostById(postId);
        model.addAttribute("post", post);
        return "posts-edit";
    }

    @PostMapping("/posts/{postId}/edit")
    public String updatePost(@PathVariable("postId") Long postId,
                             @Valid
                             @ModelAttribute("post") PostDto post,
                             BindingResult result) {
        if(result.hasErrors()) {
            return "posts-edit";
        }

        post.setId(postId);
        postService.updatePost(post);
        return "redirect:/posts";
    }
}
