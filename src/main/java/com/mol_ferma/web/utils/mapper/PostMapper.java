package com.mol_ferma.web.utils.mapper;

import com.mol_ferma.web.dto.PostDto;
import com.mol_ferma.web.models.Post;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PostMapper {
    public static Post mapToPost(PostDto postDto) {
        return Post.builder()
                .id(postDto.getId())
                .title(postDto.getTitle())
                .photoUrl(postDto.getPhotoUrl())
                .content(postDto.getContent())
                .createdOn(postDto.getCreatedOn())
                .address(postDto.getAddress())
                .region(postDto.getRegion())
                .createdBy(postDto.getCreatedBy())
                .build();
    }

    public static PostDto mapToPostDto(Post post) {
        PostDto postDto = PostDto.builder()
                .id(post.getId())
                .address(post.getAddress())
                .title(post.getTitle())
                .content(post.getContent())
                .photoUrl(post.getPhotoUrl())
                .createdOn(post.getCreatedOn())
                .updatedOn(post.getUpdatedOn())
                .region(post.getRegion())
                .createdBy(post.getCreatedBy())
                .build();
        return postDto;
    }

}
