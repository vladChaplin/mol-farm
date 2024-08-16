package com.mol_ferma.web.controller;

import com.mol_ferma.web.dto.PostDto;
import com.mol_ferma.web.models.Post;
import com.mol_ferma.web.service.GcsStorageService;
import com.mol_ferma.web.service.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class PostController {

    private final PostService postService;

    private final GcsStorageService gcsStorageService;

    @Autowired
    public PostController(PostService postService, GcsStorageService gcsStorageService) {
        this.postService = postService;
        this.gcsStorageService = gcsStorageService;
    }

    @GetMapping("/posts")
    public String listPosts(Model model) {
        List<PostDto> posts = postService.findAllPosts();
        model.addAttribute("posts", posts);
        return "posts-list";
    }

    @GetMapping("/posts/new")
    public String createPostForm(Model model) {
        PostDto post = new PostDto();
        model.addAttribute("post", post);
        return "posts-create";
    }

    @PostMapping("/posts/new")
    public String saveNewPost(@Valid @ModelAttribute("post") PostDto postDto,
                              BindingResult result,
                              Model model) {
//        if(postDto.getPhotoUrl().isEmpty()) {
//            result.addError(new FieldError("postDto", "photoUrl", "Необходимо выбрать изображение фермы!"));
//        }
        if(result.hasErrors()) {
            model.addAttribute("post", postDto);
            return "posts-create";
        }

        MultipartFile image = postDto.getPhotoFile();
        StringBuilder strBuilder = new StringBuilder(LocalDateTime.now().getSecond());
        var newFileName = strBuilder.append("_").append(image.getOriginalFilename()).toString();
        String fileUrl = "";

        try {
            File tempFile = File.createTempFile("upload-", newFileName);
            image.transferTo(tempFile);
            fileUrl = gcsStorageService.uploadFile(tempFile, newFileName);
        } catch (IOException e) {
            throw new RuntimeException("Error upload file Google Cloud Storage!" + e);
        }

        postDto.setPhotoUrl(fileUrl);
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
                             @ModelAttribute("post") PostDto postDto,
                             BindingResult result) {
        if(result.hasErrors()) {
            return "posts-edit";
        }



        postDto.setId(postId);
        postService.updatePost(postDto);
        return "redirect:/posts";
    }
}
