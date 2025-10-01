package com.example.stocks.service;

import com.example.stocks.dto.*;
//import com.example.stocks.entity.ExchangeRateEn;
import com.example.stocks.entity.KospiIndexEn;
import com.example.stocks.entity.OilPriceEn;
import com.example.stocks.entity.enumeration.OilType;
//import com.example.stocks.repository.ExchangeRateRe;
import com.example.stocks.repository.KospiIndexRe;
import com.example.stocks.repository.OilPriceRe;
import com.example.stocks.repository.StockPriceRe;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StockServiceSer {
    private final KospiIndexRe kospiIndexRe; // 코스피 지수 Repository
//    private final ExchangeRateRe exchangePriceRe; // 환율 Repository
    private final OilPriceRe oilPriceRe; // 유가 Repository
    private final StockPriceRe stockPriceRe; // 주식 거래량 Repository

    // 코스피 지수 데이터 처리 메서드
    public MaResDto kospiIndex() {
        List<KospiIndexEn> indexList = kospiIndexRe.findLatest15Days(); // 최근 15일간의 코스피 지수 조회
        indexList.sort(Comparator.comparing(KospiIndexEn::getDate)); // 날짜 기준 오름차순 정렬

        // 차트용 DTO 리스트 생성
        List<MaResKospiIndexDto.KospiChart> kospiChartList = indexList.stream()
                .map(item -> MaResKospiIndexDto.KospiChart.builder()
                        .kChat(item.getClosingPrice().longValue())
                        .kDate(item.getDate().toString())
                        .build())
                .toList();

        if (indexList.size() < 3) return null; // 데이터가 3개 미만일 경우 null 반환

        // 전날, 전전날 데이터 추출
        KospiIndexEn yesterday = indexList.get(indexList.size() - 2);
        KospiIndexEn dayBefore = indexList.get(indexList.size() - 3);

        // 가격 및 변화율 계산
        BigDecimal yesterdayPrice = yesterday.getClosingPrice();
        BigDecimal dayBeforePrice = dayBefore.getClosingPrice();
        BigDecimal diff = yesterdayPrice.subtract(dayBeforePrice);
        String indecreaseFormatted = (diff.signum() >= 0 ? "+" : "") + diff.longValue();

        double percentage = dayBeforePrice.compareTo(BigDecimal.ZERO) == 0
                ? 0.0
                : diff.divide(dayBeforePrice, 4, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100)).doubleValue();

        String percentageFormatted = String.format("%+.2f", percentage);

        // 전일 종가 DTO 생성
        MaResDto.PreviousClose previousClose = MaResDto.PreviousClose.builder()
                .price(yesterdayPrice.longValue())
                .indecrease(Long.parseLong(indecreaseFormatted))
                .percentage(Double.parseDouble(percentageFormatted))
                .build();

        // 코스피 DTO 생성
        MaResKospiIndexDto kospiDto = MaResKospiIndexDto.builder()
                .kospiPreviousClose(previousClose)
                .kospiChat(kospiChartList)
                .build();

        // 최종 응답 DTO 반환
        return MaResDto.builder()
                .code("SU")
                .message("Success")
                .kospiIndex(kospiDto)
                .build();
    }

    // 환율 데이터 처리 메서드
//    public MaResDto exchangeRate() {
//        String currency = "미국 달러 (USD)";
//
//        // 해당 통화의 최근 15일 데이터 조회
//        List<ExchangeRateEn> rateList = exchangePriceRe.findLatest15DaysByCurrency(currency);
//
//        if (rateList == null || rateList.size() < 2) {
//            return MaResDto.builder()
//                    .code("ER")
//                    .message("Not enough data for exchange rate")
//                    .build();
//        }
//
//        rateList.sort(Comparator.comparing(ExchangeRateEn::getDate));
//
//        // 차트용 DTO 리스트 생성
//        List<MaResExchangeRateDto.ExchangeChart> exchangeChartList = rateList.stream()
//                .map(data -> MaResExchangeRateDto.ExchangeChart.builder()
//                        .eDate(data.getDate().toString())
//                        .eChat(data.getClosingPrice().longValue())
//                        .build())
//                .toList();
//
//        // 전일과 전전일 비교
//        ExchangeRateEn yesterday = rateList.get(rateList.size() - 1);
//        ExchangeRateEn dayBefore = rateList.get(rateList.size() - 2);
//
//        BigDecimal yClose = yesterday.getClosingPrice();
//        BigDecimal dClose = dayBefore.getClosingPrice();
//        BigDecimal diff = yClose.subtract(dClose);
//        long indecrease = diff.longValue();
//
//        double percentage = dClose.compareTo(BigDecimal.ZERO) == 0
//                ? 0.0
//                : diff.divide(dClose, 4, RoundingMode.HALF_UP)
//                .multiply(BigDecimal.valueOf(100)).doubleValue();
//
//        // 전일 환율 DTO
//        MaResDto.PreviousClose previousClose = MaResDto.PreviousClose.builder()
//                .price(yClose.longValue())
//                .indecrease(indecrease)
//                .percentage(percentage)
//                .build();
//
//        // 환율 DTO 생성
//        MaResExchangeRateDto exchangeRateDto = MaResExchangeRateDto.builder()
//                .exchangePreviousClose(previousClose)
//                .exchangeChat(exchangeChartList)
//                .build();
//
//        // 최종 응답 반환
//        return MaResDto.builder()
//                .code("SU")
//                .message("Success")
//                .exchangeRate(exchangeRateDto)
//                .build();
//    }

    // 유가 데이터 처리 메서드
    public MaResDto oilPrice() {
        List<MaResOilPriceDto.OilTypeData> oilTypeDataList = new ArrayList<>();

        // 모든 유종에 대해 반복
        for (OilType oilType : OilType.values()) {
            List<OilPriceEn> oilList = oilPriceRe.findLatest15DaysByOilType(oilType.name());

            if (oilList.size() < 2) continue; // 데이터 부족 시 건너뜀

            oilList.sort(Comparator.comparing(OilPriceEn::getDate));

            // 차트용 DTO 리스트 생성
            List<MaResOilPriceDto.OilChart> oilCharts = oilList.stream()
                    .map(item -> MaResOilPriceDto.OilChart.builder()
                            .oDate(item.getDate().toString())
                            .oChat(item.getAveragePriceCompetition().longValue())
                            .build())
                    .toList();

            // 전일, 전전일 비교
            OilPriceEn yesterday = oilList.get(oilList.size() - 1);
            OilPriceEn dayBefore = oilList.get(oilList.size() - 2);

            BigDecimal yPrice = yesterday.getAveragePriceCompetition();
            BigDecimal dPrice = dayBefore.getAveragePriceCompetition();
            BigDecimal diff = yPrice.subtract(dPrice);
            long indecrease = diff.longValue();

            double percentage = dPrice.compareTo(BigDecimal.ZERO) == 0
                    ? 0.0
                    : diff.divide(dPrice, 4, RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(100)).doubleValue();

            // 전일 유가 DTO
            MaResDto.PreviousClose previousClose = MaResDto.PreviousClose.builder()
                    .price(yPrice.longValue())
                    .indecrease(indecrease)
                    .percentage(percentage)
                    .build();

            // 유가 DTO 생성
            MaResOilPriceDto.OilTypeData typeData = MaResOilPriceDto.OilTypeData.builder()
                    .oilType(oilType.name())
                    .oilChat(oilCharts)
                    .oilPreviousClose(previousClose)
                    .build();

            oilTypeDataList.add(typeData);
        }

        MaResOilPriceDto oilDto = MaResOilPriceDto.builder()
                .oilTypes(oilTypeDataList)
                .build();

        return MaResDto.builder()
                .code("SU")
                .message("Success")
                .oilPrice(oilDto)
                .build();
    }
    public MaResDto findTop10ByRecentDateOrderByTradingVolumeDesc() {
        // 1. Repository에서 가격 변동 데이터까지 모두 포함된 리스트를 가져옵니다.
        List<PreResStockListDto> recentList = stockPriceRe.findTop10ByRecentDateOrderByTradingVolumeDesc();

        // 2. 최종 DTO 리스트로 간단하게 변환합니다. (계산 로직이 필요 없어졌습니다.)
        List<MaResTradingVolumeDto> resultList = recentList.stream()
                .map(recentStock -> MaResTradingVolumeDto.builder()
                        .stocks(recentStock.getStockName())
                        .volume(recentStock.getVolume())
                        .volumeIndecrease(recentStock.getPriceChange()) // DB의 price_change 값을 사용
                        .volumePercentage(recentStock.getPriceChangeRate()) // DB의 price_change_rate 값을 사용
                        .build())
                .toList();

        return MaResDto.builder()
                .code("SU")
                .message("Success")
                .tradingVolume(resultList)
                .build();
    }
}