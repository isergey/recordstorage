package mongo;

import com.mongodb.BasicDBObject;

import java.util.Date;

public class Record {
    String id;
    String originalId;
    String source;
    String format;
    String schema;
    String content;
    Date createDate;
    Date updateDate;
    String duplicateKey;
    boolean deleted;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOriginalId() {
        return originalId;
    }

    public void setOriginalId(String originalId) {
        this.originalId = originalId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getDuplicateKey() {
        return duplicateKey;
    }

    public void setDuplicateKey(String duplicateKey) {
        this.duplicateKey = duplicateKey;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public BasicDBObject toBasicDBObject() {
        BasicDBObject dbRecord = new BasicDBObject("id", getId()).
                append("originalId", getOriginalId()).
                append("source", getSource()).
                append("format", getFormat()).
                append("schema", getSchema()).
                append("content", getContent()).
                append("createDate", getCreateDate()).
                append("updateDate", getUpdateDate()).
                append("duplicateKey", getDuplicateKey()).
                append("deleted", isDeleted());
        return dbRecord;
    }
}
