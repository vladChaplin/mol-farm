package com.mol_ferma.web.controller;

import com.mol_ferma.web.dto.PostDto;
import com.mol_ferma.web.dto.RegionDto;
import com.mol_ferma.web.models.Region;
import com.mol_ferma.web.repository.UserRepository;
import com.mol_ferma.web.service.GcsStorageService;
import com.mol_ferma.web.service.PostService;
import com.mol_ferma.web.service.RegionService;
import com.mol_ferma.web.utils.FileUploadUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class PostController {

    private final PostService postService;

    private final RegionService regionService;

    private final UserRepository userRepository;

    private final GcsStorageService gcsStorageService;

    @Autowired
    public PostController(PostService postService,
                          RegionService regionService,
                          UserRepository userRepository,
                          GcsStorageService gcsStorageService) {
        this.postService = postService;
        this.regionService = regionService;
        this.userRepository = userRepository;
        this.gcsStorageService = gcsStorageService;
    }

    @GetMapping("/posts")
    public String listPosts(HttpServletRequest request,
            Model model) {
        List<PostDto> posts = postService.findAllPosts();

        model.addAttribute("posts", posts);
        model.addAttribute("currentURI", request.getRequestURI());

        return "posts-list";
    }

    @GetMapping("/posts/search")
    public String searchPost(@RequestParam(value = "title") String title, Model model) {
        List<PostDto> posts = postService.searchPosts(title);
        model.addAttribute("posts", posts);
        return "posts-list";
    }

    @GetMapping("/posts/{postId}")
    public String postDetail(@PathVariable("postId") Long postId, Model model) {
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

        PostDto postDto = postService.findPostById(postId);
        Region region = postDto.getRegion();
        String formattedDate = postDto.getUpdatedOn().format(formatterDate);

        model.addAttribute("post", postDto);
        model.addAttribute("region", region);
        model.addAttribute("formattedDate", formattedDate);

        return "posts-detail";
    }

    @GetMapping("/posts/new")
    public String createPostForm(Model model) {
        PostDto post = new PostDto();
        List<RegionDto> regions = regionService.findAllRegions();

        model.addAttribute("post", post);
        model.addAttribute("regions", regions);

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

        MultipartFile image = postDto.getPhotoFile();
        String fileUrl = FileUploadUtil.uploadFileToGCS(image, gcsStorageService);

        postDto.setPhotoUrl(fileUrl);
        postService.savePost(postDto);

        return "redirect:/posts";
    }

    @GetMapping("/posts/{postId}/edit")
    public String editPostForm(@PathVariable("postId") Long postId, Model model) {
        PostDto post = postService.findPostById(postId);
        List<RegionDto> regions = regionService.findAllRegions();

        model.addAttribute("post", post);
        model.addAttribute("regions", regions);
        return "posts-edit";
    }

    @PostMapping("/posts/{postId}/edit")
    public String updatePost(@PathVariable("postId") Long postId,
                             @Valid
                             @ModelAttribute("post") PostDto postDto,
                             BindingResult result,
                             Model model) {
        if(result.hasErrors()) {
            model.addAttribute("post", postDto);
            return "posts-edit";
        }

        if(!postDto.isNullPhotoFile()) {

            MultipartFile image = postDto.getPhotoFile();

            if(!postDto.isNullPhotoUrl()) {
                gcsStorageService.deleteFile(postDto.getPhotoUrl());
            }

            String fileUrl = FileUploadUtil.uploadFileToGCS(image, gcsStorageService);

            postDto.setPhotoUrl(fileUrl);
        }

        postDto.setId(postId);
        postService.updatePost(postDto);
        return "redirect:/posts";
    }

    @GetMapping("/posts/{postId}/delete")
    public String deletePost(@PathVariable("postId") Long postId, Model model) {
        PostDto postDto = postService.findPostById(postId);
        boolean flag = gcsStorageService.deleteFile(postDto.getPhotoUrl());

        if(flag) System.out.println("ФАЙЛ УСПЕШНО УДАЛЁН НА GOOGLE CLOUD STORAGE");

        postService.delete(postId);
        return "redirect:/posts";
    }
}
