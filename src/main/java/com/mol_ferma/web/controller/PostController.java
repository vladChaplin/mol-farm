package com.mol_ferma.web.controller;

import com.mol_ferma.web.dto.PostDto;
import com.mol_ferma.web.models.Post;
import com.mol_ferma.web.service.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class PostController {

    @Value("${file.storage.path-full.img}")
    private String fileStoragePathFull;
    @Value("${file.storage.path-project.img}")
    private String fileStoragePathImg;
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/posts")
    public String listPosts(Model model) {
        List<Post> posts = postService.findAllPosts();
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

        if(postDto.getPhotoUrl().isEmpty()) {
            result.addError(new FieldError("postDto", "photoUrl", "Необходимо выбрать изображение фермы!"));
        }

        if(result.hasErrors()) {
            model.addAttribute("post", postDto);
            return "posts-create";
        }


        MultipartFile image = postDto.getPhotoUrl();
        LocalDateTime localDateTime = LocalDateTime.now();
        StringBuilder storageFileName = new StringBuilder(fileStoragePathFull);
        storageFileName.append(localDateTime.getSecond())
                .append("_").append(image.getOriginalFilename());;

        try {
            Path uploadPath = Paths.get(storageFileName.toString());
            fileStoragePathImg += uploadPath.getFileName();

            if(!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            try(InputStream inputStream = image.getInputStream()) {
                Files.copy(inputStream, uploadPath, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        postService.savePost(postDto, fileStoragePathImg);
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
//        postService.updatePost(post);
        return "redirect:/posts";
    }
}
