package top.frankxxj.codeai.plugin.entity.dto;

import top.frankxxj.codeai.plugin.entity.MessageType;

public record HistoryRequestDTO(MessageType messageType,
                                String text) {
}
