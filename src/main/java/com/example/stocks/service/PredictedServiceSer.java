package com.example.stocks.service;

import com.example.stocks.dto.*;
import com.example.stocks.entity.StockPriceEn;
import com.example.stocks.repository.PredictedStockPriceRe;
import com.example.stocks.repository.StockPriceRe;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

// 예측
@Service
@RequiredArgsConstructor
public class PredictedServiceSer {
    private final StockPriceRe stockPriceRe;
    private final PredictedStockPriceRe predictedStockPriceRe;

    public PreResDto tradingVolumeTop10(PreReqDto preReqDto) {
        List<PreResStockListDto> stockList;

        // 종목 코드가 지정된 경우 해당 종목 정보만 조회
        if (preReqDto != null && preReqDto.getShortCode() != null && !preReqDto.getShortCode().isEmpty()) {
            stockList = stockPriceRe.findByRecentDateAndShortCodeDto(preReqDto.getShortCode());
        } else {
            // 없으면 최근 날짜 기준 거래량 상위 10개 종목 조회
            stockList = stockPriceRe.findTop10ByRecentDateOrderByTradingVolumeDesc();
        }

        // 데이터가 없을 경우 응답
        if (stockList.isEmpty()) {
            return PreResDto.builder()
                    .code("NO_DATA")
                    .message("No stock data found")
                    .topStockList(Collections.emptyList())
                    .build();
        }

        // 종목 리스트 DTO 생성
        List<PreResTopDto> resultList = stockList.stream()
                .map(stock -> PreResTopDto.builder()
                        .code(stock.getStockID())
                        .stocks(stock.getStockName())
                        .price(new BigDecimal(stock.getStockPrice()))
                        .build())
                .collect(Collectors.toList());

        // 가장 상위 종목의 상세 데이터 가져오기
        String selectedShortCode = stockList.get(0).getStockID();
        PreReqDto selectedReqDto = new PreReqDto(selectedShortCode);

        // 차트 및 예측 데이터 가져오기
        PreResStockChartDto stockChart = getStockChartData(selectedReqDto);
        PreResRateDto predictedData = getPredictedPriceList(selectedReqDto);

        // 응답 객체 생성
        return PreResDto.builder()
                .code("SU")
                .message("Success")
                .topStockList(resultList)
                .stockChart(stockChart)
                .preRate(predictedData)
                .build();
    }

    // 차트 데이터 (7일, 30일, 1년)
    private PreResStockChartDto getStockChartData(PreReqDto preReqDto) {
        String shortCode = preReqDto.getShortCode();

        // 최근 7일 및 30일 데이터 조회
        List<StockPriceEn> stockPrices7Days = stockPriceRe.findRecentStockPrices(shortCode, 7);
        List<StockPriceEn> stockPrices30Days = stockPriceRe.findRecentStockPrices(shortCode, 30);

        // 날짜순 정렬
        stockPrices7Days.sort(Comparator.comparing(StockPriceEn::getDate));
        stockPrices30Days.sort(Comparator.comparing(StockPriceEn::getDate));

        // --- LocalDate를 사용한 날짜 계산 (개선됨) ---
        LocalDate today = LocalDate.now();
        Date thisMonthStart = Date.from(today.withDayOfMonth(1).atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date oneYearAgo = Date.from(today.withDayOfMonth(1).minusYears(1).atStartOfDay(ZoneId.systemDefault()).toInstant());

        List<Object[]> monthlyAvgData = stockPriceRe.findMonthlyAvgChartData(shortCode, oneYearAgo, thisMonthStart);

        // 하나라도 비어있으면 null 반환
        if (stockPrices7Days.isEmpty() || stockPrices30Days.isEmpty() || monthlyAvgData.isEmpty()) {
            return null;
        }

        SimpleDateFormat dailyFormat = new SimpleDateFormat("yyyy-MM-dd");

        // 1주일 차트 데이터
        List<PreResStockChartDto.StockChart> weekStockCharts = stockPrices7Days.stream()
                .map(sp -> PreResStockChartDto.StockChart.builder()
                        .openingPrice(sp.getOpeningPrice())
                        .closingPrice(sp.getClosingPrice())
                        .highestPrice(sp.getHighestPrice())
                        .lowestPrice(sp.getLowestPrice())
                        .date(dailyFormat.format(sp.getDate()))
                        .build())
                .collect(Collectors.toList());

        // 1달 차트 데이터
        List<PreResStockChartDto.StockChart> monthStockCharts = stockPrices30Days.stream()
                .map(sp -> PreResStockChartDto.StockChart.builder()
                        .openingPrice(sp.getOpeningPrice())
                        .closingPrice(sp.getClosingPrice())
                        .highestPrice(sp.getHighestPrice())
                        .lowestPrice(sp.getLowestPrice())
                        .date(dailyFormat.format(sp.getDate()))
                        .build())
                .collect(Collectors.toList());

        // 1년 차트 데이터 (월별 평균)
        List<PreResStockChartDto.StockChart> yearStockCharts = monthlyAvgData.stream()
                .map(objArr -> PreResStockChartDto.StockChart.builder()
                        .openingPrice(((Number) objArr[1]).longValue())
                        .closingPrice(((Number) objArr[2]).longValue())
                        .highestPrice(((Number) objArr[3]).longValue())
                        .lowestPrice(((Number) objArr[4]).longValue())
                        .date((String) objArr[0])
                        .build())
                .collect(Collectors.toList());

        // 요약 정보 세팅
        StockPriceEn firstWeek = stockPrices7Days.get(0);
        StockPriceEn firstMonth = stockPrices30Days.get(0);
        Object[] firstMonthData = monthlyAvgData.get(0);

        PreResStockChartDto.ChartSummary weekChartSummary = PreResStockChartDto.ChartSummary.builder()
                .openingPrice(firstWeek.getOpeningPrice())
                .closingPrice(firstWeek.getClosingPrice())
                .highestPrice(firstWeek.getHighestPrice())
                .lowestPrice(firstWeek.getLowestPrice())
                .stockCharts(weekStockCharts)
                .build();

        PreResStockChartDto.ChartSummary monthChartSummary = PreResStockChartDto.ChartSummary.builder()
                .openingPrice(firstMonth.getOpeningPrice())
                .closingPrice(firstMonth.getClosingPrice())
                .highestPrice(firstMonth.getHighestPrice())
                .lowestPrice(firstMonth.getLowestPrice())
                .stockCharts(monthStockCharts)
                .build();

        PreResStockChartDto.ChartSummary yearChartSummary = PreResStockChartDto.ChartSummary.builder()
                .openingPrice(((Number) firstMonthData[1]).longValue())
                .closingPrice(((Number) firstMonthData[2]).longValue())
                .highestPrice(((Number) firstMonthData[3]).longValue())
                .lowestPrice(((Number) firstMonthData[4]).longValue())
                .stockCharts(yearStockCharts)
                .build();

        return PreResStockChartDto.builder()
                .shortCode(shortCode)
                .weekChart(weekChartSummary)
                .monthChart(monthChartSummary)
                .yearChart(yearChartSummary)
                .build();
    }

    // 예측 데이터
    private PreResRateDto getPredictedPriceList(PreReqDto preReqDto) {
        String shortCode = preReqDto.getShortCode();
        List<PreResPredictedDto> predictedList = predictedStockPriceRe.findLatestSevenPredictionsByShortCode(shortCode);

        // --- 불필요한 null 체크 제거 (개선됨) ---
        return PreResRateDto.builder()
                .previousList(predictedList.isEmpty() ? null : predictedList)
                .build();
    }

    // request 처리
    public PreResDto getPredictionByShortCode(PreReqDto preReqDto) {
        // 종목 코드가 없으면 거래량 상위 10개 처리
        if (preReqDto == null || preReqDto.getShortCode() == null || preReqDto.getShortCode().isEmpty()) {
            return tradingVolumeTop10(new PreReqDto());
        }

        // 종목 정보 조회
        List<PreResStockListDto> stockList = stockPriceRe.findByRecentDateAndShortCodeDto(preReqDto.getShortCode());

        if (stockList.isEmpty()) {
            return PreResDto.builder()
                    .code("NO_DATA")
                    .message("No stock data found for shortCode: " + preReqDto.getShortCode())
                    .topStockList(Collections.emptyList())
                    .build();
        }

        // 종목 요약 정보 설정
        PreResStockListDto stock = stockList.get(0);
        List<PreResTopDto> resultList = Collections.singletonList(
                PreResTopDto.builder()
                        .code(stock.getStockID())
                        .stocks(stock.getStockName())
                        .price(new BigDecimal(stock.getStockPrice()))
                        .build()
        );

        // 차트 및 예측 데이터 조회
        PreResStockChartDto stockChart = getStockChartData(preReqDto);
        PreResRateDto predictedData = getPredictedPriceList(preReqDto);

        return PreResDto.builder()
                .code("SU")
                .message("Success")
                .topStockList(resultList)
                .stockChart(stockChart)
                .preRate(predictedData)
                .build();
    }
}