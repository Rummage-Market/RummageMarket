package rummage.RummageMarket.Service;

import java.awt.Image;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import rummage.RummageMarket.Config.Auth.PrincipalDetails;
import rummage.RummageMarket.Domain.Post.Post;
import rummage.RummageMarket.Domain.Post.PostRepository;
import rummage.RummageMarket.Web.Dto.Post.PostUploadDto;

@Service
public class PostService {
	
	@Autowired
	PostRepository postRepository;
	
	@Value("${file.path}")
	private String uploadFolder;
	
	public void upload(PostUploadDto postUploadDto,@AuthenticationPrincipal PrincipalDetails principalDetails) {
		UUID uuid = UUID.randomUUID(); //랜덤의 숫자생성
		String imageFileName = uuid+"_"+postUploadDto.getFile().getOriginalFilename(); //사진이름을 최대한 중복이 안되게끔 처리
		System.out.println("서비스 호출");
		Path imageFilePath = Paths.get(uploadFolder+imageFileName);
		
		try {
			Files.write(imageFilePath,postUploadDto.getFile().getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Post post= postUploadDto.toEntity(imageFileName, principalDetails.getUser());
		postRepository.save(post);
	}

	@Transactional(readOnly = true)//영속성 컨텍스트 변경 감지를 해서, 더티체킹, flush(반영)
    public List<Post> postList(){
        List<Post> posts= postRepository.findAllIdDesc();
        return posts;
    }
}