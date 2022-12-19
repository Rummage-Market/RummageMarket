package rummage.RummageMarket.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import rummage.RummageMarket.Config.Auth.PrincipalDetails;
import rummage.RummageMarket.Domain.Post.Post;
import rummage.RummageMarket.Domain.Post.PostRepository;
import rummage.RummageMarket.Handler.Ex.CustomException;
import rummage.RummageMarket.Handler.Ex.CustomValidationApiException;
import rummage.RummageMarket.Web.Dto.Post.PostUploadDto;

@Service
public class PostService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Transactional
    public void upload(MultipartFile file, PostUploadDto postUploadDto,
            @AuthenticationPrincipal PrincipalDetails principalDetails) {

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(file.getContentType());
        objectMetadata.setContentLength(file.getSize());

        String originalFilename = file.getOriginalFilename();// 김영광.jpg
        int index = originalFilename.lastIndexOf(".");// '.'이라는 문자가 발견되는 위치에 해당하는 index값(위치값) = 3
        String ext = originalFilename.substring(index + 1);// index + 1 = 4 -> jpg

        String storeFileName = UUID.randomUUID() + "." + ext;// uuid.jpg
        String key = "upload/" + storeFileName;

        try (InputStream inputStream = file.getInputStream()) {
            amazonS3Client.putObject(new PutObjectRequest(bucket, key, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String storeFileUrl = amazonS3Client.getUrl(bucket, key).toString();
        Post post = postUploadDto.toEntity(storeFileUrl, principalDetails.getUser());
        postRepository.save(post);
    }

    // 게시글 수정
    @Transactional
    public Post update(int postid, MultipartFile file, PostUploadDto postUploadDto,
            @AuthenticationPrincipal PrincipalDetails principalDetails) {

        Post post = postRepository.findById(postid).orElseThrow(() -> {
            throw new CustomValidationApiException("찾을 수 없는 게시글입니다.");
        });

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(file.getContentType());
        objectMetadata.setContentLength(file.getSize());

        String originalFilename = file.getOriginalFilename();// 김영광.jpg
        int index = originalFilename.lastIndexOf(".");// '.'이라는 문자가 발견되는 위치에 해당하는 index값(위치값) = 3
        String ext = originalFilename.substring(index + 1);// index + 1 = 4 -> jpg

        String storeFileName = UUID.randomUUID() + "." + ext;// uuid.jpg
        String key = "upload/" + storeFileName;

        try (InputStream inputStream = file.getInputStream()) {
            amazonS3Client.putObject(new PutObjectRequest(bucket, key, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String storeFileUrl = amazonS3Client.getUrl(bucket, key).toString();

        if (ext.isEmpty()) {
            post.setImageUrl(post.getImageUrl());
        } else {
            String originalKey = post.getImageUrl().substring(54);
            amazonS3Client.deleteObject(bucket, originalKey);
            post.setImageUrl(storeFileUrl);
        }

        post.setUser(principalDetails.getUser());
        post.setTitle(postUploadDto.getTitle());
        post.setContent(postUploadDto.getContent());
        post.setAddress1(postUploadDto.getAddress1());
        post.setAddress2(postUploadDto.getAddress2());
        post.setPlace(postUploadDto.getPlace());
        post.setItem(postUploadDto.getItem());
        post.setPrice(postUploadDto.getPrice());

        return post;
    }

    @Transactional(readOnly = true)
    public Page<Post> postList(Pageable pageable, int principalId) {

        Page<Post> posts = postRepository.findPostList(pageable);

        posts.forEach((post) -> {

            post.setInterestCount(post.getInterest().size());

            post.getInterest().forEach((interest) -> {
                if (interest.getUser().getId() == principalId) {
                    post.setInterestState(true);
                }
            });
        });
        return posts;
    }

    @Transactional(readOnly = true)
    public List<Post> popularPost() {

        List<Post> posts = postRepository.findPopularPostList();

        posts.forEach((post) -> {
            post.setInterestCount(post.getInterest().size());
            post.setCommentCount(post.getComments().size());
        });
        return posts;
    }

    @Transactional(readOnly = true)
    public Post detailpost(int principalId, int postId) {

        Post post = postRepository.findById(postId).orElseThrow(() -> {
            throw new CustomException("해당 게시글은 없는 게시글입니다.");
        });

        post.setInterestCount(post.getInterest().size());

        post.getInterest().forEach((interest) -> {
            if (interest.getUser().getId() == principalId) {
                post.setInterestState(true);
            }
        });

        return post;
    }

    @Transactional(readOnly = true)
    public Post findByPostId(int postId) {
        Post post = postRepository.findById(postId).orElseThrow(null);
        return post;
    }

    @Transactional(readOnly = true)
    public Page<Post> searchPostList(Pageable pageable, int principalId, String address1, String address2,
            String item) {

        Page<Post> searchedPosts = postRepository.searchPostList(pageable, address1, address2, item);

        searchedPosts.forEach((post) -> {

            post.setInterestCount(post.getInterest().size());

            post.getInterest().forEach((interest) -> {
                if (interest.getUser().getId() == principalId) {
                    post.setInterestState(true);
                }
            });
        });

        return searchedPosts;
    }
}