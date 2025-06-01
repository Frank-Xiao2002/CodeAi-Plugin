package top.frankxxj.codeai.plugin.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatSession {
    private String conversationId;
    private MessageType messageType;
    private String content;
}
