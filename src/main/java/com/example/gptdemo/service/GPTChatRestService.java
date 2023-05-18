package com.example.gptdemo.service;

import com.example.gptdemo.dto.request.GPTCompletionChatRequest;
import com.example.gptdemo.dto.request.GPTCompletionRequest;
import com.example.gptdemo.dto.response.CompletionChatResponse;
import com.example.gptdemo.dto.response.CompletionResponse;
import com.example.gptdemo.dto.response.CompletionResponse.Message;
import com.theokanning.openai.completion.CompletionResult;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GPTChatRestService {
  private final OpenAiService openAiService;

  public CompletionResponse completion(final GPTCompletionRequest restRequest) {
    CompletionResult result = openAiService.createCompletion(GPTCompletionRequest.of(restRequest));
    CompletionResponse response = CompletionResponse.of(result);

    List<String> messages = response.getMessages().stream()
        .map(Message::getText)
        .collect(Collectors.toList());

    return response;
  }

  public CompletionChatResponse completionChat(GPTCompletionChatRequest gptCompletionChatRequest) {
    ChatCompletionResult chatCompletion = openAiService.createChatCompletion(
            GPTCompletionChatRequest.of(gptCompletionChatRequest));

    CompletionChatResponse response = CompletionChatResponse.of(chatCompletion);
    System.out.println(response.getMessages().toString());

    return response;
  }

  public CompletionChatResponse completionChat(String content) {
    List<ChatMessage> messages = new ArrayList<>();
    messages.add(new ChatMessage("system", "너는 문자열을 분석해서 json타입으로 변환하는 데이터 분석 프로그램이야. json의 key 값은 'action', 'name', 'amount' 가 있고 'action'의 value는 ['송금', '조회'] 중 한 가지야. 오타가 존재할 수 있으니 유사한 단어로 이해해 줘. 'action'이 '조회' 일 때 'name', 'amount' 는 null을 값으로 하면 돼. 예를 들어 '김건에게 만원 송금해줘' 라는 입력을 받으면 { \"action\" : \"송금\", \"name\" : \"김건\", \"amount\" : 10000} 이렇게 응답하면 돼"));
    messages.add(new ChatMessage("user", content));
    GPTCompletionChatRequest gptCompletionChatRequest = new GPTCompletionChatRequest("gpt-3.5-turbo", messages, 100);
    ChatCompletionResult chatCompletion = openAiService.createChatCompletion(
            GPTCompletionChatRequest.of(gptCompletionChatRequest));

    CompletionChatResponse response = CompletionChatResponse.of(chatCompletion);
    System.out.println(response.getMessages().get(0).getMessage());
    return response;

  }
}
