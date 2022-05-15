package io.github.xmchxup.backend.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;

import javax.persistence.*;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "files")
@Where(clause = "delete_time is null")
public class DBFile extends BaseEntity {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String fileName;

    private String fileType;

    @Lob
    private byte[] data;

    private Long ownerId;

    public DBFile(String fileName, String fileType, Long ownerId) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.ownerId = ownerId;
    }

    @Override
    public String toString() {
        return "DBFile{" +
                "uid='" + id + '\'' +
                ", fileName='" + fileName + '\'' +
                ", fileType='" + fileType + '\'' +
                ", ownerId=" + ownerId +
                '}';
    }
}
