package top.frankxxj.codeai.plugin.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class EditorFile {
    private String filename;
    private String content;
}
