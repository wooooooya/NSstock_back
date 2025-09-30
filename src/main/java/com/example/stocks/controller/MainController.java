package com.example.stocks.controller;

import com.example.stocks.dto.*;
import com.example.stocks.service.PredictedServiceSer;
import com.example.stocks.service.StockServiceSer;
import com.example.stocks.service.UserService; // UserService import 추가
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity; // ResponseEntity import 추가
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api") // API 경로를 좀 더 명확하게 /api로 변경 (기존 /stock도 가능)
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class MainController {

    // 기존 서비스
    private final StockServiceSer stockServiceSer;
    private final PredictedServiceSer predictedServiceSer;

    // UserService 의존성 주입 추가
    private final UserService userService;

    // GET /api/stock/main
    @GetMapping("/stock/main")
    public MaResDto getMainStockInfo() {
        // (기존 코드와 동일)
        MaResDto kospiDto = stockServiceSer.kospiIndex();
        MaResDto exchangeDto = stockServiceSer.exchangeRate();
        MaResDto oilDto = stockServiceSer.oilPrice();
        MaResDto tradingVolumeDto = stockServiceSer.findTop10ByRecentDateOrderByTradingVolumeDesc();

        return MaResDto.builder()
                .code("SU")
                .message("Success")
                .kospiIndex(isSuccess(kospiDto) ? kospiDto.getKospiIndex() : null)
                .exchangeRate(isSuccess(exchangeDto) ? exchangeDto.getExchangeRate() : null)
                .oilPrice(isSuccess(oilDto) ? oilDto.getOilPrice() : null)
                .tradingVolume(isSuccess(tradingVolumeDto) ? tradingVolumeDto.getTradingVolume() : null)
                .build();
    }

    private boolean isSuccess(MaResDto dto) {
        return dto != null && "SU".equals(dto.getCode());
    }

    // GET or POST /api/stock/prediction
    @RequestMapping(value = "/stock/prediction", method = {RequestMethod.GET, RequestMethod.POST})
    public PreResDto getPredictionInfo(@RequestParam(value = "shortCodeParam", required = false) String shortCodeParam,
                                       @RequestBody(required = false) PreReqDto preReqDtoBody) {
        // (기존 코드와 동일)
        String shortCode = null;

        if (shortCodeParam != null && !shortCodeParam.isBlank()) {
            shortCode = shortCodeParam.trim();
        } else if (preReqDtoBody != null && preReqDtoBody.getShortCode() != null) {
            shortCode = preReqDtoBody.getShortCode().trim();
        }

        if (shortCode == null) {
            return predictedServiceSer.tradingVolumeTop10(new PreReqDto());
        } else {
            PreReqDto dto = new PreReqDto(shortCode);
            return predictedServiceSer.getPredictionByShortCode(dto);
        }
    }

    /**
     * 1. 회원가입
     */
    @PostMapping("/user/signup")
    public ResponseEntity<String> signup(@RequestBody UserSignUpRequestDto requestDto) {
        userService.signUp(requestDto);
        return ResponseEntity.ok("회원가입이 완료되었습니다.");
    }

    /**
     * 5. 로그인
     */
    @PostMapping("/user/login")
    public ResponseEntity<String> login(@RequestBody UserLoginRequestDto requestDto) {
        String result = userService.login(requestDto);
        return ResponseEntity.ok(result); // "로그인 성공!" 메시지 반환
    }

    /**
     * 2. 비밀번호 수정
     * @param loginId 경로 변수로 수정할 사용자의 ID를 받음
     */
    @PatchMapping("/user/{loginId}/password")
    public ResponseEntity<String> updatePassword(@PathVariable String loginId, @RequestBody UserPasswordUpdateRequestDto requestDto) {
        userService.updatePassword(loginId, requestDto);
        return ResponseEntity.ok("비밀번호가 성공적으로 변경되었습니다.");
    }

    /**
     * 3. 회원 탈퇴
     */
    @DeleteMapping("/user/withdraw")
    public ResponseEntity<String> withdraw(@RequestBody UserWithdrawalRequestDto requestDto) {
        userService.withdraw(requestDto);
        return ResponseEntity.ok("회원 탈퇴 처리가 완료되었습니다.");
    }

    /**
     * 4. 즐겨찾기 등록
     * @param loginId 경로 변수로 즐겨찾기를 추가할 사용자의 ID를 받음
     */
    @PostMapping("/user/{loginId}/favorites")
    public ResponseEntity<String> addFavorite(@PathVariable String loginId, @RequestBody FavoriteAddRequestDto requestDto) {
        userService.addFavorite(loginId, requestDto);
        return ResponseEntity.ok("즐겨찾기가 추가되었습니다.");
    }
}