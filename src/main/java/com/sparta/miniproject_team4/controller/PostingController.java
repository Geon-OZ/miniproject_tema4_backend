package com.sparta.miniproject_team4.controller;

import com.sparta.miniproject_team4.dto.PostingDto;
import com.sparta.miniproject_team4.model.Posting;
import com.sparta.miniproject_team4.repository.PostingRepository;
import com.sparta.miniproject_team4.security.UserDetailsImpl;
import com.sparta.miniproject_team4.service.PostingService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class PostingController {

    private final PostingService postingService;
    private final PostingRepository postingRepository;

    //포스팅 글 작성
    @PostMapping("/api/posting")
    public Posting createPosting(@RequestBody PostingDto postingDto,
                                    @AuthenticationPrincipal UserDetailsImpl userDetails){
        if (userDetails == null){
            throw new NullPointerException("로그인 후 사용 가능합니다");
        }else {
            Long userId = userDetails.getUser().getId();
            Posting posting = postingService.createPosting(postingDto, userId);
            return posting;
        }

    }

    // 포스팅 전체 조회
    @GetMapping("/api/posting")
    public List<Posting> getAllPosting(){
        return postingRepository.findAllByOrderByCreatedAtDesc();
    }

    // 포스팅  디테일 조회
    @GetMapping("/api/posting/{id}")
    public Posting getPosting(@PathVariable Long id){
        Posting posting = postingRepository.findById(id).orElseThrow(
                ()->new NullPointerException("해당글이 존재하지 않습니다.")
        );
        return posting;
    }


    // 마이페이지 포스팅 조회
    @GetMapping("/api/myposting/{userId}")
    public List<Posting> getMyPosting(@PathVariable Long userId){

        return postingRepository.findAllByUserId(userId);
    }


    // 포스팅 수정
    @PutMapping("/api/posting/{id}")
    public Long updatePosting(@PathVariable Long id, @RequestBody PostingDto postingDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        Long currentUserId = userDetails.getUser().getId();
        Optional<Posting> posting = postingRepository.findById(id);
        Long postingUserId = posting.get().getUserId();

        if (!Objects.equals(currentUserId, postingUserId)){
            throw new NullPointerException("본인이 작성한 글만 수정이 가능합니다.");
        }
        postingService.update(id, postingDto);
        return id;
    }


    // 포스팅삭제
    @DeleteMapping("/api/posting/{id}")
    public Long deletePosting(@PathVariable Long id,@AuthenticationPrincipal UserDetailsImpl userDetails){
        Long currentUserId = userDetails.getUser().getId();
        Optional<Posting> posting = postingRepository.findById(id);
        Long postingUserId = posting.get().getUserId();

        if (!Objects.equals(currentUserId, postingUserId)){
            throw new NullPointerException("본인이 작성한 글만 삭제 가능합니다.");
        }
        postingRepository.deleteById(id);
        return id;
    }


}
