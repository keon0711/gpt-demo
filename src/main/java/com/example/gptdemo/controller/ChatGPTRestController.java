package com.example.gptdemo.controller;


import com.example.gptdemo.dto.request.GPTCompletionChatRequest;
import com.example.gptdemo.dto.request.GPTCompletionRequest;
import com.example.gptdemo.dto.response.CompletionChatResponse;
import com.example.gptdemo.dto.response.CompletionResponse;
import com.example.gptdemo.service.GPTChatRestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chatgpt/rest")
@RequiredArgsConstructor
public class ChatGPTRestController {

  private final GPTChatRestService gptChatRestService;

  @PostMapping("/completion")
  public CompletionResponse completion(final @RequestBody GPTCompletionRequest gptCompletionRequest) {

    return gptChatRestService.completion(gptCompletionRequest);
  }

  @PostMapping("/completion/chat")
  public CompletionChatResponse completionChat(final @RequestBody GPTCompletionChatRequest gptCompletionChatRequest) {

    return gptChatRestService.completionChat(gptCompletionChatRequest);
  }

  @GetMapping("/completion/chat/content")
  public CompletionChatResponse completionChat(final @RequestParam("content") String content) {
    return gptChatRestService.completionChat(content);
  }

}
