package com.example.stocks.controller;

import com.example.stocks.dto.MaResDto;
import com.example.stocks.dto.PreReqDto;
import com.example.stocks.dto.PreResDto;
import com.example.stocks.service.PredictedServiceSer;
import com.example.stocks.service.StockServiceSer;
import org.springframework.web.bind.annotation.CrossOrigin;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stock")  // 모든 URL 앞에 /stock 붙음
@RequiredArgsConstructor   // final 필드를 자동으로 생성자 주입
@CrossOrigin(origins = "*")
public class MainController {

    private final StockServiceSer stockServiceSer; // 메인 의존성
    private final PredictedServiceSer predictedServiceSer; // 예측 의존성

    // GET /stock/main
    @GetMapping("/main")
    public MaResDto getMainStockInfo() {
        MaResDto kospiDto = stockServiceSer.kospiIndex(); // 코스피 지수 데이터 조회
        MaResDto exchangeDto = stockServiceSer.exchangeRate(); // 환율 데이터 조회
        MaResDto oilDto = stockServiceSer.oilPrice(); // 유가 데이터 조회
        MaResDto tradingVolumeDto = stockServiceSer.tradingVolumeTop10(); // 거래량 상위 10개 종목 데이터 조회

        // 각 데이터가 정상 조회되었는지 확인 후 값 할당, 실패 시 null 처리
        return MaResDto.builder()
                .code("SU")  // 성공 코드 설정
                .message("Success")  // 성공 메시지 설정
                .kospiIndex(isSuccess(kospiDto) ? kospiDto.getKospiIndex() : null)
                .exchangeRate(isSuccess(exchangeDto) ? exchangeDto.getExchangeRate() : null)
                .oilPrice(isSuccess(oilDto) ? oilDto.getOilPrice() : null)
                .tradingVolume(isSuccess(tradingVolumeDto) ? tradingVolumeDto.getTradingVolume() : null)
                .build();
    }

    // MaResDto가 성공 상태인지 판단하는 헬퍼 메서드
    private boolean isSuccess(MaResDto dto) {
        return dto != null && "SU".equals(dto.getCode());
    }

    //GET  or POST /stock/prediction
    @RequestMapping(value = "/prediction", method = {RequestMethod.GET, RequestMethod.POST})
    public PreResDto getPredictionInfo(@RequestParam(value = "shortCodeParam",required = false) String shortCodeParam,
                                       @RequestBody(required = false) PreReqDto preReqDtoBody) {

        String shortCode = null;

        // GET 요청 쿼리 파라미터에 shortCode 있으면 사용
        if (shortCodeParam != null && !shortCodeParam.isBlank()) {
            shortCode = shortCodeParam.trim();
        }
        // POST 요청 바디에 shortCode 있으면 사용
        else if (preReqDtoBody != null && preReqDtoBody.getShortCode() != null) {
            shortCode = preReqDtoBody.getShortCode().trim();
        }

        // shortCode 없으면 거래량 상위 10개 예측 데이터 반환
        if (shortCode == null) {
            return predictedServiceSer.tradingVolumeTop10(new PreReqDto());
        }
        // shortCode 있으면 해당 종목 예측 데이터 반환
        else {
            PreReqDto dto = new PreReqDto(shortCode);
            return predictedServiceSer.getPredictionByShortCode(dto);
        }
    }
}