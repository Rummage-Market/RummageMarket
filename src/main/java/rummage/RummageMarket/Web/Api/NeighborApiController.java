package rummage.RummageMarket.Web.Api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import rummage.RummageMarket.Config.Auth.PrincipalDetails;
import rummage.RummageMarket.Service.NeighborService;
import rummage.RummageMarket.Web.Dto.CMRespDto;

@RestController
public class NeighborApiController {

    @Autowired
    NeighborService neighborService;

    @PostMapping("/api/neighbor/{toUserId}")
    public ResponseEntity<?> neighbor(@AuthenticationPrincipal PrincipalDetails principalDetails,
            @PathVariable int toUserId) {
        neighborService.neighbor(principalDetails.getUser().getId(), toUserId);
        return new ResponseEntity<>(new CMRespDto<>(1, "이웃맺기 성공", null), HttpStatus.OK);
    }

    @DeleteMapping("/api/neighbor/{toUserId}")
    public ResponseEntity<?> unNeighbor(@AuthenticationPrincipal PrincipalDetails principalDetails,
            @PathVariable int toUserId) {
        neighborService.unNeighbor(principalDetails.getUser().getId(), toUserId);
        return new ResponseEntity<>(new CMRespDto<>(1, "이웃취소하기 성공", null), HttpStatus.OK);
    }
}