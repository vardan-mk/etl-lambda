package am.vardanmk.etllambda.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.io.StringWriter;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Table(value = "notes")
public class Notes {
    @Id
    private long noteId;

    private String userEmail;

    private String title;

    private String note;

    private LocalDateTime createTime;
    private LocalDateTime lastUpdateTime;

    @Override
    public String toString() {

        StringWriter sw = new StringWriter();
        sw.append(String.valueOf(noteId));
        sw.append(",");
        sw.append(userEmail);
        sw.append(",");
        sw.append(title);
        sw.append(",");
        sw.append(note);
        sw.append(",");
        sw.append(String.valueOf(createTime));
        sw.append(",");
        sw.append(String.valueOf(lastUpdateTime));
        return sw.toString();
//        return StringWriter(noteId + "," + userEmail + "," + title + "," + note + "," + createTime + "," + lastUpdateTime);
    }
}
