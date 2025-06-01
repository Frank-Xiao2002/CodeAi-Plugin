package top.frankxxj.codeai.plugin.entity.dto;

import top.frankxxj.codeai.plugin.entity.EditorFile;

import java.util.List;

public record ChatDTO(String userPrompt,
                      String conversationId,
                      String snippet,
                      String lang,
                      List<EditorFile> files
) {
}
