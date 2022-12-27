package com.example.back.controller;

import com.example.back.pojo.Tickets;
import com.example.back.result.Result;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.example.back.result.ResultFactory;
import com.example.back.service.CommonQuestionsService;
import com.example.back.service.ExecutorListService;
import com.example.back.service.RecentPostsService;
import com.example.back.service.TicketsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
public class StationController {
    @Autowired
    CommonQuestionsService commonQuestionsService;
    @Autowired
    ExecutorListService executorListService;
    @Autowired
    RecentPostsService recentPostsService;
    @Autowired
    TicketsService ticketsService;

    @GetMapping("/api/common_questions")
    @ResponseBody
    public Result listCommonQuestions() {
        return ResultFactory.buildSuccessResult(commonQuestionsService.list());
    }

    @GetMapping("/api/executor_list")
    @ResponseBody
    public Result listExecutorList() {
        return ResultFactory.buildSuccessResult(executorListService.list());
    }

    @GetMapping("/api/recent_posts")
    @ResponseBody
    public Result listRecentPosts() {
        return ResultFactory.buildSuccessResult(recentPostsService.list());
    }

    @GetMapping("/api/tickets")
    @ResponseBody
    public Result listTickets(@RequestParam("startStation") String startStation,
                              @RequestParam("endStation") String endStation,
                              @RequestParam("isQuick") boolean isQuick
//                              @RequestParam("pageNum") int pageNum,
//                              @RequestParam("pageSize") int pageSize
                              ) {
        List<Tickets> tickets = ticketsService.listByStation(startStation, endStation, isQuick);
//        Page<Tickets> tickets = ticketsService.listByStationAndPage(pageNum,pageSize,startStation, endStation, isQuick);
//        return ResultFactory.buildSuccessResult(tickets);
           return ResultFactory.buildSuccessResult(ticketsService.listByStation(startStation, endStation, isQuick));
    }

    @GetMapping("/api/search")
    @ResponseBody
    public Result search(@RequestParam("wd") String word) {
        List<Map<String, String>> all = commonQuestionsService.search(word);
        all.addAll(recentPostsService.search(word));
        return ResultFactory.buildSuccessResult(all);
    }
}
